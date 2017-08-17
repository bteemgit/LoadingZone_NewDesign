package com.example.admin.loadingzone.modules.home;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import com.example.admin.loadingzone.cancel_job.JobCancelReasonListActivity;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.myjob.EditAvailbleDriverOrTruckActivity;
import com.example.admin.loadingzone.modules.myjob.StartJobActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.JobLoaddetailsResponse;
import com.example.admin.loadingzone.retrofit.model.MessageCreateResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.github.clans.fab.FloatingActionMenu;
import com.github.fabtransitionactivity.SheetLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.admin.loadingzone.R.id.floating_action_menu;

public class PostedJobDetailsActivity extends BaseActivity implements SheetLayout.OnFabAnimationEndListener {
    @NonNull
    @BindView(R.id.textCustomerName)
    TextView textViewCutomerName;
    @NonNull
    @BindView(R.id.textCustomerEmail)
    TextView textViewCutomerEmail;
    @NonNull
    @BindView(R.id.textMobile)
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
    @BindView(R.id.textTruckType)
    TextView textViewTruckType;
    @NonNull
    @BindView(R.id.textPaymentMode)
    TextView textViewPaymentMode;

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
    @BindView(R.id.ExpStartEndJob_assignedJobs)
    LinearLayout linerExpStartEndJob_assignedJobs;


    @NonNull
    @BindView(R.id.texExpStartDate)
    TextView textViewExpStartDate;

    @NonNull
    @BindView(R.id.textExpEndDate)
    TextView textViewExpEndDate;


    @NonNull
    @BindView(R.id.btnStartjob)
    Button buttonJobStart;
    @NonNull
    @BindView(R.id.btnCanceljob)
    Button buttonCanceljob;
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
    @BindView(R.id.textVehicleLocation)
    TextView textVehicleLocation;
    @NonNull
    @BindView(R.id.textLastUpdatedDate)
    TextView textLastUpdatedDate;
    @NonNull
    @BindView(R.id.textLastUpdatedTime)
    TextView textLastUpdatedTime;

    @NonNull
    @BindView(R.id.id_LinearStart_EndDateime)
    LinearLayout linearLayout_id_Start_EndDateime;

    @NonNull
    @BindView(R.id.id_LinearStart_EndDateime_Label)
    LinearLayout LinearLayout_id_Start_EndDateime_Label;

    @NonNull
    @BindView(R.id.rootview)
    RelativeLayout rootView;
    @NonNull
    @BindView(R.id.fabmessage_driver)
    com.github.clans.fab.FloatingActionButton fab_messageDriver;

    @NonNull
    @BindView(R.id.fabmessage_customer)
    com.github.clans.fab.FloatingActionButton fab_messageCustomer;

    @NonNull
    @BindView(R.id.fabcall_driver)
    com.github.clans.fab.FloatingActionButton fabcall_driver;

    @NonNull
    @BindView(R.id.fabcall_customer)
    com.github.clans.fab.FloatingActionButton fab_callCustomer;
    @NonNull
    @BindView(floating_action_menu)
    FloatingActionMenu floatingActionMenu;

    @NonNull
    @BindView(R.id.texJobCode)
    TextView textViewjbCode;

    @NonNull
    @BindView(R.id.linear_prefferedtruckDetails)
    LinearLayout linearPrefferedTruckDetails;

    @NonNull
    @BindView(R.id.linear_truckLabel)
    LinearLayout linearTruckLabel;

    @NonNull
    @BindView(R.id.textCustomTruckSize_inAssignedVehicleInfo)
    TextView textViewCustomTruckSize_inAssignedVehicleInfo;

    @NonNull
    @BindView(R.id.textTruckSize_inPrefferedTruckDetails)
    TextView textViewCustomTruckSize_inPrefferedTruckDetails;


    @NonNull
    @BindView(R.id.textJobStatus)
    TextView textViewJobStatus;


    @NonNull
    @BindView(R.id.TextRunningStatus)
    TextView textViewRunningStatus;


