package com.example.kimjaeseung.cultureseoul2.performance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.squareup.picasso.Picasso;

import static com.example.kimjaeseung.cultureseoul2.utils.DateUtils.dateToString;

/**
 * Created by heo04 on 2017-07-22.
 */

public class DetailActivity extends AppCompatActivity
{
    private ImageView mPerformImage;
    private TextView mPerformTitle;
    private ListView mListView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPerformImage = (ImageView) findViewById(R.id.iv_detail_image);
        mPerformTitle = (TextView) findViewById(R.id.tv_detail_title);
        mListView = (ListView) findViewById(R.id.lv_detail);
        mButton = (Button) findViewById(R.id.btn_detail_url);

        Intent intent = getIntent();
        final CultureEvent cultureEvent = (CultureEvent) intent.getSerializableExtra("key");

        mPerformTitle.setText(cultureEvent.getTitle());
        Picasso.with(this) // 공연 이미지
                .load(cultureEvent.getMainImg().toLowerCase())
                .error(R.drawable.smile_50dp)
                .into(mPerformImage);
        mPerformTitle.setSelected(true);

        DetailAdapter mDetailAdapter = new DetailAdapter();
        mListView.setAdapter(mDetailAdapter);
        mListView.setDivider(null);

        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String homeURL = cultureEvent.getOrgLink();
                if(homeURL.charAt(0) == ' ')
                    homeURL = homeURL.substring(1);
                intent.setData(Uri.parse(homeURL));
                startActivity(intent);
           }
        });

        mDetailAdapter.addItem("기간 : " , dateToString(cultureEvent.getStartDate()) + " ~ " + dateToString(cultureEvent.getEndDate()));
        mDetailAdapter.addItem("시간 : ", cultureEvent.getTime());
        mDetailAdapter.addItem("장소 : ", cultureEvent.getPlace());
        mDetailAdapter.addItem("이용대상 : ", cultureEvent.getUseTrgt());
        mDetailAdapter.addItem("이용요금 : ", cultureEvent.getUseFee());
        mDetailAdapter.addItem("주최 : ", cultureEvent.getSponsor());
        mDetailAdapter.addItem("문의 : ", cultureEvent.getInquiry());

    }
}
