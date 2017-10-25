package com.example.admin.loadingzone.modules.cancel_job;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.myjob.MyJobtabViewActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.CancelJobRequestResponse;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class CancelJobActivity extends BaseActivity {
    String cancel_reason_id, cancel_reason,JobId;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @NonNull
    @BindView(R.id.editSelectedReason)
    TextView editSelectedReason;
    @NonNull
    @BindView(R.id.editCancelComment)
    EditText editCancelComment;
    @NonNull
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @NonNull
    @BindView(R.id.relativeJobCancel)
    RelativeLayout relativeJobCancel;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_job);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cancel Job");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        cancel_reason_id = getIntent().getStringExtra("cancel_reason_id");
        cancel_reason = getIntent().getStringExtra("cancel_reason");
        JobId=getIntent().getStringExtra("JobId");

        if (!cancel_reason.equals(null))
        {
            editSelectedReason.setText(cancel_reason);
        }
    }
    // cancel the job
    @NonNull
    @OnClick(R.id.relativeJobCancel)
    public void jobCancel()
    {
        String cancel_comment=editCancelComment.getText().toString().trim();
        if (isConnectingToInternet(getApplicationContext()))
        {
            cancelJob(JobId,cancel_reason_id,cancel_comment);
        }
        else {
            showSnakBar(rootView, MessageConstants.INTERNET);
        }

    }

    //api call for canceling the job
    public void cancelJob(String job_id,String cancel_reason_id,String cancel_comment) {

        showProgressDialog(CancelJobActivity.this, "loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<CancelJobRequestResponse> call = apiService.CancelJob(GloablMethods.API_HEADER + acess_token, job_id, cancel_reason_id, cancel_comment);
        call.enqueue(new Callback<CancelJobRequestResponse>() {
            @Override
            public void onResponse(Call<CancelJobRequestResponse> call, retrofit2.Response<CancelJobRequestResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals("true") || response.body().getMeta().getStatus().equals(true))
                    {
                        Intent i=new Intent(CancelJobActivity.this, MyJobtabViewActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        showSnakBar(rootView,response.body().getMeta().getMessage());
                        startActivity(i);
                    }

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<CancelJobRequestResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });

    }
}
