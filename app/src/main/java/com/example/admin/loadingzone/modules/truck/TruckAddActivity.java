package com.example.admin.loadingzone.modules.truck;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.MakerResponse;
import com.example.admin.loadingzone.retrofit.model.Model;
import com.example.admin.loadingzone.retrofit.model.ModelResponse;
import com.example.admin.loadingzone.retrofit.model.TruckAddResponse;
import com.example.admin.loadingzone.retrofit.model.TruckType;
import com.example.admin.loadingzone.retrofit.model.TruckTypeResponse;
import com.example.admin.loadingzone.retrofit.model.TruckYearResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleDetailsResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleMaker;
import com.example.admin.loadingzone.retrofit.model.VehicleYear;
import com.example.admin.loadingzone.app.Config;
import com.example.admin.loadingzone.view.RevealBackgroundView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckAddActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private static String NEW_TRUCK_ADD = "NewTruck";
    private static String TRUCK_UPDATE = "UpdateTruck";

    public static void startTruckAddActvity(int[] startingLocation, Activity startingActivity, String isFrom) {
        Intent intent = new Intent(startingActivity, TruckAddActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        intent.putExtra("isFrom", isFrom);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startingActivity.startActivity(intent);
    }

    @BindView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @BindView(R.id.ivTruckMaker)
    ImageView imageViewTruckMaker;
    @NonNull
    @BindView(R.id.textTruckMaker)
    TextView textViewTrckMaker;
    @BindView(R.id.ivTruckModel)
    ImageView imageViewTruckModel;
    @NonNull
    @BindView(R.id.textTruckModel)
    TextView textViewTrckModel;
    @BindView(R.id.ivTruckType)
    ImageView imageViewTruckType;
    @NonNull
    @BindView(R.id.textTruckType)
    TextView textViewTrckType;
    @BindView(R.id.ivTruckYear)
    ImageView imageViewTruckYear;
    @NonNull
    @BindView(R.id.textTruckYear)
    TextView textViewTrckYear;
    @NonNull
    @BindView(R.id.editTruckCustomName)
    EditText editTextTrcukCutsomName;
    @NonNull
    @BindView(R.id.editTruckRegistrationNo)
    EditText editTextTruckRegistrationNo;

    @NonNull
    @BindView(R.id.editTruckChasisNo)
    EditText editTextTruckChasisNo;

    @NonNull
    @BindView(R.id.editTruckLicenceNo)
    EditText editTextTruckLicenceNo;
    @NonNull
    @BindView(R.id.editTruckInsurance)
    TextView editTextViewTrcukInsurance;
    @NonNull
    @BindView(R.id.editTruckAvgSpeed)
    EditText editTextTrcukAvgSpeed;
    @NonNull
    @BindView(R.id.editTruckWeight)
    EditText editTextTrcukWeight;
    @NonNull
    @BindView(R.id.editTruckLength)
    EditText editTextTrcukLength;
    @NonNull
    @BindView(R.id.editTruckHeight)
    EditText editTextTrcukHeight;
    @NonNull
    @BindView(R.id.editTruckWidth)
    EditText editTextTrcukWidth;

    @BindView(R.id.fabTruckAdd)
    FloatingActionButton fabAddTruck;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    ApiInterface apiService;
    private TruckNameListAdapter truckNameListAdapter;
    private TruckModelListAdapter truckModelListAdapter;
    private TruckTypeListAdapter truckTypeListAdapter;
    private TruckYearListAdapter truckYearListAdapter;
    private List<VehicleMaker> truckNameList = new ArrayList<>();
    private List<Model> truckModelList = new ArrayList<>();
    private List<TruckType> truckTypeList = new ArrayList<>();
    private List<VehicleYear> truckYearList = new ArrayList<>();
    String model_id, truck_typeId, model_year, isFrom, provider_vehicle_id,reg_no, chassis_no, License_no;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_add);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.truck_add_title);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        getTruckMakerList();
        getTruckTypeList();
        getTruckYearList();
        isFrom = getIntent().getStringExtra("isFrom");
        provider_vehicle_id = getIntent().getStringExtra("provider_vehicle_id");
        if (isFrom.equals(NEW_TRUCK_ADD)) {
            setupRevealBackground(savedInstanceState);
        }
        if (isFrom.equals(TRUCK_UPDATE)) {
            reg_no = getIntent().getStringExtra("reg_no");
            chassis_no = getIntent().getStringExtra("chassis_no");
            License_no = getIntent().getStringExtra("License_no");
//            truck_typeId=getIntent().getStringExtra("truck_typeId");
//            model_id=getIntent().getStringExtra("model_id");
//            model_year=getIntent().getStringExtra("model_year");

            getTruckDetails(provider_vehicle_id);
        }
