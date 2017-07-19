package com.example.kimjaeseung.cultureseoul2.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimjaeseung.cultureseoul2.R;

import butterknife.ButterKnife;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class CommunityFragment extends Fragment {
    private final static String TAG = "CommunityFragment";

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
        return view;
    }
}
