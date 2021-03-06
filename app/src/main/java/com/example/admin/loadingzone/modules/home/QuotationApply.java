package com.example.admin.loadingzone.modules.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.myqutation.MyQuotationActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.retrofit.model.QutationApplyResponse;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;


public class QuotationApply extends BaseActivity {
    private ApiInterface apiService;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @NonNull
    @BindView(R.id.edit_quotAmount)
    EditText editTextQuotAmount;
    @NonNull
    @BindView(R.id.edit_quotDes)
    EditText editTextQuotDescrption;
    @NonNull
    @BindView(R.id.linearDatePicker)
    LinearLayout linearLayoutDatePicker;
    @NonNull
    @BindView(R.id.linearTimePicker)
    LinearLayout linearLayoutTimePicker;
    @NonNull
    @BindView(R.id.relative_submit)
    LinearLayout submitQutation;
    @NonNull
    @BindView(R.id.textCustomerJobDate)
    TextView textCustomerJobDate;
    @NonNull
    @BindView(R.id.textCustomerJobTime)
    TextView textCustomerJobTime;
    @NonNull
    @BindView(R.id.textDateRequsting)
    TextView textDateRequsting;
    @NonNull
    @BindView(R.id.textTimeRequsting)
    TextView textTimeRequsting;
//    @NonNull
//    @BindView(R.id.linearDateTime)
//    LinearLayout linearLayoutDateTime;
//    @NonNull
//    @BindView(R.id.layout_ProviderPrefferedJobDate)
//    LinearLayout linearLayoutProviderPrefferedJobDate;
//    @NonNull
//    @BindView(R.id.layout_ProviderPrefferedJobTime)
//    LinearLayout linearLayoutProviderPrefferedJobTime;
    @NonNull
    @BindView(R.id.textProviderPrefferedJobDate)
    TextView textViewProviderPrefferedJobDate;
    @NonNull
    @BindView(R.id.textProviderPrefferedJobTime)
    TextView textViewProviderPrefferedJobTime;
    SimpleDateFormat sdf;
    String JobId;
    String qutation_id, quotationAmount, quotationDescription, customer_pref_date, customer_pref_time;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int startYear, startMonth, startDay, startHour, startMinute;
    // the variable for checking the provider change the job date or time
    private boolean isChangeDate = false;
    private boolean isChangeTime = false;
    // for resoloving an issue present in the kit kitkat device only(issue is Without clicking on "done" in the date picker and time picker it is choosing the date and time )
private boolean isPickerActive=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_apply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Apply Quotation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //buuterknife for injecting the views
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        JobId = getIntent().getStringExtra("JobId");
        qutation_id = getIntent().getStringExtra("qutation_id");
        // applying new quotation
        if (qutation_id.equals("new")) {
            String PrefferedLoadingDate = getIntent().getStringExtra("PrefferedLoadingDate");
            String PrefferedLoadingTime = getIntent().getStringExtra("PrefferedLoadingTime");
            textCustomerJobDate.setText(PrefferedLoadingDate);
            textCustomerJobTime.setText(PrefferedLoadingTime);
        } else {
            quotationAmount = getIntent().getStringExtra("quotationAmount");
            String job_date = getIntent().getStringExtra("job_date");
            String job_time = getIntent().getStringExtra("job_time");
            customer_pref_date = getIntent().getStringExtra("customer_pref_date");
            customer_pref_time = getIntent().getStringExtra("customer_pref_time");
            quotationDescription = getIntent().getStringExtra("quotationDescription");
            editTextQuotAmount.setText(quotationAmount);
            editTextQuotDescrption.setText(quotationDescription);
            textCustomerJobTime.setText(customer_pref_date);
            textCustomerJobDate.setText(customer_pref_time);
            textViewProviderPrefferedJobDate.setText(job_date);
            textViewProviderPrefferedJobTime.setText(job_time);
        }
    }
    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Date selection
    @NonNull
    @OnClick(R.id.linearDatePicker)
    public void StartdatePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        isPickerActive=true;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        startYear = year;
                        startMonth = monthOfYear + 1;
                        startDay = dayOfMonth;
                        textViewProviderPrefferedJobDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        isChangeDate = true;
