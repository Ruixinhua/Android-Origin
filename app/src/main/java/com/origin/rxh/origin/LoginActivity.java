package com.origin.rxh.origin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.origin.rxh.origin.database.DBService;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button registerBtn;
    private Button loginBtn;
    private DBService dbs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.login);
        dbs = new DBService(this);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo user = saveUser();
                changeToAnimation(user);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUser() != null) {
                    if (checkUser()) {
                        changeToAnimation(getUser());
                    } else {
                        setDialog("Can't login", "The password is wrong");
                    }
                }else{
                    setDialog("Can't login","The user is not exist!!");
                }

            }
        });

        //UserInfo user = new UserInfo();
    }

    private UserInfo saveUser(){
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        UserInfo user = new UserInfo(username,password);
        dbs.saveUser(user);
        return user;
    }

    private UserInfo getUser(){
        String username = usernameEdit.getText().toString();
        return dbs.getUser(username);
    }

    private boolean checkUser(){
        String password = passwordEdit.getText().toString();
        if(password.equals(getUser().getPassword())){
            return true;
        }else{
            return false;
        }
    }

    private void changeToAnimation(UserInfo user){
        Intent startAnimation = new Intent(LoginActivity.this,StartAnimationActivity.class);
        startAnimation.putExtra("username",user.getUsername());
        startActivity(startAnimation);
    }

    private void setDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("return", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        builder.show();
    }
}
