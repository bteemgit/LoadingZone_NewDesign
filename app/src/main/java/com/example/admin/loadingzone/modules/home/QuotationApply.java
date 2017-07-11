package com.example.admin.loadingzone.modules.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
    @BindView(R.id.relative_submit)
    RelativeLayout submitQutation;
    String JobId;
    String qutation_id, quotationAmount, quotationDescription;

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

    @OnClick(R.id.relative_submit)
    public void applyQutation() {
        String amount = editTextQuotAmount.getText().toString();
        String description = editTextQuotDescrption.getText().toString();
        if (amount != null && JobId != null) {
            if (isConnectingToInternet(getApplicationContext())) {
                if (!qutation_id.equals("new")) {
                    updateQutation(qutation_id, amount, description);
                } else {
                    applyQutation(JobId, amount, description);
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
    public void applyQutation(String job_id, String amount, String descrption) {

        showProgressDialog(QuotationApply.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<QutationApplyResponse> call = apiService.ApplyQutation(GloablMethods.API_HEADER + acess_token, job_id, amount, descrption);
        call.enqueue(new Callback<QutationApplyResponse>() {
            @Override
            public void onResponse(Call<QutationApplyResponse> call, retrofit2.Response<QutationApplyResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {
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
    public void updateQutation(String qutation_id, String amount, String descrption) {

        showProgressDialog(QuotationApply.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<QutationApplyResponse> call = apiService.UpdateQutation(GloablMethods.API_HEADER + acess_token, qutation_id, amount, descrption);
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
