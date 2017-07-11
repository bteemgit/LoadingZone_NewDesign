package com.example.admin.loadingzone.modules.login;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity {
    @BindView(R.id.tlSignUp)
    TabLayout tabLayoutSignup;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setupTabs();
    }
    private void setupTabs() {
        setupViewPager(viewPager);
        tabLayoutSignup.setupWithViewPager(viewPager);
        TextView newTab0 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_jobs, null);
        newTab0.setText(R.string.individual); //tab label txt
        tabLayoutSignup.getTabAt(0).setCustomView(newTab0);
        TextView newTab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_jobs, null);
         newTab1.setText(R.string.comapny); //tab label txt
        tabLayoutSignup.getTabAt(1).setCustomView(newTab1);

    }
    private void setupViewPager(ViewPager viewPager) {
        SignUpActivity.ViewPagerAdapter adapter = new SignUpActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SignupCase1(), "Individual");
        adapter.addFrag(new SignupCase2(), "Comapny");
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

}
