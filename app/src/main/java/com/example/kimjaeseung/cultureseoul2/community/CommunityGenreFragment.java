package com.example.kimjaeseung.cultureseoul2.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kimjaeseung on 2017. 9. 6..
 */

public class CommunityGenreFragment extends Fragment {
    private static final String TAG = CommunityGenreFragment.class.getSimpleName();

    public CommunityGenreFragment(){}
    public static Fragment getInstance()
    {
        CommunityGenreFragment communityGenreFragment = new CommunityGenreFragment();
        return communityGenreFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
