package com.origin.rxh.origin.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class StageStartListener implements View.OnClickListener {
    private Context context;
    private Class<?> stageClass;
    private int index;

    public StageStartListener(Context context, Class<?> stageClass, int index) {
        this.context = context;
        this.stageClass = stageClass;
        this.index = index;
    }

    @Override
    public void onClick(View v) {
        Intent stageActivity = new Intent(context,stageClass);
        stageActivity.putExtra("stageIndex",index);
        context.startActivity(stageActivity);
        //((Activity)context).finish();
    }
}
