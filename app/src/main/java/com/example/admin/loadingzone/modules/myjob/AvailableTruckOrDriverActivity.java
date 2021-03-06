package com.example.admin.loadingzone.modules.myjob;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.admin.loadingzone.retrofit.model.ActiveTrucklistResponse;
import com.example.admin.loadingzone.retrofit.model.AvailableDriver;
import com.example.admin.loadingzone.retrofit.model.AvailableTruck;
import com.example.admin.loadingzone.retrofit.model.AvailbaleDriverResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AvailableTruckOrDriverActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    @BindView(R.id.recyclerViewItemList)
    EndlessRecyclerView endlessRecyclerViewItem;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.TextEmptyList)
    TextView textEmpty;
    private ApiInterface service;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    ApiInterface apiService;
    private List<AvailableTruck> listTrckAvailble = new ArrayList<>();
    private List<AvailableDriver> listAvailableDriver = new ArrayList<>();
    private String startUnixTimeStamp, endUnixTimeStamp;
    public static int RESULT_OK = 51;
    public static String IsEditVehicle = "EditVehicle";
    public static String IsEditDriver = "EditDriver";
    private AvalibleTruckListAdapter avalibleTruckListAdapter;
    private AvailbleDriverListAdapter availbleDriverListAdapter;
    String jobStatus;
    String isFrom="null";
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
        setContentView(R.layout.activity_available_truck_or_driver);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service = ApiClient.getClient().create(ApiInterface.class);
        jobStatus = getIntent().getStringExtra("jobStatus");
        isFrom=getIntent().getStringExtra("isFrom");
        // toolbar title
        if (jobStatus.equals(IsEditVehicle)) {
            getSupportActionBar().setTitle("Available Trucks");
        } else {
            getSupportActionBar().setTitle("Available Drivers");
        }
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
        endlessRecyclerViewItem.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                isSwipeRefreshed=true;
                getActiveTruckandDrivers();
            }
        });

        endlessRecyclerViewItem.addPaginationListener(paginationListener);
        endlessRecyclerViewItem.addOnItemTouchListener(new RecyclerItemClickListener(AvailableTruckOrDriverActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//if isFrom is StartJob then actvity result is set to startJob Activity

                if (isFrom.equals("StartJob"))
                {
                    if (jobStatus.equals(IsEditVehicle)) {

                        String truck_name = listTrckAvailble.get(position).getCustomName();
                        String truck_type = listTrckAvailble.get(position).getVehicle().getTruckType().getTruckTypeName();
                        String truck_size = listTrckAvailble.get(position).getVehicle().getDimension();
                        String truck_id = String.valueOf(listTrckAvailble.get(position).getProviderVehicleId());
                        Intent i = new Intent();
                        i.putExtra("isEdit", "isTruckEdit");
                        i.putExtra("truck_name", truck_name);
                        i.putExtra("truck_id", truck_id);
                        i.putExtra("truck_type", truck_type);
                        i.putExtra("truck_size", truck_size);
                        setResult(4, i);
                        finish();
                    } else {
                        String driver_name = listAvailableDriver.get(position).getDriverName();
                        String driver_email = listAvailableDriver.get(position).getDriverEmail();
                        String driver_mobile = listAvailableDriver.get(position).getDriverPhone();
                        String driver_id = String.valueOf(listAvailableDriver.get(position).getDriverId());
                        String driver_image = listAvailableDriver.get(position).getDriverPic();
                        Intent i = new Intent();
                        i.putExtra("isEdit", "isDriverEdit");
                        i.putExtra("driver_id", driver_id);
                        i.putExtra("driver_email", driver_email);
                        i.putExtra("driver_mobile", driver_mobile);
                        i.putExtra("driver_name", driver_name);
                        i.putExtra("driver_image", driver_image);
                        setResult(4, i);
                        finish();
                    }
                }
                if (isFrom.equals("Posted")) {
// return the the selected values on the on actvity result of EditAvailbleDriverOrTruckActivity based on the job status
                    if (jobStatus.equals(IsEditVehicle)) {
                        String truck_name = listTrckAvailble.get(position).getCustomName();
                        String truck_type = listTrckAvailble.get(position).getVehicle().getTruckType().getTruckTypeName();
                        String truck_size = listTrckAvailble.get(position).getVehicle().getDimension();
                        String truck_id = String.valueOf(listTrckAvailble.get(position).getProviderVehicleId());
                        Intent i = new Intent();
                        i.putExtra("isEdit", "isTruckEdit");
                        i.putExtra("truck_name", truck_name);
                        i.putExtra("truck_id", truck_id);
                        i.putExtra("truck_type", truck_type);
                        i.putExtra("truck_size", truck_size);
                        setResult(2, i);
                        finish();
                    } else {
                        String driver_name = listAvailableDriver.get(position).getDriverName();
                        String driver_email = listAvailableDriver.get(position).getDriverEmail();
                        String driver_mobile = listAvailableDriver.get(position).getDriverPhone();
                        String driver_id = String.valueOf(listAvailableDriver.get(position).getDriverId());
                        String driver_image = listAvailableDriver.get(position).getDriverPic() ;
                        Toast.makeText(getApplicationContext(),driver_id,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.putExtra("isEdit", "isDriverEdit");
                        i.putExtra("driver_id", driver_id);
                        i.putExtra("driver_email", driver_email);
                        i.putExtra("driver_mobile", driver_mobile);
                        i.putExtra("driver_name", driver_name);
                        i.putExtra("driver_image", driver_image);
                        setResult(2, i);
                        finish();
                    }
                }
            }
        }));

    }

    public void getActiveTruckandDrivers
            () {

        if (offset == 1) {
            if (!isSwipeRefreshed)
            showProgressDialog(AvailableTruckOrDriverActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);

        }
        // handling the edit truck and driver with in single function
        apiService = ApiClient.getClient().create(ApiInterface.class);
        if (jobStatus.equals(IsEditVehicle)) {
            String acess_token = AppController.getString(getApplicationContext(), "acess_token");
            Call<ActiveTrucklistResponse> call = apiService.ActiveTruckandDriverList(GloablMethods.API_HEADER + acess_token, startUnixTimeStamp, endUnixTimeStamp, offset);
            call.enqueue(new Callback<ActiveTrucklistResponse>() {
                @Override
                public void onResponse(Call<ActiveTrucklistResponse> call, retrofit2.Response<ActiveTrucklistResponse> response) {
                    refreshLayout.setRefreshing(false);
                    hideProgressDialog();
                    isSwipeRefreshed=false;
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().getAvailableTrucks().isEmpty()) {
                            List<AvailableTruck> availableTruckList = response.body().getAvailableTrucks();
                            if (offset == 1) {
                                listTrckAvailble = availableTruckList;
                                updateEndlessRecyclerViewTruck();
                                hideProgressDialog();
                            } else {
                                progressBar.setVisibility(View.VISIBLE);
                                for (AvailableTruck itemModel : availableTruckList) {
                                    listTrckAvailble.add(itemModel);
                                }
                            }
                            if (listTrckAvailble.size() < limit) {
                                endlessRecyclerViewItem.setHaveMoreItem(false);
                            } else {
                                endlessRecyclerViewItem.setHaveMoreItem(true);
                            }
                            avalibleTruckListAdapter.notifyDataSetChanged();
                            offset = offset + 1;
                        } else {
                            endlessRecyclerViewItem.setHaveMoreItem(false);
                            textEmpty.setVisibility(View.VISIBLE);
                            textEmpty.setText("No Trucks Availble");
                        }

                    } else {
                        finish();
                        endlessRecyclerViewItem.setHaveMoreItem(false);
                    }
                    if (!response.isSuccessful()) {
                        showSnakBar(relativeLayoutRoot, MessageConstants.SERVERERROR);
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ActiveTrucklistResponse> call, Throwable t) {
                    hideProgressDialog();
                }
            });
        } else {
            String acess_token = AppController.getString(getApplicationContext(), "acess_token");
            Call<AvailbaleDriverResponse> call = apiService.AvailbleDriverList(GloablMethods.API_HEADER + acess_token, startUnixTimeStamp, endUnixTimeStamp, offset);
            call.enqueue(new Callback<AvailbaleDriverResponse>() {
                @Override
                public void onResponse(Call<AvailbaleDriverResponse> call, retrofit2.Response<AvailbaleDriverResponse> response) {
                    refreshLayout.setRefreshing(false);
                    hideProgressDialog();
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().getAvailableDrivers().isEmpty()) {
                            List<AvailableDriver> availableDriverList = response.body().getAvailableDrivers();
                            if (offset == 1) {
                                listAvailableDriver = availableDriverList;
                                updateEndlessRecyclerViewDriver();
                                hideProgressDialog();
                            } else {
                                progressBar.setVisibility(View.VISIBLE);
                                for (AvailableDriver itemModel : availableDriverList) {
                                    listAvailableDriver.add(itemModel);
                                }
                            }
                            if (listAvailableDriver.size() < limit) {
                                endlessRecyclerViewItem.setHaveMoreItem(false);
                            } else {
                                endlessRecyclerViewItem.setHaveMoreItem(true);
                            }
                            availbleDriverListAdapter.notifyDataSetChanged();
                            offset = offset + 1;
                        } else {
                            endlessRecyclerViewItem.setHaveMoreItem(false);
                            textEmpty.setVisibility(View.VISIBLE);
                            textEmpty.setText("No Drivers Availble");
                        }
                    } else {
                        finish();
                        endlessRecyclerViewItem.setHaveMoreItem(false);
                    }
                    if (!response.isSuccessful()) {
                        showSnakBar(relativeLayoutRoot, MessageConstants.SERVERERROR);
                    }
                    progressBar.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<AvailbaleDriverResponse> call, Throwable t) {
                    hideProgressDialog();
                }
            });
        }

    }

    private void updateEndlessRecyclerViewTruck() {
        avalibleTruckListAdapter = new AvalibleTruckListAdapter(listTrckAvailble, R.layout.item_truck_avalible, getApplicationContext());
        endlessRecyclerViewItem.setAdapter(avalibleTruckListAdapter);
    }

    private void updateEndlessRecyclerViewDriver() {
        availbleDriverListAdapter = new AvailbleDriverListAdapter(listAvailableDriver, R.layout.item_availvle_driver, getApplicationContext());
        endlessRecyclerViewItem.setAdapter(availbleDriverListAdapter);
    }

    @Override
    public void onBackPressed() {
        if (isFrom.equals("StartJob"))
        {
            if (jobStatus.equals(IsEditVehicle)) {
                Intent i = new Intent();
                i.putExtra("isEdit", "isTruckEdit");
                i.putExtra("truck_name", "");
                i.putExtra("truck_id", "");
                i.putExtra("truck_type", "");
                i.putExtra("truck_size", "");
                setResult(4, i);
                finish();
            }
            else {
                Intent i = new Intent();
                i.putExtra("driver_id", "");
                i.putExtra("driver_email", "");
                i.putExtra("driver_mobile", "");
                i.putExtra("driver_name", "");
                setResult(4, i);
                finish();
            }
        }
        else
        {
            if (jobStatus.equals(IsEditVehicle)) {
                Intent i = new Intent();
                i.putExtra("isEdit", "isTruckEdit");
                i.putExtra("truck_name", "");
                i.putExtra("truck_id", "");
                i.putExtra("truck_type", "");
                i.putExtra("truck_size", "");
                setResult(2, i);
                finish();
            }
            else
            {
                Intent i = new Intent();
                i.putExtra("isEdit", "isDriverEdit");
                i.putExtra("driver_id", "");
                i.putExtra("driver_email", "");
                i.putExtra("driver_mobile", "");
                i.putExtra("driver_name", "");
                setResult(2, i);
                finish();
            }
        }
    }
}
