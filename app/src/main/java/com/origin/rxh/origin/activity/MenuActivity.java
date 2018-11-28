package com.origin.rxh.origin.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.origin.rxh.origin.base.LandActivity;
import com.origin.rxh.origin.R;
import com.origin.rxh.origin.database.DBService;
import com.origin.rxh.origin.general.Question;
import com.origin.rxh.origin.general.UserInfo;
import com.origin.rxh.origin.listener.LogoutClickListener;
import com.origin.rxh.origin.listener.NextActivityClickListener;
import com.origin.rxh.origin.listener.StageStartListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends LandActivity {
    private TextView userInfoView;
    private TextView warningView;
    private ImageButton[] stagesBt;
    private TextView []stagesText;
    private Button logoutBt;
    private Button userInfoBt;
    private DBService dbs;
    private UserInfo user;

    public static List<List<String[]>> textContents;
    public static List<List<Integer>> imagesNumber;
    public static List<List<Question>> questionsNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();

        String userInfoMes = "Welcome "+user.getUsername() + ", your grades is " + user.getGrade();
        userInfoView.setText(userInfoMes);
        for(int i = 0;i < user.getGrade() / 10 + 1;i++){
            stagesBt[i].setClickable(true);
            stagesBt[i].setAlpha(1f);
            stagesBt[i].setOnClickListener(new StageStartListener(this,StageActivity.class,i));
            stagesText[i].setTextColor(getResources().getColor(R.color.stageBrightState));
        }
        String warnMes = "You have to get " + ((user.getGrade() / 10 + 1) * 10 - user.getGrade())+ " more marks to enter next stage";
        warningView.setText(warnMes);

        userInfoBt.setOnClickListener(new NextActivityClickListener(this, UserInfoActivity.class));
        logoutBt.setOnClickListener(new LogoutClickListener(this));
        loadData();
    }

    private void loadData(){
        textContents = new ArrayList<>();
        imagesNumber = new ArrayList<>();
        questionsNumber = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            textContents.add(new ArrayList<String[]>());
            imagesNumber.add(new ArrayList<Integer>());
            questionsNumber.add(new ArrayList<Question>());
            loadContents("stage_contents"+(i+1)+".txt",i);
            loadQuestions("stage_question"+(i+1)+".txt",i);
        }
    }

    private void loadContents(String fileName, int index) {
        String reads = readFile(fileName);
        Log.d("file all", reads);
        if (reads != null) {
            Log.d("file enter","enter");
            String stageContents[] = reads.split("\n");
            String tempContents[][] = new String[3][stageContents.length];
            for (int i=0;i < stageContents.length;i++) {
                String temp[] = stageContents[i].split("#");
                for (int j = 0; j < temp.length; j++) {
                    tempContents[j][i] = temp[j];
                    Log.d("file", temp[j]);
                }
            }
            textContents.get(index).add(tempContents[0]);
            textContents.get(index).add(tempContents[1]);
            textContents.get(index).add(tempContents[2]);
        }
    }

    private void loadQuestions(String fileName, int index) {
        String reads = readFile(fileName);
        Log.d("file all", reads);
        if (reads != null) {
            Log.d("file enter","enter");
            String stageContents[] = reads.split("\n");
            for (int i=0;i < stageContents.length;i++) {
                String temp[] = stageContents[i].split("#");
                Question question = new Question(temp[0],temp[1],new String[]{temp[2],temp[3]},Integer.parseInt(temp[4]));
                questionsNumber.get(index).add(question);
                dbs.saveQuestion(question);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(textContents != null && imagesNumber != null && questionsNumber != null){
            return;
        }else{
            loadData();
        }
    }

    private void init(){
        userInfoView = findViewById(R.id.username_text);
        warningView = findViewById(R.id.warning);
        stagesBt = new ImageButton[]{findViewById(R.id.stage1_bt),findViewById(R.id.stage2_bt),findViewById(R.id.stage3_bt)};
        stagesText = new TextView[]{findViewById(R.id.stage1_text),findViewById(R.id.stage2_text),findViewById(R.id.stage3_text)};
        logoutBt = findViewById(R.id.logout_bt);
        userInfoBt = findViewById(R.id.user_info_bt);
        dbs = new DBService(this);
        user = dbs.getUser(dbs.getUserTemp());
    }

}
