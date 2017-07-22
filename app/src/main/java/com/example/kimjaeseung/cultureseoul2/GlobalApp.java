package com.example.kimjaeseung.cultureseoul2;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class GlobalApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        FirebaseApp.initializeApp(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
