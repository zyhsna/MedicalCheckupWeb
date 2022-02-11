package edu.xj.medicalcheckupweb.service;

import edu.xj.medicalcheckupweb.POJO.Questionnaire;

import java.awt.desktop.QuitEvent;
import java.util.List;

/**
 * @author zyhsna
 */
public interface QuestionService {
     /**
      * 根据问卷ID来组成问卷
      * @param questionnaireId 问卷ID
      * @return 问卷,如果为null，代表这个问卷不存在
      */
    Questionnaire getQuestionsByQuestionnaireId(int questionnaireId);

    /**
     * 新增问卷操作
     * @param newQuestionnaire 新的问卷
     * @return 1 增加成功  0 失败
     */
    int addQuestionnaire(Questionnaire newQuestionnaire);

    /**
     * <p>service层 根据相关信息来搜索问卷，搜索范围为问卷标题</p>
     * <b>注意，这里不进行问卷问题的搜索</b>
     * @param searchData 搜索内容
     * @return questionnaire
     */
    List<Questionnaire> searchQuestionnaire(String searchData);

    /**
     * 获取完成人数较多的问卷
     * @return List< Questionnaire >
     */
    List<Questionnaire> getHotQuestionnaire();
}
