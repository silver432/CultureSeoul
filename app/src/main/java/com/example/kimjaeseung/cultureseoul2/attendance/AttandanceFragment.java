package com.example.kimjaeseung.cultureseoul2.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimjaeseung.cultureseoul2.R;

import butterknife.ButterKnife;

/**
 * Created by heo04 on 2017. 7. 19..
 */

public class AttandanceFragment extends Fragment {
    private final static String TAG = "AttandanceFragment";

    public AttandanceFragment(){}

    public static Fragment getInstance(){
        AttandanceFragment attandanceFragment = new AttandanceFragment();
        return attandanceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance,container,false);
        ButterKnife.bind(this,view);
        //setHasOptionsMenu(true);
        return view;
    }
}
