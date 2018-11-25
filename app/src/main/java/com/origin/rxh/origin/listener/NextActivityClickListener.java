package com.origin.rxh.origin.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class NextActivityClickListener implements View.OnClickListener {
    private Context context;
    private Class<?> stageClass;

    public NextActivityClickListener(Context context, Class<?> stageClass) {
        this.context = context;
        this.stageClass = stageClass;
    }

    @Override
    public void onClick(View v) {
        Intent stageActivity = new Intent(context,stageClass);
        context.startActivity(stageActivity);
    }
}
