package com.example.admin.loadingzone.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.LoginResponse;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by admin on 5/16/2017.
 */

public class SignupCase1 extends Fragment {

    // this fragmnet indivdual sign up
    public SignupCase1() {
        // Required empty public constructor
    }

    BaseActivity baseActivity;
    @BindView(R.id.editUserName)
    EditText editTextUsername;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextCPassword)
    EditText editTextCpassWord;
    @BindView(R.id.buttonSignUp)
    Button buttonSignUp;
    @BindView(R.id.root)
    LinearLayout relativeLayoutRoot;
    private ApiInterface apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        baseActivity = (BaseActivity) getActivity();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        return view;
    }

    @OnClick(R.id.buttonSignUp)
    public void signup() {
        String userName = editTextUsername.getText().toString().trim();
        String passWord = editTextPassword.getText().toString().trim();
        String CpassWord = editTextCpassWord.getText().toString().trim();
        String user_type_role = "1";//for individual signup
        String user_type_id = "2";//for provider identification
        if (GloablMethods.validate(userName, passWord, CpassWord)) {
            if (baseActivity.isValidEmaillId(userName)) {
                if (passWord.equals(CpassWord)) {
                    //signup api call
                    if (baseActivity.isConnectingToInternet(getActivity()))
                        signUp(userName, CpassWord, user_type_role,user_type_id);
                } else {
                    baseActivity.showSnakBar(relativeLayoutRoot, MessageConstants.PASSWORD_MISMATCH);
                }
            } else {
                baseActivity.showSnakBar(relativeLayoutRoot, MessageConstants.INVALID_EMAIL);
            }
        } else {
            baseActivity.showSnakBar(relativeLayoutRoot, MessageConstants.PROVIDE_BASIC_INFO);
        }
    }

    //api call for sign up
    public void signUp(String username, String password, String usertype, String user_type_id) {

        baseActivity.showProgressDialog(getActivity(), "loading");
        Call<LoginResponse> call = apiService.SignUp(username, password, usertype, user_type_id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                baseActivity.hideProgressDialog();
                if (response.isSuccessful()) {
                   // baseActivity.showSnakBar(relativeLayoutRoot, response.body().getMeta().getMessage());
                    //baseActivity.showSnakBar(relativeLayoutRoot,"Sign Up Success,Email has been sent to the registered address");
                    Toast.makeText(getContext(), response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(relativeLayoutRoot, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());

                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                baseActivity.hideProgressDialog();
            }
        });

    }
}