//                        linearLayoutProviderPrefferedJobDate.setVisibility(View.VISIBLE);
//                        linearLayoutProviderPrefferedJobTime.setVisibility(View.VISIBLE);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    // select time
    @NonNull
    @OnClick(R.id.linearTimePicker)
    public void StartTimePicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        isPickerActive=true;
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        startHour = hourOfDay;
                        startMinute = minute;
                        textViewProviderPrefferedJobTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        isChangeTime = true;
//                        linearLayoutProviderPrefferedJobTime.setVisibility(View.VISIBLE);
//                        linearLayoutProviderPrefferedJobDate.setVisibility(View.VISIBLE);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
    @OnClick(R.id.relative_submit)
    public void applyQutation() {
        String loading_date = null;
        String loading_time = null;
        String amount = editTextQuotAmount.getText().toString();
        String description = editTextQuotDescrption.getText().toString();
        if (isChangeDate) {
            loading_date = textViewProviderPrefferedJobDate.getText().toString();
        } else {
            loading_date = textCustomerJobDate.getText().toString();
        }
        if (isChangeTime) {
            loading_time = textViewProviderPrefferedJobTime.getText().toString();
        } else {
            loading_time = textCustomerJobTime.getText().toString();
        }
        if (amount != null && JobId != null) {
            if (isConnectingToInternet(getApplicationContext())) {
                if (!qutation_id.equals("new")) {
                    updateQutation(qutation_id, amount, description, loading_date, loading_time);
                } else {
                    applyQutation(JobId, amount, description, loading_date, loading_time);
                }
            } else {
                showSnakBar(rootView, MessageConstants.INTERNET);
            }
        } else {
            Snackbar snackbar = Snackbar
                    .make(rootView, "Please provide Qutation Amount", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    //api call for apply qutation
    public void applyQutation(String job_id, String amount, String descrption, String loading_date, String loading_time) {

        showProgressDialog(QuotationApply.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<QutationApplyResponse> call = apiService.ApplyQutation(GloablMethods.API_HEADER + acess_token, job_id, amount, descrption, loading_date, loading_time);
        call.enqueue(new Callback<QutationApplyResponse>() {
            @Override
            public void onResponse(Call<QutationApplyResponse> call, retrofit2.Response<QutationApplyResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals("true") || response.body().getMeta().getStatus().equals(true)) {
                        Intent i = new Intent(QuotationApply.this, HomeActivity.class);
                        showSnakBar(rootView, "Quotation added successfully");
                        i.putExtra("isFrom", "home");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
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
            public void onFailure(Call<QutationApplyResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(rootView, call.request().headers().get("meta"), Snackbar.LENGTH_LONG);
                snackbar.show();
                hideProgressDialog();


            }
        });

    }


    //api call for Update qutation
    public void updateQutation(String qutation_id, String amount, String descrption, String loading_date, String loading_time) {

        showProgressDialog(QuotationApply.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<QutationApplyResponse> call = apiService.UpdateQutation(GloablMethods.API_HEADER + acess_token, qutation_id, amount, descrption, loading_date, loading_time);
        call.enqueue(new Callback<QutationApplyResponse>() {
            @Override
            public void onResponse(Call<QutationApplyResponse> call, retrofit2.Response<QutationApplyResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals("true") || response.body().getMeta().getStatus().equals(true)) {
                        Intent i = new Intent(QuotationApply.this, MyQuotationActivity.class);
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
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
            public void onFailure(Call<QutationApplyResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(rootView, call.request().headers().get("meta"), Snackbar.LENGTH_LONG);
                snackbar.show();
                hideProgressDialog();
            }
        });

    }

    public String setNormalDate(String date) {
        final String OLD_FORMAT = "yyyy-MM-dd";
        final String NEW_FORMAT = "yyyy-MM-dd";
        String newDate = "";
        try {
            sdf = new SimpleDateFormat(OLD_FORMAT);
            Date d = sdf.parse(date);
            sdf.applyPattern(NEW_FORMAT);
            newDate = sdf.format(d);
        } catch (ParseException e) {

        }
        return newDate;
    }
}
