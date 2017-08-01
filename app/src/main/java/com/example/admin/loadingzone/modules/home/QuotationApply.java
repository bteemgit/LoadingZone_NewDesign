package com.example.admin.loadingzone.modules.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.Calendar;

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

    String JobId;
    String qutation_id, quotationAmount, quotationDescription;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int startYear, startMonth, startDay, startHour, startMinute;
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
        quotationAmount = getIntent().getStringExtra("quotationAmount");
        String job_date = getIntent().getStringExtra("job_date");
        String job_time = getIntent().getStringExtra("job_time");
        // for displaying customer prefered time and date
        if (job_date!=null)
            textCustomerJobDate.setText(job_date);
        if (job_time!=null)
        textCustomerJobTime.setText(job_time);
        quotationDescription = getIntent().getStringExtra("quotationDescription");
        if (!qutation_id.equals("new")) {
            editTextQuotAmount.setText(quotationAmount);
            editTextQuotDescrption.setText(quotationDescription);
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
    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    startYear = year;
                    startMonth = monthOfYear + 1;
                    startDay = dayOfMonth;
                    textDateRequsting.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

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

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        startHour = hourOfDay;
                        startMinute = minute;

                        textTimeRequsting.setText(hourOfDay + ":" + minute);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

}

    @OnClick(R.id.relative_submit)
    public void applyQutation() {
        String amount = editTextQuotAmount.getText().toString();
        String description = editTextQuotDescrption.getText().toString();
        String loading_date=textDateRequsting.getText().toString();
        String loading_time=textTimeRequsting.getText().toString();
        if (amount != null && JobId != null) {
            if (isConnectingToInternet(getApplicationContext())) {
                if (!qutation_id.equals("new")) {
                    updateQutation(qutation_id, amount, description,loading_date,loading_time);
                } else {
                    applyQutation(JobId, amount, description,loading_date,loading_time);
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
    public void applyQutation(String job_id, String amount, String descrption,String loading_date,String loading_time) {

        showProgressDialog(QuotationApply.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<QutationApplyResponse> call = apiService.ApplyQutation(GloablMethods.API_HEADER + acess_token, job_id, amount, descrption,loading_date,loading_time);
        call.enqueue(new Callback<QutationApplyResponse>() {
            @Override
            public void onResponse(Call<QutationApplyResponse> call, retrofit2.Response<QutationApplyResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals("true") || response.body().getMeta().getStatus().equals(true)) {
                        Intent i = new Intent(QuotationApply.this, HomeActivity.class);
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
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
    public void updateQutation(String qutation_id, String amount, String descrption,String loading_date,String loading_time) {

        showProgressDialog(QuotationApply.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<QutationApplyResponse> call = apiService.UpdateQutation(GloablMethods.API_HEADER + acess_token, qutation_id, amount, descrption,loading_date,loading_time);
        call.enqueue(new Callback<QutationApplyResponse>() {
            @Override
            public void onResponse(Call<QutationApplyResponse> call, retrofit2.Response<QutationApplyResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {
                        Intent i = new Intent(QuotationApply.this, MyQuotationActivity.class);
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
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
}
