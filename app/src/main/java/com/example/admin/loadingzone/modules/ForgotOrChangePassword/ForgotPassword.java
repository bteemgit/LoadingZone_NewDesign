package com.example.admin.loadingzone.modules.ForgotOrChangePassword;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.login.LoginActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.ForgotPasswordResponse;

import org.json.JSONObject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;

import static android.R.attr.password;

public class ForgotPassword extends BaseActivity implements View.OnClickListener {
    private EditText editTextUserName;
    private Button getPassword;



    ProgressDialog progressDialog;

    LinearLayout linearLayout;

    private ApiInterface apiService;

    @NonNull
    @BindView(R.id.id_linearForgot)
    LinearLayout rootView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editTextUserName = (EditText) findViewById(R.id.id_editUserName_frgtpwd);
        getPassword = (Button) findViewById(R.id.id_btn_getpassword);
        progressDialog = new ProgressDialog(ForgotPassword.this);

        linearLayout = (LinearLayout)findViewById(R.id.id_linearForgot);

        // click on listner
        getPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_btn_getpassword:
                String username = editTextUserName.getText().toString().trim();
                // for differeating the service provider

                if (validate(username)) {


                    Boolean  a = isConnectingToInternet(ForgotPassword.this);
                    if(a) {
                        ForgotPassword(username);
                    }else {
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                                        startActivity(i);
                                    }
                                });

                        View sbView = snackbar.getView();

                        //sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.complete_job_back_color));
                        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.samkara_submit_button));
// Changing message text color
                        snackbar.setActionTextColor(Color.WHITE);



// Changing action button text color

                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();
                    }



                } else {
                    showBaseToast(MessageConstants.PROVIDE_BASIC_INFO);
                }
                break;
        }

    }

    private boolean validate(String... strings) {
        for (String string : strings) {
            if (string == null) return false;
            if (string.length() < 1) return false;
        }
        return true;
    }


    private void ForgotPassword(String userName) {
        progressDialog.setMessage("loading");
        progressDialog.show();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
      //  Call<PostedJobResponse> call = apiService.PostedJobList(GloablMethods.API_HEADER + acess_token, offset);
        Call<ForgotPasswordResponse> call = apiService.ForgotPassword(GloablMethods.API_HEADER + acess_token,userName);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, retrofit2.Response<ForgotPasswordResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals("true") || response.body().getMeta().getStatus().equals(true)) {
                       /* Snackbar snackbar = Snackbar
                                .make(linearLayout,"Password has been sent to your mail ID", Snackbar.LENGTH_LONG);
                        snackbar.show();*/
                       Toast.makeText(ForgotPassword.this,response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                   /* } else {
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getErrors().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }*/
                    }

                    Log.e("test..............", response.body().getMeta().getMessage());

                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);


                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(in);


                }else if(!response.isSuccessful()){

                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }


                /*if (response.code() == 404) {
                    progressDialog.dismiss();
                    //Toast.makeText(ForgotPassword.this,, Toast.LENGTH_SHORT).show();


                    Snackbar snackbar = Snackbar
                            .make(linearLayout,"Please enter a registered Email ID" , Snackbar.LENGTH_LONG);
                    snackbar.show();
                }*/



               /*
                else {
                    hideProgressDialog();

                    showSnakBar(rootView, MessageConstants.SERVERERROR);
                     *//*try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }*//*

                }*/




            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
            }




        });


    }




}
