package com.bishal.coursemaster.models;

public class QuestionModel {
    private String question;
    private String answer;
    private String a;
    private String b;
    private String c;
    private String d;
    private Long type;
    public QuestionModel() {

    }
    public QuestionModel(String a, String question, String b, String c, String d, Long type, String answer) {
        this.a = a;
        this.question = question;
        this.b = b;
        this.c = c;
        this.d = d;
        this.type = type;
        this.answer = answer;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
