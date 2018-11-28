package com.origin.rxh.origin.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class LandActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
