package com.origin.rxh.origin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.yasic.library.particletextview.View.ParticleTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageActivity extends LandActivity implements MyInterface.DialogReturn {

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

    private int stageIndex;
    public List<Integer> imagesNumber;
    public List<List<String>> textContents;
    public List<Question> questionList;

    public List<Integer[]> imageResources;
    MyInterface myInterface;
    MyInterface.DialogReturn dialogReturn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        init();

        stageIndex = getIntent().getIntExtra("stageIndex", 0);
        userInfoBt.setOnClickListener(new NextActivityClickListener(this, UserInfoActivity.class));
        returnBt.setOnClickListener(new NextActivityClickListener(this, MenuActivity.class));
        logoutBt.setOnClickListener(new LogoutClickListener(this));
        mNavigationBar.setLayoutManager(mLayoutManager);

        initStage();
        addButtonListener(this);
        fullScreen();
    }

    private void initStage() {
        mAdapter = new StageAdapter(itemNumbers);
        //mNavigationBar.setAdapter(mAdapter);

        imageResources = new ArrayList<>();
        imageResources.add(new Integer[]{R.drawable.stage1_res1,R.drawable.stage1_res2,R.drawable.stage1_res3,
                R.drawable.stage1_res4,R.drawable.stage1_res5,R.drawable.stage1_res6,R.drawable.stage1_res7,
                R.drawable.stage1_res8,R.drawable.stage1_res3,R.drawable.stage1_res4});
        imageResources.add(new Integer[]{R.drawable.stage2_res1,R.drawable.stage2_res2,R.drawable.stage2_res3,
                R.drawable.stage2_res4,R.drawable.stage2_res5,R.drawable.stage2_res6,R.drawable.stage2_res7,
                R.drawable.stage2_res8,R.drawable.stage2_res3,R.drawable.stage2_res4});
        imageResources.add(new Integer[]{R.drawable.stage3_res1,R.drawable.stage3_res2,R.drawable.stage3_res3,
                R.drawable.stage3_res4,R.drawable.stage3_res5,R.drawable.stage3_res6,R.drawable.stage3_res7,
                R.drawable.stage3_res8,R.drawable.stage3_res3,R.drawable.stage3_res4});
        imagesNumber = new ArrayList<>();
        imagesNumber.addAll(Arrays.asList(imageResources.get(stageIndex)));
        textContents = new ArrayList<>();
        questionList = MenuActivity.questionsNumber.get(stageIndex);
        textContents.add(new ArrayList<String>());
        textContents.add(new ArrayList<String>());
        textContents.add(new ArrayList<String>());
        for (int i = 0; i < textContent.length; i++) {
            textContent[i].setConfig(Setting.setConfigDelay(MenuActivity.textContents.get(stageIndex).get(i)));
        }
        setContent();
    }

    private void init() {
        usernameText = findViewById(R.id.username_text);
        gradesText = findViewById(R.id.grades_text);
        dbs = new DBService(this);
        user = dbs.getUser(dbs.getUserTemp());
        usernameText.setText(user.getUsername());
        String tempGrades = "Grades: " + user.getCorrectQue().size();
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
        //questions = new Question[]{new Question("StageOneQ1", getStringById(R.string.part1_s1_q1), new String[]{getStringById(R.string.part1_s1_a1_A), getStringById(R.string.part1_s1_a1_B)}, 0)};
        //dbs.saveQuestion(questions[0]);
    }

    private Activity stageContext;

    public void addButtonListener(Activity context) {
        stageContext = context;

        // backward button listener
        backwardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partPositionCurrent == 0 && textPositionCurrent == 0) {
                    finish();
                } else if (textPositionCurrent <= 0) {
                    partPositionCurrent--;
                    textPositionCurrent = 0;
                    questionNo = partPositionCurrent;
                    setBackward();
                } else {
                    if (textPositionCurrent < 3) {
                        textContent[textPositionCurrent].setVisibility(View.INVISIBLE);
                        textContent[textPositionCurrent].setTargetText(partPositionCurrent);
                        textPositionCurrent--;
                    }
                }
            }
        });

        // forward button listener
        forwardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (partPositionCurrent >= MenuActivity.textContents.get(stageIndex).get(0).length) {
                    // TODO start next activity
                    textPositionCurrent = -1;
                    partPositionCurrent = MenuActivity.textContents.get(stageIndex).get(0).length - 1;
                    questionNo = partPositionCurrent;
                    setDialog("Finished", "Your grades is " + user.getCorrectQue().size(), StageActivity.this);
                } else if (textPositionCurrent == 2) {
                    showQuestionDialog();
                    fullScreen();
                } else {
                    textPositionCurrent++;
                    if (textPositionCurrent < 3) {
                        // TODO setContent
                        setContent();
                    } else {
                        textPositionCurrent = 2;
                    }

                }
            }
        });
    }

    @Override
    public void setDialog(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("return menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent menuActivity = new Intent(StageActivity.this, MenuActivity.class);
                startActivity(menuActivity);
                finish();
            }
        });
        builder.show();
    }

    private int choice = -1;
    private Question tempQuestion;

    public void showQuestionDialog() {
        Log.d("dialog", "show question");
        myInterface.setListener(this);
        //tempQuestion = questions[questionNo];
        tempQuestion = questionList.get(questionNo);
        dbs = new DBService(this);
        user = dbs.getUser(dbs.getUserTemp());
        Log.d("question size", user.getCorrectQue().size() + "");
        for (Question q : user.getCorrectQue().values()) {
            Log.d("question", q.getQuestionNo());
        }
        Log.d("question temp", tempQuestion.getQuestionNo());
        if (user.getCorrectQue().get(tempQuestion.getQuestionNo()) == null) {
            Log.d("dialog", "question");

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
        } else {
            partPositionCurrent++;
            textPositionCurrent = 0;
            questionNo = partPositionCurrent;
            setInvisible();
            setContent();
            myInterface.getListener().onDialogCompleted(false);
        }
    }

    @Override
    public void onDialogCompleted(boolean answer) {
        if (answer) {
            partPositionCurrent++;
            textPositionCurrent = 0;
            if (tempQuestion.getCorrectAnswer() == choice) {
                Log.d("submit", "info");
                user.updateCorrect(tempQuestion);
                user.getWrongQue().remove(tempQuestion.getQuestionNo());
                user.updateGrade(1);
                Log.d("submit", user.getCorrectQue().size() + "");
                for (Question q : user.getCorrectQue().values()) {
                    Log.d("submit user", q.getQuestionNo());
                }

                dbs.saveUser(user);
                Log.d("submit dbs", dbs.getUser(dbs.getUserTemp()).getCorrectQue().size() + "");
                Log.d("submit question", dbs.getUser(dbs.getUserTemp()).getCorrectQue().get(tempQuestion.getQuestionNo()).getQuestionNo());
                String tempGrades = "Grades: " + user.getCorrectQue().size();
                gradesText.setText(tempGrades);
            } else {
                user.updateWrong(tempQuestion);
                dbs.saveUser(user);
            }
            setInvisible();
            setContent();
        }
        choice = -1;
        questionNo = partPositionCurrent;
    }


    public void setInvisible() {
        for (ParticleTextView aTextContent : textContent) {
            aTextContent.setVisibility(View.INVISIBLE);
        }
    }

    public void setContent() {
        if (partPositionCurrent < imagesNumber.size() && textPositionCurrent < MenuActivity.textContents.get(stageIndex).size()) {
            imageContent.setImageDrawable(getImageResource(imagesNumber.get(partPositionCurrent)));
            textContent[textPositionCurrent].setVisibility(View.VISIBLE);
            textContent[textPositionCurrent].setTargetText(partPositionCurrent);
            textContent[textPositionCurrent].startAnimation();
        }
    }

    public void setBackward() {
        for (int i = 0; i < textContent.length; i++) {
            textContent[i].setTargetText(partPositionCurrent);
        }
        textContent[textPositionCurrent].setVisibility(View.VISIBLE);
        textContent[textPositionCurrent].startAnimation();
        imageContent.setImageDrawable(getImageResource(imagesNumber.get(partPositionCurrent)));
    }

    public Drawable getImageResource(int id) {
        return getResources().getDrawable(id);
    }

    public String getStringById(int id) {
        return getResources().getString(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
