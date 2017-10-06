package com.example.admin.loadingzone.modules.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.BottomNavigationViewHelper;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.global.SessionManager;
import com.example.admin.loadingzone.modules.ForgotOrChangePassword.ChangePassword;
import com.example.admin.loadingzone.modules.driver.DriverFragment;
import com.example.admin.loadingzone.modules.login.LoginActivity;
import com.example.admin.loadingzone.modules.message.MessageFragment;
import com.example.admin.loadingzone.modules.myjob.MyJobtabViewActivity;
import com.example.admin.loadingzone.modules.myqutation.MyQuotationActivity;
import com.example.admin.loadingzone.modules.nottification.NottificationListActivity;
import com.example.admin.loadingzone.modules.profile.UserProfileActivity;
import com.example.admin.loadingzone.modules.truck.TruckFragment;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.app.Config;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {
    // for bottom menu
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView mBottomNav;
    private ApiInterface apiService;
    private int mSelectedItem;
    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;
    @BindView(R.id.id_profile_xml)
    LinearLayout linear_profile;
    @BindView(R.id.id_linear_myquotation)
    LinearLayout linear_myquotation;
    @BindView(R.id.id_linearmyJob)
    LinearLayout linear_linearmyJob;
    @BindView(R.id.nav_changepassword)
    LinearLayout linear_changepassword;
    @BindView(R.id.drawer_layout)
    DrawerLayout nav_drawer;
    @BindView(R.id.id_img_logout)
    ImageView img_logout;
    private SessionManager sessionManager;
    String profilepic = "01";
    ImageView user_imageView;
    Fragment homeFragment = new HomeFragment();
    Fragment truckFragment = new TruckFragment();
    Fragment driverFragment = new DriverFragment();
    Fragment messageFragment = new MessageFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    // bottom navigation click listner
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.mHome) {
                Fragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();
                fragmentTransaction.addToBackStack(null);
                return true;
            } else if (id == R.id.mDriver) {
                Fragment driverFragment = new DriverFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, driverFragment).commit();
                fragmentTransaction.addToBackStack(null);
                return true;
            } else if (id == R.id.mTruck) {
                Fragment truckFragment = new TruckFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, truckFragment).commit();
                fragmentTransaction.addToBackStack(null);
                return true;

            } else if (id == R.id.mChat) {
                Fragment messageFragment = new MessageFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, messageFragment).commit();
                fragmentTransaction.addToBackStack(null);
                return true;
            }
            return false;
        }

    };
    String isFrom = "home";

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
        BottomNavigationViewHelper.disableShiftMode(mBottomNav);   // bottom navigation disabling the animations
        mBottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
// setting the drawer header items
        profilepic = AppController.getString(getApplicationContext(), "provider_pic");
        TextView text_users_name = (TextView) findViewById(R.id.id_text_users_name);
        text_users_name.setText(AppController.getString(getApplicationContext(), "customer_name"));
        TextView text_usersemail = (TextView) findViewById(R.id.id_text_usersemail);
        text_usersemail.setText((AppController.getString(getApplicationContext(), "customer_email")));
        user_imageView = (ImageView) findViewById(R.id.imageView6);
        Context context = this;
        Picasso.with(context)
                .load(AppController.getString(getApplicationContext(), "provider_pic"))
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(user_imageView);

        isFrom = getIntent().getStringExtra("isFrom");
        // fragment handling
        if (isFrom.equals("home")) {
            Fragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else if (isFrom.equals("truck")) {
            Fragment truckFragment = new TruckFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, truckFragment).commit();
            mBottomNav.setSelectedItemId(R.id.mTruck);
        } else if (isFrom.equals("driver")) {
            Fragment driverFragment = new DriverFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, driverFragment).commit();
            mBottomNav.setSelectedItemId(R.id.mTruck);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Context context = this;
        Picasso.with(context)
                .load(AppController.getString(getApplicationContext(), "provider_pic"))
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(user_imageView);

    }


    @OnClick(R.id.id_profile_xml)
    void nav_profileClick() {
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
        nav_drawer.closeDrawers();
    }

    @OnClick(R.id.id_linear_myquotation)
    void nav_myquotationClick() {
        Intent intent = new Intent(getApplicationContext(), MyQuotationActivity.class);
        nav_drawer.closeDrawers();
        startActivity(intent);
    }

    @OnClick(R.id.id_linearmyJob)
    void nav_myJobClick() {
        Intent intent = new Intent(getApplicationContext(), MyJobtabViewActivity.class);
        nav_drawer.closeDrawers();
        startActivity(intent);

    }

    @OnClick(R.id.nav_changepassword)
    void nav_changePasswordClick() {
        Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
        nav_drawer.closeDrawers();
        startActivity(intent);
    }

    @NonNull
    @OnClick(R.id.nav_notification)
    public void navNottification() {
        Intent i = new Intent(getApplicationContext(), NottificationListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        nav_drawer.closeDrawers();
        startActivity(i);
    }

    @OnClick(R.id.id_img_logout)
    void nav_logoutClick() {
        Logout();
        nav_drawer.closeDrawers();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    public void Logout() {

        showProgressDialog(HomeActivity.this, "Log outing...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Call<Meta> call = apiService.Logout(GloablMethods.API_HEADER + acess_token, regId);
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


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Are you sure..? want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


}
