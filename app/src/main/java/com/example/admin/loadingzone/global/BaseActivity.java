package com.example.admin.loadingzone.global;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.loadingzone.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import butterknife.BindView;

public class BaseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    public Dialog dialog;
    public EditText et_subject;
    public EditText et_message;
    private BaseActivity context;
    public Button btnSentMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(BaseActivity.this);


    }
    public void messageDilog() {
        context=this;
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.activity_base);
        dialog.setCancelable(true);
        et_subject=(EditText)dialog.findViewById(R.id.edit_message_subject);
        et_message=(EditText)dialog.findViewById(R.id.edit_message);
        btnSentMessage=(Button)dialog.findViewById(R.id.sentBtnMessage) ;
        dialog.show();
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
    public void showSnakBar(View v,String message)
    {
        Snackbar snackbar = Snackbar
                .make(v,message,Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    //method for email validation
    public boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    public void showProgressDialog(Context context,String message) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {

        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showBaseToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
    // converting the date and time to unixtimestamp
    public long UnixTimeStampConvertion(int year, int month, int day, int hour, int minute) {

        // String dtStart = "7/3/2017 12:5:10 GMT";
        String dateToString = month + "/" + day + "/" + year + " " + hour + ":" + minute + ":" + "00" + " " + "GMT";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss z");
        Date date = null;
        try {
            date = format.parse(dateToString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long unixTime = (long) date.getTime() / 1000;
        return unixTime;
    }
}
