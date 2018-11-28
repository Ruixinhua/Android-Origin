package com.origin.rxh.origin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.origin.rxh.origin.R;
import com.origin.rxh.origin.general.Setting;
import com.origin.rxh.origin.general.StageAdapter;

import java.util.ArrayList;

public class StageOneActivity extends StageActivity {
    private String[] itemNumbers = {"1", "2", "3", "4"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        addButtonListener(this);
        //showQuestionDialog(this);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void init() {
        mAdapter = new StageAdapter(itemNumbers);
        mNavigationBar.setAdapter(mAdapter);

        imageNumbers = new int[]{R.drawable.res1, R.drawable.res2, R.drawable.res3};
        textNumbers = new ArrayList<>();
        textNumbers.add(new String[]{getStringById(R.string.par1_sec1_sen1_1), getStringById(R.string.par1_sec1_sen2_1), getStringById(R.string.par1_sec1_sen3_1)});
        textNumbers.add(new String[]{getStringById(R.string.par1_sec1_sen1_2), getStringById(R.string.par1_sec1_sen2_2), getStringById(R.string.par1_sec1_sen3_2)});
        textNumbers.add(new String[]{getStringById(R.string.par1_sec1_sen1_3), getStringById(R.string.par1_sec1_sen2_3), getStringById(R.string.par1_sec1_sen3_3)});
        for (int i = 0; i < textContent.length; i++) {
            textContent[i].setConfig(Setting.setConfigDelay(textNumbers.get(i)));
        }

        setContent();
    }

}
