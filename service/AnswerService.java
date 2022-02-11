package edu.xj.medicalcheckupweb.service;


import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.Answer;
import edu.xj.medicalcheckupweb.POJO.AnswerForPreview;
import edu.xj.medicalcheckupweb.POJO.AnswerForTmp;

import java.util.List;

public interface AnswerService {

    /**
     * 根据用户的ID来查询相关用户答过得问卷
     * @param userId 用户ID
     * @param pageNum 当前页数
     * @param pageSize 一页的大小
     * @return answer列表
     */
    PageInfo<AnswerForPreview> getAnswerPreviewByUserId(int userId, int pageNum, int pageSize);

    /**
     * 用户回答新的问卷
     * @param answer Answer类型封装的数据
     * @return 1 回答成功  其他失败
     */
    int addAnswer(AnswerForTmp answer);

    /**
     * 根据userId和answerId来获取到答卷信息
     * @param userId 用户id
     * @param answerId 答卷id
     * @return Answer封装的回答信息
     */
    Answer getAnswerDetail(int userId, int answerId);

    /**
     * 根据问卷ID获取到问卷的相关信息
     * @param answerId  答卷ID
     * @return null 或者封装好的answerForPreview对象，原来commentDoctorName变为患者姓名
     */
    AnswerForPreview getUncommentAnswerPreviewByAnswerId(int answerId);
}