//        if (isFrom.equals("TruckView")) {
//            getSupportActionBar().setTitle("Truck Details");
//            fabAddTruck.setVisibility(View.GONE);
//            editTextTrcukCutsomName.setFocusable(true);
//            editTextTrcukAvgSpeed.setFocusable(false);
//            editTextViewTrcukInsurance.setFocusable(false);
//            editTextTrcukWeight.setFocusable(false);
//            editTextTrcukLength.setFocusable(false);
//        }
    }

    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {

        } else {

        }
    }

    // select truck
    @OnClick(R.id.ivTruckMaker)
    public void selectTruck() {

        showbottomTrckName();

    }

    @OnClick(R.id.editTruckInsurance)
    public void PickInsurancedate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        editTextViewTrcukInsurance.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    // select model
    @OnClick(R.id.ivTruckModel)
    public void selectModel() {
        showbottomTrckModel();
    }

    // select truckType
    @OnClick(R.id.ivTruckType)
    public void selectTruckType() {
        showbottomTrckType();

    }

    // select truckyear
    @OnClick(R.id.ivTruckYear)
    public void selectTruckYear() {
        showbottomTrckYear();
    }

    // Add new Truck
    @OnClick(R.id.fabTruckAdd)
    public void addTruck() {

        String avg_running_speed = editTextTrcukAvgSpeed.getText().toString().trim();
        String custom_name = editTextTrcukCutsomName.getText().toString().trim();

        String reg_no = editTextTruckRegistrationNo.getText().toString().trim();
        String chasisNo = editTextTruckChasisNo.getText().toString().trim();
        String LicNo = editTextTruckLicenceNo.getText().toString().trim();

        String insurance_exp_date = editTextViewTrcukInsurance.getText().toString().trim();
        String weight = editTextTrcukWeight.getText().toString().trim();
        String container_length = editTextTrcukLength.getText().toString().trim();
        String container_width = editTextTrcukWidth.getText().toString().trim();
        String container_height = editTextTrcukHeight.getText().toString().trim();
        if (isFrom.equals("NewTruck")) {
            if (isConnectingToInternet(getApplicationContext())) {
                AddTruck(avg_running_speed, custom_name, insurance_exp_date, weight, container_length, container_width, container_height, model_id, truck_typeId, model_year, reg_no, chasisNo, LicNo);
            } else {
                showSnakBar(rootView, MessageConstants.INTERNET);


            }
        } else {
            if (!provider_vehicle_id.equals(null) && provider_vehicle_id.length() > 0) {
                if (isConnectingToInternet(getApplicationContext())) {
                    UpdateTruck(provider_vehicle_id, avg_running_speed, custom_name, insurance_exp_date, weight, container_length, container_width, container_height, model_id, truck_typeId, model_year, reg_no, chasisNo, LicNo);
                } else {
                    showSnakBar(rootView, MessageConstants.INTERNET);
                }
            }
        }
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        //vRevealBackground.setFillPaintColor(0xFF16121a);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    private void showbottomTrckName() {
        final LayoutInflater li = LayoutInflater.from(TruckAddActivity.this);
        final View view = li.inflate(R.layout.trck_select, null);
        final Dialog mBottomSheetDialog = new Dialog(TruckAddActivity.this, R.style.MaterialDialogSheet);
        EndlessRecyclerView endlessRecyclerViewTrcuk = (EndlessRecyclerView) view.findViewById(R.id.recyclerViewTruckName);
        // Recyclerview Layout manager
        endlessRecyclerViewTrcuk.setLayoutManager(new GridLayoutManager(this, 2));
        endlessRecyclerViewTrcuk.setHasFixedSize(true);
        endlessRecyclerViewTrcuk.setNestedScrollingEnabled(true);
        truckNameListAdapter = new TruckNameListAdapter(getApplicationContext(), truckNameList);
        endlessRecyclerViewTrcuk.setAdapter(truckNameListAdapter);
        truckNameListAdapter.notifyDataSetChanged();
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        endlessRecyclerViewTrcuk.addOnItemTouchListener(new RecyclerItemClickListener(TruckAddActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                int trick_id = truckNameList.get(position).getMakerId();
                getTruckModelList(trick_id);
                {
                    textViewTrckMaker.setText(truckNameList.get(position).getMakerName());
                }

                if (isFrom.equals(TRUCK_UPDATE))
                {
                    textViewTrckMaker.setText(truckNameList.get(position).getMakerName());
                    textViewTrckModel.setText("");
                    textViewTrckType.setText("");
                    textViewTrckYear.setText("");
                    model_id="";
                    model_year="";
                    truck_typeId="";
                }
                mBottomSheetDialog.dismiss();

            }
        }));
    }

    private void showbottomTrckModel() {
        final LayoutInflater li = LayoutInflater.from(TruckAddActivity.this);
        final View view = li.inflate(R.layout.trck_model, null);
        final Dialog mBottomSheetDialog = new Dialog(TruckAddActivity.this, R.style.MaterialDialogSheet);
        EndlessRecyclerView endlessRecyclerViewTrcukModel = (EndlessRecyclerView) view.findViewById(R.id.recyclerViewTruckModel);
        // Recyclerview Layout manager
        endlessRecyclerViewTrcukModel.setLayoutManager(new GridLayoutManager(this, 2));
        endlessRecyclerViewTrcukModel.setHasFixedSize(true);
        endlessRecyclerViewTrcukModel.setNestedScrollingEnabled(true);
        truckModelListAdapter = new TruckModelListAdapter(getApplicationContext(), truckModelList);
        endlessRecyclerViewTrcukModel.setAdapter(truckModelListAdapter);
        truckModelListAdapter.notifyDataSetChanged();
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        endlessRecyclerViewTrcukModel.addOnItemTouchListener(new RecyclerItemClickListener(TruckAddActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                textViewTrckModel.setText(truckModelList.get(position).getModelName());
                model_id = truckModelList.get(position).getModelId();
                mBottomSheetDialog.dismiss();

            }
        }));
    }

    private void showbottomTrckType() {
        final LayoutInflater li = LayoutInflater.from(TruckAddActivity.this);
        final View view = li.inflate(R.layout.trck_type, null);
        final Dialog mBottomSheetDialog = new Dialog(TruckAddActivity.this, R.style.MaterialDialogSheet);
        EndlessRecyclerView endlessRecyclerViewTrcukType = (EndlessRecyclerView) view.findViewById(R.id.recyclerViewTruckType);
        // Recyclerview Layout manager
        endlessRecyclerViewTrcukType.setLayoutManager(new GridLayoutManager(this, 2));
        endlessRecyclerViewTrcukType.setHasFixedSize(true);
        endlessRecyclerViewTrcukType.setNestedScrollingEnabled(true);
        truckTypeListAdapter = new TruckTypeListAdapter(getApplicationContext(), truckTypeList);
        endlessRecyclerViewTrcukType.setAdapter(truckTypeListAdapter);
        truckTypeListAdapter.notifyDataSetChanged();

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        endlessRecyclerViewTrcukType.addOnItemTouchListener(new RecyclerItemClickListener(TruckAddActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                textViewTrckType.setText(truckTypeList.get(position).getTruckTypeName());
                truck_typeId = truckTypeList.get(position).getTruckTypeId();
                mBottomSheetDialog.dismiss();

            }
        }));
    }

    private void showbottomTrckYear() {
        final LayoutInflater li = LayoutInflater.from(TruckAddActivity.this);
        final View view = li.inflate(R.layout.trck_year, null);
        final Dialog mBottomSheetDialog = new Dialog(TruckAddActivity.this, R.style.MaterialDialogSheet);
        EndlessRecyclerView endlessRecyclerViewTrcukYear = (EndlessRecyclerView) view.findViewById(R.id.recyclerViewTruckYear);
        // Recyclerview Layout manager
        endlessRecyclerViewTrcukYear.setLayoutManager(new GridLayoutManager(this, 2));
        endlessRecyclerViewTrcukYear.setHasFixedSize(true);
        endlessRecyclerViewTrcukYear.setNestedScrollingEnabled(true);
        truckYearListAdapter = new TruckYearListAdapter(getApplicationContext(), truckYearList);
        endlessRecyclerViewTrcukYear.setAdapter(truckYearListAdapter);
        truckYearListAdapter.notifyDataSetChanged();

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        endlessRecyclerViewTrcukYear.addOnItemTouchListener(new RecyclerItemClickListener(TruckAddActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                textViewTrckYear.setText(truckYearList.get(position).getYear());
                model_year = truckYearList.get(position).getYear();
                mBottomSheetDialog.dismiss();

            }
        }));
    }

    //:---------------------------------------------- api calls-----------------------------://
    //    // api call method for truck maker list
    private void getTruckMakerList() {
        showProgressDialog(TruckAddActivity.this, "Loading..");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<MakerResponse> call = apiService.TrckMakerList(GloablMethods.API_HEADER + acess_token);
        call.enqueue(new Callback<MakerResponse>() {


            @Override
            public void onResponse(Call<MakerResponse> call, retrofit2.Response<MakerResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    truckNameList = response.body().getVehicleMaker();

                }
            }

            @Override
            public void onFailure(Call<MakerResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    // api call method for truck model list
    private void getTruckModelList(int maker_id) {
        showProgressDialog(TruckAddActivity.this, "Loading..");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<ModelResponse> call = apiService.TrckModelList(GloablMethods.API_HEADER + acess_token, maker_id);
        call.enqueue(new Callback<ModelResponse>() {


            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    truckModelList = response.body().getModel();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    // api call method for truck type list
    private void getTruckTypeList() {
        //   showProgressDialog(TruckAddActivity.this, "Loading Profile..");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckTypeResponse> call = apiService.TruckTypeList(GloablMethods.API_HEADER + acess_token);
        call.enqueue(new Callback<TruckTypeResponse>() {


            @Override
            public void onResponse(Call<TruckTypeResponse> call, retrofit2.Response<TruckTypeResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    truckTypeList = response.body().getTruckType();
                }
            }

            @Override
            public void onFailure(Call<TruckTypeResponse> call, Throwable t) {
                //        hideProgressDialog();
            }
        });
    }

    // api call method for truck type list
    private void getTruckYearList() {
        //showProgressDialog(TruckAddActivity.this, "Loading Profile..");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckYearResponse> call = apiService.TruckYearList(GloablMethods.API_HEADER + acess_token);
        call.enqueue(new Callback<TruckYearResponse>() {


            @Override
            public void onResponse(Call<TruckYearResponse> call, retrofit2.Response<TruckYearResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    truckYearList = response.body().getVehicleYear();
                }
            }

            @Override
            public void onFailure(Call<TruckYearResponse> call, Throwable t) {
                //       hideProgressDialog();
            }
        });
    }
    //:-----------Truck Add Api-------------:\\

    private void AddTruck(String avg_running_speed, String custom_name, String insurance_exp_date, String weight,
                          String container_length, String container_width, String container_height, String model_id, String truck_type_id, String model_year, String reg_no, String chasisNo, String LicNo) {
        showProgressDialog(TruckAddActivity.this, "Add Truck,please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        //  String device_token = pref.getString("regId", null);
        Call<TruckAddResponse> call = apiService.TruckAdd(GloablMethods.API_HEADER + acess_token, avg_running_speed, custom_name, insurance_exp_date, weight, container_length, container_width, container_height, model_id, truck_type_id, model_year, reg_no, chasisNo, LicNo);
        call.enqueue(new Callback<TruckAddResponse>() {
            @Override
            public void onResponse(Call<TruckAddResponse> call, Response<TruckAddResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    String vehicle_id = response.body().getProviderVehicleId();
                    Intent i = new Intent(TruckAddActivity.this, TruckDocumentAddActivity.class);
                    i.putExtra("vehicle_id", vehicle_id);
                    i.putExtra("isFrom","NewDoc");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showSnakBar(rootView, "Truck Added Successfully");
                    startActivity(i);
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
            public void onFailure(Call<TruckAddResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();

            }
        });
    }
    //:-----------Truck Update Api-------------:\\

    private void UpdateTruck(String vehicle_id, String avg_running_speed, String custom_name, String insurance_exp_date, String weight,
                             String container_length, String container_width, String container_height, String model_id, String truck_type_id, String model_year, String reg_no, String chasisNo, String LicNo) {
        showProgressDialog(TruckAddActivity.this, "please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        //  String device_token = pref.getString("regId", null);
        Call<TruckAddResponse> call = apiService.TruckUpdate(GloablMethods.API_HEADER + acess_token, vehicle_id, avg_running_speed, custom_name, insurance_exp_date, weight, container_length, container_width, container_height, model_id, truck_type_id, model_year, reg_no, chasisNo, LicNo);
        call.enqueue(new Callback<TruckAddResponse>() {
            @Override
            public void onResponse(Call<TruckAddResponse> call, Response<TruckAddResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful())

                {
                    String vehicle_id = response.body().getProviderVehicleId();
                    Intent i = new Intent(TruckAddActivity.this, TruckDocumentEditActivity.class);
                    i.putExtra("provider_vehicle_id", provider_vehicle_id);
                    i.putExtra("vehicle_id", vehicle_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showSnakBar(rootView, "Truck Updated Successfully");
                    startActivity(i);
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
            public void onFailure(Call<TruckAddResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();

            }
        });
    }

// getting the existing truck details

private void getTruckDetails(String provider_vehicle_id) {
    showProgressDialog(TruckAddActivity.this, "please wait...");
    apiService =
            ApiClient.getClient().create(ApiInterface.class);
    String acess_token = AppController.getString(getApplicationContext(), "acess_token");
    Call<VehicleDetailsResponse> call = apiService.GetTruckDetails(GloablMethods.API_HEADER + acess_token, provider_vehicle_id);
    call.enqueue(new Callback<VehicleDetailsResponse>() {
        @Override
        public void onResponse(Call<VehicleDetailsResponse> call, Response<VehicleDetailsResponse> response) {
            hideProgressDialog();
            if (response.isSuccessful()) {
                textViewTrckMaker.setText(response.body().getVehicle().getManufacturer().getMakerName());
                textViewTrckModel.setText(response.body().getVehicle().getModel().getModelName());
                textViewTrckType.setText(response.body().getVehicle().getTruckType().getTruckTypeName());
                textViewTrckYear.setText(response.body().getVehicle().getModelYear());
                editTextTrcukCutsomName.setText(response.body().getCustomName());
                editTextTrcukAvgSpeed.setText(response.body().getAvgRunningSpeed());
                editTextViewTrcukInsurance.setText(response.body().getInsuranceExpDate());
                editTextTruckRegistrationNo.setText(reg_no);
                editTextTruckChasisNo.setText(chassis_no);
                editTextTruckLicenceNo.setText(License_no);
                editTextTrcukWeight.setText(response.body().getWeight() + "");
                truck_typeId=response.body().getVehicle().getTruckType().getTruckTypeId();
                model_year=response.body().getVehicle().getModelYear();
                model_id=response.body().getVehicle().getModel().getModelId();
                String dimensions=response.body().getVehicle().getDimension();
                if (dimensions!=null ||!dimensions.equals(""))
                {
                    String[] separated = dimensions.split("x");
                    String height_=separated[0];
                    String[] height_coma = height_.split("'");
                    String height=height_coma[0];
                    String length_=separated[1];
                    String[] length_coma = length_.split("'");
                    String length=length_coma[0];
                    String width_=separated[2];
                    String[] width_coma = width_.split("'");
                    String width=width_coma[0];
                    editTextTrcukHeight.setText(height);
                    editTextTrcukLength.setText(length);
                    editTextTrcukWidth.setText(width);
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
}
