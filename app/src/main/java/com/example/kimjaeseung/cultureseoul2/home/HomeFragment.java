package com.example.kimjaeseung.cultureseoul2.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimjaeseung.cultureseoul2.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.go.seoul.culturalevents.CulturalEventButtonTypeA;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";
    //apikey 입력해야함
    private String openApiKey = "";

    @Bind(R.id.home_button_culturalevent) CulturalEventButtonTypeA culturalEventButtonTypeA;

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
        culturalEventButtonTypeA.setOpenAPIKey(openApiKey);
        return view;
    }
}
