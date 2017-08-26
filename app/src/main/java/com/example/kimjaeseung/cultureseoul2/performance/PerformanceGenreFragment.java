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

import com.example.kimjaeseung.cultureseoul2.GlobalApp;
import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

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
    static String mGenreTitle = "";
    static int setGenreCount = 0;
    GlobalApp globalApp;

    public PerformanceGenreFragment()
    {

    }

    public static Fragment getInstance()
    {
        PerformanceGenreFragment performanceGenreFragment = new PerformanceGenreFragment();
        return performanceGenreFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_performance_genre, container, false);

        ButterKnife.bind(this, view);

        globalApp = (GlobalApp) getApplicationContext();

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

        setData();

    }

    @Override
    public void onClick(CultureEvent cultureEvent)
    {
        Intent startToDetailActivity = new Intent(getActivity(), DetailActivity.class);
        startToDetailActivity.putExtra("key", cultureEvent);
        startActivity(startToDetailActivity);
    }

    private void setData()
    {
        mAdapter.setItemList(globalApp.getmList());
        mAdapter.notifyAdapter();
        divisionGenre();
    }

    public static void setmGenreTitle(String setGenre) {
        mGenreTitle = setGenre;
        setGenreCount = 1;
    }

    /* 장르 구분 */
    public void divisionGenre()
    {
        List<CultureEvent> newList = new ArrayList<>();

        for (CultureEvent cultureEvent :mCultureEventLIst)
        {
            String name = cultureEvent.getCodeName();
            if (name.contains(mGenreTitle))
                newList.add(cultureEvent);
        }

        mAdapter.setFilter(newList);
    }


    /* 스피너 선택 이벤트*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (setGenreCount == 0) {
            if (mGenreTitle == "")
                mGenreTitle = "콘서트";    // default 장르 값
            else
                mGenreTitle = mGenreStr[position];
        }

        setGenreCount = 0;
        divisionGenre();
    }

    /* 스피너 선택 이벤트*/
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}