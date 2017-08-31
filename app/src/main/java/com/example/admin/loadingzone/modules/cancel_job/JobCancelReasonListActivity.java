package com.example.admin.loadingzone.modules.cancel_job;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.CancelJobReasonResponse;
import com.example.admin.loadingzone.retrofit.model.QuotationCancelReason;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class JobCancelReasonListActivity extends BaseActivity {

    @BindView(R.id.recyclerViewJobCancelReasonList)
    EndlessRecyclerView recyclerViewJobCancelReasonList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    ApiInterface apiService;
    String JobId;
    private List<QuotationCancelReason> quotationCancelReasonList = new ArrayList<>();
    JobCancelReasonListAdapter jobCancelReasonListAdapter;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {

            getJobCancelReasonList();
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_cancelreason_list);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Cancel Reason");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        JobId = getIntent().getStringExtra("JobId");//getting job id from posted job details
        refreshLayout.setRefreshing(false);
        setUpListeners();
        if (isConnectingToInternet(getApplicationContext()))
            getJobCancelReasonList();
        else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);

        }
    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewJobCancelReasonList.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 1;
                getJobCancelReasonList();
            }
        });

        recyclerViewJobCancelReasonList.addPaginationListener(paginationListener);
        recyclerViewJobCancelReasonList.addOnItemTouchListener(new RecyclerItemClickListener(JobCancelReasonListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                String cancel_reason_id = String.valueOf(quotationCancelReasonList.get(position).getQuotationCancelReasonId());
                String cancel_reason = quotationCancelReasonList.get(position).getQuotationCancelReason();
                Intent i = new Intent(JobCancelReasonListActivity.this, CancelJobActivity.class);
                i.putExtra("cancel_reason_id", cancel_reason_id);
                i.putExtra("cancel_reason", cancel_reason);
                i.putExtra("JobId", JobId);
                startActivity(i);

            }
        }));

    }

    // reasons are taking from the server and listing for cancel a job
    public void getJobCancelReasonList
    () {

        if (offset == 1) {
            showProgressDialog(JobCancelReasonListActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<CancelJobReasonResponse> call = apiService.CancelJobReasonsList(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<CancelJobReasonResponse>() {
            @Override
            public void onResponse(Call<CancelJobReasonResponse> call, retrofit2.Response<CancelJobReasonResponse> response) {


                refreshLayout.setRefreshing(false);
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getQuotationCancelReason().isEmpty()) {
                        List<QuotationCancelReason> quotationCancelReason = response.body().getQuotationCancelReason();
                        if (offset == 1) {
                            quotationCancelReasonList = quotationCancelReason;
                            updateEndlessRecyclerView();
                            hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (QuotationCancelReason itemModel : quotationCancelReason) {
                                quotationCancelReasonList.add(itemModel);
                            }
                        }
                        if (quotationCancelReason.size() < limit) {
                            recyclerViewJobCancelReasonList.setHaveMoreItem(false);
                        } else {
                            recyclerViewJobCancelReasonList.setHaveMoreItem(true);
                        }
                        jobCancelReasonListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        recyclerViewJobCancelReasonList.setHaveMoreItem(false);
                    }

                } else {
                    finish();
                    recyclerViewJobCancelReasonList.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(relativeLayoutRoot, meta.getString("message"));

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CancelJobReasonResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }

    private void updateEndlessRecyclerView() {
        jobCancelReasonListAdapter = new JobCancelReasonListAdapter(quotationCancelReasonList, R.layout.item_cancel_reason, getApplicationContext());
        recyclerViewJobCancelReasonList.setAdapter(jobCancelReasonListAdapter);
    }
}

