package edu.xj.medicalcheckupweb.POJO;

import java.io.Serializable;

/**
 * 用来封装用户已经做过的相关问卷,注意是在用户界面预览的，不包括回答内容
 */
public class AnswerForPreview implements Serializable {
    /**
     * <p>用户ID</p>
     * <p>问卷ID</p>
     * <p>答卷ID</p>
     * <p>对问卷进行评测的医师ZID</p>
     * <p>评测结果</p>
     * <p>问卷名字</p>
     */
    private int answerUserId;
    private int questionnaireId;
    private int answerId;
    private int commentDoctorId;
    private String comment;
    private String questionnaireTitle;
    private String commentDoctorName;

    public String getCommentDoctorName() {
        return commentDoctorName;
    }

    public void setCommentDoctorName(String commentDoctorName) {
        this.commentDoctorName = commentDoctorName;
    }

    public int getAnswerUserId() {
        return answerUserId;
    }

    public void setAnswerUserId(int answerUserId) {
        this.answerUserId = answerUserId;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getCommentDoctorId() {
        return commentDoctorId;
    }

    public void setCommentDoctorId(int commentDoctorId) {
        this.commentDoctorId = commentDoctorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQuestionnaireTitle() {
        return questionnaireTitle;
    }

    public void setQuestionnaireTitle(String questionnaireTitle) {
        this.questionnaireTitle = questionnaireTitle;
    }
}
