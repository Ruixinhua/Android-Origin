package com.origin.rxh.origin.start;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.origin.rxh.origin.general.BaseActivity;
import com.origin.rxh.origin.R;
import com.origin.rxh.origin.general.UserInfo;
import com.origin.rxh.origin.database.DBService;

public class LoginActivity extends BaseActivity {
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button registerBtn;
    private Button loginBtn;
    private DBService dbs;
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
                if(getUsername().length() == 0){
                    setDialog("Username empty","The username can not be empty");
                }else if(getUsername().length() > 10){
                    setDialog("Invalid Username","The length of username should not exceed 10");
                }else if(getPassword().length() < 5 || getPassword().length() > 10){
                    setDialog("Invalid Password","The length of password should between 6~10");
                }else{
                    changeToAnimation();
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
                        setDialog("Can't login", "The password is wrong");
                    }
                }else{
                    setDialog("Can't login","The user is not exist!!");
                }

            }
        });
    }

    private UserInfo saveUser(){
        UserInfo user = new UserInfo(getUsername(),getPassword());
        dbs.saveUser(user);
        dbs.saveUserTemp(user.getUsername());
        user = dbs.getUser(dbs.getUserTemp());
        return user;
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
        Intent startAnimation = new Intent(LoginActivity.this, StartActivity.class);
        startAnimation.putExtra("username",user.getUsername());
        startActivity(startAnimation);
        this.finish();
    }

    private void changeToMenu(String username){
        user = dbs.getUser(dbs.getUserTemp());
        Intent startMenu = new Intent(LoginActivity.this, MenuActivity.class);
        startMenu.putExtra("username",username);
        startActivity(startMenu);
        this.finish();
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

    // if the user has login, he / she will enter the menu activity
    private void checkLoginStatus(){
        String username = dbs.getUserTemp();
        if(username != null){
            changeToMenu(username);
        }else{
            return;
        }
    }

    public static UserInfo getUserInfo(){
        return user;
    }

}
