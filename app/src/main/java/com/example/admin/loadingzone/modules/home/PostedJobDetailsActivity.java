package com.example.admin.loadingzone.modules.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Transition;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.SplashActivity;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.modules.login.LoginActivity;
import com.example.admin.loadingzone.modules.myjob.StartJobActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileEditActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.JobLoaddetailsResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.github.fabtransitionactivity.SheetLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.admin.loadingzone.R.id.ivUserProfilePhoto;
import static com.example.admin.loadingzone.R.id.rootView;
import static com.example.admin.loadingzone.R.id.textTruckType;

public class PostedJobDetailsActivity extends BaseActivity implements SheetLayout.OnFabAnimationEndListener {
    @NonNull
    @BindView(R.id.textCustomerName)
    TextView textViewCutomerName;
    @NonNull
    @BindView(R.id.textCustomerEmail)
    TextView textViewCutomerEmail;
    @NonNull
    @BindView(R.id.textCustomerMobile)
    TextView textViewCutomerMobile;
    @NonNull
    @BindView(R.id.text_From)
    TextView textViewJob_From;
    @NonNull
    @BindView(R.id.text_To)
    TextView textViewJob_To;
    @NonNull
    @BindView(R.id.textTotalDist)
    TextView textViewJobTotalDist;
    @NonNull
    @BindView(R.id.textRequestedDate)
    TextView textViewRequestedDate;
    @NonNull
    @BindView(R.id.textJobDate)
    TextView textViewJobDate;
    @NonNull
    @BindView(R.id.textQutoation)
    TextView textViewQutoation;
    @NonNull
    @BindView(R.id.textLoadingMaterial)
    TextView textViewLoadingMaterial;
    @NonNull
    @BindView(R.id.textLoadingMat_Weight)
    TextView textLoadingMat_Weight;
    @NonNull
    @BindView(R.id.textBudget)
    TextView textViewBudget;
    @NonNull
    @BindView(R.id.textTotalDistance)
    TextView textViewTotalDistance;
    @NonNull
    @BindView(R.id.textJobFrom)
    TextView textViewJobFrom;
    @NonNull
    @BindView(R.id.texJobTo)
    TextView textViewJobTo;
    @NonNull
    @BindView(R.id.texLoadingDate)
    TextView textViewLoadingDate;
    @NonNull
    @BindView(R.id.textTruckSize)
    TextView textViewTruckSize;
    @NonNull
    @BindView(R.id.textTruckType)
    TextView textViewTruckType;
    @NonNull
    @BindView(R.id.textPaymentMode)
    TextView textViewPaymentMode;
    @NonNull
    @BindView(R.id.textCurrency)
    TextView textViewCurrency;
    @NonNull
    @BindView(R.id.bottom_sheet)
    SheetLayout mSheetLayout;
    @NonNull
    @BindView(R.id.fabQuotation)
    FloatingActionButton fabQuotationApply;
    @NonNull
    @BindView(R.id.ivCustomerProfilePhoto)
    ImageView imageViewCustomPic;
    @NonNull
    @BindView(R.id.vUserStats)
    LinearLayout linerUserstaus;
    @NonNull
    @BindView(R.id.btnStartjob)
    Button buttonJobStart;

