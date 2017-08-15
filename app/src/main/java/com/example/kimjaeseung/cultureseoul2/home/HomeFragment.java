package com.example.kimjaeseung.cultureseoul2.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEventOutWrapper;
import com.example.kimjaeseung.cultureseoul2.login.LoginActivity;
import com.example.kimjaeseung.cultureseoul2.network.CultureService;
import com.example.kimjaeseung.cultureseoul2.performance.DetailActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

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

public class HomeFragment extends Fragment implements HomeAdapter.HomeAdapterOnClickHandler{
    private final static String TAG = "HomeFragment";
    private String openApiKey = "74776b4f6873696c34364a6368704d";

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    private static final int NUM_LIST_ITEMS = 100;
    private HomeAdapter mAdapter;
    @Bind(R.id.homeImageRecycler) RecyclerView mHomeList;

    //@Bind(R.id.home_button_culturalevent) CulturalEventButtonTypeA culturalEventButtonTypeA;
    @Bind(R.id.home_button_culturalevent) CulturalEventTypeMini culturalEventTypeMini;
    public HomeFragment(){}

    public static Fragment getInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        culturalEventTypeMini.setOpenAPIKey(openApiKey);

        //이후 예외처리
        mAuth=FirebaseAuth.getInstance();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mHomeList.setLayoutManager(layoutManager);
        mHomeList.setHasFixedSize(true);

        mAdapter = new HomeAdapter(this.getContext(), this);
        mHomeList.setAdapter(mAdapter);

        loadData();

    }

    private void loadData()
    {
        // http://openapi.seoul.go.kr:8088/sample/json/SearchConcertDetailService/1/5/23075/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CultureService cultureService = retrofit.create(CultureService.class);
        Call<CultureEventOutWrapper> callCultureEvent = cultureService.getCultureEvents(
                "74776b4f6873696c34364a6368704d", "json", "SearchConcertDetailService", 1, 20, ""
        );
        callCultureEvent.enqueue(new Callback<CultureEventOutWrapper>()
        {
            @Override
            public void onResponse(Call<CultureEventOutWrapper> call, Response<CultureEventOutWrapper> response)
            {
                if (response.isSuccessful())
                {
                    // 성공
                    CultureEventOutWrapper result = response.body();
                    List<CultureEvent> list = result.getCultureEventWrapper().getCultureEventList();
                    mAdapter.setItemList(list); // recyclerview에 데이터 추가
                    mAdapter.notifyAdapter();   // 화면 갱신
                } else
                {
                    // 실패
                }
            }

            @Override
            public void onFailure(Call<CultureEventOutWrapper> call, Throwable t)
            {
                loadData();
            }
        });
    }

    @Override
    public void onClick(CultureEvent cultureEvent)
    {
        Intent startToDetailActivity = new Intent(getActivity(), DetailActivity.class);
        startToDetailActivity.putExtra("key", cultureEvent);
        startActivity(startToDetailActivity);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home,menu);
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
