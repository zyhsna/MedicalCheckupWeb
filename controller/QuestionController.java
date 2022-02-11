package edu.xj.medicalcheckupweb.controller;

import edu.xj.medicalcheckupweb.POJO.Questionnaire;
import edu.xj.medicalcheckupweb.service.QuestionService;
import edu.xj.medicalcheckupweb.util.JSONData;
import edu.xj.medicalcheckupweb.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zyhsna
 * <p>问题相关的controller，主要负责问卷分发和设计等等</p>
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 根据问卷的ID来获取问卷中的问题
     *
     * @param questionnaireId
     * @return 问卷信息
     */
    @RequestMapping("/pri/getQuestionsByQuestionnaireId")
    public JSONData getQuestionsByQuestionnaireId(int questionnaireId) {
        Questionnaire questionnaire = questionService.getQuestionsByQuestionnaireId(questionnaireId);
        if (questionnaire == null) {
            return JSONData.buildError(12, "问卷获取失败");
        }
        //获取成功返回信息
        return JSONData.buildSuccess(questionnaire);
    }

    @PostMapping("/pri/addQuestionnaire")
    public JSONData addQuestionnaire(@RequestBody Questionnaire newQuestionnaire) {
        //TODO 增加新建问卷的权限认证,也就是只有医师能够进行新加问卷操作
        //TODO 前端记得增加判断问题列表不为null
        int result = questionService.addQuestionnaire(newQuestionnaire);
        if (result == 1) {
            return JSONData.buildSuccess();
        } else {
            return JSONData.buildError(13, "新增问卷失败");
        }
    }

    @GetMapping("/searchQuestionnaire")
    public JSONData searchQuestionnaire(String searchData) {
        //TODO 前端判断搜索数据不为空
        List<Questionnaire> questionnaireList = questionService.searchQuestionnaire(searchData);
        if (questionnaireList == null) {
            return JSONData.buildError(14, "搜索问卷无结果");
        }
        return JSONData.buildSuccess(questionnaireList);
    }

    /**
     * 获取完成人数较多的问卷用于展示
     * @return 0 正常 其他错误
     */
    @GetMapping("/getHotQuestionnaire")
    public JSONData getHotQuestionnaire() {
        List<Questionnaire> questionnaireList = questionService.getHotQuestionnaire();
        if (questionnaireList != null) {

            return JSONData.buildSuccess(questionnaireList);
        }
        else {
            return JSONData.buildError(20,"获取热门问卷失败");
        }
    }
}
