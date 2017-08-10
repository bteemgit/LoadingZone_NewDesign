package com.example.admin.loadingzone.modules.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.modules.home.PostedJobListAdapter;
import com.example.admin.loadingzone.modules.myjob.MyJobListAdapter;
import com.example.admin.loadingzone.modules.myqutation.QutationDetailsActivity;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.retrofit.model.PendingJobResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by admin on 6/25/2017.
 */

public class JobCompletedFragment  extends Fragment {

    public JobCompletedFragment() {
        // Required empty public constructor
    }
    @NonNull
    @BindView(R.id.recyclerViewJobList)
    EndlessRecyclerView recyclerViewPqutation;
    @NonNull
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @NonNull
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBarFooter;
    @BindView(R.id.root)
    RelativeLayout relativeLayoutRoot;
    private MyJobListAdapter postedJobListAdapter;
    private ProgressDialog pDialog;
    private ApiInterface service;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private List<JobList> jobList = new ArrayList<>();
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {

       getompletedJobs();
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };
   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myjob, container, false);
        ButterKnife.bind(this, view);
        setUpListeners();
        pDialog = new ProgressDialog(getActivity());
        service = ApiClient.getClient().create(ApiInterface.class);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
// Recyclerview Layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPqutation.setLayoutManager(layoutManager);
        recyclerViewPqutation.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     getompletedJobs();

    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPqutation.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshLayout.setRefreshing(true);
                offset = 1;
   getompletedJobs();
            }
        });

        recyclerViewPqutation.addPaginationListener(paginationListener);
        recyclerViewPqutation.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }
        }));

    }
    // Getting the job posted by the customer
    public void getompletedJobs
    () {

        if (offset == 1) {
            pDialog.setMessage("loading");
            pDialog.show();
        } else {
            progressBarFooter.setVisibility(View.VISIBLE);
        }
        service = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getActivity(), "acess_token");
        Call<PendingJobResponse> call = service.CompletedJobList(GloablMethods.API_HEADER + acess_token,offset);
        call.enqueue(new Callback<PendingJobResponse>() {
            @Override
            public void onResponse(Call<PendingJobResponse> call, retrofit2.Response<PendingJobResponse> response) {
                refreshLayout.setRefreshing(false);
                pDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getJobList().isEmpty()) {
                        List<JobList> JobList = response.body().getJobList();
                        if (offset == 1) {
                            jobList = JobList;
                            updateEndlessRecyclerView();
                            pDialog.dismiss();
                        } else {
                            progressBarFooter.setVisibility(View.VISIBLE);
                            for (JobList itemModel : JobList) {
                                jobList.add(itemModel);
                            }
                        }
                        if (JobList.size() < limit) {
                            recyclerViewPqutation.setHaveMoreItem(false);
                        } else {
                            recyclerViewPqutation.setHaveMoreItem(true);
                        }
                        postedJobListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        recyclerViewPqutation.setHaveMoreItem(false);
                    }

                } else {
                    getActivity().finish();
                    recyclerViewPqutation.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    //showSnakBar(relativeLayoutRoot, MessageConstants.SERVERERROR);
                }
                progressBarFooter.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PendingJobResponse> call, Throwable t) {
                // Log error here since request failed
                pDialog.dismiss();
            }
        });
    }

    private void updateEndlessRecyclerView() {
        postedJobListAdapter = new MyJobListAdapter(jobList, R.layout.item_home_postedjob, getContext());
        recyclerViewPqutation.setAdapter(postedJobListAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}