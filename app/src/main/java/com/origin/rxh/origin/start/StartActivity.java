package com.origin.rxh.origin.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.origin.rxh.origin.general.LandActivity;
import com.origin.rxh.origin.R;
import com.origin.rxh.origin.general.Setting;
import com.yasic.library.particletextview.View.ParticleTextView;

public class StartActivity extends LandActivity {
    private ParticleTextView startText;
    private Button startBt;
    private int runCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_animation);

        startBt = findViewById(R.id.start_button);
        startAnimation();
        final Handler handler = new Handler();
        runCount = 0;
        // set a timer to control the animation
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                if(runCount == 0){
                    startBt.setVisibility(View.VISIBLE);
                    startBt.setText(R.string.jump);
                    startBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent menuActivity = new Intent(StartActivity.this,MenuActivity.class);
                            menuActivity.putExtra("username",getIntent().getStringExtra("username"));
                            startActivity(menuActivity);
                            finish();
                        }
                    });
                }
                if(runCount == 2){
                    stopAnimation();
                    handler.removeCallbacks(this);
                }
                runCount++;
                handler.postDelayed(this, (Setting.getDelay() + 300) * 2);
            }

        };
        handler.postDelayed(runnable, 1000);


    }

    private void stopAnimation(){
        startText.setAnimationFrozen();
        startBt.setText(R.string.start);
    }
    private void startAnimation(){
        startText = findViewById(R.id.startText);
        String texts[] = {this.getString(R.string.text_one),this.getString(R.string.text_two),this.getString(R.string.text_three)};
        startText.setConfig(Setting.setConfig(texts));
        startText.startAnimation();
    }
}
