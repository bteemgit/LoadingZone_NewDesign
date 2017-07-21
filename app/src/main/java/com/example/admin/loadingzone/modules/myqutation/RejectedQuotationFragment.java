package com.example.admin.loadingzone.modules.myqutation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.Item;
import com.example.admin.loadingzone.retrofit.model.PendingQutationResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by admin on 6/19/2017.
 */

public class RejectedQuotationFragment extends Fragment {

    public RejectedQuotationFragment() {
        // Required empty public constructor
    }

    @NonNull
    @BindView(R.id.recyclerViewPqutation)
    EndlessRecyclerView recyclerViewPqutation;
    @NonNull
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @NonNull
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBarFooter;
    private List<Item> itemsArrayList = new ArrayList<>();
    private PendingQutationListAdapter pendingQutationListAdapter;
    private ProgressDialog pDialog;
    private ApiInterface service;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {

            getRejectedQutation();
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
        View view = inflater.inflate(R.layout.fragment_pending_qutation, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setRefreshing(false);
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
        getRejectedQutation();

    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPqutation.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshLayout.setRefreshing(true);
                offset = 1;
                getRejectedQutation();
            }
        });

        recyclerViewPqutation.addPaginationListener(paginationListener);
        recyclerViewPqutation.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String qutation_id = String.valueOf(itemsArrayList.get(position).getQuotationId());
                String job_title = itemsArrayList.get(position).getJobTitle();
                String cus_name = itemsArrayList.get(position).getCustomer().getName();
                String cus_email = itemsArrayList.get(position).getCustomer().getEmail();
                String cus_phone = itemsArrayList.get(position).getCustomer().getPhone1();
                String cus_profile = itemsArrayList.get(position).getCustomer().getProfilePic();
                String quotationCurrency = itemsArrayList.get(position).getQuotationCurrency();
       //         String quotationAmount = itemsArrayList.get(position).getQuotationAmount();
                String dateSubmitted = itemsArrayList.get(position).getDateSubmitted();
                String dateAccepted = itemsArrayList.get(position).getDateAccepted();
       //         String dateRejected=itemsArrayList.get(position).getDateRejected();
                String  quotationStatus=itemsArrayList.get(position).getQuotationStatus();
                String quotationDescription=itemsArrayList.get(position).getQuotationDescription();
                String quotationAmount = String.valueOf(itemsArrayList.get(position).getQuotationAmount());

                String jobdate = itemsArrayList.get(position).getJobDetails().getLoadingDate();
                String jobDescription = itemsArrayList.get(position).getJobDetails().getMaterialDescription();
                String dateRequested = (String) itemsArrayList.get(position).getJobDetails().getDateRequested();
                String activeQuotations =  itemsArrayList.get(position).getJobDetails().getQuotationCount();
                String distance = String.valueOf(itemsArrayList.get(position).getJobDetails().getLocationDistance());


                Intent i = new Intent(getActivity(), QutationDetailsActivity.class);
                i.putExtra("qutation_id", qutation_id);
                i.putExtra("job_title", job_title);
                i.putExtra("cus_name", cus_name);
                i.putExtra("cus_email", cus_email);
                i.putExtra("cus_phone", cus_phone);
                i.putExtra("cus_profile", cus_profile);
                i.putExtra("quotationCurrency", quotationCurrency);
       //         i.putExtra("quotationAmount", quotationAmount);
                i.putExtra("dateSubmitted", dateSubmitted);
                i.putExtra("dateAccepted", dateAccepted);
       //         i.putExtra("dateRejected", dateRejected);
                i.putExtra("quotationStatus", quotationStatus);
                i.putExtra("quotationDescription", quotationDescription);
                i.putExtra("isFrom","rejected");
//jflkjflkdjgldfk
                i.putExtra("quotationAmount", quotationAmount);

                i.putExtra("jobdate",jobdate);
                i.putExtra("jobDescription",jobDescription);
                i.putExtra("dateRequested",dateRequested);
                i.putExtra("activeQuotations",activeQuotations);
                i.putExtra("distance",distance);


                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }));

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        updateEndlessRecyclerView();
    }


    public void getRejectedQutation
            () {

        if (offset == 1) {
            pDialog.setMessage("loading");
            pDialog.show();
        } else {
            progressBarFooter.setVisibility(View.VISIBLE);
        }
        service =
                ApiClient.getClient().create(ApiInterface.class);

        String acess_token = AppController.getString(getContext(), "acess_token");
        Call<PendingQutationResponse> call = service.RejectedQutationList(GloablMethods.API_HEADER +acess_token,offset);
        call.enqueue(new Callback<PendingQutationResponse>() {
            @Override
            public void onResponse(Call<PendingQutationResponse> call, retrofit2.Response<PendingQutationResponse> response) {

                refreshLayout.setRefreshing(false);
                pDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getItems().isEmpty()) {
                        List<Item> jobLists = response.body().getItems();
                        if (offset == 1) {
                            itemsArrayList = jobLists;
                            updateEndlessRecyclerView();
                            pDialog.hide();
                        } else {
                            progressBarFooter.setVisibility(View.VISIBLE);
                            for (Item itemModel : jobLists) {
                                itemsArrayList.add(itemModel);
                            }
                        }
                        if (jobLists.size() < limit) {
                            recyclerViewPqutation.setHaveMoreItem(false);
                        } else {
                            recyclerViewPqutation.setHaveMoreItem(true);
                        }
                        pendingQutationListAdapter.notifyDataSetChanged();
                        offset = offset + 1;

                        Log.i("tag", itemsArrayList.toString());
                    } else {
                        recyclerViewPqutation.setHaveMoreItem(false);
                    }

                } else {
                    getActivity().finish();
                    recyclerViewPqutation.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {

                    Snackbar snackbar = Snackbar
                            .make(getActivity().findViewById(R.id.root), "se", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
                //  progressDialog.dismiss();
                progressBarFooter.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PendingQutationResponse> call, Throwable t) {
                // Log error here since request failed
                pDialog.dismiss();

            }
        });
    }

    private void updateEndlessRecyclerView() {
        pendingQutationListAdapter = new PendingQutationListAdapter( itemsArrayList,R.layout.pending_qutation_item,getContext());
        recyclerViewPqutation.setAdapter(pendingQutationListAdapter);
        // progressDialog.dismiss();
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
