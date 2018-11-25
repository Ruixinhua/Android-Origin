package com.origin.rxh.origin.start;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.origin.rxh.origin.Template;
import com.origin.rxh.origin.general.LandActivity;
import com.origin.rxh.origin.R;
import com.origin.rxh.origin.general.UserInfo;
import com.origin.rxh.origin.listener.LogoutClickListener;
import com.origin.rxh.origin.listener.NextActivityClickListener;

public class MenuActivity extends LandActivity {
    private TextView userInfoView;
    private TextView warningView;
    private ImageButton[] stagesBt;
    private TextView []stagesText;
    private Button logoutBt;
    private Button userInfoBt;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
        user = LoginActivity.getUserInfo();
        String userInfoMes = "Welcome "+user.getUsername() + ", your grades is " + user.getGrade();
        userInfoView.setText(userInfoMes);
        for(int i = 0;i < user.getGrade() / 10 + 1;i++){
            stagesBt[i].setClickable(true);
            stagesBt[i].setAlpha(1f);
            stagesBt[i].setOnClickListener(new NextActivityClickListener(this,Template.class));
            stagesText[i].setTextColor(getResources().getColor(R.color.stageBrightState));
        }
        String warnMes = "You have to get " + ((user.getGrade() / 10 + 1) * 10 - user.getGrade())+ " more marks to enter next stage";
        warningView.setText(warnMes);

        userInfoBt.setOnClickListener(new NextActivityClickListener(this, UserInfoActivity.class));
        logoutBt.setOnClickListener(new LogoutClickListener(this));

    }

    private void init(){
        userInfoView = findViewById(R.id.username_text);
        warningView = findViewById(R.id.warning);
        stagesBt = new ImageButton[]{findViewById(R.id.stage1_bt),findViewById(R.id.stage2_bt),findViewById(R.id.stage3_bt)};
        stagesText = new TextView[]{findViewById(R.id.stage1_text),findViewById(R.id.stage2_text),findViewById(R.id.stage3_text)};
        logoutBt = findViewById(R.id.logout_bt);
        userInfoBt = findViewById(R.id.user_info_bt);
    }

}
