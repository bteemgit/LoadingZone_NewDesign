package com.example.admin.loadingzone.modules.profile;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.global.SessionManager;
import com.example.admin.loadingzone.modules.driver.DriverAddActivity;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.permission.PermissionsActivity;
import com.example.admin.loadingzone.permission.PermissionsChecker;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.retrofit.model.UserProfileResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
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

public class UserProfileEditActivity extends BaseActivity implements View.OnClickListener {

    /**
     * Permission List
     */
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    @NonNull
    @BindView(R.id.ivUserProfilePhoto)
    ImageView imageViewProfileImage;
    @BindView(R.id.editProviderEmail)
    @NonNull
    EditText editTextUserEmail;
    @NonNull
    @BindView(R.id.editProviderName)
    EditText editTextPrividerName;
    @NonNull
    @BindView(R.id.editProviderLocation)
    EditText editTextPrividerLocation;
    @NonNull
    @BindView(R.id.editProviderMobile)
    EditText editTextPrividerMobile;
    @NonNull
    @BindView(R.id.fabProfileUpload)
    FloatingActionButton fabProfileDataUpload;
    @NonNull
    @BindView(R.id.btnEditProfilePic)
    Button btnEditProfilePic;
    @NonNull
    @BindView(R.id.btnEditProfilePicUpload)
    Button btnEditProfilePicUpload;
    SessionManager session;
    private ApiInterface apiService;
    String userImage, userName, userEmail, userMobile, userLocation, isFrom;
    private int avatarSize;
    String imagePath;
    PermissionsChecker checker;
    String service_provider_id="null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);
        //tool bar init...
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
       /* android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.profile_title);*/
        checker = new PermissionsChecker(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        //If the session is logged in move to Home
        session = new SessionManager(getApplicationContext());
        userEmail = AppController.getString(getApplicationContext(), "customer_email");
        if (userEmail != null || userEmail.length() > 0) {
            editTextUserEmail.setText(userEmail);
            editTextUserEmail.setKeyListener(null);
        }
        // :-fetching the details from userprofile activity
        isFrom = getIntent().getStringExtra("isFrom");
        if (isFrom.equals("Profile")) {
            userName = getIntent().getStringExtra("userName");
            userMobile = getIntent().getStringExtra("userMobile");
            userLocation = getIntent().getStringExtra("userLocation");
            userImage = getIntent().getStringExtra("userImage");
            editTextPrividerName.setText(userName);
            editTextPrividerMobile.setText(userMobile);
            editTextPrividerLocation.setText(userLocation);
            Picasso.with(UserProfileEditActivity.this)
                    .load(userImage)
                    .placeholder(R.drawable.img_circle_placeholder)
                    .resize(avatarSize, avatarSize)
                    .centerCrop()
                    .transform(new CircleTransformation())
                    .into(imageViewProfileImage);

        }

        editTextPrividerLocation.setOnClickListener(this);
        editTextPrividerLocation.setFocusable(false);
    }


