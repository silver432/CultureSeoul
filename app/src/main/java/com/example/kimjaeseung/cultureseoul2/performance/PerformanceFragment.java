package com.example.kimjaeseung.cultureseoul2.performance;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEventOutWrapper;
import com.example.kimjaeseung.cultureseoul2.network.CultureService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class PerformanceFragment extends Fragment
{
    private final static String TAG = "PerformanceFragment";
    private static final int NUM_LIST_ITEMS = 100;
    private PerformanceAdapter mAdapter;

    @Bind(R.id.performance_list)
    RecyclerView mPerformanceList;

    public PerformanceFragment()
    {
    }

    public static Fragment getInstance()
    {
        PerformanceFragment performanceFragment = new PerformanceFragment();
        return performanceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_performance, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mPerformanceList.setLayoutManager(layoutManager);
        mPerformanceList.setHasFixedSize(true);

        mAdapter = new PerformanceAdapter(NUM_LIST_ITEMS);
        mPerformanceList.setAdapter(mAdapter);

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
                "74776b4f6873696c34364a6368704d", "json", "SearchConcertDetailService", 1, 10, "94040"
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
}
