package com.origin.rxh.origin;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.yasic.library.particletextview.MovingStrategy.MovingStrategy;
import com.yasic.library.particletextview.MovingStrategy.RandomMovingStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;

public class StartAnimationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_animation);
        String texts[] = {this.getString(R.string.text_one),this.getString(R.string.text_two),this.getString(R.string.text_three)};
        ParticleTextView particleTextView = findViewById(R.id.startText);
        RandomMovingStrategy randomMovingStrategy = new RandomMovingStrategy();
        particleTextView.setConfig(setConfig(2,texts,0.2f,40,4000l,randomMovingStrategy));
        particleTextView.startAnimation();
    }

    private ParticleTextViewConfig setConfig(int step, String []texts, float releasing, int size, long delay, MovingStrategy ms){
        ParticleTextViewConfig config = new ParticleTextViewConfig.Builder()
                .setRowStep(step)
                .setColumnStep(step)
                .setTargetText(texts)
                .setReleasing(releasing)
                .setParticleRadius(1)
                .setMiniDistance(0.1)
                .setTextSize(size)
                .setDelay(delay)
                .setMovingStrategy(ms)
                .instance();
        return config;
    }
}
