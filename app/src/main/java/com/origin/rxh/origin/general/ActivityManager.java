package com.origin.rxh.origin.general;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    private List<Activity> AllActivitites = new ArrayList<Activity>();
    private static ActivityManager instance;

    public ActivityManager() {

    }

    public synchronized static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        AllActivitites.add(activity);
    }

    public void OutSign() {
        for (Activity activity : AllActivitites) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