    @Override
    public void onClick(View v) {
        try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(UserProfileEditActivity.this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            System.out.println(e);
            // TODO: Handle the error.
        }

    }
    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.fabProfileUpload)
    public void profileDataUpload() {
        String providerName = editTextPrividerName.getText().toString().trim();
        String location = editTextPrividerLocation.getText().toString().trim();
        String mobile = editTextPrividerMobile.getText().toString().trim();
        service_provider_id = AppController.getString(getApplicationContext(), "service_provider_id");
        //if profile is already created
        if (!isFrom.equals("login"))
        {
            //if profile is created
            if (!service_provider_id.equals("null")||service_provider_id!="null") {
                if (GloablMethods.validate(providerName, location, mobile)) {
                    if(isValidMobile(mobile)){

                        if (isConnectingToInternet(UserProfileEditActivity.this)) {
                            userProfileUpdate(service_provider_id,providerName, mobile, location, "54.9", "89.99");
                        } else {
                            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
                        }
                    }

                } else {
                    showSnakBar(relativeLayoutRoot, MessageConstants.PLEASE_FILL_ALL);
                }
            }
        }
        else {
            if (!service_provider_id.equals("null")||service_provider_id!="null")
            {
                if (isConnectingToInternet(UserProfileEditActivity.this)) {
                    userProfileUpdate(service_provider_id,providerName, mobile, location, "54.9", "89.99");

                } else {
                    showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
                }
            }
            else
            {
                if (isConnectingToInternet(UserProfileEditActivity.this)) {
                    if (!providerName.equals(null)&&mobile.equals(null)&&location.equals(null))
                    {
                        userProfileCreate(providerName, mobile, location, "54.9", "89.99");
                    }
                    else {
                        showSnakBar(relativeLayoutRoot, MessageConstants.PLEASE_FILL_ALL);
                    }

                } else {
                    showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
                }
            }

        }



    }
    //method for mobile no. validation
    public boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 6 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
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
public void uploadImageToServer()
{
if (isConnectingToInternet(UserProfileEditActivity.this))
    {
        uploadImage();
    }
    else
{
    showSnakBar(relativeLayoutRoot,MessageConstants.INTERNET);
}
}

    //    // api call method for user profile
    private void userProfileCreate(String provider_name, String phone_1, String location_name, String latitude, String longitude) {
        showProgressDialog(UserProfileEditActivity.this, "Uplaoding the Profile Data");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<UserProfileResponse> call = apiService.CreateProfileDetails(GloablMethods.API_HEADER + acess_token, provider_name, phone_1, location_name, latitude, longitude);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, retrofit2.Response<UserProfileResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    session.setLogin(true);
                    AppController.setString(UserProfileEditActivity.this, "user_name", response.body().getProviderName());
                    AppController.setString(getApplicationContext(), "provider_pic", response.body().getProfilePic());
                    AppController.setString(UserProfileEditActivity.this, "service_provider_id", response.body().getServiceProviderId());
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    i.putExtra("isFrom","home");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    showSnakBar(relativeLayoutRoot, "You have Created the profile");
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    // api call method for user profile update
    private void userProfileUpdate(String service_provider_id,String provider_name, String phone_1, String location_name, String latitude, String longitude) {
        showProgressDialog(UserProfileEditActivity.this, "Uplaoding the Profile Data");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<UserProfileResponse> call = apiService.CreateProfileUpdate(GloablMethods.API_HEADER + acess_token, service_provider_id,provider_name, phone_1, location_name, latitude, longitude);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, retrofit2.Response<UserProfileResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    AppController.setString(UserProfileEditActivity.this, "customer_name", response.body().getProviderName());
                    AppController.setString(getApplicationContext(), "provider_pic", response.body().getProfilePic());

                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    i.putExtra("isFrom","home");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    showSnakBar(relativeLayoutRoot, "You have Updated the profile");
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }
    //: ----------------------------------------profile pic update section -----------------------------------:-//

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
                Picasso.with(UserProfileEditActivity.this)
                        .load(new File(imagePath))
                        .placeholder(R.drawable.img_circle_placeholder)
                        .resize(avatarSize, avatarSize)
                        .centerCrop()
                        .transform(new CircleTransformation())
                        .into(imageViewProfileImage);
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
                editTextPrividerLocation.setText(place.getName());



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
    showProgressDialog(UserProfileEditActivity.this,"Uploading The Image...");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //File creating from selected URL
        File file = new File(imagePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
         MultipartBody.Part body =
                MultipartBody.Part.createFormData("profile_pic", file.getName(), requestFile);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<UserProfileResponse> resultCall = apiService.UploadprofilePicProvider(GloablMethods.API_HEADER + acess_token, body);
        // finally, execute the request
        resultCall.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
               hideProgressDialog();
                // Response Success or Fail
                if (response.isSuccessful()) {
                    //setting Image to AppController
                    AppController.setString(getApplicationContext(), "provider_pic", response.body().getProfilePic());
                    AppController.setString(getApplicationContext(), "service_provider_id", String.valueOf(response.body().getServiceProviderId()));
                    btnEditProfilePicUpload.setVisibility(View.GONE);
                    btnEditProfilePic.setVisibility(View.VISIBLE);
                    Picasso.with(UserProfileEditActivity.this)
                            .load(response.body().getProfilePic())
                            .placeholder(R.drawable.img_circle_placeholder)
                            .resize(avatarSize, avatarSize)
                            .centerCrop()
                            .transform(new CircleTransformation())
                            .into(imageViewProfileImage);
                    Snackbar.make(relativeLayoutRoot, R.string.string_upload_success, Snackbar.LENGTH_LONG).show();
                }else {
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

                }
                imagePath = "";

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }


}
