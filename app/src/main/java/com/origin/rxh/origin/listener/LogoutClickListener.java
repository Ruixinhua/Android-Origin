package com.origin.rxh.origin.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.origin.rxh.origin.general.ActivityManager;
import com.origin.rxh.origin.start.LoginActivity;
import com.origin.rxh.origin.database.DBService;

public class LogoutClickListener implements View.OnClickListener {
    private Context context;
    private DBService dbs;
    public LogoutClickListener(Context context) {
        this.context = context;
        dbs = new DBService(context);
    }

    @Override
    public void onClick(View v) {
        dbs.deleteUserTemp();
        Intent loginActivity = new Intent(context,LoginActivity.class);
        context.startActivity(loginActivity);
        ActivityManager.getInstance().OutSign();
    }
}
