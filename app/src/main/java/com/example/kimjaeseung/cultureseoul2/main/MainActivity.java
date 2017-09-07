package com.example.kimjaeseung.cultureseoul2.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.attendance.AttandanceFragment;
import com.example.kimjaeseung.cultureseoul2.community.CommunityFragment;
import com.example.kimjaeseung.cultureseoul2.home.HomeFragment;
import com.example.kimjaeseung.cultureseoul2.login.LoginActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceRealTimeFragment;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private GoogleApiClient mGoogleApiClient;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private String mName;
    private String mPhotoUrl;
    private String mEmail;


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

        Log.d(TAG,"onCreate");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            Toast.makeText(this, "로그인 필요 test", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            /*
            Log.d(TAG, "user is not null");
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                mName = profile.getDisplayName();
                mEmail = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();

                if(mName == null){
                    Log.d(TAG,"name is null");
                }else{
                    Log.d(TAG,"name is not null"+mName);
                }
                if(mEmail == null){
                    Log.d(TAG,"email is null");
                }else{
                    Log.d(TAG,"email is not null"+mEmail);
                }

                Log.d(TAG, "success load profile");

            }
            */
        }else{
            mName = mFirebaseUser.getDisplayName();
            mEmail = mFirebaseUser.getEmail();
            if(mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }

        }



        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //툴바 설정
        /* 참고
        toolbar.setTitleTextColor(Color.parseColor("#ffff33")); //제목의 칼라
        toolbar.setSubtitle(R.string.subtitle); //부제목 넣기
        toolbar.setNavigationIcon(R.mipmap.ic_launcher); //제목앞에 아이콘 넣기
        */
        setSupportActionBar(toolbar); //툴바를 액션바와 같게 만든다

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_profile);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        int id = item.getItemId();
                        if(id == R.id.menu_nav_signout){
                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            Log.d(TAG, "logout firebase");
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        View nav_profile_view = navigationView.getHeaderView(0);
        TextView nav_profile_name = (TextView) nav_profile_view.findViewById(R.id.profile_name);
        nav_profile_name.setText(mName);
        TextView nav_profile_email = (TextView) nav_profile_view.findViewById(R.id.profile_email);
        nav_profile_email.setText(mEmail);


        CoordinatorLayout.LayoutParams layoutParams=(CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        selectPage=getIntent().getStringExtra("select_page");
        if (selectPage!=null){
            if (selectPage.equals(CommunityFragment.class.getSimpleName())) {
                bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_community);
            }else if(selectPage.equals(PerformanceRealTimeFragment.class.getSimpleName())){
                bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_performance);
            }else if(selectPage.equals("HomeFragment")){
                bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_performance);
            }
        }else {
            bottomNavigationView.setSelectedItemId(R.id.main_bottomnavigation_home);
        }
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
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
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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

    //뒤로 가기 2번 종료
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if( 0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime ){
            ActivityCompat.finishAffinity(this);
        }else{
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        }
    }

}