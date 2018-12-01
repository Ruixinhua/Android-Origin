package com.origin.rxh.origin.general;

//Reference:https://github.com/Yasic/ParticleTextView
import com.yasic.library.particletextview.MovingStrategy.BidiHorizontalStrategy;
import com.yasic.library.particletextview.MovingStrategy.BidiVerticalStrategy;
import com.yasic.library.particletextview.MovingStrategy.CornerStrategy;
import com.yasic.library.particletextview.MovingStrategy.MovingStrategy;
import com.yasic.library.particletextview.MovingStrategy.RandomMovingStrategy;
import com.yasic.library.particletextview.MovingStrategy.VerticalStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;

import java.util.Random;

public class Setting {
    private static int step = 2;
    private static float releasing = 0.2f;
    private static int size = 70;
    private static long delay = 4000l;
    public static MovingStrategy movingStrategy[] = {new RandomMovingStrategy(),new VerticalStrategy(),new CornerStrategy(),new BidiVerticalStrategy(),new BidiHorizontalStrategy()};

    public static ParticleTextViewConfig setConfig(int step, String []texts, float releasing, int size, long delay, MovingStrategy ms) {
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

    public static ParticleTextViewConfig setConfig(String []texts) {
        return setConfig(step,texts,releasing,size,delay,movingStrategy[0]);
    }

    public static ParticleTextViewConfig setConfig(String texts) {
        return setConfig(step,new String[]{texts},releasing,size,delay,movingStrategy[0]);
    }

    public static ParticleTextViewConfig setConfigDelay(String []texts) {
        Random random = new Random();
        return setConfig(step,texts,releasing,40,-1l,movingStrategy[random.nextInt(5)]);
    }

    public static int getStep() {
        return step;
    }

    public static float getReleasing() {
        return releasing;
    }

    public static int getSize() {
        return size;
    }

    public static long getDelay() {
        return delay;
    }

}
