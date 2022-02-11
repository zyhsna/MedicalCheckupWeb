package edu.xj.medicalcheckupweb.POJO;

import java.io.Serializable;


/**
 * @author zyhsna
 */
public class Question implements Serializable {
    private int questionId;
    private int questionnaireId;
    private String questionText;
    private int questionType;
    private String questionOption1;
    private String questionOption2;
    private String questionOption3;
    private String questionOption4;
    private String questionOption5;
    private String questionOption6;




    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getQuestionOption1() {
        return questionOption1;
    }

    public void setQuestionOption1(String questionOption1) {
        this.questionOption1 = questionOption1;
    }

    public String getQuestionOption2() {
        return questionOption2;
    }

    public void setQuestionOption2(String questionOption2) {
        this.questionOption2 = questionOption2;
    }

    public String getQuestionOption3() {
        return questionOption3;
    }

    public void setQuestionOption3(String questionOption3) {
        this.questionOption3 = questionOption3;
    }

    public String getQuestionOption4() {
        return questionOption4;
    }

    public void setQuestionOption4(String questionOption4) {
        this.questionOption4 = questionOption4;
    }

    public String getQuestionOption5() {
        return questionOption5;
    }

    public void setQuestionOption5(String questionOption5) {
        this.questionOption5 = questionOption5;
    }

    public String getQuestionOption6() {
        return questionOption6;
    }

    public void setQuestionOption6(String questionOption6) {
        this.questionOption6 = questionOption6;
    }
}
