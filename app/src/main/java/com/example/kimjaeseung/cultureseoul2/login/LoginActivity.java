package com.example.kimjaeseung.cultureseoul2.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_gotomain})
    public void mOnClick(View view){
        switch (view.getId()){
            case R.id.login_gotomain:
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