    @NonNull
    @BindView(R.id.linear_assignedVehehicle_heading)
    LinearLayout linearAssignedVehicleHeading;
    @NonNull
    @BindView(R.id.linear_assignedDriver_heading)
    LinearLayout linear_assignedDriver_heading;
    @NonNull
    @BindView(R.id.liner_JobStatus_heading)
    LinearLayout liner_JobStatus_heading;
    @NonNull
    @BindView(R.id.linearJobstatusItem)
    LinearLayout linearJobstatusItem;
    @NonNull
    @BindView(R.id.linearAssignedDriverItem)
    LinearLayout linearAssignedDriverItem;
    @NonNull
    @BindView(R.id.linearAssignedVehicleItem)
    LinearLayout linearAssignedVehicleItem;
    @NonNull
    @BindView(R.id.ivEditVechicle)
    ImageView imageViewEditAssignedVehicle;
    @NonNull
    @BindView(R.id.ivEditDriver)
    ImageView imageViewEditAssignedDriver;
    @NonNull
    @BindView(R.id.textCustomVechicleName)
    TextView textCustomVechicleName;
    @NonNull
    @BindView(R.id.textAssignedTruckType)
    TextView textAssignedTruckType;
    @NonNull
    @BindView(R.id.textStartDate)
    TextView textStartDate;
    @NonNull
    @BindView(R.id.textStartTime)
    TextView textStartTime;
    @NonNull
    @BindView(R.id.textEndDate)
    TextView textEndDate;
    @NonNull
    @BindView(R.id.textEndTime)
    TextView textEndTime;
    @NonNull
    @BindView(R.id.textDriverName)
    TextView textDriverName;
    @NonNull
    @BindView(R.id.textDriverEmail)
    TextView textDriverEmail;
    @NonNull
    @BindView(R.id.textDriverMobile)
    TextView textDriverMobile;
    @NonNull
    @BindView(R.id.textVehicleRunningStatus)
    TextView textVehicleRunningStatus;
    @NonNull
    @BindView(R.id.textVehicleLocation)
    TextView textVehicleLocation;
    @NonNull
    @BindView(R.id.textLastUpdatedDate)
    TextView textLastUpdatedDate;
    @NonNull
    @BindView(R.id.textLastUpdatedTime)
    TextView textLastUpdatedTime;
    @NonNull
    @BindView(R.id.rootview)
    RelativeLayout rootView;
    private static int REQUEST_CODE = 41;
    String JobId, isFrom, job_status_code;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        JobId = getIntent().getStringExtra("JobId");
        isFrom = getIntent().getStringExtra("isFrom");
        String profilepic = getIntent().getStringExtra("profilepic");
        String CutomerName = getIntent().getStringExtra("name");
        String CutomerEmail = getIntent().getStringExtra("email");
        String CutomerMobile = getIntent().getStringExtra("phone1");
        String Job_From = getIntent().getStringExtra("FromLoc_name");
        String Job_To = getIntent().getStringExtra("ToLoc_name");
        String JobTotalDist = getIntent().getStringExtra("LocationDistance");
        String RequestedDate = getIntent().getStringExtra("DateRequested");
        String JobDate = getIntent().getStringExtra("DateOfLoading");
        String Qutoation = getIntent().getStringExtra("QuotationCount");
        String LoadingMaterial = getIntent().getStringExtra("Material_name");
        String LoadingMat_Weight = getIntent().getStringExtra("weight");
        String materialDescription = getIntent().getStringExtra("MaterialDescription");
        String TotalDistance = getIntent().getStringExtra("LocationDistance");
        String JobFrom = getIntent().getStringExtra("FromLoc_name");
        String JobTo = getIntent().getStringExtra("ToLoc_name");
        String LoadingDate = getIntent().getStringExtra("DateOfLoading");
        String TruckSize = getIntent().getStringExtra("TruckSize_dimension");
        String TruckType = getIntent().getStringExtra("TruckType_name");
        String PaymentMode = getIntent().getStringExtra("PaymentType_name");
        String Currency = getIntent().getStringExtra("Currency_name");
        job_status_code = getIntent().getStringExtra("job_status_code");
        Log.d("job_status_code", job_status_code);
        if (isFrom.equals("Home")) {
            linerUserstaus.setVisibility(View.VISIBLE);
            fabQuotationApply.setVisibility(View.VISIBLE);
            buttonJobStart.setVisibility(View.GONE);
        }
        if (isFrom.equals("Pendingjob")) {
            linerUserstaus.setVisibility(View.GONE);
            fabQuotationApply.setVisibility(View.GONE);
            //here some widgets visibility handled by according to the job status code is quoations accepted
            if (job_status_code.equals("quotation-accepted")) {
                buttonJobStart.setVisibility(View.VISIBLE);
                linearAssignedVehicleHeading.setVisibility(View.GONE);
                linear_assignedDriver_heading.setVisibility(View.GONE);
                liner_JobStatus_heading.setVisibility(View.GONE);
                linearAssignedDriverItem.setVisibility(View.GONE);
                linearAssignedVehicleItem.setVisibility(View.GONE);
                linearJobstatusItem.setVisibility(View.GONE);

            } else {
                buttonJobStart.setVisibility(View.GONE);
                // getting the loading details from the curesponding job
                getLoadingJobDeatails(JobId);
                linearAssignedVehicleHeading.setVisibility(View.VISIBLE);
                linear_assignedDriver_heading.setVisibility(View.VISIBLE);
                linearAssignedDriverItem.setVisibility(View.VISIBLE);
                linearAssignedVehicleItem.setVisibility(View.VISIBLE);
                linearJobstatusItem.setVisibility(View.VISIBLE);
                liner_JobStatus_heading.setVisibility(View.VISIBLE);
            }

        }
        textViewCutomerName.setText(CutomerName);
        textViewCutomerEmail.setText(CutomerEmail);
        textViewCutomerMobile.setText(CutomerMobile);
        textViewJob_From.setText(Job_From);
        textViewJob_To.setText(Job_To);
        textViewJobTotalDist.setText(JobTotalDist);
        textViewRequestedDate.setText(RequestedDate);
        textViewJobDate.setText(JobDate);
        textViewQutoation.setText(Qutoation);
        textViewLoadingMaterial.setText(LoadingMaterial);
        textLoadingMat_Weight.setText(LoadingMat_Weight);
        textViewBudget.setText(materialDescription);
        textViewTotalDistance.setText(TotalDistance);
        textViewJobFrom.setText(JobFrom);
        textViewJobTo.setText(JobTo);
        textViewLoadingDate.setText(LoadingDate);
        textViewTruckSize.setText(TruckSize);
        textViewTruckType.setText(TruckType);
        textViewPaymentMode.setText(PaymentMode);
        textViewCurrency.setText(Currency);
        Picasso.with(PostedJobDetailsActivity.this)
                .load(profilepic)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(88, 88)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(imageViewCustomPic);
        // fab click & animation
        mSheetLayout.setFab(fabQuotationApply);
        mSheetLayout.setFabAnimationEndListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.fabQuotation)
    public void fabQuotationApply() {
        mSheetLayout.expandFab();

    }

    @NonNull
    @OnClick(R.id.btnStartjob)
    public void startJob() {
        Intent intent = new Intent(getApplicationContext(), StartJobActivity.class);
        intent.putExtra("JobId", JobId);
        startActivity(intent);

    }


    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(getApplicationContext(), QuotationApply.class);
        intent.putExtra("JobId", JobId);
        intent.putExtra("qutation_id", "new");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            mSheetLayout.contractFab();
        }
    }