    String CutomerMobile = null;
    View dark_bg;
    private static int REQUEST_CODE = 41;
    String JobId, isFrom, job_status_code, job_time, job_date;
    private ApiInterface apiService;
    public static String IsEditVehicle = "EditVehicle";
    public static String IsEditDriver = "EditDriver";


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
        dark_bg = findViewById(R.id.background_dimmer);
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {

                if (opened) {
                    dark_bg.setVisibility(View.VISIBLE);
                } else {
                    dark_bg.setVisibility(View.GONE);
                }

            }
        });
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        JobId = getIntent().getStringExtra("JobId");
        String job_code = getIntent().getStringExtra("job_code");
        String JobStatus = getIntent().getStringExtra("JobStatus");

        isFrom = getIntent().getStringExtra("isFrom");
        String profilepic = getIntent().getStringExtra("profilepic");
        String CutomerName = getIntent().getStringExtra("name");
        String CutomerEmail = getIntent().getStringExtra("email");
        String CutomerMobileNo = getIntent().getStringExtra("phone1");
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
        job_status_code = getIntent().getStringExtra("job_status_code");
        job_date = getIntent().getStringExtra("job_date");
        job_time = getIntent().getStringExtra("job_time");
        if (isFrom.equals("Home")) {
            linerUserstaus.setVisibility(View.VISIBLE);
            fabQuotationApply.setVisibility(View.VISIBLE);
            buttonJobStart.setVisibility(View.GONE);
            floatingActionMenu.setVisibility(View.GONE);
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
            }
        }
        if (isFrom.equals("AssignedJob")) {

            //setting visibility of Exp.Start/End Date in headerview
            linerExpStartEndJob_assignedJobs.setVisibility(View.VISIBLE);
            //setting invisibile of ActiveQuotation.etc in headerview
            linerUserstaus.setVisibility(View.GONE);
            //setting invisibile of preffered truckdetails
            linearPrefferedTruckDetails.setVisibility(View.GONE);
            linearTruckLabel.setVisibility(View.GONE);

            //setting visibility of Start/End date and time
            linearLayout_id_Start_EndDateime.setVisibility(View.VISIBLE);
            LinearLayout_id_Start_EndDateime_Label.setVisibility(View.VISIBLE);

            //the job can only cancel after the drver and vehicle is assignd and before the drvier loading the materials
            if (job_status_code.equals("job-started") || job_status_code.equals("vehicle-blocked")) {
                buttonCanceljob.setVisibility(View.VISIBLE);
            } else {
                buttonCanceljob.setVisibility(View.GONE);
            }

            // getting the loading details from the curesponding job
            getLoadingJobDeatails(JobId);
            linearAssignedVehicleHeading.setVisibility(View.VISIBLE);
            linear_assignedDriver_heading.setVisibility(View.VISIBLE);
            linearAssignedDriverItem.setVisibility(View.VISIBLE);
            linearAssignedVehicleItem.setVisibility(View.VISIBLE);
            linearJobstatusItem.setVisibility(View.VISIBLE);
            liner_JobStatus_heading.setVisibility(View.VISIBLE);
        }


        textViewCutomerName.setText(CutomerName);
        textViewCutomerEmail.setText(CutomerEmail);
        textViewCutomerMobile.setText(CutomerMobileNo);
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

        textViewjbCode.setText(job_code);


        textViewJobStatus.setText(JobStatus);


      //  textViewTruckCustName.setText();

        textViewCustomTruckSize_inAssignedVehicleInfo.setText(TruckSize);
        textViewCustomTruckSize_inPrefferedTruckDetails.setText(TruckSize);
        textViewTruckType.setText(TruckType);
        textViewPaymentMode.setText(PaymentMode);
        //textViewCurrency.setText(Currency);
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

    // change driver from assigned job
    @NonNull
    @OnClick(R.id.ivEditDriver)
    public void editDriver() {

        String start_date = textStartDate.getText().toString().trim();
        String start_time = textStartTime.getText().toString().trim();
        String end_time = textEndTime.getText().toString().trim();
        String end_date = textEndDate.getText().toString().trim();
        // if job_status_code is equals to vehicle-blocked then the driver is not started the job ,only these condition we can change the truck and driver
        if (job_status_code.equals("vehicle-blocked")) {
            Intent intent = new Intent(getApplicationContext(), EditAvailbleDriverOrTruckActivity.class);
            intent.putExtra("JobId", JobId);
            intent.putExtra("jobStatus", IsEditDriver); // for identyfying teh job is new while editing the truck and driver
            intent.putExtra("start_date", start_date);
            intent.putExtra("start_time", start_time);
            intent.putExtra("end_time", end_time);
            intent.putExtra("end_date", end_date);
            startActivityForResult(intent, 2);
        }
    }

    // change Truck from assigned job
    @NonNull
    @OnClick(R.id.ivEditVechicle)
    public void editTruck() {
        String start_date = textStartDate.getText().toString().trim();
        String start_time = textStartTime.getText().toString().trim();
        String end_time = textEndTime.getText().toString().trim();
        String end_date = textEndDate.getText().toString().trim();
        // if job_status_code is equals to vehicle-blocked then the driver is not started the job ,only these condition we can change the truck and driver
        if (job_status_code.equals("vehicle-blocked")) {
            Intent intent = new Intent(getApplicationContext(), EditAvailbleDriverOrTruckActivity.class);
            intent.putExtra("JobId", JobId);
            intent.putExtra("jobStatus", IsEditVehicle); // for identyfying teh job is new while editing the truck and driver
            intent.putExtra("start_date", start_date);
            intent.putExtra("start_time", start_time);
            intent.putExtra("end_time", end_time);
            intent.putExtra("end_date", end_date);
            intent.putExtra("isFrom", "Posted");
            startActivityForResult(intent, 2);
        }

    }


    // for sending messages to Customer
    @NonNull
    @OnClick(R.id.fabmessage_customer)
    public void messageCustomer() {
        messageDilog();
        btnSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = et_subject.getText().toString();
                String message = et_message.getText().toString();
                String message_type_id = "3"; //Provider to Customer based on Job Post"
                if (message.length() > 0 && subject.length() > 0) {
                    if (isConnectingToInternet(PostedJobDetailsActivity.this)) {
                        sendMessage(subject, message, message_type_id);


                    } else {
                        showSnakBar(rootView, MessageConstants.INTERNET);
                    }
                } else {
                    showSnakBar(rootView, "Please type messge");
                }
            }
        });
    }

    // for sending messages to Driver
    @NonNull
    @OnClick(R.id.fabmessage_driver)
    public void messageDriver() {
        messageDilog();
        btnSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = et_subject.getText().toString();
                String message = et_message.getText().toString();
                String message_type_id = "7"; //Provider to Customer based on Job Post"
                if (message.length() > 0 && subject.length() > 0) {
                    if (isConnectingToInternet(PostedJobDetailsActivity.this)) {
                        sendMessage(subject, message, message_type_id);


                    } else {
                        showSnakBar(rootView, MessageConstants.INTERNET);
                    }
                } else {
                    showSnakBar(rootView, "Please type messge");
                }
            }
        });
    }


    //call driver
    @NonNull
    @OnClick(R.id.fabcall_customer)
    public void callcustomer() {
        if (isPermissionGranted()) {
            call_action();
        }
    }

    // for canceling a job(the job can only cancel after the drver and vehicle is assignd and before the drvier loading the materials)
    @NonNull
    @OnClick(R.id.btnCanceljob)
    public void cancelJob() {
        Intent i = new Intent(PostedJobDetailsActivity.this, JobCancelReasonListActivity.class);
        i.putExtra("JobId", JobId);
        startActivity(i);
    }

    public void call_action() {

        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + CutomerMobile));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }

    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    @NonNull
    @OnClick(R.id.btnStartjob)
    public void startJob() {

        Intent intent = new Intent(getApplicationContext(), StartJobActivity.class);
        intent.putExtra("JobId", JobId);
        intent.putExtra("jobStatus", "StartNewJob"); // for identyfying teh job is new while editing the truck and driver
        startActivity(intent);

    }


    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(getApplicationContext(), QuotationApply.class);
        intent.putExtra("JobId", JobId);
        intent.putExtra("qutation_id", "new");
        intent.putExtra("job_date", job_date);
        intent.putExtra("job_time", job_time);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            mSheetLayout.contractFab();
        }
        if (requestCode == 2) {
            String isUpdated = data.getStringExtra("isUpdated");
            if (isUpdated != null)
                if (isUpdated.equals("True")) {
                    getLoadingJobDeatails(JobId);
                } else return;
        }

    }

    // getting the loading details and assigned driver details
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

                        textStartDate.setText(response.body().getAssignedVehicle().getExpectedStartDate());
                        textEndDate.setText(response.body().getAssignedVehicle().getExpectedEndDate());
                        textEndTime.setText(response.body().getAssignedVehicle().getExpectedEndTime());
                        textStartTime.setText(response.body().getAssignedVehicle().getExpectedStartTime());


                        textViewRunningStatus.setText(response.body().getLoadStatus().getRunningStatus().getRunningStatusText());


                        textViewExpStartDate.setText(response.body().getAssignedVehicle().getExpectedStartDate());
                        textViewExpEndDate.setText(response.body().getAssignedVehicle().getExpectedEndDate());

                        textDriverName.setText(response.body().getAssignedDriver().getDriverName());
                        textDriverEmail.setText(response.body().getAssignedDriver().getDriverEmail());
                        textDriverMobile.setText(response.body().getAssignedDriver().getDriverPhone());
                      //  textVehicleRunningStatus.setText(response.body().getLoadStatus().getRunningStatus().getRunningStatusName());
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


    // for sending new message to customer or drvier
    private void sendMessage(String subject, String message, String message_type_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "sending...");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<MessageCreateResponse> call = apiService.CreateMessage(GloablMethods.API_HEADER + acess_token, JobId, message_type_id, subject, message);
        call.enqueue(new Callback<MessageCreateResponse>() {
            @Override
            public void onResponse(Call<MessageCreateResponse> call, Response<MessageCreateResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful())

                {
//                    if (response.body().getMeta().getStatus().equals("true")) {
                    et_message.setText("");
                    et_subject.setText("");
                    showSnakBar(rootView, "Message sent");
                    finish();
//                    }
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
            public void onFailure(Call<MessageCreateResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }


}
