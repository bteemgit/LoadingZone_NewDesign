package com.example.admin.loadingzone.modules.truck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.retrofit.model.TruckdocumentsViewResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleDoc;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckDocumentEditActivity extends BaseActivity {
    ApiInterface apiService;
    TruckDocumentDeleteListAdapter truckDocumentDeleteListAdapter;
    private List<VehicleDoc> vehicleDocArrayList = new ArrayList<>();
    @NonNull
    @BindView(R.id.recyclerTruckDocDeleteList)
    EndlessRecyclerView recyclerTruckDocDeleteList;
    @NonNull
    @BindView(R.id.relativeRoot)
    RelativeLayout rootView;
    String provider_vehicle_id,vehicle_id;
    @NonNull
    @BindView(R.id.linerTruckEditFinish)
    LinearLayout linerTruckEditFinish;
    @NonNull
    @BindView(R.id.linearTruckDocAdd)
    LinearLayout linearTruckDocAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_documnetedit);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Document Edit");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        provider_vehicle_id = getIntent().getStringExtra("provider_vehicle_id");
        vehicle_id = getIntent().getStringExtra("vehicle_id");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerTruckDocDeleteList.setLayoutManager(layoutManager);
        if (isConnectingToInternet(getApplicationContext())) {
            getTruckDocList(provider_vehicle_id);
        } else {
            showSnakBar(rootView, MessageConstants.INTERNET);
        }
    }

    // add new doc to the list
    @NonNull
    @OnClick(R.id.linearTruckDocAdd)
    public void AddnewDoc() {
        Intent i = new Intent(getApplicationContext(), TruckDocumentAddActivity.class);
        i.putExtra("vehicle_id", vehicle_id);
        i.putExtra("isFrom","DocUpdate");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @NonNull
    // finish the update
    @OnClick(R.id.linerTruckEditFinish)
    public void finishEdit() {
        Intent i = new Intent(getApplicationContext(), TruckViewActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
    //getting the truck document list
    public void getTruckDocList
    (final String provider_vehicle_id) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckdocumentsViewResponse> call = apiService.getTruckDocList(GloablMethods.API_HEADER + acess_token, provider_vehicle_id, 1);
        call.enqueue(new Callback<TruckdocumentsViewResponse>() {
            @Override
            public void onResponse(Call<TruckdocumentsViewResponse> call, retrofit2.Response<TruckdocumentsViewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getVehicleDocs().isEmpty()) {
                        vehicleDocArrayList = response.body().getVehicleDocs();
                        truckDocumentDeleteListAdapter = new TruckDocumentDeleteListAdapter(vehicleDocArrayList,TruckDocumentEditActivity.this, new TruckDocumentDeleteListAdapter.ListAdapterListener() {
                            @Override
                            public void onClickAtOKButton(int position) {
                                int doc_id=vehicleDocArrayList.get(position).getVehicleDocumentId();
                                DeleteTruck(doc_id);
                            }
                        });
                        recyclerTruckDocDeleteList.setAdapter(truckDocumentDeleteListAdapter);
                        truckDocumentDeleteListAdapter.notifyDataSetChanged();

                    }
                }
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(rootView, meta.getString("message"));
                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<TruckdocumentsViewResponse> call, Throwable t) {
            }
        });
    }
    // delete the document

    private void DeleteTruck(int doc_id) {
showProgressDialog(TruckDocumentEditActivity.this,"deleting..");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<Meta> call = apiService.DeleteTruckDocumet(GloablMethods.API_HEADER + acess_token, doc_id);
        call.enqueue(new Callback<Meta>() {
            @Override
            public void onResponse(Call<Meta> call, Response<Meta> response) {
          hideProgressDialog();
                if (response.isSuccessful()) {

                    showSnakBar(rootView,response.body().getMessage());
                    getTruckDocList(provider_vehicle_id);

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                       showSnakBar(rootView, meta.getString("message"));


                    } catch (Exception e) {
                        showSnakBar(rootView, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Meta> call, Throwable t) {

            }
        });
    }

}
