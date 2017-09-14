package com.example.admin.loadingzone.modules.truck;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.driver.DriverListAdapter;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.AdddriverResponnse;
import com.example.admin.loadingzone.retrofit.model.DriverList;
import com.example.admin.loadingzone.retrofit.model.TruckAddResponse;
import com.example.admin.loadingzone.retrofit.model.TruckDriverViewResponse;
import com.example.admin.loadingzone.retrofit.model.TruckdocumentsViewResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleDetailsResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleDoc;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckEditUpdateActivity extends BaseActivity {
    @NonNull
    @BindView(R.id.relative_submit)
    RelativeLayout relativeLayoutSubmit;
    @NonNull
    @BindView(R.id.linerUpdateTruck)
    LinearLayout linerUpdateTruck;
    @NonNull
    @BindView(R.id.linearDeleteTruck)
    LinearLayout linearDeleteTruck;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @NonNull
    @BindView(R.id.textTruckMaker)
    TextView textViewTruckMaker;
    @NonNull
    @BindView(R.id.textTruckModel)
    TextView textViewTruckModel;
    @NonNull
    @BindView(R.id.textTruckType)
    TextView textViewTruckType;
    @NonNull
    @BindView(R.id.textTruckYear)
    TextView textViewTruckYear;
    @NonNull
    @BindView(R.id.textCustomName)
    TextView textCustomName;
    @NonNull
    @BindView(R.id.textInsuranceDate)
    TextView textViewInsuranceDate;
    @NonNull
    @BindView(R.id.textAverageSpeed)
    TextView textAverageSpeed;
    @NonNull
    @BindView(R.id.textTruckWeight)
    TextView textTruckWeight;
    @NonNull
    @BindView(R.id.textTruckDimension)
    TextView textTruckDimension;
    @NonNull
    @BindView(R.id.ivDriverProfilePhoto)
    ImageView ivDriverProfilePhoto;
    @NonNull
    @BindView(R.id.textDriverEmail)
    TextView textDriverEmail;
    @NonNull
    @BindView(R.id.textDriverName)
    TextView textDriverName;
    @NonNull
    @BindView(R.id.textDriverPhone)
    TextView textDriverPhone;
    @NonNull
    @BindView(R.id.textAddNewDriver)
    TextView textAddNewDriver;
    @NonNull
    @BindView(R.id.ivTruckEdit)
    ImageView ivTruckEdit;
    @NonNull
    @BindView(R.id.reltive_existingDriver)
    LinearLayout reltive_existingDriver;
    @BindView(R.id.id_assignDriverClick)
    LinearLayout assignDriver_card;
    @BindView(R.id.id_linear_driver_details)
    LinearLayout assignDriverDetailsExists;
    @NonNull
    @BindView(R.id.textRegistrationNo)
    TextView textViewRegistrationNo;
    @NonNull
    @BindView(R.id.textChasisNo)
    TextView textViewChasisNo;
    @NonNull
    @BindView(R.id.textLicenseNo)
    TextView textViewLicenseNo;
    @NonNull
    @BindView(R.id.img_ChangeDriver)
    ImageView imageViewChangeDriver;
    @NonNull
    @BindView(R.id.linearDriverDetails)
    LinearLayout linearDriverDetails_Head;
    @NonNull
    @BindView(R.id.recyclerview_doc_list)
    EndlessRecyclerView endlessRecyclerViewTruckDocList;
    ApiInterface apiService;
    String provider_vehicle_id, truckId, reg_no, chassis_no, License_no;
    String driver = "driver";
    List<DriverList> Listdrivers= new ArrayList<>();

    DriverListAdapter driverListAdapter;
    TruckDocumentListAdapter truckDocumentListAdapter;
    private List<VehicleDoc> vehicleDocArrayList = new ArrayList<>();
    private  static String TRUCK_UPDATE="UpdateTruck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_edit_update);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Truck Details");

        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        provider_vehicle_id = getIntent().getStringExtra("provider_vehicle_id");
        truckId = getIntent().getStringExtra("truckId");
        driver = getIntent().getStringExtra("driver");
        reg_no = getIntent().getStringExtra("reg_no");
        chassis_no = getIntent().getStringExtra("chassis_no");
        License_no = getIntent().getStringExtra("License_no");
        //calling the driver list api
        Listdrivers=getDriverList();
        String From = getIntent().getStringExtra("isFrom");
        if(From.equals("PendingTruckView")){
            linearDriverDetails_Head.setVisibility(View.GONE);
            //assignDriverDetailsExists.setVisibility(View.GONE);
            assignDriver_card.setVisibility(View.GONE);
        }

        // recyclerview layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TruckEditUpdateActivity.this, 2);
        endlessRecyclerViewTruckDocList.setLayoutManager(gridLayoutManager);

        if (isConnectingToInternet(getApplicationContext()))
            getTruckDocList(provider_vehicle_id);
        else {
            showSnakBar(rootView, MessageConstants.INTERNET);
        }

        if (isConnectingToInternet(getApplicationContext()))
            getTruckDetails(provider_vehicle_id);
        else {
            showSnakBar(rootView, MessageConstants.INTERNET);
        }

        if (driver.equals("false")) {
            assignDriverDetailsExists.setVisibility(View.GONE);
            reltive_existingDriver.setVisibility(View.GONE);
            textAddNewDriver.setVisibility(View.VISIBLE);
            imageViewChangeDriver.setVisibility(View.GONE);
            ivTruckEdit.setVisibility(View.GONE);

        } else {
            assignDriver_card.setVisibility(View.GONE);
            reltive_existingDriver.setVisibility(View.VISIBLE);
            textAddNewDriver.setVisibility(View.GONE);
            ivTruckEdit.setVisibility(View.VISIBLE);
        }




    }


    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // update truck
    @NonNull
    @OnClick(R.id.linerUpdateTruck)
    public void updateTruck() {
        Intent i = new Intent(TruckEditUpdateActivity.this, TruckAddActivity.class);
        i.putExtra("isFrom", TRUCK_UPDATE);
        i.putExtra("provider_vehicle_id", provider_vehicle_id);
        i.putExtra("reg_no",reg_no);
        i.putExtra("chassis_no",chassis_no);
        i.putExtra("License_no",License_no);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    // Add driver
    @NonNull
    @OnClick(R.id.textAddNewDriver)
    public void addDriver() {
        showbottomDriverList();
    }

    // Change driver
    @NonNull
    @OnClick(R.id.img_ChangeDriver)
    public void changeDriver() {
        showbottomDriverList();

    }




    // delete truck
    @NonNull
    @OnClick(R.id.linearDeleteTruck)
    public void deleteTruck() {
        if (provider_vehicle_id.length() > 0) {

            if (isConnectingToInternet(getApplicationContext())) {
                DeleteTruck(provider_vehicle_id);
            } else {
                showSnakBar(rootView, MessageConstants.INTERNET);

            }
        }
    }

    private void showbottomDriverList() {
        final LayoutInflater li = LayoutInflater.from(TruckEditUpdateActivity.this);
        final View view = li.inflate(R.layout.trck_model, null);
        final Dialog mBottomSheetDialog = new Dialog(TruckEditUpdateActivity.this, R.style.MaterialDialogSheet);
        EndlessRecyclerView endlessRecyclerViewTrcukModel = (EndlessRecyclerView) view.findViewById(R.id.recyclerViewTruckModel);
        // Recyclerview Layout manager
        endlessRecyclerViewTrcukModel.setLayoutManager(new LinearLayoutManager(this));
        endlessRecyclerViewTrcukModel.setHasFixedSize(true);
        endlessRecyclerViewTrcukModel.setNestedScrollingEnabled(true);
        // Driver List
        if (isConnectingToInternet(getApplicationContext())) {
            Listdrivers = getDriverList();
            driverListAdapter = new DriverListAdapter(Listdrivers, R.layout.item_driver_list, getApplicationContext());
            endlessRecyclerViewTrcukModel.setAdapter(driverListAdapter);
            driverListAdapter.notifyDataSetChanged();
        } else {
            showSnakBar(rootView, MessageConstants.INTERNET);

        }
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        endlessRecyclerViewTrcukModel.addOnItemTouchListener(new RecyclerItemClickListener(TruckEditUpdateActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String driverId = String.valueOf(Listdrivers.get(position).getDriverId());
                assignDriverToTruck(driverId, truckId);
                mBottomSheetDialog.dismiss();

            }
        }));
    }

    //getting the truck document list
    public void getTruckDocList
    (String provider_vehicle_id) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckdocumentsViewResponse> call = apiService.getTruckDocList(GloablMethods.API_HEADER + acess_token, provider_vehicle_id, 1);
        call.enqueue(new Callback<TruckdocumentsViewResponse>() {
            @Override
            public void onResponse(Call<TruckdocumentsViewResponse> call, retrofit2.Response<TruckdocumentsViewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getVehicleDocs().isEmpty()) {
                        vehicleDocArrayList = response.body().getVehicleDocs();
                        truckDocumentListAdapter = new TruckDocumentListAdapter(vehicleDocArrayList, TruckEditUpdateActivity.this);
                        endlessRecyclerViewTruckDocList.setAdapter(truckDocumentListAdapter);
                        truckDocumentListAdapter.notifyDataSetChanged();
                        endlessRecyclerViewTruckDocList.addOnItemTouchListener(new RecyclerItemClickListener(TruckEditUpdateActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Intent i = new Intent(getApplicationContext(), TruckDocumentDetailActivity.class);
                                i.putExtra("image_url", vehicleDocArrayList.get(position).getDocumentFile());
                                i.putExtra("doc_date", vehicleDocArrayList.get(position).getDocumentDate());
                                i.putExtra("doc_title", vehicleDocArrayList.get(position).getDocumentTitle());
                                startActivity(i);

                            }
                        }));

                    }
                }
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(rootView, meta.getString("message"));
                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());

                    }
                }

            }

            @Override
            public void onFailure(Call<TruckdocumentsViewResponse> call, Throwable t) {
            }
        });
    }

    // getting the driver list aded by the provider
    public List<DriverList> getDriverList
    () {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckDriverViewResponse> call = apiService.driverList(GloablMethods.API_HEADER + acess_token, 1);
        call.enqueue(new Callback<TruckDriverViewResponse>() {
            @Override
            public void onResponse(Call<TruckDriverViewResponse> call, retrofit2.Response<TruckDriverViewResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getDriverList().isEmpty()) {
                        Listdrivers = response.body().getDriverList();
                    } else {
                        showSnakBar(rootView, "You didnt Add any drivers,please Add drivers");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(rootView, meta.getString("message"));
                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());

                    }
                }

            }

            @Override
            public void onFailure(Call<TruckDriverViewResponse> call, Throwable t) {
                // Log error here since request failed

            }
        });
        return Listdrivers;
    }

    // get truck details
    private void getTruckDetails(String provider_vehicle_id) {
        showProgressDialog(TruckEditUpdateActivity.this, "please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<VehicleDetailsResponse> call = apiService.GetTruckDetails(GloablMethods.API_HEADER + acess_token, provider_vehicle_id);
        call.enqueue(new Callback<VehicleDetailsResponse>() {
            @Override
            public void onResponse(Call<VehicleDetailsResponse> call, Response<VehicleDetailsResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    textViewTruckMaker.setText(response.body().getVehicle().getManufacturer().getMakerName());
                    textViewTruckModel.setText(response.body().getVehicle().getModel().getModelName());
                    textViewTruckType.setText(response.body().getVehicle().getTruckType().getTruckTypeName());
                    textViewTruckYear.setText(response.body().getVehicle().getModelYear());
                    textCustomName.setText(response.body().getCustomName());
                    textAverageSpeed.setText(response.body().getAvgRunningSpeed());
                    textViewInsuranceDate.setText(response.body().getInsuranceExpDate());
                    textViewRegistrationNo.setText(reg_no);
                    textViewChasisNo.setText(chassis_no);
                    textViewLicenseNo.setText(License_no);
                    textTruckWeight.setText(response.body().getWeight() + "");
                    textTruckDimension.setText(response.body().getVehicle().getDimension());
                    String driver_exists = response.body().getDriver_exists();
                    if (driver_exists.equals("true")) {
                        reltive_existingDriver.setVisibility(View.VISIBLE);
                        textAddNewDriver.setVisibility(View.GONE);
                        ivTruckEdit.setVisibility(View.VISIBLE);
                        String drivername = response.body().getDriver().getDriverName();
                        String driverEmail = response.body().getDriver().getDriverEmail();
                        String driverMobile = response.body().getDriver().getDriverPhone();
                        String driverProfile = response.body().getDriver().getDriverPic();
                        SetDriverField(drivername, driverEmail, driverMobile, driverProfile);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<VehicleDetailsResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(rootView, call.request().headers().get("meta"), Snackbar.LENGTH_LONG);
                snackbar.show();
                hideProgressDialog();


            }
        });
    }

    // Delte truck
    private void DeleteTruck(String provider_vehicle_id) {
        showProgressDialog(TruckEditUpdateActivity.this, "please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckAddResponse> call = apiService.DeleteTruck(GloablMethods.API_HEADER + acess_token, provider_vehicle_id);
        call.enqueue(new Callback<TruckAddResponse>() {
            @Override
            public void onResponse(Call<TruckAddResponse> call, Response<TruckAddResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    Snackbar snackbar = Snackbar
                            .make(rootView, "Deleted", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Intent i = new Intent(TruckEditUpdateActivity.this, TruckViewActivity.class);
                    startActivity(i);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TruckAddResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(rootView, call.request().headers().get("meta"), Snackbar.LENGTH_LONG);
                snackbar.show();
                hideProgressDialog();


            }
        });
    }

    // Delte truck
    private void assignDriverToTruck(String driver_id, String vehicle_id) {
        showProgressDialog(TruckEditUpdateActivity.this, "asigning driver please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<AdddriverResponnse> call = apiService.AssignDrivertoTruck(GloablMethods.API_HEADER + acess_token, driver_id, vehicle_id);
        call.enqueue(new Callback<AdddriverResponnse>() {
            @Override
            public void onResponse(Call<AdddriverResponnse> call, Response<AdddriverResponnse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), TruckViewActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<AdddriverResponnse> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(rootView, call.request().headers().get("meta"), Snackbar.LENGTH_LONG);
                snackbar.show();
                hideProgressDialog();
            }
        });
    }

    public void SetDriverField(String drivername, String driverEmail, String driverMobile, String driverPic)
    {
        if (drivername != null)
            textDriverName.setText(drivername);
        if (driverEmail != null)
            textDriverEmail.setText(driverEmail);
        if (driverMobile != null)
            textDriverPhone.setText(driverMobile);
        if (driverPic != null)
            Picasso.with(TruckEditUpdateActivity.this)
                    .load(new File(driverPic))
                    .placeholder(R.drawable.img_circle_placeholder)
                    .resize(60, 60)
                    .centerCrop()
                    .transform(new CircleTransformation())
                    .into(ivDriverProfilePhoto);
    }


}
