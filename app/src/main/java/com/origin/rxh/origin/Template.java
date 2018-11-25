package com.origin.rxh.origin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.origin.rxh.origin.general.LandActivity;
import com.origin.rxh.origin.listener.LogoutClickListener;
import com.origin.rxh.origin.listener.NextActivityClickListener;
import com.origin.rxh.origin.start.LoginActivity;
import com.origin.rxh.origin.start.MenuActivity;
import com.origin.rxh.origin.start.UserInfoActivity;

//public class ${activityName} extends BaseActivity
public class Template extends LandActivity {
    private TextView usernameText;
    private TextView gradesText;
    private Button userInfoBt;
    private Button returnBt;
    private Button logoutBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        //setContentView(R.layout.${layoutName});
        init();

        userInfoBt.setOnClickListener(new NextActivityClickListener(this, UserInfoActivity.class));
        returnBt.setOnClickListener(new NextActivityClickListener(this, MenuActivity.class));
        logoutBt.setOnClickListener(new LogoutClickListener(this));
    }

    private void init() {
        usernameText = findViewById(R.id.username_text);
        gradesText = findViewById(R.id.grades_text);
        usernameText.setText(LoginActivity.getUserInfo().getUsername());
        String tempGrades = "Grades: " + LoginActivity.getUserInfo().getGrade();
        gradesText.setText(tempGrades);
        userInfoBt = findViewById(R.id.user_info_bt);
        returnBt = findViewById(R.id.return_bt);
        logoutBt = findViewById(R.id.logout_bt);
    }
}
