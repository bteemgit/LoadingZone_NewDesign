package com.example.admin.loadingzone.modules.driver;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.profile.UserProfileEditActivity;
import com.example.admin.loadingzone.permission.PermissionsActivity;
import com.example.admin.loadingzone.permission.PermissionsChecker;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.DriverList;
import com.example.admin.loadingzone.retrofit.model.TruckDriverAddResponse;
import com.example.admin.loadingzone.retrofit.model.UserProfileResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleList;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.example.admin.loadingzone.view.RevealBackgroundView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAddActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    /**
     * Permission List
     */
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    public static void startdriverAddActvity(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, DriverAddActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startingActivity.startActivity(intent);
    }

    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    @BindView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @BindView(R.id.ivDriverProfilePhoto)
    ImageView imageViewDriverProfileImage;
    @NonNull
    @BindView(R.id.btnEditProfilePic)
    Button btnEditProfilePic;
    @NonNull
    @BindView(R.id.btnEditProfilePicUpload)
    Button btnEditProfilePicUpload;
    @NonNull
    @BindView(R.id.fabDriverAddDetails)
    FloatingActionButton fabDriverAdd;
    @NonNull
    @BindView(R.id.editDriverEmail)
    EditText editTextDriverEmail;
    @NonNull
    @BindView(R.id.editDriverName)
    EditText editTextDriverName;
    @NonNull
    @BindView(R.id.editDriverLocation)
    EditText editTextDriverAdress;
    @NonNull
    @BindView(R.id.editDriverMobile)
    EditText editTextDriverMobile;
    private ApiInterface apiService;
    private int avatarSize;
    String imagePath;
    PermissionsChecker checker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_add);
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Driver");
        setupRevealBackground(savedInstanceState);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        checker = new PermissionsChecker(this);

       // editTextDriverAdress.setOnClickListener(this);
        editTextDriverAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(DriverAddActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    System.out.println(e);
                    // TODO: Handle the error.
                }
            }
        });

        editTextDriverAdress.setFocusable(false);

    }




   /* @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editDriverLocation:
                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(DriverAddActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    System.out.println(e);
                    // TODO: Handle the error.
                }
        }
    }*/

    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        //vRevealBackground.setFillPaintColor(0xFF16121a);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {

        } else {

        }
    }




    // Add new Driver
    @NonNull
    @OnClick(R.id.fabDriverAddDetails)
    public void addTruck() {
        String driverEmail = editTextDriverEmail.getText().toString().trim();
        String driverName = editTextDriverName.getText().toString().trim();
        String driverAdress = editTextDriverAdress.getText().toString().trim();
        String driverMobile = editTextDriverMobile.getText().toString().trim();

        if (GloablMethods.validate(driverEmail, driverName, driverAdress,driverMobile)){
            if (isValidEmaillId(driverEmail)) {

                if(isValidMobile(driverMobile)){
                    if (isConnectingToInternet(DriverAddActivity.this)) {
                        AddDriver(driverName, driverMobile, driverEmail, driverAdress);
                    } else {
                        showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
                    }
                }
            }
            else{
                showSnakBar(relativeLayoutRoot, MessageConstants.INVALID_EMAIL);
            }
        }else{
            showSnakBar(relativeLayoutRoot, MessageConstants.PROVIDE_BASIC_INFO);
        }






    }

    //method for mobile no. validation
    public boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 6 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
                //txtPhone.setError("Not Valid Number");
               // Toast.makeText(this, "Not Valid Mobile Number", Toast.LENGTH_SHORT).show();
                showSnakBar(relativeLayoutRoot, "Not a valid Mobile Number !!");
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }



    @OnClick(R.id.btnEditProfilePic)
    public void changeProfilePic() {
        View v = new View(getApplicationContext());
        showImagePopup(v);
    }

    @OnClick(R.id.btnEditProfilePicUpload)
    public void uploadImageToServer() {
        if (isConnectingToInternet(DriverAddActivity.this)) {
            uploadImage();
        } else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }
    }

    //:-----------Driver Add Api-------------:\\

    private void AddDriver(String driver_name, String driver_phone, String driver_email, String driver_address) {
        showProgressDialog(DriverAddActivity.this, "Add Driver,please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckDriverAddResponse> call = apiService.AddDriver(GloablMethods.API_HEADER + acess_token, driver_name, driver_phone, driver_email, driver_address);
        call.enqueue(new Callback<TruckDriverAddResponse>() {
            @Override
            public void onResponse(Call<TruckDriverAddResponse> call, Response<TruckDriverAddResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful())
                {
                    showSnakBar(relativeLayoutRoot, "Driver Added Successfully");
                    Intent i = new Intent(DriverAddActivity.this, DriverViewActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(relativeLayoutRoot, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }



               // showSnakBar(relativeLayoutRoot, response.body().getMeta().getMessage().toString());
            }

            @Override
            public void onFailure(Call<TruckDriverAddResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();

            }
        });
    }

    //:.....................profile pic upload

    /**
     * Showing Image Picker
     */
    public void showImagePopup(View view) {
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 1010);
        }
    }

    /***
     * OnResult of Image Picked
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                //  buttonImageuplaod.setVisibility(View.GONE);
                Snackbar.make(relativeLayoutRoot, R.string.string_unable_to_pick_image, Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                btnEditProfilePicUpload.setVisibility(View.VISIBLE);
                btnEditProfilePic.setVisibility(View.GONE);
//                Picasso.with(getApplicationContext()).load(new File(imagePath))
//                        .into(imageViewProfile);
                Picasso.with(DriverAddActivity.this)
                        .load(new File(imagePath))
                        .placeholder(R.drawable.img_circle_placeholder)
                        .resize(avatarSize, avatarSize)
                        .centerCrop()
                        .transform(new CircleTransformation())
                        .into(imageViewDriverProfileImage);

                Snackbar.make(relativeLayoutRoot, "Please tap upload button ", Snackbar.LENGTH_LONG).show();
                cursor.close();
            } else {
                Snackbar.make(relativeLayoutRoot, R.string.string_unable_to_load_image, Snackbar.LENGTH_LONG).show();
            }
        }
        else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                editTextDriverAdress.setText(place.getName());



            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }

    private void uploadImage() {
        showProgressDialog(DriverAddActivity.this, "Uploading The Image...");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //File creating from selected URL
        File file = new File(imagePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("profile_pic", file.getName(), requestFile);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<UserProfileResponse> resultCall = apiService.UploadDriverprofilePic(GloablMethods.API_HEADER + acess_token, body);
        // finally, execute the request
        resultCall.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                hideProgressDialog();
                // Response Success or Fail
                if (response.isSuccessful()) {
                    AppController.setString(getApplicationContext(), "pic", response.body().getProfilePic());

                    btnEditProfilePicUpload.setVisibility(View.GONE);
                    btnEditProfilePic.setVisibility(View.VISIBLE);
                    Picasso.with(DriverAddActivity.this)
                            .load(response.body().getProfilePic())
                            .placeholder(R.drawable.img_circle_placeholder)
                            .resize(avatarSize, avatarSize)
                            .centerCrop()
                            .transform(new CircleTransformation())
                            .into(imageViewDriverProfileImage);
                    Snackbar.make(relativeLayoutRoot, R.string.string_upload_success, Snackbar.LENGTH_LONG).show();


                } else {
                    Snackbar.make(relativeLayoutRoot, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();
                }

                /**
                 * Update Views
                 */
                imagePath = "";

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }


}
