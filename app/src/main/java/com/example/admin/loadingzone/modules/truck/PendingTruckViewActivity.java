package com.example.admin.loadingzone.modules.truck;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.admin.loadingzone.R.id.floating_action_menu;

public class PendingTruckViewActivity extends BaseActivity {

    @BindView(R.id.recyclerViewTruck)
    EndlessRecyclerView endlessRecyclerViewTrck;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;

    @NonNull
    @BindView(R.id.fabAddTruck)
    com.github.clans.fab.FloatingActionButton fab_messageDriver;


    @NonNull
    @BindView(floating_action_menu)
    FloatingActionMenu floatingActionMenu;

    private BottomNavigationView mBottomNav;
    ApiInterface apiService;
    private int selectedItemPosition = -1;
    TrckListAdapter trckListAdapter;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private List<VehicleList> vehicleListList = new ArrayList<>();
    private  static String NEW_TRUCK_ADD="NewTruck";
    private boolean isSwipeRefreshed = false;
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
        mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNav.setVisibility(View.GONE);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pending Trucks");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        refreshLayout.setRefreshing(false);
        setUpListeners();
        if (isConnectingToInternet(getApplicationContext()))
            getTrckList();
        else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);

        }

        floatingActionMenu.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewTrck.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                isSwipeRefreshed=true;
                getTrckList();
            }
        });

        endlessRecyclerViewTrck.addPaginationListener(paginationListener);
        endlessRecyclerViewTrck.addOnItemTouchListener(new RecyclerItemClickListener(PendingTruckViewActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String provider_vehicle_id = String.valueOf(vehicleListList.get(position).getProviderVehicleId());
                String driver=vehicleListList.get(position).getDriver();
                String truckId= String.valueOf(vehicleListList.get(position).getProviderVehicleId());

                String reg_no=vehicleListList.get(position).getRegistration_number();
                String chassis_no=vehicleListList.get(position).getChassis_number();
                String License_no=vehicleListList.get(position).getLicence_number();



                Intent i = new Intent(PendingTruckViewActivity.this, TruckEditUpdateActivity.class);
                i.putExtra("isFrom", "PendingTruckView");
                i.putExtra("driver", driver);
                i.putExtra("provider_vehicle_id", provider_vehicle_id);
                i.putExtra("truckId", truckId);


                i.putExtra("reg_no",reg_no);
                i.putExtra("chassis_no",chassis_no);
                i.putExtra("License_no",License_no);

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        }));

    }

    @Optional
    @OnClick(R.id.fabAddTruck)
    public void truckAdd() {
        int[] startingLocation = new int[2];
        View v = new View(PendingTruckViewActivity.this);
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        TruckAddActivity.startTruckAddActvity(startingLocation, PendingTruckViewActivity.this, NEW_TRUCK_ADD);
        overridePendingTransition(0, 0);
    }

    public void getTrckList
            () {

        if (offset == 1) {
            if (!isSwipeRefreshed)
            showProgressDialog(PendingTruckViewActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckResponse> call = apiService.PendingTruckList(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<TruckResponse>() {
            @Override
            public void onResponse(Call<TruckResponse> call, retrofit2.Response<TruckResponse> response) {
                refreshLayout.setRefreshing(false);
                isSwipeRefreshed=false;
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
