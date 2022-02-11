package edu.xj.medicalcheckupweb.POJO;

import java.io.Serializable;
import java.util.List;

/**
 * 对问卷的完整的回答，用于点开完整看
 *
 * @author zyhsna
 */
public class Answer implements Serializable {
    private int answerId;
    private int answerUserId;
    private int questionnaireId;
    private int commentDoctorId;
    private String comment;
    private List<AnswerDetail> answerDetailList;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

    public List<AnswerDetail> getAnswerDetailList() {
        return answerDetailList;
    }

    public void setAnswerDetailList(List<AnswerDetail> answerDetailList) {
        this.answerDetailList = answerDetailList;
    }
}
