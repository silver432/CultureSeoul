package com.example.kimjaeseung.cultureseoul2.performance;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class PerformanceRealTimeFragment extends Fragment implements PerformanceAdapter.PerformanceAdapterOnClickHandler, SearchView.OnQueryTextListener
{
    private final static String TAG = "PRTF";
    private static final int NUM_LIST_ITEMS = 100;
    private PerformanceAdapter mAdapter;

    @Bind(R.id.performance_list)
    RecyclerView mPerformanceList;
    List<CultureEvent> mCultureEventLIst = new ArrayList<>();
    GlobalApp globalApp;
    SearchView searchView;

    public PerformanceRealTimeFragment()
    {
    }

    public static Fragment getInstance()
    {
        PerformanceRealTimeFragment performanceRealTimeFragment = new PerformanceRealTimeFragment();
        return performanceRealTimeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_performance_realtime, container, false);

        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        globalApp = (GlobalApp) getApplicationContext();

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
        mAdapter.setItemList(globalApp.getmList());
        mAdapter.notifyAdapter();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_performance, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem); // 액션바에 searchview 추가
        searchView.setOnQueryTextListener(this);
        Log.d(TAG, "onCreateOptionsMenu");
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    /* 필터에서 텍스트 검색 처리 */
    @Override
    public boolean onQueryTextChange(String newText)
    {
        newText = newText.toLowerCase();
        List<CultureEvent> newList = new ArrayList<>();
        for (CultureEvent cultureEvent : mCultureEventLIst)
        {
            String name = cultureEvent.getTitle();
            if (name.contains(newText))
                newList.add(cultureEvent);
        }

        mAdapter.setFilter(newList);

        return true;
    }

}
