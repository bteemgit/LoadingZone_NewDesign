package com.example.admin.loadingzone.modules.home;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.admin.loadingzone.global.SessionManager;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.retrofit.model.PostedJobResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by admin on 9/28/2017.
 */

public class HomeFragment  extends Fragment {
    @BindView(R.id.recyclerViewHomePostedJob)
    EndlessRecyclerView endlessRecyclerViewPostedJob;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    private PostedJobListAdapter postedJobListAdapter;
    private List<JobList> jobList = new ArrayList<>();
    private ApiInterface apiService;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private boolean isSwipeRefreshed = false;
    private HomeActivity homeActivity;
    String profilepic = "01";
    // endless scroll hamdler
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            if (homeActivity.isConnectingToInternet(getActivity())) {
                getJobPosted();
            } else {
                homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
            }
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }


    };

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity=(HomeActivity)getActivity();
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setRefreshing(false);
        setUpListeners();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        profilepic = AppController.getString(getContext(), "provider_pic");
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (homeActivity.isConnectingToInternet(getActivity())) {
            getJobPosted();
        } else {
            homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }

    }
    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        endlessRecyclerViewPostedJob.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                isSwipeRefreshed = true;
                getJobPosted();
            }
        });

        endlessRecyclerViewPostedJob.addPaginationListener(paginationListener);
        endlessRecyclerViewPostedJob.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent i = new Intent(getActivity(), PostedJobDetailsActivity.class);
                String JobId = jobList.get(position).getJobId();
                String job_code = jobList.get(position).getJob_code();
                String job_date = jobList.get(position).getLoadingDate();
                String job_time = jobList.get(position).getLoadingTime();
                String name = jobList.get(position).getCustomer().getName();
                String email = jobList.get(position).getCustomer().getEmail();
                String phone1 = jobList.get(position).getCustomer().getPhone1();
                profilepic = jobList.get(position).getCustomer().getProfilePic();
                String FromLoc_latt = jobList.get(position).getFromLocation().getLatitude();
                String FromLoc_long = jobList.get(position).getFromLocation().getLongitude();
                String FromLoc_name = jobList.get(position).getFromLocation().getName();
                String ToLoc_latt = jobList.get(position).getToLocation().getLatitude();
                String ToLoc_long = jobList.get(position).getToLocation().getLongitude();
                String ToLoc_name = jobList.get(position).getToLocation().getName();
                String Material_name = jobList.get(position).getMaterial().getMaterialName();
                Integer Material_id = jobList.get(position).getMaterial().getMaterialId();
                String MaterialDescription = jobList.get(position).getMaterialDescription();
                String weight = jobList.get(position).getMaterial_weight().getMaterialWeightText();
                String DateOfLoading = jobList.get(position).getPreferred_loading_date() + " " + jobList.get(position).getPreferred_loading_time();
                String PrefferedLoadingDate = jobList.get(position).getPreferred_loading_date();
                String PrefferedLoadingTime = jobList.get(position).getPreferred_loading_time();
                String PaymentType_name = jobList.get(position).getPaymentType().getPaymentTypeName();
                Integer PaymentType_id = jobList.get(position).getPaymentType().getPaymentTypeId();
                String TruckType_name = jobList.get(position).getTruckType().getTruckTypeName();
                String TruckType_id = jobList.get(position).getTruckType().getTruckTypeId();
                String TruckSize_dimension = jobList.get(position).getTruckSize().getTruckSizeDimension();
                Integer TruckSize_id = jobList.get(position).getTruckSize().getTruckSizeId();
                String LocationDistance = String.valueOf(jobList.get(position).getOrigin_destination_distance());
                String DateRequested = jobList.get(position).getDateRequested();
                String DateRequestedRelative = jobList.get(position).getDateRequestedRelative();
                String QuotationCount = jobList.get(position).getQuotationCount();
                String HasActiveQuotations = jobList.get(position).getHasActiveQuotations();
                String JobStatus = jobList.get(position).getJobStatus().getName();
                String job_status_code = jobList.get(position).getJobStatus().getCode();
                //String truckCust_name = jobList.get(position).getT().getCode();
                i.putExtra("isFrom", "Home");
                i.putExtra("job_date", job_date);
                i.putExtra("job_time", job_time);
                i.putExtra("JobId", JobId);
                i.putExtra("job_code", job_code);
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
                i.putExtra("job_status_code", job_status_code);
                i.putExtra("LocationDistance", LocationDistance);
                i.putExtra("DateRequested", DateRequested);
                i.putExtra("DateRequestedRelative", DateRequestedRelative);
                i.putExtra("QuotationCount", QuotationCount);
                i.putExtra("HasActiveQuotations", HasActiveQuotations);
                i.putExtra("JobStatus", JobStatus);
                i.putExtra("PrefferedLoadingDate", PrefferedLoadingDate);
                i.putExtra("PrefferedLoadingTime", PrefferedLoadingTime);
                startActivity(i);

            }
        }));

    }

    // Getting the job posted by the customer
    public void getJobPosted
    () {

        if (offset == 1) {
            if (!isSwipeRefreshed) {
                homeActivity.showProgressDialog(getContext(), "loading");
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
            apiService = ApiClient.getClient().create(ApiInterface.class);
            String acess_token = AppController.getString(getContext(), "acess_token");
            Call<PostedJobResponse> call = apiService.PostedJobList(GloablMethods.API_HEADER + acess_token, offset);
            call.enqueue(new Callback<PostedJobResponse>() {
                @Override
                public void onResponse(Call<PostedJobResponse> call, retrofit2.Response<PostedJobResponse> response) {
                    refreshLayout.setRefreshing(false);
                    isSwipeRefreshed = false;
                    homeActivity.hideProgressDialog();
                    if (response.isSuccessful()) {
                        if (!response.body().getJobList().isEmpty()) {
                            List<JobList> JobList = response.body().getJobList();

                            if (offset == 1) {
                                jobList = JobList;
                                updateEndlessRecyclerView();
                                homeActivity.hideProgressDialog();
                            } else {
                                progressBar.setVisibility(View.VISIBLE);
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
                        homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.SERVERERROR);
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<PostedJobResponse> call, Throwable t) {
                    // Log error here since request failed
                    homeActivity.hideProgressDialog();
                }
            });
        }
    }


    private void updateEndlessRecyclerView() {
        postedJobListAdapter = new PostedJobListAdapter(jobList, R.layout.item_home_postedjob, getContext());
        endlessRecyclerViewPostedJob.setAdapter(postedJobListAdapter);
    }
}
