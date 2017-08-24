package com.example.kimjaeseung.cultureseoul2.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEventOutWrapper;
import com.example.kimjaeseung.cultureseoul2.login.LoginActivity;
import com.example.kimjaeseung.cultureseoul2.network.CultureService;
import com.example.kimjaeseung.cultureseoul2.performance.DetailActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceAdapter;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceGenreFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Transaction;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.go.seoul.culturalevents.CulturalEventTypeMini;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class HomeFragment extends Fragment implements HomeAdapter.HomeAdapterOnClickHandler, View.OnClickListener {
    private final static String TAG = "HomeFragment";
    private String openApiKey = "74776b4f6873696c34364a6368704d";

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;


    @Bind(R.id.category_concert) public TextView category_concert;
    @Bind(R.id.category_classic) public TextView category_classic;
    @Bind(R.id.category_musical) public TextView category_musical;
    @Bind(R.id.category_theater) public TextView category_theater;
    @Bind(R.id.category_dancing) public TextView category_dancing;
    @Bind(R.id.category_exhibition) public TextView category_exhibition;
    @Bind(R.id.category_traditional_music) public TextView category_traditional_music;
    @Bind(R.id.category_festival) public TextView category_festival;
    @Bind(R.id.category_solo) public TextView category_solo;
    @Bind(R.id.category_movie) public TextView category_movie;
    @Bind(R.id.category_cultureclass) public TextView category_cultureclass;

    private String Genre;


    //@Bind(R.id.home_button_culturalevent) CulturalEventButtonTypeA culturalEventButtonTypeA;
    @Bind(R.id.home_button_culturalevent)
    CulturalEventTypeMini culturalEventTypeMini;

    public HomeFragment() {
    }

    public static Fragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        culturalEventTypeMini.setOpenAPIKey(openApiKey);

        //이후 예외처리
        mAuth = FirebaseAuth.getInstance();

        category_concert.setOnClickListener(this);
        category_classic.setOnClickListener(this);
        category_musical.setOnClickListener(this);
        category_theater.setOnClickListener(this);
        category_dancing.setOnClickListener(this);
        category_exhibition.setOnClickListener(this);
        category_traditional_music.setOnClickListener(this);
        category_festival.setOnClickListener(this);
        category_solo.setOnClickListener(this);
        category_movie.setOnClickListener(this);
        category_cultureclass.setOnClickListener(this);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(CultureEvent cultureEvent) {
        Intent startToDetailActivity = new Intent(getActivity(), DetailActivity.class);
        startToDetailActivity.putExtra("key", cultureEvent);
        startActivity(startToDetailActivity);
    }

    @Override
    public void onClick(View v) {
        String textId = ((TextView) v).getText().toString();
        PerformanceGenreFragment.setmGenreTitle(textId);
        switchFragment(PerformanceGenreFragment.getInstance());
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_nav_profile,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home_signout:
                signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        Log.d(TAG,"sign out");
        Intent i = new Intent(getContext(),LoginActivity.class);
        startActivity(i);
    }
    */
}
