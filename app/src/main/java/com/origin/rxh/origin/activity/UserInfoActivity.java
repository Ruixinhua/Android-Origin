package com.origin.rxh.origin.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.origin.rxh.origin.R;
import com.origin.rxh.origin.base.BaseActivity;
import com.origin.rxh.origin.database.DBService;
import com.origin.rxh.origin.general.Question;
import com.origin.rxh.origin.general.UserInfo;
import com.origin.rxh.origin.listener.LogoutClickListener;
import com.origin.rxh.origin.listener.NextActivityClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends BaseActivity {
    private TextView usernameText;
    private TextView gradesText;
    private Button returnMBt;
    private Button logoutBt;
    private Button returnBt;
    private DBService dbs;
    private UserInfo user;
    private ListView questionsList[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();
        setQuestionAdapter();
        returnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        returnMBt.setOnClickListener(new NextActivityClickListener(this, MenuActivity.class));
        logoutBt.setOnClickListener(new LogoutClickListener(this));
    }

    private void setQuestionAdapter(){
        List<List<String>> questions = new ArrayList<>();
        for(int i=0;i<6;i++){
            questions.add(new ArrayList<String>());
        }
        for(Question question:user.getCorrectQue().values()){
            String questionNo = question.getQuestionNo();
            if(questionNo.contains("One")){
                questions.get(0).add(questionNo);
            }
            if(questionNo.contains("Two")){
                questions.get(1).add(questionNo);
            }
            if(questionNo.contains("Three")){
                questions.get(2).add(questionNo);
            }
        }
        for(Question question:user.getWrongQue().values()){
            String questionNo = question.getQuestionNo();
            if(questionNo.contains("One")){
                questions.get(3).add(questionNo);
            }
            if(questionNo.contains("Two")){
                questions.get(4).add(questionNo);
            }
            if(questionNo.contains("Three")){
                questions.get(5).add(questionNo);
            }
        }
        QuestionListAdapter adapters[] = {new QuestionListAdapter(this, R.id.item_text, questions.get(0)), new QuestionListAdapter(this, R.id.item_text, questions.get(1)),
                new QuestionListAdapter(this, R.id.item_text, questions.get(2)), new QuestionListAdapter(this, R.id.item_text, questions.get(3)),
                new QuestionListAdapter(this, R.id.item_text, questions.get(4)), new QuestionListAdapter(this, R.id.item_text, questions.get(5))};
        for (int i = 0; i < questionsList.length; i++) {
            questionsList[i].setAdapter(adapters[i]);
        }
    }
    private void init() {
        usernameText = findViewById(R.id.username_text);
        gradesText = findViewById(R.id.grades_text);
        dbs = new DBService(this);
        user = dbs.getUser(dbs.getUserTemp());
        usernameText.setText(user.getUsername());
        String tempGrades = "Grades: " + user.getGrade();
        gradesText.setText(tempGrades);
        returnMBt = findViewById(R.id.returnm_bt);
        returnBt = findViewById(R.id.return_bt);
        logoutBt = findViewById(R.id.logout_bt);
        questionsList = new ListView[]{findViewById(R.id.correct_stage_one), findViewById(R.id.correct_stage_two),
                findViewById(R.id.correct_stage_three), findViewById(R.id.wrong_stage_one),
                findViewById(R.id.wrong_stage_two), findViewById(R.id.wrong_stage_three)};
    }

    class QuestionListAdapter extends ArrayAdapter {

        public QuestionListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_text, null);
            TextView textView = view.findViewById(R.id.item_text);
            textView.setText((String) getItem(position));
            return view;
        }
    }
}
