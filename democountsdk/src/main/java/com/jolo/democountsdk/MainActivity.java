package com.jolo.democountsdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jolo.countsdk.CountSDK;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountSDK.setDebugTrue();
        CountSDK.initCountSDKConfig(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountSDK.releaseCountSDKConfig(this);
    }
}
