package com.origin.rxh.origin.general;

import com.yasic.library.particletextview.MovingStrategy.BidiHorizontalStrategy;
import com.yasic.library.particletextview.MovingStrategy.BidiVerticalStrategy;
import com.yasic.library.particletextview.MovingStrategy.CornerStrategy;
import com.yasic.library.particletextview.MovingStrategy.MovingStrategy;
import com.yasic.library.particletextview.MovingStrategy.RandomMovingStrategy;
import com.yasic.library.particletextview.MovingStrategy.VerticalStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;

public class Setting {
    private static int step = 2;
    private static float releasing = 0.2f;
    private static int size = 70;
    private static long delay = 4000l;
    private static RandomMovingStrategy randomMovingStrategy = new RandomMovingStrategy();
    private static VerticalStrategy verticalStrategy = new VerticalStrategy();
    private static CornerStrategy cornerStrategy = new CornerStrategy();
    private static BidiVerticalStrategy bidiVerticalStrategy = new BidiVerticalStrategy();
    private static BidiHorizontalStrategy bidiHorizontalStrategy = new BidiHorizontalStrategy();

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
        return setConfig(step,texts,releasing,size,delay,randomMovingStrategy);
    }

    public static ParticleTextViewConfig setConfig(String texts) {
        return setConfig(step,new String[]{texts},releasing,size,delay,randomMovingStrategy);
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

    public static RandomMovingStrategy getRandomMovingStrategy() {
        return randomMovingStrategy;
    }

    public static VerticalStrategy getVerticalStrategy() {
        return verticalStrategy;
    }

    public static CornerStrategy getCornerStrategy() {
        return cornerStrategy;
    }

    public static BidiVerticalStrategy getBidiVerticalStrategy() {
        return bidiVerticalStrategy;
    }

    public static BidiHorizontalStrategy getBidiHorizontalStrategy() {
        return bidiHorizontalStrategy;
    }
}
