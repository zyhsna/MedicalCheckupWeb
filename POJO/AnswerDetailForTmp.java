package edu.xj.medicalcheckupweb.POJO;

import java.util.List;

/**
 * 回答问卷时候调用 answerController的addAnswer方法专属
 */
public class AnswerDetailForTmp {
    private int detailId;
    private int answerId;
    private int userId;
    private int questionId;
    private List<String> answerOptions;

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

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<String> answerOptions) {
        this.answerOptions = answerOptions;
    }
}
