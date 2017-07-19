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

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.SplashActivity;
import com.example.admin.loadingzone.modules.login.LoginActivity;
import com.example.admin.loadingzone.modules.myjob.StartJobActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileEditActivity;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.github.fabtransitionactivity.SheetLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

import static com.example.admin.loadingzone.R.id.ivUserProfilePhoto;

public class PostedJobDetailsActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener {
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
    private static int REQUEST_CODE = 41;
    String JobId, isFrom, job_status_code;


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
            if (job_status_code.equals("quotation-accepted"))
            {
                buttonJobStart.setVisibility(View.VISIBLE);
            }
            else {
                buttonJobStart.setVisibility(View.GONE);
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


}
