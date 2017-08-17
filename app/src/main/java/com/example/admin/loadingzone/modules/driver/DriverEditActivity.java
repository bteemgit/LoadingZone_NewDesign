package com.example.admin.loadingzone.modules.driver;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.driver.DriverAddActivity;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.modules.home.QuotationApply;
import com.example.admin.loadingzone.modules.profile.UserProfileActivity;
import com.example.admin.loadingzone.permission.PermissionsChecker;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.QutationApplyResponse;
import com.example.admin.loadingzone.retrofit.model.TruckDriverAddResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.example.admin.loadingzone.view.RevealBackgroundView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.xmlpull.v1.sax2.Driver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverEditActivity extends BaseActivity {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    /**
     * Permission List
     */
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    public static void startdriverAddActvity(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, DriverAddActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startingActivity.startActivity(intent);
    }

    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    @NonNull
    @BindView(R.id.relative_submit)
    LinearLayout relativeLayoutSubmit;
    @NonNull
    @BindView(R.id.linerUpdate)
    LinearLayout linerUpdate;
    @NonNull
    @BindView(R.id.linearDelete)
    LinearLayout linearDelete;
    @BindView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @BindView(R.id.ivDriverProfilePhoto)
    ImageView imageViewDriverProfileImage;
    @NonNull
    @BindView(R.id.btnEditProfilePic)
    Button btnEditProfilePic;
    @NonNull
    @BindView(R.id.btnEditProfilePicUpload)
    Button btnEditProfilePicUpload;
    @NonNull
    @BindView(R.id.fabDriverAddDetails)
    FloatingActionButton fabDriverAdd;
//    @NonNull
//    @BindView(R.id.fabDriverEdit)
//    FloatingActionButton fabDriverEdit;
    @NonNull
    @BindView(R.id.editDriverEmail)
    EditText editTextDriverEmail;
    @NonNull
    @BindView(R.id.editDriverName)
    EditText editTextDriverName;
    @NonNull
    @BindView(R.id.editDriverLocation)
    EditText editTextDriverAdress;
    @NonNull
    @BindView(R.id.editDriverMobile)
    EditText editTextDriverMobile;

    @NonNull
    @BindView(R.id.editJoinedDate)
    EditText editTextJoinedDate;

    @NonNull
    @BindView(R.id.editAssignedTruck)
    EditText editTextAssignedTruck;


    private ApiInterface apiService;
    private int avatarSize;
    String imagePath;
    PermissionsChecker checker;
    String driver_name, driver_id, driver_email, driver_mobile, driver_adress, isFrom,profile_pic,driverJoinedDate,currentlyAssignedTruck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_edit);
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Manage Driver");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        checker = new PermissionsChecker(this);
        // :-fetching the details from userprofile activity
        // if isFrom Drvier view then here we need to show the driver details and then admin can edit the driver from here
        // (Driver add and edit are handeld by in this class)
        isFrom = getIntent().getStringExtra("isFrom");
        driver_name = getIntent().getStringExtra("driver_name");
        driver_id = getIntent().getStringExtra("driver_id");
        driver_email = getIntent().getStringExtra("driver_email");
        driver_mobile = getIntent().getStringExtra("driver_mobile");
        driver_adress = getIntent().getStringExtra("driver_adress");
        profile_pic=getIntent().getStringExtra("profile_pic");


        driverJoinedDate = getIntent().getStringExtra("driverJoinedDate");
        currentlyAssignedTruck = getIntent().getStringExtra("currentlyAssignedTruck");

        Picasso.with(DriverEditActivity.this)
                .load(profile_pic)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(imageViewDriverProfileImage
                );
        if (!isFrom.equals(null) & isFrom.equals("driverView")) {
            relativeLayoutSubmit.setVisibility(View.VISIBLE);
            fabDriverAdd.setVisibility(View.GONE);
            btnEditProfilePic.setVisibility(View.GONE);
            btnEditProfilePicUpload.setVisibility(View.GONE);
            showDetailsFromDriverViewActivity(driver_name, driver_email, driver_mobile, driver_adress,driverJoinedDate,currentlyAssignedTruck);
        }

    }

    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void showDetailsFromDriverViewActivity(String driver_name, String driver_email, String driver_mobile, String driver_adress,String driver_joinedDate,String currentlyAssignedTruck) {


        if (!driver_name.equals(null))
            editTextDriverName.setText(driver_name);
        if (!driver_email.equals(null))
            editTextDriverEmail.setText(driver_email);
        if (!driver_mobile.equals(null))
            editTextDriverMobile.setText(driver_mobile);
        if (!driver_adress.equals(null))
            editTextDriverAdress.setText(driver_adress);

        if (!driver_adress.equals(null))
            editTextJoinedDate.setText(driver_joinedDate);
        if (!driver_adress.equals(null))
            editTextAssignedTruck.setText(currentlyAssignedTruck);
    }
