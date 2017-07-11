package com.example.admin.loadingzone.modules.login;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.global.SessionManager;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileEditActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editUserName)
    EditText editUserName;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.textViewForgotPassword)
    TextView textViewForgotPassword;
    @BindView(R.id.textViewSignup)
    TextView textViewSignup;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    private ApiInterface apiService;

    List<Error> errorArrayList = new ArrayList<>();
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_02);
        //buuterknife for injecting the views
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        //If the session is logged in move to Home
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Log.d("login Isuue", "sessionfalse");
            Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent1);
            finish();
        } else {
            Log.d("login Isuue", "sessionfalse");
        }


    }

    @OnClick(R.id.buttonLogin)
    public void sigin() {
        String username = editUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String usertype = "2";//for identify the provider

        if (GloablMethods.validate(username, password)) {
            if (isValidEmaillId(username)) {
                if (isConnectingToInternet(getApplicationContext()))
                    Sigin(username, password, usertype);
                else {
                    showSnakBar(rootView, MessageConstants.INTERNET);
                }
            } else {
                showSnakBar(rootView, MessageConstants.INVALID_EMAIL);
            }
        } else {
            showSnakBar(rootView, MessageConstants.PROVIDE_BASIC_INFO);
        }
    }

    @OnClick(R.id.textViewSignup)
    public void signup() {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
    }

    //api call for singin
    public void Sigin(String username, String password, String usertype) {

        showProgressDialog(LoginActivity.this, "Loading");
        Call<LoginResponse> call = apiService.Singin(username, password, usertype);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {
                        if (response.body().getData().getServiceProviderId() != null) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            session.setLogin(true);
                            AppController.setString(getApplicationContext(), "service_provider_id", String.valueOf(response.body().getData().getServiceProviderId()));
                            AppController.setString(getApplicationContext(), "customer_email", response.body().getData().getUsername());
                            AppController.setString(getApplicationContext(), "customer_name", response.body().getData().getName());
                            AppController.setString(getApplicationContext(), "acess_token", response.body().getData().getAccessToken());
                            AppController.setString(getApplicationContext(), "user_id", String.valueOf(response.body().getData().getUserId()));
                            AppController.setString(getApplicationContext(), "pic", response.body().getData().getProfilePic());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, UserProfileEditActivity.class);
                            AppController.setString(getApplicationContext(), "service_provider_id", String.valueOf(response.body().getData().getServiceProviderId()));
                            AppController.setString(getApplicationContext(), "customer_email", response.body().getData().getUsername());
                            AppController.setString(getApplicationContext(), "customer_name", response.body().getData().getName());
                            AppController.setString(getApplicationContext(), "acess_token", response.body().getData().getAccessToken());
                            AppController.setString(getApplicationContext(), "user_id", String.valueOf(response.body().getData().getUserId()));
                            AppController.setString(getApplicationContext(), "pic", response.body().getData().getProfilePic());
                            intent.putExtra("email", AppController.getString(getApplicationContext(), "customer_email"));
                            intent.putExtra("isFrom", "login");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("exception>>>",t.getMessage());

            }
        });

    }
}
