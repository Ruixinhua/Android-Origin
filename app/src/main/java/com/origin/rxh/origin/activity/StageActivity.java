package com.origin.rxh.origin.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.origin.rxh.origin.R;
import com.origin.rxh.origin.base.LandActivity;
import com.origin.rxh.origin.database.DBService;
import com.origin.rxh.origin.general.MyInterface;
import com.origin.rxh.origin.general.Question;
import com.origin.rxh.origin.general.Setting;
import com.origin.rxh.origin.general.StageAdapter;
import com.origin.rxh.origin.general.UserInfo;
import com.origin.rxh.origin.listener.LogoutClickListener;
import com.origin.rxh.origin.listener.NextActivityClickListener;
import com.origin.rxh.origin.activity.MenuActivity;
import com.origin.rxh.origin.activity.UserInfoActivity;
import com.yasic.library.particletextview.View.ParticleTextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StageActivity extends LandActivity implements MyInterface.DialogReturn{

    private TextView usernameText;
    private TextView gradesText;
    private Button userInfoBt;
    private Button returnBt;
    private Button logoutBt;
    private DBService dbs;
    private UserInfo user;
    public Button backwardBt;
    public Button forwardBt;

    public RecyclerView mNavigationBar;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public ImageView imageContent;
    public ParticleTextView[] textContent;
    public int textPositionCurrent = 0;
    public int partPositionCurrent = 0;
    public int questionNo = 0;
    private String[] itemNumbers = {"1", "2", "3", "4"};

    public int[] imageNumbers;
    public List<Integer> imagesNumber;
    public List<String[]> textNumbers;
    public List<List<String>> textContents;
    public Question[] questions;

    MyInterface myInterface;
    MyInterface.DialogReturn dialogReturn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        init();

        userInfoBt.setOnClickListener(new NextActivityClickListener(this, UserInfoActivity.class));
        returnBt.setOnClickListener(new NextActivityClickListener(this, MenuActivity.class));
        logoutBt.setOnClickListener(new LogoutClickListener(this));
        mNavigationBar.setLayoutManager(mLayoutManager);

        initStageOne();
        initStage("stage_one_contents.txt");
        addButtonListener(this);
        fullScreen();
    }

    private void initStageOne() {
        mAdapter = new StageAdapter(itemNumbers);
        //mNavigationBar.setAdapter(mAdapter);

//        imageNumbers = new int[]{R.drawable.res1, R.drawable.res2, R.drawable.res3};
        imagesNumber = new ArrayList<>();
//        textNumbers = new ArrayList<>();
//        textNumbers.add(new String[]{getStringById(R.string.par1_sec1_sen1_1), getStringById(R.string.par1_sec1_sen2_1), getStringById(R.string.par1_sec1_sen3_1)});
//        textNumbers.add(new String[]{getStringById(R.string.par1_sec1_sen1_2), getStringById(R.string.par1_sec1_sen2_2), getStringById(R.string.par1_sec1_sen3_2)});
//        textNumbers.add(new String[]{getStringById(R.string.par1_sec1_sen1_3), getStringById(R.string.par1_sec1_sen2_3), getStringById(R.string.par1_sec1_sen3_3)});
        textContents = new ArrayList<>();
        textContents.add(new ArrayList<String>());
        textContents.add(new ArrayList<String>());
        textContents.add(new ArrayList<String>());

    }

    private void initStage(String fileName){
        String reads = readFile(fileName);
        Log.d("file",reads);
        if(reads != null) {
            String stageContents[] = reads.split("\n");
            for (String stageContent : stageContents) {
                String temp[] = stageContent.split("#");
                for (int j = 0; j < temp.length; j++) {
                    textContents.get(j).add(temp[j]);
                }
                imagesNumber.add(R.drawable.res1);
            }
            for (int i = 0; i < textContent.length; i++) {
                String temp[] = new String[textContents.get(i).size()];
                for (int j = 0; j < temp.length; j++) {
                    temp[j] = textContents.get(i).get(j);
                    Log.d("file temp",temp[j]);
                }
                textContent[i].setConfig(Setting.setConfigDelay(temp));
            }
            setContent();
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
        userInfoBt = findViewById(R.id.user_info_bt);
        returnBt = findViewById(R.id.return_bt);
        logoutBt = findViewById(R.id.logout_bt);
        backwardBt = findViewById(R.id.backward_bt);
        forwardBt = findViewById(R.id.forward_bt);
        imageContent = findViewById(R.id.image_content);
        textPositionCurrent = 0;
        partPositionCurrent = 0;
        textContent = new ParticleTextView[]{findViewById(R.id.text_content_one), findViewById(R.id.text_content_two), findViewById(R.id.text_content_three)};
        mNavigationBar = findViewById(R.id.navigation_bar);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myInterface = new MyInterface();
        myInterface.setListener(this);
        // TODO question store
        questions = new Question[]{new Question("StageOneQ1", getStringById(R.string.part1_s1_q1), new String[]{getStringById(R.string.part1_s1_a1_A), getStringById(R.string.part1_s1_a1_B)}, 0)};
        dbs.saveQuestion(questions[0]);
    }

    private Activity stageContext;
    public void addButtonListener(Activity context){
        stageContext = context;
        backwardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partPositionCurrent == 0 && textPositionCurrent == 0) {
                    finish();
                } else if (textPositionCurrent == 0) {
                    partPositionCurrent--;
                    textPositionCurrent = 0;
                    setBackward();
                } else {
                    textContent[textPositionCurrent].setVisibility(View.INVISIBLE);
                    textContent[textPositionCurrent].backwardAnimation();
                    textPositionCurrent--;
                }
            }
        });

        forwardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partPositionCurrent == textContents.get(0).size()) {
                    // TODO start next activity
                    //showQuestionDialog(questions[0], context);
                } else if (textPositionCurrent == 2) {
                    showQuestionDialog();
                    fullScreen();
                } else {
                    textPositionCurrent++;
                    if (textPositionCurrent < 3) {
                        // TODO setContent
                        setContent();
                    }

                }
            }
        });
    }



    private int choice;
    private Question tempQuestion;
    public void showQuestionDialog(){
        Log.d("dialog","show question");
        myInterface.setListener(this);
        tempQuestion = questions[questionNo];
        dbs = new DBService(this);
        user = dbs.getUser(dbs.getUserTemp());
        Log.d("question size",user.getCorrectQue().size()+"");
        for(Question q:user.getCorrectQue().values()){
            Log.d("question",q.getQuestionNo());
        }
        Log.d("question temp",tempQuestion.getQuestionNo());
        if(user.getCorrectQue().get(tempQuestion.getQuestionNo()) == null) {
            Log.d("dialog","question");

            AlertDialog.Builder questionDialog = new AlertDialog.Builder(stageContext);
            questionDialog.setTitle(tempQuestion.getQuestion());
            questionDialog.setCancelable(false);
            questionDialog.setSingleChoiceItems(tempQuestion.getAnswer(), -1,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            choice = which;
                        }
                    });
            questionDialog.setPositiveButton("Submit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myInterface.getListener().onDialogCompleted(true);
                        }
                    });
            questionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false);
                }
            });
            questionDialog.show();
        }else{
            partPositionCurrent++;
            textPositionCurrent = 0;
            setInvisible();
            setContent();
            myInterface.getListener().onDialogCompleted(false);
        }
    }

    @Override
    public void onDialogCompleted(boolean answer) {
        if(answer){
            partPositionCurrent++;
            textPositionCurrent = 0;
            if(tempQuestion.getCorrectAnswer() == choice) {
                Log.d("submit", "info");
                user.updateCorrect(tempQuestion);
                user.updateGrade(1);
                Log.d("submit",user.getCorrectQue().size()+"");
                for (Question q : user.getCorrectQue().values()) {
                    Log.d("submit user",q.getQuestionNo());
                }

                dbs.saveUser(user);
                Log.d("submit dbs", dbs.getUser(dbs.getUserTemp()).getGrade() + "");
                Log.d("submit question", dbs.getUser(dbs.getUserTemp()).getCorrectQue().get(tempQuestion.getQuestionNo()).getQuestionNo());
                String tempGrades = "Grades: " + user.getGrade();
                gradesText.setText(tempGrades);
            }else{
                user.updateWrong(tempQuestion);
                dbs.saveUser(user);
            }
            setInvisible();
            setContent();
        }
    }


    public void setInvisible() {
        for (ParticleTextView aTextContent : textContent) {
            aTextContent.setVisibility(View.INVISIBLE);
        }
    }

    public void setContent() {
        imageContent.setImageDrawable(getImageResource(imagesNumber.get(partPositionCurrent)));
        textContent[textPositionCurrent].setVisibility(View.VISIBLE);
        textContent[textPositionCurrent].startAnimation();
    }

    public void setBackward() {
        for (int i = 0; i < textContent.length; i++) {
            textContent[i].backwardAnimation();
            textContent[i].backwardAnimation();
        }
        for (int i = 0; i < textContent.length; i++) {
            textContent[i].startAnimation();
        }
        imageContent.setImageDrawable(getImageResource(imagesNumber.get(partPositionCurrent)));
    }

    public Drawable getImageResource(int id){
        return getResources().getDrawable(id);
    }
    public String getStringById(int id) {
        return getResources().getString(id);
    }

}
