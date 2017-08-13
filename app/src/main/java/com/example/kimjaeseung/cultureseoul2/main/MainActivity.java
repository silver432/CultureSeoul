package com.example.kimjaeseung.cultureseoul2.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.attendance.AttandanceFragment;
import com.example.kimjaeseung.cultureseoul2.performance.DetailActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.example.kimjaeseung.cultureseoul2.home.HomeFragment;
import com.example.kimjaeseung.cultureseoul2.community.CommunityFragment;
import com.google.firebase.FirebaseApp;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private String selectPage;
    private DrawerLayout mDrawerLayout;

    @Bind(R.id.main_bottomnavigation) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_profile);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );


        CoordinatorLayout.LayoutParams layoutParams=(CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        selectPage=getIntent().getStringExtra("select_page");
        if (selectPage!=null){
            if (selectPage.equals(CommunityFragment.class.getSimpleName())) {
                bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_community);
            }else if(selectPage.equals(PerformanceFragment.class.getSimpleName())){
                bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_performance);
            }
        }else {
            switchFragment(HomeFragment.getInstance());
            bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_home);
        }
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.main_bottomnavigation_home:
                    switchFragment(HomeFragment.getInstance());
                    return true;
                case R.id.main_bottomnavigation_performance:
                    switchFragment(PerformanceFragment.getInstance());
                    return true;
                case R.id.main_bottomnavigation_community:
                    switchFragment(CommunityFragment.getInstance());
                    return true;
                case R.id.main_bottomnavigation_attendance:
                    switchFragment(AttandanceFragment.getInstance());
                    return true;
            }
            return false;
        }

    };

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

}