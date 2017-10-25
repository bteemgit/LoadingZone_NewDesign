package com.example.admin.loadingzone.modules.myjob;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.ActiveTrucklistResponse;
import com.example.admin.loadingzone.retrofit.model.AvailableTruck;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SelectActvieTruckActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    @BindView(R.id.recyclerViewTrucks)
    EndlessRecyclerView endlessRecyclerViewTrucks;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    private ApiInterface service;
    private int limit = 10;
    private int offset = 1;
    private boolean hasReachedTop = false;
    ApiInterface apiService;
    private List<AvailableTruck> listTrckAvailble = new ArrayList<>();
    private ActiveTruckListAdapter activeTruckListAdapter;
    private String startUnixTimeStamp, endUnixTimeStamp;
    public static int RESULT_OK = 51;
    private boolean isSwipeRefreshed = false;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            getActiveTruckandDrivers();
        }
        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_actvie_truck);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Avalible Trucks");
        service = ApiClient.getClient().create(ApiInterface.class);
        startUnixTimeStamp = getIntent().getStringExtra("startUnixTimeStamp");
        endUnixTimeStamp = getIntent().getStringExtra("endUnixTimeStamp");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        refreshLayout.setRefreshing(false);
        setUpListeners();
        if (isConnectingToInternet(getApplicationContext())) {
            getActiveTruckandDrivers();
        } else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }
    }

    private void setUpListeners() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewTrucks.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                offset = 1;
                isSwipeRefreshed=true;
                getActiveTruckandDrivers();
            }
        });

        endlessRecyclerViewTrucks.addPaginationListener(paginationListener);
        endlessRecyclerViewTrucks.addOnItemTouchListener(new RecyclerItemClickListener(SelectActvieTruckActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String driver_exist = listTrckAvailble.get(position).getDriver_exists();
                String driver_id = null, driver_name = null, driver_pic = null;
                if (driver_exist.equals("true")) {
                    driver_id = String.valueOf(listTrckAvailble.get(position).getDriver().getDriverId());
                    driver_name = listTrckAvailble.get(position).getDriver().getDriverName();
                    driver_pic = listTrckAvailble.get(position).getDriver().getDriverPic();
                } else {
                    driver_id = "driver_isnot_assigned";
                }
                String truck_id = String.valueOf(listTrckAvailble.get(position).getProviderVehicleId());
                String truck_name = listTrckAvailble.get(position).getCustomName();
                String truck_maker = listTrckAvailble.get(position).getVehicle().getManufacturer().getMakerName();
                String truck_type = listTrckAvailble.get(position).getVehicle().getTruckType().getTruckTypeName();
                String truck_dimension = listTrckAvailble.get(position).getVehicle().getDimension();
                Intent i = new Intent();
                i.putExtra("driver_id", driver_id);
                i.putExtra("truck_id", truck_id);
                i.putExtra("truck_name", truck_name);
                i.putExtra("driver_name", driver_name);
                i.putExtra("driver_pic", driver_pic);
                i.putExtra("truck_maker", truck_maker);
                i.putExtra("truck_type", truck_type);
                i.putExtra("truck_dimension", truck_dimension);
                setResult(2, i);
                finish();
            }
        }));

    }


    public void getActiveTruckandDrivers
            () {

        if (offset == 1) {
            if (!isSwipeRefreshed)
            showProgressDialog(SelectActvieTruckActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<ActiveTrucklistResponse> call = apiService.ActiveTruckandDriverList(GloablMethods.API_HEADER + acess_token, startUnixTimeStamp, endUnixTimeStamp, offset);
        call.enqueue(new Callback<ActiveTrucklistResponse>() {
            @Override
            public void onResponse(Call<ActiveTrucklistResponse> call, retrofit2.Response<ActiveTrucklistResponse> response) {
                refreshLayout.setRefreshing(false);
                isSwipeRefreshed=false;
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getAvailableTrucks().isEmpty()) {
                        List<AvailableTruck> availableTruckList = response.body().getAvailableTrucks();
                        if (offset == 1) {
                            listTrckAvailble = availableTruckList;
                            updateEndlessRecyclerView();
                            hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (AvailableTruck itemModel : availableTruckList) {
                                listTrckAvailble.add(itemModel);
                            }

//                                adapter.addToList(contestItemsList);
                        }
                        if (listTrckAvailble.size() < limit) {
                            endlessRecyclerViewTrucks.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewTrucks.setHaveMoreItem(true);
                        }
                        activeTruckListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        showSnakBar(relativeLayoutRoot, response.body().getMeta().getMessage());
                        endlessRecyclerViewTrucks.setHaveMoreItem(false);
                    }

                } else {
                    finish();
                    endlessRecyclerViewTrucks.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(relativeLayoutRoot, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
//                            Log.d("exception", e.getMessage());
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ActiveTrucklistResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }
    private void updateEndlessRecyclerView() {
        activeTruckListAdapter = new ActiveTruckListAdapter(listTrckAvailble, R.layout.item_active_truck, getApplicationContext());
        endlessRecyclerViewTrucks.setAdapter(activeTruckListAdapter);
    }
}
