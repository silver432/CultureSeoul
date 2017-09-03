package com.example.kimjaeseung.cultureseoul2;

import android.app.Application;

import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEventOutWrapper;
import com.example.kimjaeseung.cultureseoul2.network.CultureService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class GlobalApp extends Application {

    private List<CultureEvent> mList;

    /** JSON 파싱 */
    public void loadData()
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CultureService cultureService = retrofit.create(CultureService.class);
        Call<CultureEventOutWrapper> callCultureEvent = cultureService.getCultureEvents(
                "74776b4f6873696c34364a6368704d", "json", "SearchConcertDetailService", 1, 800, ""
        );
        callCultureEvent.enqueue(new Callback<CultureEventOutWrapper>() {
            @Override
            public void onResponse(Call<CultureEventOutWrapper> call, Response<CultureEventOutWrapper> response) {
                if (response.isSuccessful()) {
                    // 성공
                    CultureEventOutWrapper result = response.body();
                    List<CultureEvent> list = result.getCultureEventWrapper().getCultureEventList();
                    setmList(list);
                }
                else
                {
                    // 실패
                }
            }

            @Override
            public void onFailure(Call<CultureEventOutWrapper> call, Throwable t) {
                loadData();
            }
        });

    }

    public void setmList(List<CultureEvent> list)
    {
        mList = list;
    }

    public List<CultureEvent> getmList()
    {
        return mList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
//        FirebaseApp.initializeApp(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
