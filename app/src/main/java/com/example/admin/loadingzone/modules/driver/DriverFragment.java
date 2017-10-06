package com.example.admin.loadingzone.modules.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.DriverList;
import com.example.admin.loadingzone.retrofit.model.TruckDriverViewResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by admin on 10/4/2017.
 */

public class DriverFragment extends Fragment {
    public DriverFragment() {
    }

    @BindView(R.id.recyclerViewDriver)
    EndlessRecyclerView endlessRecyclerViewDriver;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @NonNull
    @BindView(R.id.fabDriverAdd)
    FloatingActionButton fabDriverAdd;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    ApiInterface apiService;
    private boolean isSwipeRefreshed = false;
    private DriverListAdapter driverListAdapter;
    private List<DriverList> driverList = new ArrayList<>();
    private HomeActivity homeActivity;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            if (homeActivity.isConnectingToInternet(getActivity())) {
                getDriverList();
            } else {
                homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
            }

        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity) getActivity();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setRefreshing(false);
        setUpListeners();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (homeActivity.isConnectingToInternet(getActivity())) {
            getDriverList();
        } else {
            homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }

    }
    // adding a new driver
    @OnClick(R.id.fabDriverAdd)
    public void truckAdd() {
        int[] startingLocation = new int[2];
        View v = new View(getContext());
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        DriverAddActivity.startdriverAddActvity(startingLocation, getActivity());
        homeActivity.overridePendingTransition(0, 0);
    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerViewDriver.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                isSwipeRefreshed=true;
                getDriverList();
            }
        });

        endlessRecyclerViewDriver.addPaginationListener(paginationListener);
        endlessRecyclerViewDriver.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String driver_name = driverList.get(position).getDriverName();
                String driver_id = String.valueOf(driverList.get(position).getDriverId());
                String driver_email = driverList.get(position).getDriverEmail();
                String driver_mobile = driverList.get(position).getDriverPhone();
                String driver_adress = driverList.get(position).getDriverAddress();
                String profile_pic = driverList.get(position).getDriverPic();

                String driverJoinedDate = driverList.get(position).getCreatedDate();
                String currentlyAssignedTruck;
                if(driverList.get(position).getDriverTruck() == null ){
                    currentlyAssignedTruck = "Nil";
                }else {
                    currentlyAssignedTruck  = driverList.get(position).getDriverTruck().getCustomName();
                }
                String isFrom = "driverView";
                Intent i = new Intent(getActivity(), DriverEditActivity.class);
                i.putExtra("driver_name", driver_name);
                i.putExtra("driver_id", driver_id);
                i.putExtra("driver_email", driver_email);
                i.putExtra("driver_mobile", driver_mobile);
                i.putExtra("driver_adress", driver_adress);
                i.putExtra("profile_pic", profile_pic);
                i.putExtra("driverJoinedDate",driverJoinedDate);
                i.putExtra("currentlyAssignedTruck",currentlyAssignedTruck);
                i.putExtra("isFrom", isFrom);
                startActivity(i);
            }
        }));

    }

    // getting driver list from the server

    public void getDriverList
            () {

        if (offset == 1) {
            if (!isSwipeRefreshed)
                homeActivity.showProgressDialog(getActivity(), "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getContext(), "acess_token");
        Call<TruckDriverViewResponse> call = apiService.driverList(GloablMethods.API_HEADER + acess_token,offset);
        call.enqueue(new Callback<TruckDriverViewResponse>() {
            @Override
            public void onResponse(Call<TruckDriverViewResponse> call, retrofit2.Response<TruckDriverViewResponse> response) {
                refreshLayout.setRefreshing(false);
                isSwipeRefreshed=false;
                homeActivity.hideProgressDialog();

                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getDriverList().isEmpty()) {

                        List<DriverList> Listvechicle = response.body().getDriverList();
                        if (offset == 1) {
                            driverList = Listvechicle;
                            updateEndlessRecyclerView();
                            homeActivity. hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (DriverList itemModel : Listvechicle) {
                                driverList.add(itemModel);
                            }

//                                adapter.addToList(contestItemsList);
                        }
                        if (Listvechicle.size() < limit) {
                            endlessRecyclerViewDriver.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewDriver.setHaveMoreItem(true);
                        }
                        driverListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        endlessRecyclerViewDriver.setHaveMoreItem(false);
                    }

                } else {
                    getActivity().finish();
                    endlessRecyclerViewDriver.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.SERVERERROR);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TruckDriverViewResponse> call, Throwable t) {
                // Log error here since request failed
                homeActivity.hideProgressDialog();
            }
        });
    }

    private void updateEndlessRecyclerView() {
        driverListAdapter = new DriverListAdapter(driverList, R.layout.item_driver_list, getContext());
        endlessRecyclerViewDriver.setAdapter(driverListAdapter);
        // progressDialog.dismiss();
    }
}
