package edu.xj.medicalcheckupweb.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.xj.medicalcheckupweb.POJO.Question;
import edu.xj.medicalcheckupweb.POJO.Questionnaire;
import edu.xj.medicalcheckupweb.dao.QuestionDao;
import edu.xj.medicalcheckupweb.dao.QuestionnaireDao;
import edu.xj.medicalcheckupweb.service.QuestionService;
import edu.xj.medicalcheckupweb.util.ProjectToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zyhsna
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionnaireDao questionnaireDao;

    @Autowired
    private RedisTemplate redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Questionnaire getQuestionsByQuestionnaireId(int questionnaireId) {
        Object o = redisTemplate.opsForValue().get(ProjectToken.UPDATE_QUESTIONNAIRE + questionnaireId);
        if (o!=null){
            //redis中存在问卷，直接拉取下来
            return objectMapper.convertValue(o, Questionnaire.class);
        }
        //先判断是否有这个id编号的问卷存在
        int result = questionnaireDao.checkQuestionnaireExistenceById(questionnaireId);
        if (result==0){
            //代表没这个编号的问卷
            return null;
        }
        Questionnaire questionnaire = questionnaireDao.findQuestionnaireById(questionnaireId);
        //根据问卷ID来查询问题
        List<Question> questionList = new ArrayList<>();
        questionList = questionDao.findQuestionsByQuestionnaireId(questionnaireId);
        questionnaire.setQuestionList(questionList);
        //更新到redis中
        updateQuestionnaireToRedis(questionnaire);
        return questionnaire;
    }

    @Override
    public int addQuestionnaire(Questionnaire newQuestionnaire) {

        List<Question> questionList = newQuestionnaire.getQuestionList();

        int questionnaireResult = questionnaireDao.addQuestionnaire(newQuestionnaire);
        int questionnaireId = newQuestionnaire.getQuestionnaireId();
        for (Question question : questionList) {
            //把question加入数据库中
            //TODO 检测问题插入的正确性
            question.setQuestionnaireId(questionnaireId);
            int result = questionDao.addQuestion(question);
        }
        return questionnaireResult;
    }

    @Override
    public List<Questionnaire> searchQuestionnaire(String searchData) {

        List<Questionnaire> questionnaireList = questionnaireDao.searchQuestionnaire("%"+searchData+"%");
        return questionnaireList;
    }

    @Override
    public List<Questionnaire> getHotQuestionnaire() {
        int questionnaireLimitNum = 6;
        return questionnaireDao.getHotQuestionnaire(questionnaireLimitNum);
    }

    /**
     * 查询成功后将问卷更新到redis当中，下次直接取出来
     * @param questionnaire 问卷信息
     */
    private void updateQuestionnaireToRedis(Questionnaire questionnaire){
        redisTemplate.opsForValue().set(ProjectToken.UPDATE_QUESTIONNAIRE+questionnaire.getQuestionnaireId(), questionnaire);
    }
}
