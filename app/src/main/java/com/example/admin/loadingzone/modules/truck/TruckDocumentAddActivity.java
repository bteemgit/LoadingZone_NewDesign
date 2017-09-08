package com.example.admin.loadingzone.modules.truck;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.global.Utility;
import com.example.admin.loadingzone.modules.profile.UserProfileEditActivity;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.TruckdocumentsResponse;
import com.example.admin.loadingzone.retrofit.model.TruckdocumentsViewResponse;
import com.example.admin.loadingzone.retrofit.model.UserProfileResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleDoc;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckDocumentAddActivity extends BaseActivity {
    ApiInterface apiService;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String imagePath, vehicle_id,isFrom;
    @NonNull
    @BindView(R.id.btnSelectPhoto)
    Button btnSelectPhoto;
    @NonNull
    @BindView(R.id.ivImagePreview)
    ImageView ivImagePreview;


    @NonNull
    @BindView(R.id.editDescription)
    EditText editTextDescription;
    @NonNull
    @BindView(R.id.mrootView)
    RelativeLayout rootView;
    @NonNull
    @BindView(R.id.btnUpload)
    Button btnUpload;
    @NonNull
    @BindView(R.id.fabFinish)
    FloatingActionButton fabFinish;
    @BindView(R.id.linearDocuments)
    LinearLayout linearDocuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_document_add);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.truck_documents);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        vehicle_id = getIntent().getStringExtra("vehicle_id");
        isFrom=getIntent().getStringExtra("isFrom");
    }

    @NonNull
    @OnClick(R.id.btnSelectPhoto)
    public void selectPhoto() {
        selectImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library")) {
                        View v = new View(getApplicationContext());
                        showImagePopup(v);
                    }

                } else {

                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(TruckDocumentAddActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(TruckDocumentAddActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result) {
                        View v = new View(getApplicationContext());
                        showImagePopup(v);
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void showImagePopup(View view) {
        // File System.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);
        // Chooser of file system options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
        startActivityForResult(chooserIntent, SELECT_FILE);

    }

    // upload files
    @NonNull
    @OnClick(R.id.btnUpload)
    public void documentUpload() {

        if (isConnectingToInternet(TruckDocumentAddActivity.this)) {
            String description = editTextDescription.getText().toString();
            uploadImage(description, vehicle_id);
        } else {
            showSnakBar(rootView, MessageConstants.INTERNET);
        }
    }

    // finish
    @NonNull
    @OnClick(R.id.fabFinish)
    public void finish() {
        if (isFrom.equals("DocUpdate"))
        {
            Intent i = new Intent(TruckDocumentAddActivity.this, TruckDocumentEditActivity.class);
            i.putExtra("vehicle_id",vehicle_id);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(TruckDocumentAddActivity.this, TruckViewActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }


    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        linearDocuments.setVisibility(View.VISIBLE);
        ivImagePreview.setImageBitmap(photo);
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), photo);
        imagePath = getRealPathFromURI(tempUri);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data == null) {
            //  buttonImageuplaod.setVisibility(View.GONE);
            Snackbar.make(rootView, R.string.string_unable_to_pick_image, Snackbar.LENGTH_INDEFINITE).show();
            return;
        }
        Uri selectedImageUri = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
        linearDocuments.setVisibility(View.VISIBLE);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            Picasso.with(TruckDocumentAddActivity.this)
                    .load(new File(imagePath))
                    .placeholder(R.drawable.img_circle_placeholder)
                    .resize(200, 200)
                    .centerCrop()
                    .into(ivImagePreview);

            Snackbar.make(rootView, R.string.string_reselect, Snackbar.LENGTH_LONG).show();
            cursor.close();
        } else {
            Snackbar.make(rootView, R.string.string_unable_to_load_image, Snackbar.LENGTH_LONG).show();
        }
    }



    // upoload the new doc
    private void uploadImage(String description, String vehicle_id) {
        showProgressDialog(TruckDocumentAddActivity.this, "Uploading...");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //File creating from selected URL
        File file = new File(imagePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("document_file", file.getName(), requestFile);

        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckdocumentsResponse> resultCall = apiService.UploadTruckDocuments(GloablMethods.API_HEADER + acess_token, body, vehicle_id, description);
        // finally, execute the request
        resultCall.enqueue(new Callback<TruckdocumentsResponse>() {
            @Override
            public void onResponse(Call<TruckdocumentsResponse> call, Response<TruckdocumentsResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    Snackbar.make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG).show();
                    imagePath = "";
                    linearDocuments.setVisibility(View.GONE);
                    editTextDescription.setText("");
                } else {
                    Snackbar.make(rootView, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TruckdocumentsResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }
}
