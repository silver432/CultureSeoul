package com.example.kimjaeseung.cultureseoul2.performance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;

/**
 * Created by heo04 on 2017-07-22.
 */

public class DetailActivity extends AppCompatActivity
{
    TextView mPerformTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPerformTitle = (TextView) findViewById(R.id.tv_detail_title);
        Intent intent = getIntent();
        CultureEvent cultureEvent = (CultureEvent) intent.getSerializableExtra("key");
        mPerformTitle.setText(cultureEvent.getTitle());
    }
}
