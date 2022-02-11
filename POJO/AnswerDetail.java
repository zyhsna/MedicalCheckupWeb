package edu.xj.medicalcheckupweb.POJO;

import java.io.Serializable;

public class AnswerDetail implements Serializable {
    private int detailId;
    private int answerId;
    private int userId;
    private int questionId;
    private String answerOption1;
    private String answerOption2;
    private String answerOption3;
    private String answerOption4;
    private String answerOption5;
    private String answerOption6;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerOption1() {
        return answerOption1;
    }

    public void setAnswerOption1(String answerOption1) {
        this.answerOption1 = answerOption1;
    }

    public String getAnswerOption2() {
        return answerOption2;
    }

    public void setAnswerOption2(String answerOption2) {
        this.answerOption2 = answerOption2;
    }

    public String getAnswerOption3() {
        return answerOption3;
    }

    public void setAnswerOption3(String answerOption3) {
        this.answerOption3 = answerOption3;
    }

    public String getAnswerOption4() {
        return answerOption4;
    }

    public void setAnswerOption4(String answerOption4) {
        this.answerOption4 = answerOption4;
    }

    public String getAnswerOption5() {
        return answerOption5;
    }

    public void setAnswerOption5(String answerOption5) {
        this.answerOption5 = answerOption5;
    }

    public String getAnswerOption6() {
        return answerOption6;
    }

    public void setAnswerOption6(String answerOption6) {
        this.answerOption6 = answerOption6;
    }
}
