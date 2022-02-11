package edu.xj.medicalcheckupweb.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.Answer;
import edu.xj.medicalcheckupweb.POJO.AnswerForPreview;
import edu.xj.medicalcheckupweb.dao.CommentDao;
import edu.xj.medicalcheckupweb.dao.QuestionnaireDao;
import edu.xj.medicalcheckupweb.dao.UserDao;
import edu.xj.medicalcheckupweb.service.CommentService;
import edu.xj.medicalcheckupweb.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private QuestionnaireDao questionnaireDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserDao userDao;

    @Override
    public int addComment(String comment, int answerId, int userId, int doctorId) {
        int result = commentDao.addComment(comment, answerId, userId, doctorId);
        //更新redis内容
        BoundHashOperations<String, Object, Object> answerOps = getAnswerOps(userId);
        Object o = answerOps.get(String.valueOf(answerId));
        Answer answer = JsonUtil.jsonToPojo((String) o, Answer.class);
        answer.setComment(comment);
        answer.setCommentDoctorId(doctorId);
        updateAnswerDetail(answer, userId);
        return result;
    }

    @Override
    public PageInfo<AnswerForPreview> getNoComment(int doctorId, int pageNum, int pageSize) {
        //首先先查询医师自己发布的问卷是否有人还没评测，之后再查询其他的
        List<Integer> byDoctorId = questionnaireDao.getQuestionnaireIdByDoctorId(doctorId);
        //unCommentAnswerIds 未评测的答卷ID
        PageHelper.startPage(pageNum, pageSize);
        List<AnswerForPreview> unCommentedAnswers = commentDao.getNoComment();
        for (AnswerForPreview unCommentedAnswer : unCommentedAnswers) {
            String userNameByUserId = userDao.getUserNameByUserId(unCommentedAnswer.getAnswerUserId());
            //注意这里是患者姓名，不是医生姓名，就不再封装一个对象了
            unCommentedAnswer.setCommentDoctorName(userNameByUserId);
        }
        return new PageInfo<>(unCommentedAnswers);
    }

    private BoundHashOperations<String, Object, Object> getAnswerOps(int userId) {
        String answerKey;
        answerKey = String.format("answer:%s", userId);
        return redisTemplate.boundHashOps(answerKey);
    }

    private void updateAnswerDetail(Answer answer, int userId) {
        BoundHashOperations<String, Object, Object> answerOps = getAnswerOps(userId);
        answerOps.put(String.valueOf(answer.getAnswerId()), Objects.requireNonNull(JsonUtil.objectToJson(answer)));
    }
}