// update the driver
    @OnClick(R.id.linerUpdate)
    public void editDriver() {
        getSupportActionBar().setTitle("Edit Driver");
        fabDriverAdd.setVisibility(View.VISIBLE);
        relativeLayoutSubmit.setVisibility(View.GONE);
        btnEditProfilePic.setVisibility(View.VISIBLE);

    }

    // Add new Driver
    @NonNull
    @OnClick(R.id.fabDriverAddDetails)
    public void addTruck() {
        String driverEmail = editTextDriverEmail.getText().toString().trim();
        String driverName = editTextDriverName.getText().toString().trim();
        String driverAdress = editTextDriverAdress.getText().toString().trim();
        String driverMobile = editTextDriverMobile.getText().toString().trim();
        if (isConnectingToInternet(DriverEditActivity.this)) {
            UpdateDriver(driver_id,driverName, driverMobile, driverEmail, driverAdress);
        } else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }

    }
@NonNull
@OnClick(R.id.linearDelete)
public void deleteDriver()
{
    if (driver_id!=null)
    DeleteDriver(driver_id);
}
   //:-----------Driver Update Api-------------:\\

    private void UpdateDriver(String driver_id,String driver_name, String driver_phone, String driver_email, String driver_address) {
        showProgressDialog(DriverEditActivity.this, "Updating Driver,please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckDriverAddResponse> call = apiService.UpdateDriver(GloablMethods.API_HEADER + acess_token,driver_id, driver_name, driver_phone, driver_email, driver_address);
        call.enqueue(new Callback<TruckDriverAddResponse>() {
            @Override
            public void onResponse(Call<TruckDriverAddResponse> call, Response<TruckDriverAddResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful())

                {
                    showSnakBar(relativeLayoutRoot, "Driver Added Successfully");
                    Intent i = new Intent(DriverEditActivity.this, DriverViewActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(relativeLayoutRoot, meta.getString("message"));
                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<TruckDriverAddResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();

            }
        });
    }


    private void DeleteDriver(String driver_id) {
        showProgressDialog(DriverEditActivity.this, "please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckDriverAddResponse> call = apiService.DeleteDriver(GloablMethods.API_HEADER + acess_token,driver_id);
        call.enqueue(new Callback<TruckDriverAddResponse>() {
            @Override
            public void onResponse(Call<TruckDriverAddResponse> call, Response<TruckDriverAddResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    Snackbar snackbar = Snackbar
                            .make(relativeLayoutRoot,"Deleted", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Intent i = new Intent(DriverEditActivity.this, HomeActivity.class);
                        startActivity(i);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(relativeLayoutRoot, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
                }

            @Override
            public void onFailure(Call<TruckDriverAddResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(relativeLayoutRoot, call.request().headers().get("meta"), Snackbar.LENGTH_LONG);
                snackbar.show();
                hideProgressDialog();


            }
        });
    }
}
