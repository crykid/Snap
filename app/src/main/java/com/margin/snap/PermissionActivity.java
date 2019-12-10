package com.margin.snap;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.margin.snap.framwork.BaseTopActivity;

public class PermissionActivity extends BaseTopActivity implements View.OnClickListener {
    private static final String TAG = "PermissionActivity";
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        findViewById(R.id.btn_permission_single).setOnClickListener(this);
        findViewById(R.id.btn_permission_many).setOnClickListener(this);


    }


    public void requestPermission(String permission) {
        Log.d(TAG, "requestPermission: " + permission);
        if (ContextCompat.checkSelfPermission(this, permission) != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permission});
        }

    }

    public void requestPermissions(String[] permissions) {

        ActivityCompat.requestPermissions(this, permissions, 0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PermissionChecker.PERMISSION_GRANTED) {

                Log.d(TAG, "onRequestPermissionsResult: permissionDenied :" + permissions[i]);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btn_permission_many:
                requestPermissions(permissions);

                break;
            case R.id.btn_permission_single:

                requestPermission(  Manifest.permission.READ_PHONE_STATE);
                break;
        }
    }
}
