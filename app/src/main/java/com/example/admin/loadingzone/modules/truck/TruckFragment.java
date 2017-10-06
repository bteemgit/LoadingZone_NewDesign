package com.example.admin.loadingzone.modules.truck;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.TruckResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleList;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.admin.loadingzone.R.id.floating_action_menu;

/**
 * Created by admin on 9/28/2017.
 */

public class TruckFragment extends Fragment {

    public TruckFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerViewTruck)
    EndlessRecyclerView endlessRecyclerViewTrck;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    @NonNull
    @BindView(R.id.fabAddTruck)
    com.github.clans.fab.FloatingActionButton fab_AddTruck;
    @NonNull
    @BindView(R.id.fabPendingTrucks)
    com.github.clans.fab.FloatingActionButton fab_pendingTrucks;
    @NonNull
    @BindView(floating_action_menu)
    FloatingActionMenu floatingActionMenu;
    ApiInterface apiService;
    TrckListAdapter trckListAdapter;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private List<VehicleList> vehicleListList = new ArrayList<>();
    private static String NEW_TRUCK_ADD = "NewTruck";
    private boolean isSwipeRefreshed = false;
    private HomeActivity homeActivity;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            if (homeActivity.isConnectingToInternet(getActivity())) {
                getTrckList();
            } else {
                homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
            }

        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };
    View dark_bg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity=(HomeActivity)getActivity();
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_truck, container, false);
        ButterKnife.bind(this, view);
        dark_bg = view.findViewById(R.id.background_dimmer);
        refreshLayout.setRefreshing(false);
        setUpListeners();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {

                if (opened) {
                    dark_bg.setVisibility(View.VISIBLE);
                } else {
                    dark_bg.setVisibility(View.GONE);
                }

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (homeActivity.isConnectingToInternet(getActivity())) {
            getTrckList();
        } else {
            homeActivity.showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }

    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        endlessRecyclerViewTrck.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                isSwipeRefreshed=true;
                getTrckList();
            }
        });

        endlessRecyclerViewTrck.addPaginationListener(paginationListener);
        endlessRecyclerViewTrck.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String provider_vehicle_id = String.valueOf(vehicleListList.get(position).getProviderVehicleId());
                String driver = vehicleListList.get(position).getDriver();
                String truckId = String.valueOf(vehicleListList.get(position).getProviderVehicleId());

                String reg_no = vehicleListList.get(position).getRegistration_number();
                String chassis_no = vehicleListList.get(position).getChassis_number();
                String License_no = vehicleListList.get(position).getLicence_number();
                Intent i = new Intent(getActivity(), TruckEditUpdateActivity.class);
                i.putExtra("isFrom", "TruckView");
                i.putExtra("driver", driver);
                i.putExtra("provider_vehicle_id", provider_vehicle_id);
                i.putExtra("truckId", truckId);
                i.putExtra("reg_no", reg_no);
                i.putExtra("chassis_no", chassis_no);
                i.putExtra("License_no", License_no);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        }));

    }

    @Optional
    @OnClick(R.id.fabAddTruck)
    public void truckAdd() {
        int[] startingLocation = new int[2];
        View v = new View(getActivity());
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        TruckAddActivity.startTruckAddActvity(startingLocation, getActivity(), NEW_TRUCK_ADD);
       // overridePendingTransition(0, 0);

    }


    @Optional
    @OnClick(R.id.fabPendingTrucks)
    public void pendingTrucks() {
        Intent intent = new Intent(getActivity(), PendingTruckViewActivity.class);
        startActivity(intent);
    }


    public void getTrckList( ) {

        if (offset == 1) {
            if (!isSwipeRefreshed)
                homeActivity.showProgressDialog(getActivity(), "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getContext(), "acess_token");
        Call<TruckResponse> call = apiService.TrckList(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<TruckResponse>() {
            @Override
            public void onResponse(Call<TruckResponse> call, retrofit2.Response<TruckResponse> response) {
                refreshLayout.setRefreshing(false);
                isSwipeRefreshed=false;
                homeActivity.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getVehicleList().isEmpty()) {
                        List<VehicleList> Listvechicle = response.body().getVehicleList();
                        if (offset == 1) {
                            vehicleListList = Listvechicle;
                            updateEndlessRecyclerView();
                            homeActivity.hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (VehicleList itemModel : Listvechicle) {
                                vehicleListList.add(itemModel);
                            }
                        }
                        if (Listvechicle.size() < limit) {
                            endlessRecyclerViewTrck.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewTrck.setHaveMoreItem(true);
                        }
                        trckListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        endlessRecyclerViewTrck.setHaveMoreItem(false);
                    }

                } else {
                    getActivity().finish();
                    endlessRecyclerViewTrck.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        homeActivity.showSnakBar(relativeLayoutRoot, meta.getString("message"));
                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TruckResponse> call, Throwable t) {
                // Log error here since request failed
                homeActivity.hideProgressDialog();
            }
        });
    }


    private void updateEndlessRecyclerView() {
        trckListAdapter = new TrckListAdapter(vehicleListList, R.layout.item_trck, getContext());
        endlessRecyclerViewTrck.setAdapter(trckListAdapter);
    }

}
