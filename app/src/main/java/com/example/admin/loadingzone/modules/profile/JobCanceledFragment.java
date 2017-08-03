package com.example.admin.loadingzone.modules.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.admin.loadingzone.modules.myjob.MyJobListAdapter;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.CancelJobListResponse;
import com.example.admin.loadingzone.retrofit.model.CancelledJob;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.retrofit.model.PendingJobResponse;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
public class JobCanceledFragment extends Fragment {

    public JobCanceledFragment() {
        // Required empty public constructor
    }
    @NonNull
    @BindView(R.id.recyclerViewCanceledJobList)
    EndlessRecyclerView recyclerViewCanceledJobList;
    @NonNull
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @NonNull
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBarFooter;
    @BindView(R.id.root)
    RelativeLayout relativeLayoutRoot;
    private CanceledJobListAdapter canceledJobListAdapter;
    private ProgressDialog pDialog;
    private ApiInterface service;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private List<CancelledJob> joblistCanceled = new ArrayList<>();
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {

            getCanceledJob();
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canceled_job, container, false);
        ButterKnife.bind(this, view);
        setUpListeners();
        pDialog = new ProgressDialog(getActivity());
        service = ApiClient.getClient().create(ApiInterface.class);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
// Recyclerview Layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCanceledJobList.setLayoutManager(layoutManager);
        recyclerViewCanceledJobList.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCanceledJob();

    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCanceledJobList.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshLayout.setRefreshing(true);
                offset = 1;
                getCanceledJob();
            }
        });

        recyclerViewCanceledJobList.addPaginationListener(paginationListener);
        recyclerViewCanceledJobList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }
        }));

    }

    // Getting the job cancled by the customer or provider
    public void getCanceledJob
    () {

        if (offset == 1) {
            pDialog.setMessage("loading");
            pDialog.show();
        } else {
            progressBarFooter.setVisibility(View.VISIBLE);
        }
        service = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getActivity(), "acess_token");
        Call<CancelJobListResponse> call = service.CanceledJobList(GloablMethods.API_HEADER + acess_token,offset);
        call.enqueue(new Callback<CancelJobListResponse>() {
            @Override
            public void onResponse(Call<CancelJobListResponse> call, retrofit2.Response<CancelJobListResponse> response) {
                refreshLayout.setRefreshing(false);
                pDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getCancelledJobs().isEmpty()) {
                        List<CancelledJob> cancelledJobList = response.body().getCancelledJobs();
                        if (offset == 1) {
                            joblistCanceled = cancelledJobList;
                            updateEndlessRecyclerView();
                            pDialog.dismiss();
                        } else {
                            progressBarFooter.setVisibility(View.VISIBLE);
                            for (CancelledJob itemModel : cancelledJobList) {
                                joblistCanceled.add(itemModel);
                            }
                        }
                        if (cancelledJobList.size() < limit) {
                            recyclerViewCanceledJobList.setHaveMoreItem(false);
                        } else {
                            recyclerViewCanceledJobList.setHaveMoreItem(true);
                        }
                        canceledJobListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        recyclerViewCanceledJobList.setHaveMoreItem(false);
                    }

                } else {
                    getActivity().finish();
                    recyclerViewCanceledJobList.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {

                }
                progressBarFooter.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CancelJobListResponse> call, Throwable t) {
                // Log error here since request failed
                pDialog.dismiss();
            }
        });
    }

    private void updateEndlessRecyclerView() {
        canceledJobListAdapter = new CanceledJobListAdapter(joblistCanceled, R.layout.item_job_canceled, getContext());
        recyclerViewCanceledJobList.setAdapter(canceledJobListAdapter);
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