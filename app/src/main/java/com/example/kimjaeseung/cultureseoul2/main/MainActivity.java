package com.example.kimjaeseung.cultureseoul2.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.attendance.AttandanceFragment;
import com.example.kimjaeseung.cultureseoul2.community.CommunityFragment;
import com.example.kimjaeseung.cultureseoul2.home.HomeFragment;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceRealTimeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private String selectPage;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Bind(R.id.main_bottomnavigation) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //툴바 설정
        /* 참고
        toolbar.setTitleTextColor(Color.parseColor("#ffff33")); //제목의 칼라
        toolbar.setSubtitle(R.string.subtitle); //부제목 넣기
        toolbar.setNavigationIcon(R.mipmap.ic_launcher); //제목앞에 아이콘 넣기
        */
        setSupportActionBar(toolbar); //툴바를 액션바와 같게 만든다

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

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
            }else if(selectPage.equals(PerformanceRealTimeFragment.class.getSimpleName())){
                bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_performance);
            }
        }else {
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
            invalidateOptionsMenu();
            return false;
        }

    };

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }


    //toolbar 관련
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        Log.d(TAG,"optionmenu create");
        if (bottomNavigationView.getSelectedItemId()==R.id.main_bottomnavigation_performance){
            menu.getItem(1).setVisible(true);
        }else menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //toolbar 관련 끝
}