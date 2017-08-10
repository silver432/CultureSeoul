package com.example.kimjaeseung.cultureseoul2.performance;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.kimjaeseung.cultureseoul2.R;

/**
 * Created by heo04 on 2017-08-09.
 */

public class PerformanceActivity extends AppCompatActivity
{
    TabLayout tabLayout;
    ViewPager viewPager;

    private static int PAGE_NUMBER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        tabLayout = (TabLayout) findViewById(R.id.tl_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("First Tab"));
        tabLayout.addTab(tabLayout.newTab().setText("Second Tab"));
        tabLayout.addTab(tabLayout.newTab().setText("Third Tab"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager = (ViewPager) findViewById(R.id.vp_view_pager);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
                    return new PerformanceFragment();
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
