package com.example.admin.loadingzone.modules.myjob;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.modules.home.PostedJobDetailsActivity;
import com.example.admin.loadingzone.modules.home.PostedJobListAdapter;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.retrofit.model.PendingJobResponse;
import com.example.admin.loadingzone.retrofit.model.PostedJobResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by admin on 5/16/2017.
 */

public class JobPendingFragment extends Fragment {

    public JobPendingFragment() {
        // Required empty public constructor
    }

    @NonNull
    @BindView(R.id.recyclerViewJobList)
    EndlessRecyclerView endlessRecyclerViewPostedJob;
    @NonNull
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @NonNull
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBarFooter;
    @BindView(R.id.root)
    RelativeLayout relativeLayoutRoot;
    private ProgressDialog pDialog;
    private MyJobListAdapter postedJobListAdapter;
    private List<JobList> jobList = new ArrayList<>();
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private ApiInterface apiService;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {

            getJobPosted();

        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myjob, container, false);
        ButterKnife.bind(this, view);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        pDialog = new ProgressDialog(getActivity());
        refreshLayout.setRefreshing(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerViewPostedJob.setLayoutManager(layoutManager);
        endlessRecyclerViewPostedJob.setHasFixedSize(true);
        setUpListeners();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getJobPosted();
    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerViewPostedJob.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                getJobPosted();
            }
        });

        endlessRecyclerViewPostedJob.addPaginationListener(paginationListener);
        endlessRecyclerViewPostedJob.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent i = new Intent(getActivity(), PostedJobDetailsActivity.class);
                String JobId = jobList.get(position).getJobId();
                String name = jobList.get(position).getCustomer().getName();
                String email = jobList.get(position).getCustomer().getEmail();
                String phone1 = jobList.get(position).getCustomer().getPhone1();
                String profilepic = jobList.get(position).getCustomer().getProfilePic();
                String FromLoc_latt = jobList.get(position).getFromLocation().getLatitude();
                String FromLoc_long = jobList.get(position).getFromLocation().getLongitude();
                String FromLoc_name = jobList.get(position).getFromLocation().getName();
                String ToLoc_latt = jobList.get(position).getToLocation().getLatitude();
                String ToLoc_long = jobList.get(position).getToLocation().getLongitude();
                String ToLoc_name = jobList.get(position).getToLocation().getName();
                String Material_name = jobList.get(position).getMaterial().getMaterialName();
                Integer Material_id = jobList.get(position).getMaterial().getMaterialId();
                String MaterialDescription = jobList.get(position).getMaterialDescription();
                String weight = String.valueOf(jobList.get(position).getWeight());
                String DateOfLoading = jobList.get(position).getDateOfLoading();
                String PaymentType_name = jobList.get(position).getPaymentType().getPaymentTypeName();
                Integer PaymentType_id = jobList.get(position).getPaymentType().getPaymentTypeId();
                String TruckType_name = jobList.get(position).getTruckType().getTruckTypeName();
                String TruckType_id = jobList.get(position).getTruckType().getTruckTypeId();
                String TruckSize_dimension = jobList.get(position).getTruckSize().getTruckSizeDimension();
                Integer TruckSize_id = jobList.get(position).getTruckSize().getTruckSizeId();
                String Currency_name = jobList.get(position).getCurrency().getCurrencyName();
                String LocationDistance = String.valueOf(jobList.get(position).getLocationDistance());
                String DateRequested = jobList.get(position).getDateRequested();
                String DateRequestedRelative = jobList.get(position).getDateRequestedRelative();
                String Budget = jobList.get(position).getBudget();
                String QuotationCount = jobList.get(position).getQuotationCount();
                String HasActiveQuotations = jobList.get(position).getHasActiveQuotations();
                String JobStatus = jobList.get(position).getJobStatus().getName();
                i.putExtra("isFrom", "Pendingjob");
                i.putExtra("JobId", JobId);
                i.putExtra("name", name);
                i.putExtra("email", email);
                i.putExtra("phone1", phone1);
                i.putExtra("profilepic", profilepic);
                i.putExtra("FromLoc_latt", FromLoc_latt);
                i.putExtra("FromLoc_long", FromLoc_long);
                i.putExtra("FromLoc_name", FromLoc_name);
                i.putExtra("ToLoc_latt", ToLoc_latt);
                i.putExtra("ToLoc_long", ToLoc_long);
                i.putExtra("ToLoc_name", ToLoc_name);
                i.putExtra("Material_name", Material_name);
                i.putExtra("Material_id", Material_id);
                i.putExtra("MaterialDescription", MaterialDescription);
                i.putExtra("weight", weight);
                i.putExtra("DateOfLoading", DateOfLoading);
                i.putExtra("PaymentType_name", PaymentType_name);
                i.putExtra("PaymentType_id", PaymentType_id);
                i.putExtra("TruckType_name", TruckType_name);
                i.putExtra("TruckType_id", TruckType_id);
                i.putExtra("TruckSize_id", TruckSize_id);
                i.putExtra("TruckSize_dimension", TruckSize_dimension);
                i.putExtra("Currency_name", Currency_name);
                i.putExtra("LocationDistance", LocationDistance);
                i.putExtra("DateRequested", DateRequested);
                i.putExtra("DateRequestedRelative", DateRequestedRelative);
                i.putExtra("Budget", Budget);
                i.putExtra("QuotationCount", QuotationCount);
                i.putExtra("HasActiveQuotations", HasActiveQuotations);
                i.putExtra("JobStatus", JobStatus);
                startActivity(i);

            }
        }));

    }

    // Getting the job posted by the customer
    public void getJobPosted
    () {

        if (offset == 1) {
            pDialog.setMessage("loading");
            pDialog.show();
        } else {
            progressBarFooter.setVisibility(View.VISIBLE);
        }
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getActivity(), "acess_token");
        String service_provider_id = AppController.getString(getActivity(), "service_provider_id");
        Call<PendingJobResponse> call = apiService.PendingJobList(GloablMethods.API_HEADER + acess_token, service_provider_id, offset);
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
                            endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewPostedJob.setHaveMoreItem(true);
                        }
                        postedJobListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                    }

                } else {
                    getActivity().finish();
                    endlessRecyclerViewPostedJob.setHaveMoreItem(false);
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

    private void updateEndlessRecyclerView() {
        postedJobListAdapter = new MyJobListAdapter(jobList, R.layout.item_home_postedjob, getContext());
        endlessRecyclerViewPostedJob.setAdapter(postedJobListAdapter);
    }
}