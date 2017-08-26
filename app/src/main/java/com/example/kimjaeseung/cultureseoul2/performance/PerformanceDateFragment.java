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
import android.widget.Button;

import com.example.kimjaeseung.cultureseoul2.GlobalApp;
import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.community.AddChatRoomActivity;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by heo04 on 2017-08-10.
 */

public class PerformanceDateFragment extends Fragment implements PerformanceAdapter.PerformanceAdapterOnClickHandler
{
    private final static String TAG = "PDF";
    private PerformanceAdapter mAdapter;

    @Bind(R.id.performance_rv_date) RecyclerView mPerformanceList;
    @Bind(R.id.performance_btn_date) Button mButton;

    List<CultureEvent> mCultureEventLIst = new ArrayList<>();
    GlobalApp mGlobalApp;

    public PerformanceDateFragment()
    {

    }

    public static Fragment getInstance()
    {
        PerformanceDateFragment performanceDateFragment = new PerformanceDateFragment();
        return performanceDateFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_performance_date, container, false);

        ButterKnife.bind(this, view);

        //setHasOptionsMenu(true);

        mGlobalApp = (GlobalApp) getApplicationContext();

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
        //채팅방추가를 위해 intent 넘어옴
        String choose = getActivity().getIntent().getStringExtra("choose");
        if (choose != null && choose.equals(AddChatRoomActivity.class.getSimpleName()))
        {
            Intent intent = new Intent(getActivity(), AddChatRoomActivity.class);
            intent.putExtra("key", cultureEvent);
            startActivity(intent);

        } else
        {
            Intent startToDetailActivity = new Intent(getActivity(), DetailActivity.class);
            startToDetailActivity.putExtra("key", cultureEvent);
            startActivity(startToDetailActivity);
        }
        getActivity().getIntent().putExtra("choose", "");
    }


    private void setData()
    {
        mAdapter.setItemList(mGlobalApp.getmList());
        mAdapter.notifyAdapter();
    }


}
