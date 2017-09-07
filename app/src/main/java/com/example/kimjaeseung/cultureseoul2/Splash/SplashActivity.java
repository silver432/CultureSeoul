package com.example.kimjaeseung.cultureseoul2.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.kimjaeseung.cultureseoul2.login.LoginActivity;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;

/**
 * Created by Nick0917 on 2017-08-10.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }


}
