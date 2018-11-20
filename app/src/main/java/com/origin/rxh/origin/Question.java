package com.origin.rxh.origin;

public class Question {
    private String questionNo;
    private String question;
    private String [] answer;

    public Question(String questionNo, String question, String[] answer) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswer() {
        return answer;
    }

    public String getQuestionNo() {
        return questionNo;
    }
}
