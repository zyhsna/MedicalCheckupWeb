package edu.xj.medicalcheckupweb.controller;

import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.Answer;
import edu.xj.medicalcheckupweb.POJO.AnswerForPreview;
import edu.xj.medicalcheckupweb.service.CommentService;
import edu.xj.medicalcheckupweb.util.JSONData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 医师对问卷进行评测
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 医师对用户提交的问卷进行评测
     *
     * @param comment  医师的评测
     * @param answerId 答卷ID
     * @param userId   用户ID
     * @param doctorId 医师ID
     * @return 封装的json 1 代表成功
     */
    @RequestMapping("/pri/addComment")
    public JSONData addComment(String comment, int answerId, int userId, int doctorId) {
        int result = commentService.addComment(comment, answerId, userId, doctorId);
        if (result == 1) {
            return JSONData.buildSuccess();
        } else {
            return JSONData.buildError(18, "评测答卷失败");
        }
    }

    /**
     * 获取未评测的答卷给医师来评测
     *
     * @param doctorId 医师ID
     * @return List<answerId> 里面为答卷ID
     */
    @RequestMapping("/pri/getNoComment")
    public JSONData getNoComment(int doctorId, int pageNum, int pageSize) {
        PageInfo<AnswerForPreview> noComment = commentService.getNoComment(doctorId, pageNum, pageSize);
        if (noComment == null) {
            return JSONData.buildError(19, "获取未评测答卷失败");
        }
        return JSONData.buildSuccess(noComment);
    }


}
