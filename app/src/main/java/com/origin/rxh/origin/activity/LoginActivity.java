package com.origin.rxh.origin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.origin.rxh.origin.base.BaseActivity;
import com.origin.rxh.origin.R;
import com.origin.rxh.origin.general.UserInfo;
import com.origin.rxh.origin.database.DBService;

public class LoginActivity extends BaseActivity {
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button registerBtn;
    private Button loginBtn;
    public static DBService dbs;
    private static UserInfo user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbs = new DBService(this);
        checkLoginStatus();
        setContentView(R.layout.activity_login);

        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.login);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUser() == null) {
                    if (getUsername().length() == 0) {
                        setDialog("Username empty", "The username can not be empty", LoginActivity.this);
                        setFullScreen();
                    } else if (getUsername().length() > 10) {
                        setDialog("Invalid Username", "The length of username should not exceed 10", LoginActivity.this);
                        setFullScreen();
                    } else if (getPassword().length() < 5 || getPassword().length() > 10) {
                        setDialog("Invalid Password", "The length of password should between 6~10", LoginActivity.this);
                        setFullScreen();
                    } else {
                        changeToAnimation();
                    }
                }else{
                    setDialog("User exist","The user name exist, please select another", LoginActivity.this);
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUser() != null) {
                    if (checkUser()) {
                        dbs.saveUserTemp(getUser().getUsername());
                        changeToMenu(getUser().getUsername());
                    } else {
                        setDialog("Can't login", "The password is wrong",LoginActivity.this);
                        setFullScreen();
                    }
                }else{
                    setDialog("Can't login","The user is not exist!!",LoginActivity.this);
                    setFullScreen();
                }

            }
        });
    }

    private void saveUser(){
        user = new UserInfo(getUsername(),getPassword());
        dbs.saveUser(user);
        dbs.saveUserTemp(user.getUsername());
    }

    private UserInfo getUser(){
        return dbs.getUser(getUsername());
    }

    private String getUsername(){
        return usernameEdit.getText().toString();
    }

    private String getPassword(){
        return passwordEdit.getText().toString();
    }

    private boolean checkUser(){
        String password = passwordEdit.getText().toString();
        if(password.equals(getUser().getPassword())){
            return true;
        }else{
            return false;
        }
    }

    private void changeToAnimation(){
        saveUser();
        setFullScreen();
        Intent startAnimation = new Intent(LoginActivity.this, StartActivity.class);
        startAnimation.putExtra("username",user.getUsername());
        startActivity(startAnimation);
        this.finish();
    }

    private void changeToMenu(String username){
        user = getUserInfo();
        setFullScreen();
        Intent startMenu = new Intent(LoginActivity.this, MenuActivity.class);
        startMenu.putExtra("username",username);
        startActivity(startMenu);
        this.finish();
    }



    private void setFullScreen(){
        WindowManager.LayoutParams lp =  getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    // if the user has login, he / she will enter the menu activity
    private void checkLoginStatus(){
        String username = dbs.getUserTemp();
        if(username != null){
            changeToMenu(username);
        }else{
            return;
        }
    }

    public UserInfo getUserInfo(){
        dbs = new DBService(this);
        user = dbs.getUser(dbs.getUserTemp());
        return user;
    }

}
