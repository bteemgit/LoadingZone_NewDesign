package com.example.admin.loadingzone.modules.profile;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.BaseActivity;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.UserProfileResponse;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.example.admin.loadingzone.view.RevealBackgroundView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class UserProfileActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @BindView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
//    @BindView(R.id.rvUserProfile)
//    RecyclerView rvUserProfile;

    @BindView(R.id.tlUserProfileTabs)
    TabLayout tlUserProfileTabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.ivUserProfilePhoto)
    ImageView ivUserProfilePhoto;
    @BindView(R.id.vUserDetails)
    View vUserDetails;
    @BindView(R.id.btnEditProfile)
    Button btnFollow;
    @BindView(R.id.vUserStats)
    View vUserStats;
    @BindView(R.id.vUserProfileRoot)
    View vUserProfileRoot;
    @NonNull
    @BindView(R.id.textTruckCount)
    TextView textViewTrckCount;
    @NonNull
    @BindView(R.id.textPendingJobCount)
    TextView textViewPendingJobCount;
    @NonNull
    @BindView(R.id.textUserMobile)
    TextView textViewUserMobile;
    @NonNull
    @BindView(R.id.textUserLocation)
    TextView textViewUserLocation;
    @NonNull
    @BindView(R.id.textUserName)
    TextView textViewUserName;
    @NonNull
    @BindView(R.id.textUserEmail)
    TextView textViewUserEmail;
    @NonNull
    @BindView(R.id.textCompltedCount)
    TextView textViewCompltedCount;
    private ApiInterface apiService;
    private int avatarSize;
    private String profilePhoto;
    private int[] tabIcons = {
            R.drawable.ic_truck_tab,
          //  R.drawable.ic_tab_pending,
            R.drawable.ic_tab_complted

    };
String userName,userEmail,userMobile,completedJob,pendingJob,countTruck,userLocation;

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //tool bar init...
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        this.profilePhoto = getString(R.string.user_profile_photo);
        setupTabs();
        setupRevealBackground(savedInstanceState);
        getuUserProfile();

    }


    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupTabs() {
        setupViewPager(viewPager);
        tlUserProfileTabs.setupWithViewPager(viewPager);
        TextView newTab0 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_jobs, null);
        newTab0.setCompoundDrawablesWithIntrinsicBounds(tabIcons[0], 0, 0, 0);
        tlUserProfileTabs.getTabAt(0).setCustomView(newTab0);
        TextView newTab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_jobs, null);
        newTab1.setCompoundDrawablesWithIntrinsicBounds(tabIcons[1], 0, 0, 0);
        tlUserProfileTabs.getTabAt(1).setCustomView(newTab1);

    }
    @OnClick(R.id.btnEditProfile)
public void navigateToEditProfile()
    {
        Intent intent=new Intent(UserProfileActivity.this,UserProfileEditActivity.class);
        intent.putExtra("isFrom","Profile");
        intent.putExtra("userName", userName);
        intent.putExtra("userMobile", userMobile);
        intent.putExtra("userLocation", userLocation);
        intent.putExtra("userImage",profilePhoto);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setFillPaintColor(0xFF16121a);
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
    protected void onResume() {
        super.onResume();
        getuUserProfile();
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        UserProfileActivity.ViewPagerAdapter adapter = new UserProfileActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new JobCompletedFragment(), "Completed");
        adapter.addFrag(new JobCanceledFragment(), "Canceled");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void animateUserProfileOptions() {
        tlUserProfileTabs.setTranslationY(-tlUserProfileTabs.getHeight());
        tlUserProfileTabs.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    private void animateUserProfileHeader() {
        vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
        ivUserProfilePhoto.setTranslationY(-ivUserProfilePhoto.getHeight());
        vUserDetails.setTranslationY(-vUserDetails.getHeight());
        vUserStats.setAlpha(0);
        vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
        ivUserProfilePhoto.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
        vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
        vUserStats.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
    }

    // api call method for user profile details
    private void getuUserProfile() {
        showProgressDialog(UserProfileActivity.this, "Loading Profile..");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<UserProfileResponse> call = apiService.UserProfile(GloablMethods.API_HEADER + acess_token);
        call.enqueue(new Callback<UserProfileResponse>() {


            @Override
            public void onResponse(Call<UserProfileResponse> call, retrofit2.Response<UserProfileResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    userName=response.body().getProviderName();
                    userEmail=response.body().getEmail();
                    userMobile=response.body().getPhone1();
                    completedJob= String.valueOf(response.body().getStatistics().getCompletedJobs());
                    pendingJob= String.valueOf(response.body().getStatistics().getPendingJobs());
                    countTruck=response.body().getStatistics().getVehicles();
                    userLocation=response.body().getLocationName();
                    profilePhoto=response.body().getProfilePic();
                    textViewUserName.setText(userName);
                    textViewUserEmail.setText(userEmail);
                    textViewUserMobile.setText(userMobile);
                    textViewCompltedCount.setText(completedJob);
                    textViewPendingJobCount.setText(pendingJob);
                    textViewTrckCount.setText(countTruck);
                    textViewUserLocation.setText(userLocation);
                    Picasso.with(UserProfileActivity.this)
                            .load(response.body().getProfilePic())
                            .placeholder(R.drawable.img_circle_placeholder)
                            .resize(avatarSize, avatarSize)
                            .centerCrop()
                            .transform(new CircleTransformation())
                            .into(ivUserProfilePhoto);

                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

}
