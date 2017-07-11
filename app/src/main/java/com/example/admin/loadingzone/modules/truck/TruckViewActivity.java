package com.example.admin.loadingzone.modules.truck;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileActivity;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.TruckResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;

public class TruckViewActivity extends BaseActivity {

    @BindView(R.id.recyclerViewTruck)
    EndlessRecyclerView endlessRecyclerViewTrck;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    @NonNull
    @BindView(R.id.fabTruckAdd)
    FloatingActionButton fabTruckAdd;
    ApiInterface apiService;
    private int selectedItemPosition = -1;
    TrckListAdapter trckListAdapter;
    private int limit = 10;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private List<VehicleList> vehicleListList = new ArrayList<>();
    private  static String NEW_TRUCK_ADD="NewTruck";
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            getTrckList();
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_view);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.truck_title);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        refreshLayout.setRefreshing(false);
        setUpListeners();
        if (isConnectingToInternet(getApplicationContext()))
            getTrckList();
        else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);

        }
    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewTrck.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                getTrckList();
            }
        });

        endlessRecyclerViewTrck.addPaginationListener(paginationListener);
        endlessRecyclerViewTrck.addOnItemTouchListener(new RecyclerItemClickListener(TruckViewActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String provider_vehicle_id = String.valueOf(vehicleListList.get(position).getProviderVehicleId());
                String driver=vehicleListList.get(position).getDriver();
                String truckId= String.valueOf(vehicleListList.get(position).getProviderVehicleId());
                Intent i = new Intent(TruckViewActivity.this, TruckEditUpdateActivity.class);
                i.putExtra("isFrom", "TruckView");
                i.putExtra("driver", driver);
                i.putExtra("provider_vehicle_id", provider_vehicle_id);
                i.putExtra("truckId", truckId);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        }));

    }

    @Optional
    @OnClick(R.id.fabTruckAdd)
    public void truckAdd() {
        int[] startingLocation = new int[2];
        View v = new View(TruckViewActivity.this);
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        TruckAddActivity.startTruckAddActvity(startingLocation, TruckViewActivity.this, NEW_TRUCK_ADD);
        overridePendingTransition(0, 0);
    }

    public void getTrckList
            () {

        if (offset == 1) {
            showProgressDialog(TruckViewActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckResponse> call = apiService.TrckList(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<TruckResponse>() {
            @Override
            public void onResponse(Call<TruckResponse> call, retrofit2.Response<TruckResponse> response) {


                refreshLayout.setRefreshing(false);
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getVehicleList().isEmpty()) {
                        List<VehicleList> Listvechicle = response.body().getVehicleList();
                        if (offset == 1) {
                            vehicleListList = Listvechicle;
                            updateEndlessRecyclerView();
                            hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (VehicleList itemModel : Listvechicle) {
                                vehicleListList.add(itemModel);
                            }
                        }
                        if (Listvechicle.size() < limit) {
                            endlessRecyclerViewTrck.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewTrck.setHaveMoreItem(true);
                        }
                        trckListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        endlessRecyclerViewTrck.setHaveMoreItem(false);
                    }

                } else {
                    finish();
                    endlessRecyclerViewTrck.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    showSnakBar(relativeLayoutRoot, MessageConstants.SERVERERROR);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TruckResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }


    private void updateEndlessRecyclerView() {
        trckListAdapter = new TrckListAdapter(vehicleListList, R.layout.item_trck, getApplicationContext());
        endlessRecyclerViewTrck.setAdapter(trckListAdapter);
    }

}
