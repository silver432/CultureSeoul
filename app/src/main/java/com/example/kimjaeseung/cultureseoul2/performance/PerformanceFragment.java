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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class PerformanceFragment extends Fragment {
    private final static String TAG = "PerformanceFragment";
    private static final int NUM_LIST_ITEMS = 100;
    private PerformanceAdapter mAdapter;

    @Bind(R.id.performance_list) RecyclerView mPerformanceList;

    public PerformanceFragment(){}

    public static Fragment getInstance(){
        PerformanceFragment performanceFragment = new PerformanceFragment();
        return performanceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_performance,container,false);

        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mPerformanceList.setLayoutManager(layoutManager);

        mAdapter = new PerformanceAdapter(NUM_LIST_ITEMS);
        mPerformanceList.setAdapter(mAdapter);

        return view;
    }


}
