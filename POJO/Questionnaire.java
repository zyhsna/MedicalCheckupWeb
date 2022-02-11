package edu.xj.medicalcheckupweb.POJO;

import java.io.Serializable;
import java.util.List;

/**
 * @author zyhsna
 */
public class Questionnaire implements Serializable {
    /**从上到下依次为
     * <p>问卷ID，标识每份问卷</p>
     * <p>医师ID，标识哪个医生出的</p>
     * <p>问卷类型，是对应哪种病状</p>
     * <p>问卷完成人数</p>
     * <p>问卷名字</p>
     * <p>问卷的list</p>
     */
    private int questionnaireId;
    private int doctorId;
    private int questionnaireType;
    private int accomplishmentNum;
    private String questionnaireTitle;
    private List<Question> questionList;

    public String getQuestionnaireTitle() {
        return questionnaireTitle;
    }

    public void setQuestionnaireTitle(String questionnaireTitle) {
        this.questionnaireTitle = questionnaireTitle;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getQuestionnaireType() {
        return questionnaireType;
    }

    public void setQuestionnaireType(int questionnaireType) {
        this.questionnaireType = questionnaireType;
    }

    public int getAccomplishmentNum() {
        return accomplishmentNum;
    }

    public void setAccomplishmentNum(int accomplishmentNum) {
        this.accomplishmentNum = accomplishmentNum;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
