package com.example.admin.loadingzone.modules.myqutation;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.modules.home.QuotationApply;
import com.example.admin.loadingzone.modules.login.LoginActivity;
import com.example.admin.loadingzone.modules.login.SignUpActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.MessageCreateResponse;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.retrofit.model.QutationApplyResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QutationDetailsActivity extends BaseActivity {
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
    @BindView(R.id.btnStartjob)
    Button btnStartjob;
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
    @BindView(R.id.ivCustomerProfilePhoto)
    ImageView ivUserProfilePhoto;
    @NonNull
    @BindView(R.id.textQutationStatus)
    TextView textQutationStatus;
    @NonNull
    @BindView(R.id.textQutationAmount)
    TextView textQutationAmount;
    @NonNull
    @BindView(R.id.textQutationCurrency)
    TextView textQutationCurrency;
    @NonNull
    @BindView(R.id.textQutationDate)
    TextView textQutationDate;
    @NonNull
    @BindView(R.id.textDate)
    TextView textDate;
    @NonNull
    @BindView(R.id.textQutationDescription)
    TextView textQutationDescription;
    @NonNull
    @BindView(R.id.textMessage)
    TextView textMessage;
    @NonNull
    @BindView(R.id.linearDelete)
    LinearLayout linearLayoutDelete;
    @NonNull
    @BindView(R.id.linerUpdate)
    LinearLayout linearLayoutUpdate;
    @NonNull
    @BindView(R.id.rootview)
    RelativeLayout rootView;
    @NonNull
    @BindView(R.id.relativeSendMessage)
    RelativeLayout relativeSendMessage;
    String qutation_id, quotationAmount, quotationDescription, job_id;
    private ApiInterface apiService;
    String isFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qutation_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quotation Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        btnStartjob.setVisibility(View.GONE);
        qutation_id = getIntent().getStringExtra("qutation_id");
        job_id = getIntent().getStringExtra("job_id");
        String job_title = getIntent().getStringExtra("job_title");
        String cus_name = getIntent().getStringExtra("cus_name");
        String cus_email = getIntent().getStringExtra("cus_email");
        String cus_phone = getIntent().getStringExtra("cus_phone");
        String cus_profile = getIntent().getStringExtra("cus_profile");
        String quotationCurrency = getIntent().getStringExtra("quotationCurrency");
        quotationAmount = getIntent().getStringExtra("quotationAmount");
        String dateSubmitted = getIntent().getStringExtra("dateSubmitted");
        String dateAccepted = getIntent().getStringExtra("dateAccepted");
        String dateRejected = getIntent().getStringExtra("dateRejected");
        String quotationStatus = getIntent().getStringExtra("quotationStatus");
        quotationDescription = getIntent().getStringExtra("quotationDescription");
        isFrom = getIntent().getStringExtra("isFrom");
        if (!isFrom.equals(null))
            if (isFrom.equals("pending")) {
                textDate.setVisibility(View.GONE);
                textQutationDate.setVisibility(View.GONE);
            }
        if (isFrom.equals("accepted")) {
            linearLayoutDelete.setVisibility(View.GONE);
            linearLayoutUpdate.setVisibility(View.GONE);
            textDate.setVisibility(View.VISIBLE);
            textQutationDate.setVisibility(View.VISIBLE);
            textDate.setText("Accepted Date");
            textMessage.setVisibility(View.VISIBLE);
            textQutationDate.setText(dateAccepted);

        }
        if (isFrom.equals("rejected")) {
            linearLayoutDelete.setVisibility(View.GONE);
            linearLayoutUpdate.setVisibility(View.GONE);
            textDate.setVisibility(View.VISIBLE);
            textQutationDate.setVisibility(View.VISIBLE);
            textDate.setText("Rejected Date");
            textQutationDate.setText(dateRejected);
        }
// job title speration
        String[] splitedTitle = job_title.split("\\s+");
        textViewCutomerName.setText(cus_name);
        textViewCutomerEmail.setText(cus_email);
        textViewCutomerMobile.setText(cus_phone);
        textViewJob_From.setText(splitedTitle[2]);
        textViewJob_To.setText(splitedTitle[4]);
        textQutationStatus.setText(quotationStatus);
        textQutationAmount.setText(quotationAmount);
        textQutationCurrency.setText(quotationCurrency);
        textQutationDescription.setText(quotationDescription);
        Picasso.with(QutationDetailsActivity.this)
                .load(cus_profile)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(88, 88)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivUserProfilePhoto);
        textViewRequestedDate.setText(dateSubmitted);


    }

    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.linearDelete)
    public void deleteQutation() {
        if (qutation_id != null && job_id != null)
            if (isConnectingToInternet(getApplicationContext())) {
                deleteQutation(qutation_id);
            } else {
                showSnakBar(rootView, MessageConstants.INTERNET);

            }

    }

    // send message to customer only after qutation id accepted
    @OnClick(R.id.relativeSendMessage)
    public void SendMessage() {
        if (isFrom.equals("accepted")) {
            messageDilog();
            btnSentMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String subject = et_subject.getText().toString();
                    String message = et_message.getText().toString();
                    if (message.length() > 0 && subject.length() > 0) {
                        if (isConnectingToInternet(QutationDetailsActivity.this)) {
                            sendMessage(subject, message);


                        } else {
                            showSnakBar(rootView, MessageConstants.INTERNET);
                        }
                    } else {
                        showSnakBar(rootView, "Please type messge");
                    }
                }
            });
        } else return;
    }

    @OnClick(R.id.linerUpdate)
    public void updateQutation() {

        Intent i = new Intent(QutationDetailsActivity.this, QuotationApply.class);
        i.putExtra("qutation_id", qutation_id);
        i.putExtra("quotationAmount", quotationAmount);
        i.putExtra("quotationDescription", quotationDescription);
        i.putExtra("JobId", job_id);
        startActivity(i);


    }

    private void sendMessage(String subject, String message) {

        showProgressDialog(QutationDetailsActivity.this, "sending...");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        String message_type_id = "4"; // message type id  :Provider to Customer based on Job Quotation"
        Call<MessageCreateResponse> call = apiService.CreateMessage(GloablMethods.API_HEADER + acess_token, qutation_id, message_type_id, subject, message);
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

    //api call for Update qutation
    public void deleteQutation(String qutation_id) {

        showProgressDialog(QutationDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<QutationApplyResponse> call = apiService.DeleteQutation(GloablMethods.API_HEADER + acess_token, qutation_id);
        call.enqueue(new Callback<QutationApplyResponse>() {
            @Override
            public void onResponse(Call<QutationApplyResponse> call, retrofit2.Response<QutationApplyResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {
                        Intent i = new Intent(QutationDetailsActivity.this, MyQuotationActivity.class);
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
