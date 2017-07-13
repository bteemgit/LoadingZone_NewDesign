package com.example.admin.loadingzone.modules.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.BottomNavigationViewHelper;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.MessageConstants;
import com.example.admin.loadingzone.global.SessionManager;
import com.example.admin.loadingzone.modules.driver.DriverViewActivity;
import com.example.admin.loadingzone.modules.login.LoginActivity;
import com.example.admin.loadingzone.modules.message.MessageListViewActivity;
import com.example.admin.loadingzone.modules.myjob.MyJobtabViewActivity;
import com.example.admin.loadingzone.modules.myqutation.MyQuotationActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileActivity;
import com.example.admin.loadingzone.modules.truck.TrckListAdapter;
import com.example.admin.loadingzone.modules.truck.TruckViewActivity;
import com.example.admin.loadingzone.recyclerview.EndlessRecyclerView;
import com.example.admin.loadingzone.recyclerview.RecyclerItemClickListener;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.FromLocation;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.retrofit.model.PostedJobResponse;
import com.example.admin.loadingzone.retrofit.model.TruckResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleList;
import com.example.admin.loadingzone.util.Config;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    // for bottom menu
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView mBottomNav;
    private ApiInterface apiService;
    private int mSelectedItem;
    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;
    private SessionManager sessionManager;
    @BindView(R.id.recyclerViewHomePostedJob)
    EndlessRecyclerView endlessRecyclerViewPostedJob;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;



    @BindView(R.id.id_linear_myquotation)
    LinearLayout linear_myquotation;
    @BindView(R.id.id_linearmyJob)
    LinearLayout linear_linearmyJob;
    @BindView(R.id.id_img_logout)
    ImageView img_logout;



    private PostedJobListAdapter postedJobListAdapter;
    private List<JobList> jobList = new ArrayList<>();
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            if (isConnectingToInternet(getApplicationContext()))
            {
              getJobPosted();
            }
            else {
                showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);

            }
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Loading Zone");

        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        sessionManager = new SessionManager(getApplicationContext());
        // navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // bottom navigation

        BottomNavigationViewHelper.disableShiftMode(mBottomNav);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 1);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);

        refreshLayout.setRefreshing(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewPostedJob.setLayoutManager(layoutManager);
        setUpListeners();


        if (isConnectingToInternet(getApplicationContext()))
        {
       getJobPosted();
    }
        else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }

        linear_myquotation.setOnClickListener(this);
        linear_linearmyJob.setOnClickListener(this);
        img_logout.setOnClickListener(this);
    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewPostedJob.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                  getJobPosted();
            }
        });

        endlessRecyclerViewPostedJob.addPaginationListener(paginationListener);
        endlessRecyclerViewPostedJob.addOnItemTouchListener(new RecyclerItemClickListener(HomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent i=new Intent(HomeActivity.this,PostedJobDetailsActivity.class);
                String JobId = jobList.get(position).getJobId();
                String name = jobList.get(position).getCustomer().getName();
                String email = jobList.get(position).getCustomer().getEmail();
                String phone1 = jobList.get(position).getCustomer().getPhone1();
                String profilepic = jobList.get(position).getCustomer().getProfilePic();
                String FromLoc_latt = jobList.get(position).getFromLocation().getLatitude();
                String FromLoc_long = jobList.get(position).getFromLocation().getLongitude();
                String FromLoc_name = jobList.get(position).getFromLocation().getName();
                String ToLoc_latt = jobList.get(position).getToLocation().getLatitude();
                String ToLoc_long = jobList.get(position).getToLocation().getLongitude();
                String ToLoc_name = jobList.get(position).getToLocation().getName();
                String Material_name = jobList.get(position).getMaterial().getMaterialName();
                Integer Material_id = jobList.get(position).getMaterial().getMaterialId();


                String MaterialDescription = jobList.get(position).getMaterialDescription();
                String weight =jobList.get(position).getWeight();
                String DateOfLoading = jobList.get(position).getDateOfLoading();
                String PaymentType_name = jobList.get(position).getPaymentType().getPaymentTypeName();
                Integer PaymentType_id = jobList.get(position).getPaymentType().getPaymentTypeId();
                String TruckType_name = jobList.get(position).getTruckType().getTruckTypeName();
                String TruckType_id = jobList.get(position).getTruckType().getTruckTypeId();
                String TruckSize_dimension = jobList.get(position).getTruckSize().getTruckSizeDimension();
                Integer TruckSize_id = jobList.get(position).getTruckSize().getTruckSizeId();
            //    String Currency_name = jobList.get(position).getCurrency().getCurrencyName();
                String LocationDistance = String.valueOf(jobList.get(position).getLocationDistance());
                String DateRequested = jobList.get(position).getDateRequested();
                String DateRequestedRelative = jobList.get(position).getDateRequestedRelative();
                String Budget = jobList.get(position).getBudget();
                String QuotationCount = jobList.get(position).getQuotationCount();
                String HasActiveQuotations = jobList.get(position).getHasActiveQuotations();
                String JobStatus = jobList.get(position).getJobStatus().getName();
                i.putExtra("isFrom","Home");
                i.putExtra("JobId",JobId);
                i.putExtra("name",name);
                i.putExtra("email",email);
                i.putExtra("phone1",phone1);
                i.putExtra("profilepic",profilepic);
                i.putExtra("FromLoc_latt",FromLoc_latt);
                i.putExtra("FromLoc_long",FromLoc_long);
                i.putExtra("FromLoc_name",FromLoc_name);
                i.putExtra("ToLoc_latt",ToLoc_latt);
                i.putExtra("ToLoc_long",ToLoc_long);
                i.putExtra("ToLoc_name",ToLoc_name);
                i.putExtra("Material_name",Material_name);
                i.putExtra("Material_id",Material_id);
                i.putExtra("MaterialDescription",MaterialDescription);
                i.putExtra("weight",weight);
                i.putExtra("DateOfLoading",DateOfLoading);
                i.putExtra("PaymentType_name",PaymentType_name);
                i.putExtra("PaymentType_id",PaymentType_id);
                i.putExtra("TruckType_name",TruckType_name);
                i.putExtra("TruckType_id",TruckType_id);
                i.putExtra("TruckSize_id",TruckSize_id);
                i.putExtra("TruckSize_dimension",TruckSize_dimension);
             //   i.putExtra("Currency_name",Currency_name);
                i.putExtra("LocationDistance",LocationDistance);
                i.putExtra("DateRequested",DateRequested);
                i.putExtra("DateRequestedRelative",DateRequestedRelative);
                i.putExtra("Budget",Budget);
                i.putExtra("QuotationCount",QuotationCount);
                i.putExtra("HasActiveQuotations",HasActiveQuotations);
                i.putExtra("JobStatus",JobStatus);
                startActivity(i);

            }
        }));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);

        } else {
            super.onBackPressed();
        }
    }

    private void selectFragment(MenuItem item) {
        // init corresponding fragment

        if (item!=null)
            switch (item.getItemId()) {
                case R.id.mHome:
                   getJobPosted();
                   break;
                case R.id.mDriver:
                    Intent intent = new Intent(HomeActivity.this, DriverViewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.mTruck:
                    Intent i = new Intent(HomeActivity.this, TruckViewActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    break;
                case R.id.id_linear_myquotation:
                    Intent intent1 = new Intent(HomeActivity.this, MyQuotationActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    break;
                case R.id.mChat:
                    Intent intent2= new Intent(HomeActivity.this, MessageListViewActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    break;

            }
        // update selected item
        mSelectedItem = item.getItemId();
        // uncheck the other items.
        for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int[] startingLocation = new int[2];
                    View v = new View(HomeActivity.this);
                    v.getLocationOnScreen(startingLocation);
                    startingLocation[0] += v.getWidth() / 2;
                    UserProfileActivity.startUserProfileFromLocation(startingLocation, HomeActivity.this);
                    overridePendingTransition(0, 0);
                }
            }, 200);


        }
        else if(id==R.id.nav_myjob)
        {
            Intent intent = new Intent(HomeActivity.this, MyJobtabViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if(id==R.id.nav_myqutation)
        {
            Intent intent = new Intent(HomeActivity.this, MyQuotationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
            else if (id == R.id.nav_logout) {
            Logout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Logout
            () {

        showProgressDialog(HomeActivity.this, "Log outing...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        //  String device_token = pref.getString("regId", null);
        Call<Meta> call = apiService.Logout(GloablMethods.API_HEADER + acess_token, "1");
        call.enqueue(new Callback<Meta>() {
            @Override
            public void onResponse(Call<Meta> call, Response<Meta> response) {
                if (response.isSuccessful()) {
                    AppController.clear(getApplicationContext());
                    sessionManager.logoutUser();
                    hideProgressDialog();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Meta> call, Throwable t) {
                // Log error here since request failed
                //   progressDialog.dismiss();

            }
        });
    }

    // Getting the job posted by the customer
    public void getJobPosted
    () {

        if (offset == 1) {
            showProgressDialog(HomeActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<PostedJobResponse> call = apiService.PostedJobList(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<PostedJobResponse>() {
            @Override
            public void onResponse(Call<PostedJobResponse> call, retrofit2.Response<PostedJobResponse> response) {
                refreshLayout.setRefreshing(false);
                hideProgressDialog();
                if (response.isSuccessful() ) {
                    if (!response.body().getJobList().isEmpty()) {
                        List<JobList> JobList = response.body().getJobList();
                        if (offset == 1) {
                            jobList = JobList;
                            updateEndlessRecyclerView();
                            hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (JobList itemModel : JobList) {
                                jobList.add(itemModel);
                            }
                        }
                        if (JobList.size() < limit) {
                            endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewPostedJob.setHaveMoreItem(true);
                        }
                        postedJobListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                    }

                } else {
                    finish();
                    endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    showSnakBar(relativeLayoutRoot, MessageConstants.SERVERERROR);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PostedJobResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }


    private void updateEndlessRecyclerView() {
        postedJobListAdapter = new PostedJobListAdapter(jobList, R.layout.item_home_postedjob, getApplicationContext());
        endlessRecyclerViewPostedJob.setAdapter(postedJobListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_linear_myquotation:
                Intent intent = new Intent(getApplicationContext(),MyQuotationActivity.class);
                startActivity(intent);
                break;
            case R.id.id_linearmyJob:
                intent = new Intent(getApplicationContext(), MyJobtabViewActivity.class);
                startActivity(intent);
                break;
            case R.id.id_img_logout:
                Logout();
                break;
        }
    }
}
