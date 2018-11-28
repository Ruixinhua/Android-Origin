package com.origin.rxh.origin.database;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;

import java.io.InputStream;

public class DataManager extends Activity {

    public void readFile(String filename){
        String fileName = filename;
        try{
            InputStream is = getResources().getAssets().open(fileName);
            int length = is.available();
            byte[]  buffer = new byte[length];
            is.read(buffer);
            String result = new String(buffer, "utf8");

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}