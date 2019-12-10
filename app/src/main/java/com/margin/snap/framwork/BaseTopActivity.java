package com.margin.snap.framwork;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by : mr.lu
 * Created at : 2019-11-25 at 14:44
 * Description:
 */
public class BaseTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SnapConfigurator.getInstance().topActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
