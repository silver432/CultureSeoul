package com.example.kimjaeseung.cultureseoul2.performance;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.kimjaeseung.cultureseoul2.GlobalApp;
import com.example.kimjaeseung.cultureseoul2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by heo04 on 2017-08-09.
 */

public class PerformanceFragment extends Fragment
{
    private final static String TAG = "PerformanceFragment";

    public PerformanceFragment() {}

    public static Fragment getInstance()
    {
        PerformanceFragment performanceFragment = new PerformanceFragment();
        return performanceFragment;
    }

    static int PageFlag = 0;
    public static void getPageFlag() {
        PageFlag = 1;
    }

    @Bind(R.id.tl_view_pager) TabLayout tabLayout;
    @Bind(R.id.vp_view_pager) ViewPager viewPager;
    @Bind(R.id.pb_performance_fragment)
    ProgressBar progressBar;
    GlobalApp mGlobalApp;

    private static int PAGE_NUMBER = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_performance, container, false);

        ButterKnife.bind(this, view);

        mGlobalApp = (GlobalApp) getApplicationContext();

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#3666A5"),android.graphics.PorterDuff.Mode.MULTIPLY); // ProgressBar 색 변경

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {  // 0.5초마다 데이터 불러졌는지 확인
            public void run() {

                if (mGlobalApp.getmList() == null)
                    handler.postDelayed(this, 300);

                else // 데이터 다 받아졌으면 페이지 만듦
                {
                    progressBar.setVisibility(View.GONE);
                    makePager();
                }
            }
        };

        handler.post(runnable);

        return view;
    }

    public void makePager()
    {
        tabLayout.addTab(tabLayout.newTab().setText("실시간"));
        tabLayout.addTab(tabLayout.newTab().setText("장르별"));
        tabLayout.addTab(tabLayout.newTab().setText("날짜별"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (PageFlag == 1) {
            viewPager.setCurrentItem( 1 );
            PageFlag = 0;
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }

    /* ViewPager Adapter */
    private class PagerAdapter extends FragmentStatePagerAdapter
    {
        private int tabCount;

        public PagerAdapter(FragmentManager fm, int tabCount)
        {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new PerformanceRealTimeFragment();
                case 1:
                    return new PerformanceGenreFragment();
                case 2:
                    return new PerformanceDateFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return PAGE_NUMBER;
        }
    }
}
