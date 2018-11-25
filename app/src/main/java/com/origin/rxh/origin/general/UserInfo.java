package com.origin.rxh.origin.general;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private String username;
    private String password;
    private int grade;
    private List<Question> correctQue;
    private List<Question> wrongQue;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
        grade = 0;
        correctQue = new ArrayList<>();
        wrongQue = new ArrayList<>();
    }

    public UserInfo(String username, String password, int grade, List<Question> correctQue, List<Question> wrongQue) {
        this.username = username;
        this.password = password;
        this.grade = grade;
        this.correctQue = correctQue;
        this.wrongQue = wrongQue;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getGrade() {
        return grade;
    }

    public void updateGrade(int addGrade){
        grade += addGrade;
    }

    public List<Question> getCorrectQue() {
        return correctQue;
    }

    public void updateCorrect(Question question){
        correctQue.add(question);
    }

    public void updateWrong(Question question){
        wrongQue.add(question);
    }

    public List<Question> getWrongQue() {
        return wrongQue;
    }
}
