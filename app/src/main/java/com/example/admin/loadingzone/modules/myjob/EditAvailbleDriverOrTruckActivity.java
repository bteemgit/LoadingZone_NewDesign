package com.example.admin.loadingzone.modules.myjob;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import com.example.admin.loadingzone.global.SlideAnimationUtil;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.BlockTruckandDriverResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class EditAvailbleDriverOrTruckActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @NonNull
    @BindView(R.id.root)
    RelativeLayout root;
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
    @BindView(R.id.textSelection)
    TextView textSelection;

    @NonNull
    @BindView(R.id.textSelctedDriverEmail)
    TextView textSelctedDriverEmail;
    /*    @NonNull
        @BindView(R.id.textSelctedDriverName)
        TextView textSelctedDriverName;*/
    @NonNull
    @BindView(R.id.textSelctedDriverMobile)
    TextView textSelctedDriverMobile;
    @NonNull
    @BindView(R.id.relative_SerachAvalibleTruck)
    RelativeLayout relativeSerachAvalibleTruck;
    @NonNull
    @BindView(R.id.card_driver)
    CardView cardViewDriver;
    @NonNull
    @BindView(R.id.card_vehicle)
    CardView cardViewVehicle;

    @NonNull
    @BindView(R.id.textSelctedTruckName)
    TextView textSelctedTruckName;
    @NonNull
    @BindView(R.id.textSelctedTruckType)
    TextView textSelctedTruckType;
    @NonNull
    @BindView(R.id.textSelctedTruckSize)
    TextView textSelctedTruckSize;

    @NonNull
    @BindView(R.id.imageDriverPic)
    ImageView imageViewDriverPic;


    @NonNull
    @BindView(R.id.textViewDriverNme)
    TextView textViewDriver;
    @NonNull
    @BindView(R.id.textChangeTruck)
    TextView textChangeTruck;

    @NonNull
    @BindView(R.id.textChangeDriver)
    TextView textChangeDriver;


    ApiInterface apiService;
    private int startYear, startMonth, startDay, startHour, startMinute;
    private int endYear, endMonth, endDay, endHour, endMinute;
    private long startUnixTimeStamp, endUnixTimeStamp;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String jobStatus, Job_id, truck_id, driver_id;
    public static String IsEditVehicle = "EditVehicle";
    public static String IsEditDriver = "EditDriver";
    public static String SEARCH_AVAIlABLE_DRIVER = "Search Avalible Driver";
    public static String SEARCH_AVAILaBLE_TRUCK = "Search Avalible Truck";
    public static String SAVE_TRUCK = "Save Truck";
    public static String SAVE_DRIVER = "Save Driver";

    String isFromAssignedJobs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_availble_driver_or_truck);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        jobStatus = getIntent().getStringExtra("jobStatus");// identyfying the action from which activity
        String start_date = getIntent().getStringExtra("start_date");
        String start_time = getIntent().getStringExtra("start_time");
        String end_time = getIntent().getStringExtra("end_time");
        String end_date = getIntent().getStringExtra("end_date");
        Job_id = getIntent().getStringExtra("JobId");
        textViewSelectedDate.setText(start_date);
        textViewSelectedTime.setText(start_time);
        textSelectedDateEnd.setText(end_date);
        textSelectedTimeEnd.setText(end_time);
        // seperating the strt date and end time from reciving the get int and assigned in to particular start year,month,time etc
        // for generating the unixtimestamp corectly
        if (!start_date.equals(null))
        {
            String[] separated_start_date = start_date.split("-");
            startYear= Integer.parseInt(separated_start_date[0].trim());
            startMonth= Integer.parseInt(separated_start_date[1].trim());
            startDay= Integer.parseInt(separated_start_date[2].trim());
        }
        if (!start_time.equals(null))
        {
            String[] separated_start_time = start_time.split(":");
            startHour= Integer.parseInt(separated_start_time[0].trim());
            startMinute= Integer.parseInt(separated_start_time[1].trim());
        }

        if (!end_date.equals(null))
        {
            String[] separated_end_date = end_date.split("-");
            endYear= Integer.parseInt(separated_end_date[0].trim());
            endMonth= Integer.parseInt(separated_end_date[1].trim());
            endDay= Integer.parseInt(separated_end_date[2].trim());
        }
        if (!end_time.equals(null))
        {
            String[] separated_start_time = end_time.split(":");
            endHour= Integer.parseInt(separated_start_time[0].trim());
            endMinute= Integer.parseInt(separated_start_time[1].trim());
        }
        //setting the toolbar according to jobStatus
        if (jobStatus.equals(IsEditDriver)) {
            getSupportActionBar().setTitle("Edit Driver");
            textSelection.setText(SEARCH_AVAIlABLE_DRIVER);
        }
        if (jobStatus.equals(IsEditVehicle)) {
            getSupportActionBar().setTitle("Edit Truck");
            textSelection.setText(SEARCH_AVAILaBLE_TRUCK);
        }
    }

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
                        textViewSelectedDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        startUnixTimeStamp = UnixTimeStampConvertion(startYear, startMonth, startDay, startHour, startMinute);

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
                            textViewSelectedTime.setText(hourOfDay + ":" + minute);
                            startUnixTimeStamp = UnixTimeStampConvertion(startYear, startMonth, startDay, startHour, startMinute);

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
                        endUnixTimeStamp = UnixTimeStampConvertion(endYear, endMonth, endDay, endHour, endMinute);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
                        textSelectedTimeEnd.setText(hourOfDay + ":" + minute);
                        endUnixTimeStamp = UnixTimeStampConvertion(endYear, endMonth, endDay, endHour, endMinute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    // seraching avalible truck on the selected date and time or driver
    @NonNull
    @OnClick(R.id.relative_SerachAvalibleTruck)
    public void searchTruck() {
        if (textSelection.getText().equals(SEARCH_AVAILaBLE_TRUCK) || textSelection.getText().equals(SEARCH_AVAIlABLE_DRIVER)) {
            View v = new View(getApplicationContext());
            SlideAnimationUtil.slideInFromLeft(this, v);
            Intent i = new Intent(EditAvailbleDriverOrTruckActivity.this, AvailableTruckOrDriverActivity.class);
            endUnixTimeStamp = UnixTimeStampConvertion(endYear, endMonth, endDay, endHour, endMinute);
            startUnixTimeStamp = UnixTimeStampConvertion(startYear, startMonth, startDay, startHour, startMinute);
            String sUnixTimeStamp = String.valueOf(startUnixTimeStamp);
            String eUnixTimeStamp = String.valueOf(endUnixTimeStamp);
            i.putExtra("startUnixTimeStamp", sUnixTimeStamp);
            i.putExtra("endUnixTimeStamp", eUnixTimeStamp);
            i.putExtra("jobStatus", jobStatus);
            i.putExtra("isFrom", "Posted");
            startActivityForResult(i, 2);
        }
        if (textSelection.getText().equals(SAVE_DRIVER)) {
// for change teh driver
            EditDriverOrTruck(Job_id, "", driver_id);
        }
        if (textSelection.getText().equals(SAVE_TRUCK)) {
            // for change the Truck
            EditDriverOrTruck(Job_id, truck_id, "");
        }

    }

    //edit driver

    @NonNull
    @OnClick(R.id.textChangeDriver)
    public void editDriver() {
        View v = new View(getApplicationContext());
        SlideAnimationUtil.slideInFromLeft(this, v);
        Intent i = new Intent(EditAvailbleDriverOrTruckActivity.this, AvailableTruckOrDriverActivity.class);
        endUnixTimeStamp = UnixTimeStampConvertion(endYear, endMonth, endDay, endHour, endMinute);
        startUnixTimeStamp = UnixTimeStampConvertion(startYear, startMonth, startDay, startHour, startMinute);
        String sUnixTimeStamp = String.valueOf(startUnixTimeStamp);
        String eUnixTimeStamp = String.valueOf(endUnixTimeStamp);
        i.putExtra("startUnixTimeStamp", sUnixTimeStamp);
        i.putExtra("endUnixTimeStamp", eUnixTimeStamp);
        i.putExtra("jobStatus", jobStatus);
        i.putExtra("isFrom", "Posted");
        startActivityForResult(i, 2);

    }
    //edit driver

    @NonNull
    @OnClick(R.id.textChangeTruck)
    public void editTruck() {
        View v = new View(getApplicationContext());
        SlideAnimationUtil.slideInFromLeft(this, v);
        Intent i = new Intent(EditAvailbleDriverOrTruckActivity.this, AvailableTruckOrDriverActivity.class);
        endUnixTimeStamp = UnixTimeStampConvertion(endYear, endMonth, endDay, endHour, endMinute);
        startUnixTimeStamp = UnixTimeStampConvertion(startYear, startMonth, startDay, startHour, startMinute);
        String sUnixTimeStamp = String.valueOf(startUnixTimeStamp);
        String eUnixTimeStamp = String.valueOf(endUnixTimeStamp);
        i.putExtra("startUnixTimeStamp", sUnixTimeStamp);
        i.putExtra("endUnixTimeStamp", eUnixTimeStamp);
        i.putExtra("jobStatus", jobStatus);
        i.putExtra("isFrom", "Posted");
        startActivityForResult(i, 2);

    }


    // on activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (data != null)
            if (requestCode == 2) {

                String isEdit = data.getStringExtra("isEdit");
                if (isEdit.equals("isDriverEdit")) {
                    // cardViewDriver.setVisibility(View.VISIBLE);
                    cardViewVehicle.setVisibility(View.GONE);
                    String driver_name = data.getStringExtra("driver_name");
                    String driver_email = data.getStringExtra("driver_email");
                    String driver_mobile = data.getStringExtra("driver_mobile");
                    String driver_photo = data.getStringExtra("driver_image");
                    driver_id = data.getStringExtra("driver_id");
                    textSelctedDriverEmail.setText(driver_email);
                    textSelctedDriverMobile.setText(driver_mobile);
                    // textSelctedDriverName.setText(driver_name);
                    textViewDriver.setText(driver_name);
                    Context context = this;
                    Picasso.with(context)
                            .load(driver_photo)
                            .resize(70, 70)
                            .centerCrop()
                            .transform(new CircleTransformation())
                            .into(imageViewDriverPic);


                    if (driver_name.length() > 0) {
                        cardViewDriver.setVisibility(View.VISIBLE);
                        textSelection.setText(SAVE_DRIVER);
                        textSelection.setVisibility(View.VISIBLE);
                    }

                } else {

                    cardViewDriver.setVisibility(View.GONE);
                    String truck_name = data.getStringExtra("truck_name");
                    String truck_type = data.getStringExtra("truck_type");
                    String truck_size = data.getStringExtra("truck_size");
                    truck_id = data.getStringExtra("truck_id");
                    textSelctedTruckName.setText(truck_name);
                    textSelctedTruckType.setText(truck_type);
                    textSelctedTruckSize.setText(truck_size);

                    if (truck_name.length() > 0) {
                        cardViewVehicle.setVisibility(View.VISIBLE);
                        textSelection.setText(SAVE_TRUCK);
                        textSelection.setVisibility(View.VISIBLE);
                    }

                }

            }
    }


    //api call for edit the driver and truck for this job
    public void EditDriverOrTruck(String job_id, String provider_vehicle_id, String job_driver_id) {

        showProgressDialog(EditAvailbleDriverOrTruckActivity.this, "loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<BlockTruckandDriverResponse> call = apiService.EditDriverOrTruck(GloablMethods.API_HEADER + acess_token, job_id, provider_vehicle_id, job_driver_id);
        call.enqueue(new Callback<BlockTruckandDriverResponse>() {
            @Override
            public void onResponse(Call<BlockTruckandDriverResponse> call, retrofit2.Response<BlockTruckandDriverResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals("true") || response.body().getMeta().getStatus().equals(true)) {
                        showSnakBar(root, response.body().getMeta().getMessage());
                        Intent i = new Intent();
                        i.putExtra("isUpdated", "True");
                        setResult(2, i);
                        finish();
                    } else {
                        showSnakBar(root, response.body().getMeta().getMessage());
                    }


                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(root, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<BlockTruckandDriverResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("isUpdated", "false");
        setResult(2, i);
        finish();
    }
}


