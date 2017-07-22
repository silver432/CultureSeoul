package com.example.kimjaeseung.cultureseoul2.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.attendance.AttandanceFragment;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.example.kimjaeseung.cultureseoul2.home.HomeFragment;
import com.example.kimjaeseung.cultureseoul2.community.CommunityFragment;
import com.google.firebase.FirebaseApp;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private String selectPage;

    @Bind(R.id.main_bottomnavigation) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        selectPage=getIntent().getStringExtra("select_page");
        if (selectPage!=null){
            if (selectPage.equals(CommunityFragment.class.getSimpleName())) switchFragment(CommunityFragment.getInstance());
        }else switchFragment(HomeFragment.getInstance());

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