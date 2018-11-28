package com.origin.rxh.origin.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo {
    private String username;
    private String password;
    private int grade;
    private Map<String,Question> correctQue;
    private Map<String,Question> wrongQue;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
        grade = 0;
        correctQue = new HashMap<>();
        wrongQue = new HashMap<>();
    }

    public UserInfo(String username, String password, int grade, Map<String,Question> correctQue, Map<String,Question> wrongQue) {
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

    public void updateCorrect(Question question){
        correctQue.put(question.getQuestionNo(),question);
    }

    public void updateWrong(Question question){
        wrongQue.put(question.getQuestionNo(),question);
    }

    public Map<String, Question> getCorrectQue() {
        return correctQue;
    }

    public Map<String, Question> getWrongQue() {
        return wrongQue;
    }
}