// gettin the loading details and assigned driver details
    public void getLoadingJobDeatails(String job_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<JobLoaddetailsResponse> call = apiService.GetLoadingDetails(GloablMethods.API_HEADER + acess_token, job_id);
        call.enqueue(new Callback<JobLoaddetailsResponse>() {
            @Override
            public void onResponse(Call<JobLoaddetailsResponse> call, retrofit2.Response<JobLoaddetailsResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true) || response.body().getMeta().getStatus().equals("true")) {

                        textCustomVechicleName.setText(response.body().getAssignedVehicle().getVehicleDetails().getCustomName());
                        textAssignedTruckType.setText(response.body().getAssignedVehicle().getVehicleDetails().getVehicle().getTruckType().getTruckTypeName());
                        textStartDate.setText(response.body().getAssignedVehicle().getJobStartingDate());
                        textEndDate.setText(response.body().getAssignedVehicle().getExpectedEndDate());
                        textEndTime.setText(response.body().getAssignedVehicle().getExpectedEndTime());
                        textStartTime.setText(response.body().getAssignedVehicle().getExpectedStartTime());
                        textDriverName.setText(response.body().getAssignedDriver().getDriverName());
                        textDriverEmail.setText(response.body().getAssignedDriver().getDriverEmail());
                        textDriverMobile.setText(response.body().getAssignedDriver().getDriverPhone());
                        textVehicleRunningStatus.setText(response.body().getLoadStatus().getRunningStatus().getRunningStatusName());
                        textVehicleLocation.setText(response.body().getLoadStatus().getLocationName());
                        textLastUpdatedDate.setText(response.body().getLoadStatus().getStatusDate());
                        textLastUpdatedTime.setText(response.body().getLoadStatus().getStatusTime());

                    } else {
                        showSnakBar(rootView, response.body().getMeta().getMessage());

                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(rootView, meta.getString("message"));

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<JobLoaddetailsResponse> call, Throwable t) {
                showSnakBar(rootView, call.request().headers().get("meta"));

                hideProgressDialog();


            }
        });

    }

}
