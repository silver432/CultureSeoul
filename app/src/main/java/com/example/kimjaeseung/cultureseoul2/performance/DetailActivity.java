package com.example.kimjaeseung.cultureseoul2.performance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;

import static com.example.kimjaeseung.cultureseoul2.utils.DateUtils.dateToString;

/**
 * Created by heo04 on 2017-07-22.
 */

public class DetailActivity extends AppCompatActivity
{
    TextView mPerformTitle;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPerformTitle = (TextView) findViewById(R.id.tv_detail_title);
        mListView = (ListView) findViewById(R.id.lv_detail);

        Intent intent = getIntent();
        CultureEvent cultureEvent = (CultureEvent) intent.getSerializableExtra("key");
        mPerformTitle.setText(cultureEvent.getTitle());

        DetailAdapter mDetailAdapter = new DetailAdapter();
        mListView.setAdapter(mDetailAdapter);

        mDetailAdapter.addItem("기간" , dateToString(cultureEvent.getStartDate()) + "~" + dateToString(cultureEvent.getEndDate()));
        mDetailAdapter.addItem("시간", cultureEvent.getTime());
        mDetailAdapter.addItem("장소", cultureEvent.getPlace());
        mDetailAdapter.addItem("이용대상", cultureEvent.getUseTrgt());
        mDetailAdapter.addItem("이용요금", cultureEvent.getUseFee());
        mDetailAdapter.addItem("주최", cultureEvent.getSponsor());
        mDetailAdapter.addItem("문의", cultureEvent.getInquiry());

    }
}
