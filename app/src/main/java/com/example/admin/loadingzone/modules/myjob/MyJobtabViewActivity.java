package com.example.admin.loadingzone.modules.myjob;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyJobtabViewActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @NonNull
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @NonNull
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    JobPendingFragment pendingJobFragment;
    //JobCompletedFragment compltedJobFragment;
    AssignedJobFragment assignedJobFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobtab_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Jobs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        intalizeFragment();
        setupViewPager(viewPager);
    }

    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void intalizeFragment()
    {
        pendingJobFragment=new JobPendingFragment();
       // compltedJobFragment=new JobCompletedFragment();
        assignedJobFragment=new AssignedJobFragment();

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

    private void setupViewPager(ViewPager viewPager) {
        MyJobtabViewActivity.ViewPagerAdapter adapter = new MyJobtabViewActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(pendingJobFragment, "Pending");
        adapter.addFrag(assignedJobFragment,"Assinged");
//        adapter.addFrag(compltedJobFragment, "Completed");
        viewPager.setAdapter(adapter);
    }
}
