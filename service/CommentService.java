package edu.xj.medicalcheckupweb.service;


import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.AnswerForPreview;

public interface CommentService {
    /**
     * 医师对问卷进行评测
     * @param comment  评测内容
     * @param answerId wenjuanID
     * @param userId 用户ID
     * @param doctorId 医师ID
     * @return 1代表评测成功 其他无
     */
    int addComment(String comment, int answerId, int userId, int doctorId);

    /**
     * 获取未评测的答卷
     * @param doctorId 医师ID
     * @param pageNum 当前页数
     * @param pageSize 一页的大小
     * @return List< answerId > 里面为答卷ID
     */
    PageInfo<AnswerForPreview> getNoComment(int doctorId, int pageNum, int pageSize);
}
