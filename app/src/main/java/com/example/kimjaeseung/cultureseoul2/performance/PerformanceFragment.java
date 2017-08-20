package com.example.kimjaeseung.cultureseoul2.performance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kimjaeseung.cultureseoul2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by heo04 on 2017-08-09.
 */

public class PerformanceFragment extends Fragment implements SearchView.OnQueryTextListener
{
    private final static String TAG = "PerformanceFragment";

    public PerformanceFragment() {}

    public static Fragment getInstance()
    {
        PerformanceFragment performanceFragment = new PerformanceFragment();
        return performanceFragment;
    }

    @Bind(R.id.tl_view_pager) TabLayout tabLayout;
    @Bind(R.id.vp_view_pager) ViewPager viewPager;
    SearchView searchView;

    private static int PAGE_NUMBER = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_performance,container,false);

        ButterKnife.bind(this,view);

        tabLayout.addTab(tabLayout.newTab().setText("실시간"));
        tabLayout.addTab(tabLayout.newTab().setText("장르별"));
        tabLayout.addTab(tabLayout.newTab().setText("날짜별"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

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

        return view;
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

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_performance, menu);

        MenuItem menuItem = menu.findItem(R.id.item_search);

        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

    }


    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        Toast.makeText(getContext(), "PerformanceTextChange", Toast.LENGTH_SHORT).show();
        return false;
    }

    //item_search 이벤트 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_search:
                Toast.makeText(getContext(), "onOptionsItemSelected", Toast.LENGTH_SHORT).show();
                //// TODO: 2017. 8. 16. 여기서 작업해주면 될 거 같음
                //SearchView searchView = new SearchView(getActivity());
                //searchView.setOnQueryTextListener(this);
                //item.setActionView(searchView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
