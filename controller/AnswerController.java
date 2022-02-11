package edu.xj.medicalcheckupweb.controller;

import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.Answer;
import edu.xj.medicalcheckupweb.POJO.AnswerForPreview;
import edu.xj.medicalcheckupweb.POJO.AnswerForTmp;
import edu.xj.medicalcheckupweb.service.AnswerService;
import edu.xj.medicalcheckupweb.util.JSONData;
import edu.xj.medicalcheckupweb.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 进行问卷回答和答卷的存储、提取一系列操作
 *
 * @author zyhsna
 */
@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * 根据用户ID来获取用户答过的问卷preview,注意不是答卷的全部信息
     *
     * @param userId 用户ID
     * @return answer list
     */
    @RequestMapping("/pri/getAnswerPreview")
    public JSONData getAnswerPreviewByUserId(int userId, int pageNum, int pageSize) {
        //TODO 加入用户登录验证
        PageInfo<AnswerForPreview> answerPreviewByUserId = answerService.getAnswerPreviewByUserId(userId, pageNum, pageSize);
        if (answerPreviewByUserId == null) {
            return JSONData.buildError(15, "获取已答问卷失败");
        }
        return JSONData.buildSuccess(answerPreviewByUserId);
    }

    /**
     * 根据问卷ID获取到问卷的相关信息
     *
     * @param answerId 答卷ID
     * @return 0 返回成功
     */
    @RequestMapping("/getUncommentAnswerPreviewByAnswerId")
    public JSONData getUncommentAnswerPreviewByAnswerId(int answerId) {
        AnswerForPreview answerForPreview = answerService.getUncommentAnswerPreviewByAnswerId(answerId);
        if (answerForPreview != null) {
            return JSONData.buildSuccess();
        } else {
            return JSONData.buildError(20, "获取答卷预览失败");
        }
    }


    /**
     * 用户回答问卷
     *
     * @param answer 问卷信息
     * @return 0 成功
     */
    @PostMapping("/pri/addAnswer")
    public JSONData addAnswer(@RequestBody AnswerForTmp answer) {
        //TODO 加入用户登录验证
        System.out.println(answer.toString());
        int result = answerService.addAnswer(answer);
        if (result == 1) {
            return JSONData.buildSuccess();
        } else {
            return JSONData.buildError(16, "回答问卷失败");
        }
    }

    /**
     * 根据用户ID和答卷ID获取到答卷详情
     *
     * @param userId   用户ID
     * @param answerId 答卷ID
     * @return 0 成功
     */
    @GetMapping("/pri/getAnswerDetail")
    public JSONData getAnswerDetail(int userId, @Nullable int answerId) {
        Answer answer = answerService.getAnswerDetail(userId, answerId);
        if (answer == null) {
            return JSONData.buildError(17, "获取答卷信息失败");
        }
        return JSONData.buildSuccess(answer);
    }
}
