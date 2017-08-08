package com.example.admin.loadingzone.modules.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
import com.example.admin.loadingzone.modules.ForgotOrChangePassword.ForgotPassword;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileEditActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.LoginResponse;
import com.example.admin.loadingzone.app.Config;
import com.example.admin.loadingzone.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessaging;

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
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String regId;
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

///firebase
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    //   String message = intent.getStringExtra("message");

                    //  Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //  txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);


        Log.e("deviceid ", "Firebase reg id: " + regId);

      /*  if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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

    @OnClick(R.id.textViewForgotPassword)
    public void ForgotPassword() {
        Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(i);
    }


    //api call for singin
    public void Sigin(String username, String password, String usertype) {

        showProgressDialog(LoginActivity.this, "Loading");
        Call<LoginResponse> call = apiService.Singin(username, password, usertype,regId);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals("true") || response.body().getMeta().getStatus().equals(true)) {
                        if (response.body().getData().getServiceProviderId() != null) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            session.setLogin(true);
                            AppController.setString(getApplicationContext(), "service_provider_id", String.valueOf(response.body().getData().getServiceProviderId()));
                            AppController.setString(getApplicationContext(), "customer_email", response.body().getData().getUsername());
                            AppController.setString(getApplicationContext(), "customer_name", response.body().getData().getName());
                            AppController.setString(getApplicationContext(), "acess_token", response.body().getData().getAccessToken());
                            AppController.setString(getApplicationContext(), "user_id", String.valueOf(response.body().getData().getUserId()));
                            AppController.setString(getApplicationContext(), "provider_pic", response.body().getData().getProfilePic());
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();


            }
        });

    }


}
