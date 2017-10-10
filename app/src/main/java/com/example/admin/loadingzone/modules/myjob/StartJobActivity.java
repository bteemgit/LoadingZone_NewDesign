package com.example.admin.loadingzone.modules.myjob;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.admin.loadingzone.global.SlideAnimationUtil;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.BlockTruckandDriverResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;

public class StartJobActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @NonNull
    @BindView(R.id.root)
    LinearLayout root;
    @NonNull
    @BindView(R.id.linearDatePicker)
    LinearLayout linearLayoutDatePicker;
    @NonNull
    @BindView(R.id.linearDatePickerEnd)
    LinearLayout linearDatePickerEnd;

    @NonNull
    @BindView(R.id.linearTimePickerEnd)
    LinearLayout linearTimePickerEnd;
    @NonNull
    @BindView(R.id.linearTimePicker)
    LinearLayout linearLayoutTimePicker;

    @NonNull
    @BindView(R.id.textSelectedTime)
    TextView textViewSelectedTime;
    @NonNull
    @BindView(R.id.textSelectedDate)
    TextView textViewSelectedDate;

    @NonNull
    @BindView(R.id.textSelectedTimeEnd)
    TextView textSelectedTimeEnd;
    @NonNull
    @BindView(R.id.textSelectedDateEnd)
    TextView textSelectedDateEnd;
    @NonNull
    @BindView(R.id.linear_SerachAvalibleTruck)
    LinearLayout relativeSerachAvalibleTruck;
    @NonNull
    @BindView(R.id.linearStartJob)
    LinearLayout relativeStartJob;

    //assigned driver and truck
    @NonNull
    @BindView(R.id.relativeDriverHead)
    RelativeLayout relativeDriverHead;
    @NonNull
    @BindView(R.id.textViewDriverName)
    TextView textViewDriverName;
    @NonNull
    @BindView(R.id.textTruckName)
    TextView textTruckName;
    @NonNull
    @BindView(R.id.textTruckModel)
    TextView textTruckModel;
    @NonNull
    @BindView(R.id.textTruckType)
    TextView textTruckType;
    @NonNull
    @BindView(R.id.textTruckDimension)
    TextView textTruckDimension;
    @NonNull
    @BindView(R.id.textChangeDriver)
    TextView textChangeDriver;
    @NonNull
    @BindView(R.id.textChangeTruck)
    TextView textChangeTruck;
    @NonNull
    @BindView(R.id.imageDriverPic)
    ImageView imageDriverPic;
    @NonNull
    @BindView(R.id.cardTruck)
    CardView cardViewTrckHead;

    @NonNull
    @BindView(R.id.fabSearchTruck)
    FloatingActionButton fab_searchTruck;

    @NonNull
    @BindView(R.id.fabCompleteStartJob)
    FloatingActionButton fab_CompleteStartJob;


    private int mYear, mMonth, mDay, mHour, mMinute;
    String JobId, jobStatus;

    private int startYear = 0, startMonth = 0 , startDay = 0, startHour = 0, startMinute = 0;
    private int endYear = 0, endMonth = 0, endDay = 0, endHour = 0, endMinute = 0;
    private long startUnixTimeStamp, endUnixTimeStamp;
    String driver_id = null, provider_vehicle_id = null;
    public static int RESULT_OK = 51;
    ApiInterface apiService;
    public static String IsEditVehicle = "EditVehicle";
    public static String IsEditDriver = "EditDriver";
    String isTruckBlocked = "NotBlocked";
    String start_date = null;
    String driverId = null,expected_start_date = null,expected_end_date = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_job_02);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Start Your Job");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        JobId = getIntent().getStringExtra("JobId");


      //selectTruckVisibility(startUnixTimeStamp, endUnixTimeStamp);



        String truckSelected = null;
        fab_CompleteStartJobVisibility(truckSelected,driver_id);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    @NonNull
    @OnClick(R.id.linearDatePicker)
    public void StartdatePicker() {

        if(cardViewTrckHead.getVisibility() == View.VISIBLE){
            cardViewTrckHead.setVisibility(View.GONE);
            relativeSerachAvalibleTruck.setVisibility(View.VISIBLE);
        }
        if(cardViewTrckHead.getVisibility() == View.VISIBLE)
            cardViewTrckHead.setVisibility(View.GONE);
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
                        textViewSelectedDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    @NonNull
    @OnClick(R.id.linearTimePicker)
    public void StartTimePicker() {


        String isSelectdate = textViewSelectedDate.getText().toString();
        if (!isSelectdate.equals("0000-00-00")) {
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

                            startUnixTimeStamp = UnixTimeStampConvertion(startYear, startMonth, startDay, startHour, startMinute);

                            textViewSelectedTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                            animateEndDate(view);


                            if(cardViewTrckHead.getVisibility() == View.VISIBLE){
                                cardViewTrckHead.setVisibility(View.GONE);
                                animateSerachTruck(view);
                            }

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        } else {
            showSnakBar(root, "Please select Date First");
        }
    }

    @NonNull
    @OnClick(R.id.linearDatePickerEnd)
    public void EnddatePicker() {
        if(cardViewTrckHead.getVisibility() == View.VISIBLE){
            cardViewTrckHead.setVisibility(View.GONE);
            relativeSerachAvalibleTruck.setVisibility(View.VISIBLE);
        }
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        endYear = year;
                        endMonth = monthOfYear + 1;
                        endDay = dayOfMonth;
                        textSelectedDateEnd.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        //for animatiting view for select end date
                        animateEndTime(view);

                    }
                }, mYear, mMonth, mDay);

        String start_date = textViewSelectedDate.getText().toString();
        long selectedFirstDate = startDatetoLong(start_date);
        datePickerDialog.getDatePicker().setMinDate(selectedFirstDate);
        datePickerDialog.show();


    }

    @NonNull
    @OnClick(R.id.linearTimePickerEnd)
    public void EndTimePicker() {


        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        endHour = hourOfDay;
                        endMinute = minute;
                        endUnixTimeStamp = UnixTimeStampConvertion(endYear, endMonth, endDay, endHour, endMinute);
                        textSelectedTimeEnd.setText(String.format("%02d:%02d", hourOfDay, minute));

//for animatiting view for selction for avalaible truck action
                        if(cardViewTrckHead.getVisibility() == View.VISIBLE){
                            cardViewTrckHead.setVisibility(View.GONE);
                            animateSerachTruck(view);
                        }

                        if(cardViewTrckHead.getVisibility() == View.GONE){
                            animateSerachTruck(view);
                        }


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

        //isDateTimeSelected = "Selected";
        //selectTruckVisibility(startUnixTimeStamp,endUnixTimeStamp);
    }

    // seraching avalible truck on the selected date and time
    @NonNull
    @OnClick(R.id.linear_SerachAvalibleTruck)
    public void serachTruck() {
        startUnixTimeStamp = UnixTimeStampConvertion(startYear, startMonth, startDay, startHour, startMinute);
        endUnixTimeStamp = UnixTimeStampConvertion(endYear, endMonth, endDay, endHour, endMinute);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
       if(startUnixTimeStamp>=tsLong){
          if(endUnixTimeStamp>=tsLong){


                if(startUnixTimeStamp<endUnixTimeStamp){
                    View v = new View(getApplicationContext());
                    SlideAnimationUtil.slideInFromLeft(this, v);
                    Intent i = new Intent(StartJobActivity.this, SelectActvieTruckActivity.class);
                    String sUnixTimeStamp = String.valueOf(startUnixTimeStamp);
                    String eUnixTimeStamp = String.valueOf(endUnixTimeStamp);
                    i.putExtra("startUnixTimeStamp", sUnixTimeStamp);
                    i.putExtra("endUnixTimeStamp", eUnixTimeStamp);
                    startActivityForResult(i, 2);
                }else{
                    showSnakBar(root, "End Time should be larger than Start time ");
                }
           }else {
               showSnakBar(root, "End Time should be greater than Current Time");
           }
       }else{
            showSnakBar(root, "Start Time should be greater than Current Time  ");
        }



    }

    // seraching avalible truck on the selected date and time to change the selected truck
    @NonNull
    @OnClick(R.id.fabSearchTruck)
    public void fab_searchAvilableTruck() {
        driver_id = null;
        textViewDriverName.setText("Drivers");
        //textChangeDriver.setText("");

        
        View v = new View(getApplicationContext());
        SlideAnimationUtil.slideInFromLeft(this, v);
        Intent i = new Intent(StartJobActivity.this, SelectActvieTruckActivity.class);
        String sUnixTimeStamp = String.valueOf(startUnixTimeStamp);
        String eUnixTimeStamp = String.valueOf(endUnixTimeStamp);
        i.putExtra("startUnixTimeStamp", sUnixTimeStamp);
        i.putExtra("endUnixTimeStamp", eUnixTimeStamp);
        startActivityForResult(i, 2);
    }




    // converting the start date in to long
    private long startDatetoLong(String start_date) {
        long cStartdate = 00000;

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = f.parse(start_date);
            cStartdate = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cStartdate;
    }


    // if drver is not added in the selected truck then we can assign driver or change driver (existing driver in truck)
    @NonNull
    @OnClick(R.id.textChangeDriver)
    public void changeDriver() {
        Intent i = new Intent(StartJobActivity.this, AvailableTruckOrDriverActivity.class);
        String sUnixTimeStamp = String.valueOf(startUnixTimeStamp);
        String eUnixTimeStamp = String.valueOf(endUnixTimeStamp);
        i.putExtra("startUnixTimeStamp", sUnixTimeStamp);
        i.putExtra("endUnixTimeStamp", eUnixTimeStamp);
        i.putExtra("jobStatus", IsEditDriver); // for identyfying teh job is new while editing the truck and driver
        i.putExtra("isFrom", "StartJob");
        startActivityForResult(i, 4);
    }

    // once the provider selct the avalible truck then job is completed and redirected in to home page
    @NonNull
    @OnClick(R.id.linearStartJob)
    public void startJob() {

        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }


    public void animateEndDate(View view) {

        linearDatePickerEnd.setVisibility(LinearLayout.VISIBLE);
        textSelectedDateEnd.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.popup_show);
        animation.setDuration(500);
        linearDatePickerEnd.setAnimation(animation);
        linearDatePickerEnd.animate();
        animation.start();
    }

    public void animateEndTime(View view) {

        linearTimePickerEnd.setVisibility(LinearLayout.VISIBLE);
        textSelectedTimeEnd.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.popup_show);
        animation.setDuration(500);
        linearTimePickerEnd.setAnimation(animation);
        linearTimePickerEnd.animate();
        animation.start();
    }


    public void animateSerachTruck(View view) {

        relativeSerachAvalibleTruck.setVisibility(LinearLayout.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.popup_show);
        animation.setDuration(500);
        relativeSerachAvalibleTruck.setAnimation(animation);
        relativeSerachAvalibleTruck.animate();
        animation.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (data != null)
            if (requestCode == 2) {
                cardViewTrckHead.setVisibility(View.VISIBLE);
                driver_id = data.getStringExtra("driver_id");
                provider_vehicle_id = data.getStringExtra("truck_id");
                String truck_name = data.getStringExtra("truck_name");
                String truck_maker = data.getStringExtra("truck_maker");
                String truck_type = data.getStringExtra("truck_type");
                String truck_dimension = data.getStringExtra("truck_dimension");
                textTruckName.setText(truck_name);
                textTruckModel.setText(truck_maker);
                textTruckType.setText(truck_type);
                textTruckDimension.setText(truck_dimension);
               // Log.d("provider_vehicle_id", provider_vehicle_id);
                if (!driver_id.equals("driver_isnot_assigned")) {
                    String driver_name = data.getStringExtra("driver_name");
                    String driver_pic = data.getStringExtra("driver_pic");
                    textViewDriverName.setText(driver_name);
                    textChangeDriver.setText("Change Driver");
                    Picasso.with(getApplicationContext())
                            .load(driver_pic)
                            .placeholder(R.drawable.img_circle_placeholder)
                            .resize(70, 70)
                            .centerCrop()
                            .transform(new CircleTransformation())
                            .into(imageDriverPic);
                    relativeSerachAvalibleTruck.setVisibility(View.GONE);
                    String truckSelected = "true";
                    fab_CompleteStartJobVisibility(truckSelected,driver_id);


                } else {
                    relativeSerachAvalibleTruck.setVisibility(View.GONE);
                    showSnakBar(root, "Please select a Driver for complete the process");
                    textChangeDriver.setText("Add Driver");


                }
            }
        if (requestCode == 4) {
            String driver_name = data.getStringExtra("driver_name");
            String driver_pic = data.getStringExtra("driver_pic");
             driver_id = data.getStringExtra("driver_id");
            textViewDriverName.setText(driver_name);
            textChangeDriver.setText("Change Driver");
            Picasso.with(getApplicationContext())
                    .load(driver_pic)
                    .placeholder(R.drawable.img_circle_placeholder)
                    .resize(70, 70)
                    .centerCrop()
                    .transform(new CircleTransformation())
                    .into(imageDriverPic);
            String truckSelected = "true";
            fab_CompleteStartJobVisibility(truckSelected,driver_id);

        }
    }

    private void fab_CompleteStartJobVisibility(String truckSelected, String driver_id) {
        if(truckSelected != null && driver_id != null){

            fab_CompleteStartJob.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.fabCompleteStartJob)
    public void blockTruck(){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            String expected_start_date = String.valueOf(startUnixTimeStamp);
                            String expected_end_date = String.valueOf(endUnixTimeStamp);

                            if(driver_id.equals("driver_isnot_assigned")){
                                showSnakBar(root, "Please select a Driver for complete the process");
                            }else{
                                BlockTruckandDriver(JobId, provider_vehicle_id, driver_id, expected_start_date, expected_end_date);
                            }


                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(StartJobActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();





    }

    //api call for blocking the driver and truck for this job
    public void BlockTruckandDriver(String job_id, String provider_vehicle_id, String job_driver_id, String expected_start_date, String expected_end_date) {

        showProgressDialog(StartJobActivity.this, "loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<BlockTruckandDriverResponse> call = apiService.BlockTruckandDriverResponse(GloablMethods.API_HEADER + acess_token, job_id, provider_vehicle_id, job_driver_id, expected_start_date, expected_end_date);
        call.enqueue(new Callback<BlockTruckandDriverResponse>() {
            @Override
            public void onResponse(Call<BlockTruckandDriverResponse> call, retrofit2.Response<BlockTruckandDriverResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    showSnakBar(root, response.body().getMeta().getMessage());
                    // for layout handling
                    relativeSerachAvalibleTruck.setVisibility(View.GONE);
                    relativeStartJob.setVisibility(View.VISIBLE);
                    Intent i = new Intent(getApplicationContext(), MyJobtabViewActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(root, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
//                        Log.d("exception", e.getMessage());
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<BlockTruckandDriverResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });

    }

}