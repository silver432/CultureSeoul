package com.example.kimjaeseung.cultureseoul2.performance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.kimjaeseung.cultureseoul2.R;

/**
 * Created by heo04 on 2017-08-09.
 */

public class PerformanceGenreFragment extends Fragment
{
    public PerformanceGenreFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_performance_genre, container, false);
        return layout;
    }
}
