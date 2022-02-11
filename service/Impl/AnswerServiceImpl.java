package edu.xj.medicalcheckupweb.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.*;
import edu.xj.medicalcheckupweb.dao.AnswerDao;
import edu.xj.medicalcheckupweb.dao.AnswerDetailDao;
import edu.xj.medicalcheckupweb.dao.QuestionnaireDao;
import edu.xj.medicalcheckupweb.dao.UserDao;
import edu.xj.medicalcheckupweb.service.AnswerService;
import edu.xj.medicalcheckupweb.service.QuestionService;
import edu.xj.medicalcheckupweb.util.JsonUtil;
import io.netty.util.internal.StringUtil;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * answerService的实现类
 *
 * @author zyhsna
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDetailDao answerDetailDao;

    @Autowired
    private QuestionnaireDao questionnaireDao;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RedisTemplate redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 获取答卷的预览信息
     * @param userId 用户ID
     * @param pageNum 当前页数
     * @param pageSize 一页的大小
     * @return 答卷信息
     */
    @Override
    public PageInfo<AnswerForPreview> getAnswerPreviewByUserId(int userId, int pageNum, int pageSize) {
        List<AnswerForPreview> answerForPreviewList = new ArrayList<>();
        //没有再到mysql中进行查询，分页查询，mybatis-plus
        PageHelper.startPage(pageNum, pageSize);
        List<AnswerForPreview> answerPreviewByUserId = answerDao.getAnswerPreviewByUserId(userId);
        for (AnswerForPreview answerForPreview : answerPreviewByUserId) {
            int commentDoctorId = answerForPreview.getCommentDoctorId();
            String userNameByUserId = userDao.getUserNameByUserId(commentDoctorId);
            answerForPreview.setCommentDoctorName(userNameByUserId);
        }
        return new PageInfo<>(answerPreviewByUserId);
    }

    @Override
    public int addAnswer(AnswerForTmp answer) {
        List<AnswerDetailForTmp> answerDetailTmpList = answer.getAnswerDetailList();
        int questionnaireId = answer.getQuestionnaireId();
        Answer answerForInsert = new Answer();
        answerForInsert.setAnswerUserId(answer.getAnswerUserId());
        answerForInsert.setQuestionnaireId(questionnaireId);
        List<AnswerDetail> answerDetailList = new ArrayList<>();
        for (int i=0;i<answerDetailTmpList.size();i++) {
            AnswerDetailForTmp answerDetailForTmp = answerDetailTmpList.get(i);

            //answerOptions 需要转换为 answerDetail中的answerOption1,2，3····
            List<String> answerOptions = answerDetailForTmp.getAnswerOptions();

            //对每个answerDetailTmp类型对象转为AnswerDetail对象
            AnswerDetail answerDetail = new AnswerDetail();
            answerDetail.setQuestionId(answerDetailForTmp.getQuestionId());
            answerDetail.setUserId(answerDetailForTmp.getUserId());
            //获取answerDetail对象的属性名
            Field[] declaredFields = answerDetail.getClass().getDeclaredFields();
            //遍历属性，将answerOption属性装入
            try {
                for (int j = 0; j < declaredFields.length; j++) {
                    String name = declaredFields[j].getName();
                    int length = name.length();
                    //获取最后一位字符
                    String substring = name.substring(length - 1, length);
                    //判断是否为数字
                    if (substring.matches("[0-9]+")){
                        Method set = AnswerDetail.class.getMethod("set"+ name.substring(0,1).toUpperCase() + name.substring(1), String.class);
                        set.invoke(answerDetail, answerOptions.get(Integer.parseInt(substring)-1));
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            answerDetailList.add(answerDetail);
        }
        try {
            answerDao.addAnswer(answerForInsert);
            answerForInsert.setAnswerDetailList(answerDetailList);
            for (AnswerDetail answerDetail : answerDetailList) {
                answerDetail.setAnswerId(answerForInsert.getAnswerId());
                int result = answerDetailDao.addAnswerDetail(answerDetail);
            }
            //同时把redis中已经答过的问卷信息更新下
            updateAnswerDetail(answerForInsert,answer.getAnswerUserId());
            //增加做完题目的人数
            questionnaireDao.increaseAccomplishmentNum(questionnaireId);
            //redis更新问卷
            questionService.getQuestionsByQuestionnaireId(questionnaireId);

        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public Answer getAnswerDetail(int userId, int answerId) {
        BoundHashOperations<String, Object, Object> answerOps = getAnswerOps(userId, 2);
        Object o = answerOps.get(String.valueOf(answerId));
        if (o != null) {
            return JsonUtil.jsonToPojo((String) o, Answer.class);
        }
        Answer answer = answerDao.getAnswerByUserId(userId, answerId);
        List<AnswerDetail> answerDetailList = answerDetailDao.getAnswerDetailByUserId(answerId);
        answer.setAnswerDetailList(answerDetailList);
        updateAnswerDetail(answer, userId);
        return answer;
    }

    @Override
    public AnswerForPreview getUncommentAnswerPreviewByAnswerId(int answerId) {
        AnswerForPreview answerForPreview = answerDao.getUncommentAnswerPreviewByAnswerId(answerId);
        String userNameByUserId = userDao.getUserNameByUserId(answerForPreview.getAnswerId());
        //注意这里是患者姓名，不是医生姓名，就不再封装一个对象了
        answerForPreview.setCommentDoctorName(userNameByUserId);
        return answerForPreview;
    }


    /**
     * <p>获取answer的hash操作对象</p>
     * <p>注意，userId即为最外层的大key</p>
     * <p>后续只需要输入问卷ID即可获得答题信息</p>
     *
     * @param userId 用户ID
     * @param type   获取的操作对象用途 1代表获取preview的操作对象  2代表获取answerDetail的操作对象
     * @return hash操作对象
     */
    private BoundHashOperations<String, Object, Object> getAnswerOps(int userId, int type) {
        String answerKey;
        if (type == 1) {
            answerKey = String.format("answer_preview:%s", userId);
        } else {
            answerKey = String.format("answer:%s", userId);
        }
        return redisTemplate.boundHashOps(answerKey);
    }


    /**
     * 更新user的已答问卷到redis当中
     *
     * @param answerForPreviewList 已经答题过得问卷信息
     * @param userId               用户ID
     */
    private void updateAnswerPreviewList(List<AnswerForPreview> answerForPreviewList, int userId) {
        BoundHashOperations<String, Object, Object> answerOps = getAnswerOps(userId, 1);
        for (AnswerForPreview answer : answerForPreviewList) {
            // 以  answer_preview： userId ：questionnaireId 的key形式存储数据
            answerOps.put( String.valueOf(answer.getQuestionnaireId()), Objects.requireNonNull(JsonUtil.objectToJson(answer)));
        }
    }

    private void updateAnswerDetail(Answer answer, int userId) {
        BoundHashOperations<String, Object, Object> answerOps = getAnswerOps(userId, 2);
        answerOps.put(String.valueOf(answer.getAnswerId()), Objects.requireNonNull(JsonUtil.objectToJson(answer)));
    }
}
