package com.example.kimjaeseung.cultureseoul2.community;


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

public class CommunityFragment extends Fragment {
    private final static String TAG = "CommunityFragment";
    private static final int NUM_LIST_ITEMS = 100;
    private CommunityAdapter mAdapter;

    @Bind(R.id.community_list) RecyclerView mCommunityList;

    public CommunityFragment(){}

    public static Fragment getInstance(){
        CommunityFragment communityFragment = new CommunityFragment();
        return communityFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community,container,false);

        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mCommunityList.setLayoutManager(layoutManager);

        mAdapter = new CommunityAdapter(NUM_LIST_ITEMS);
        mCommunityList.setAdapter(mAdapter);

        return view;
    }


}
