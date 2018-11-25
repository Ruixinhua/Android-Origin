package com.origin.rxh.origin.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.origin.rxh.origin.R;
import com.origin.rxh.origin.Template;
import com.origin.rxh.origin.general.BaseActivity;
import com.origin.rxh.origin.listener.LogoutClickListener;
import com.origin.rxh.origin.listener.NextActivityClickListener;

public class UserInfoActivity extends BaseActivity {
    private TextView usernameText;
    private TextView gradesText;
    private Button returnMBt;
    private Button logoutBt;
    private Button returnBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();
        returnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lastActivity = new Intent(UserInfoActivity.this, Template.class);
                startActivity(lastActivity);
                finish();
            }
        });
        returnMBt.setOnClickListener(new NextActivityClickListener(this, MenuActivity.class));
        logoutBt.setOnClickListener(new LogoutClickListener(this));
    }

    private void init() {
        usernameText = findViewById(R.id.username_text);
        gradesText = findViewById(R.id.grades_text);
        usernameText.setText(LoginActivity.getUserInfo().getUsername());
        String tempGrades = "Grades: " + LoginActivity.getUserInfo().getGrade();
        gradesText.setText(tempGrades);
        returnMBt = findViewById(R.id.returnm_bt);
        returnBt = findViewById(R.id.return_bt);
        logoutBt = findViewById(R.id.logout_bt);
    }
}
