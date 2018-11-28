package com.origin.rxh.origin.general;

public class Question {
    private String questionNo;
    private String question;
    private String [] answer;
    private int correctAnswer;

    public Question(String questionNo, String question, String[] answer, int correctAnswer) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
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

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
