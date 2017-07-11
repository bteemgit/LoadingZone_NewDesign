package com.example.admin.loadingzone.modules.myqutation;

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

public class MyQuotationActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @NonNull
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @NonNull
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    PendingQuotationFragment pendingQuotationFragment;
    AcceptedQuotationFragment acceptedQuotationFragment;
    RejectedQuotationFragment rejectedQuotationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.my_qutation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tabLayout.setupWithViewPager(viewPager);
        intalizeFragment();
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                switch (position) {
//                    case 1:
//                        pendingQuotationFragment.setRefreshFragment("refresh");
//                        break;
//                    case 2:
//                        acceptedQuotationFragment.setRefreshFragment("refresh");
//                        break;
//                    case 3:
//                        rejectedQuotationFragment.setRefreshFragment("refresh");
//                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
//                    case 1:
//                        pendingQuotationFragment.setRefreshFragment("refresh");
//                        break;
//                    case 2:
//                        acceptedQuotationFragment.setRefreshFragment("refresh");
//                        break;
//                    case 3:
//                        rejectedQuotationFragment.setRefreshFragment("refresh");
//                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        intalizeFragment();
    }

    public void intalizeFragment()
{
    pendingQuotationFragment=new PendingQuotationFragment();
    rejectedQuotationFragment=new RejectedQuotationFragment();
    acceptedQuotationFragment=new AcceptedQuotationFragment();
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
        MyQuotationActivity.ViewPagerAdapter adapter = new MyQuotationActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(pendingQuotationFragment, "Pending");
        adapter.addFrag(acceptedQuotationFragment, "Accepted");
        adapter.addFrag(rejectedQuotationFragment, "Rejected");
        viewPager.setAdapter(adapter);
    }

}
