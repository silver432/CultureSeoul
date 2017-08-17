package com.example.kimjaeseung.cultureseoul2.performance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEventOutWrapper;
import com.example.kimjaeseung.cultureseoul2.network.CultureService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by heo04 on 2017-08-09.
 */

public class PerformanceGenreFragment extends Fragment implements PerformanceAdapter.PerformanceAdapterOnClickHandler, AdapterView.OnItemSelectedListener
{
    private final static String TAG = "PerformanceGenreFragment";
    private PerformanceAdapter mAdapter;

    @Bind(R.id.performance_genre_list)  RecyclerView mPerformanceList;
    @Bind(R.id.spinner_genre) Spinner spinner;

    List<CultureEvent> mCultureEventLIst = new ArrayList<>();
    String[] mGenreStr;
    String mGenreTitle;

    public PerformanceGenreFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_performance_genre, container, false);

        ButterKnife.bind(this, view);

        spinner.setOnItemSelectedListener(this);

        // 스피너 항목 담을 array 생성
        mGenreStr = getResources().getStringArray(R.array.array_genre);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, mGenreStr);

        spinner.setAdapter(arrayAdapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mPerformanceList.setLayoutManager(layoutManager);
        mPerformanceList.setHasFixedSize(true);

        mAdapter = new PerformanceAdapter(this.getContext(), this, mCultureEventLIst);
        mPerformanceList.setAdapter(mAdapter);

        mGenreTitle = "클래식";    // default 장르 값

        loadData();

    }

    @Override
    public void onClick(CultureEvent cultureEvent)
    {
        Intent startToDetailActivity = new Intent(getActivity(), DetailActivity.class);
        startToDetailActivity.putExtra("key", cultureEvent);
        startActivity(startToDetailActivity);
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
                "74776b4f6873696c34364a6368704d", "json", "SearchConcertDetailService", 1, 50, ""
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

                    divisionGenre(mGenreTitle);
                }
                else
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


    /* 장르 구분 */
    public void divisionGenre(String genretitle)
    {
        String genreTitle = genretitle;
        List<CultureEvent> newList = new ArrayList<>();

        for (CultureEvent cultureEvent :mCultureEventLIst)
        {
            String name = cultureEvent.getCodeName();
            if (name.contains(genreTitle))
                newList.add(cultureEvent);
        }

        //Toast.makeText(getContext(),genreTitle, Toast.LENGTH_SHORT).show();

        mAdapter.setFilter(newList);
    }


    /* 스피너 선택 이벤트*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        mGenreTitle = mGenreStr[position];
        loadData(); // 스피너 선택될때마다 loadData해서 비효율적 => 수정 필요
        //divisionGenre(mGenreTitle); => 왜 실행 안되는지..
    }

    /* 스피너 선택 이벤트*/
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}