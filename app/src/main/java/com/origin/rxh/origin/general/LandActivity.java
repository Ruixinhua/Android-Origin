package com.origin.rxh.origin.general;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class LandActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
