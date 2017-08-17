package com.example.kimjaeseung.cultureseoul2;

import android.Manifest;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class GlobalApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getPermission();
//        FirebaseApp.initializeApp(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void getPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }


        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }
}
