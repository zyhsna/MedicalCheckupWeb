package edu.xj.medicalcheckupweb.dao;

import edu.xj.medicalcheckupweb.POJO.Answer;
import edu.xj.medicalcheckupweb.POJO.AnswerForPreview;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * answer的dao层，主要用来查询答卷信息
 * @author zyhsna
 */
@Mapper
public interface AnswerDao {
    /**
     * Dao层 根据userId来查询用户回答过得问卷
     *
     * @param userId 用户ID
     * @return List集合封装的问卷信息
     */
    @Select("select q.questionnaire_title, a.questionnaire_id, a.answer_user_id, a.comment, a.answer_id, a.comment_doctor_id\n" +
            "from (" +
            "         select *" +
            "         from answer" +
            "         where answer_user_id = #{userId}" +
            "     ) as a" +
            "         join questionnaire q on q.questionnaire_id = a.questionnaire_id")
    List<AnswerForPreview> getAnswerPreviewByUserId(int userId);

    /**
     * Dao层 新增答卷信息
     * @param answer Answer相关信息
     *               返回自增的主键ID，也就是answer的ID
     */
    @Insert("insert into answer(answer_user_id, questionnaire_id, comment) values (" +
            "#{answer.answerUserId}, #{answer.questionnaireId}, #{answer.comment} )")
    @Options(useGeneratedKeys = true, keyProperty = "answerId", keyColumn = "answer_id")
    void addAnswer(@Param("answer") Answer answer);

    /**
     * Dao层 获取答卷详细信息
     * @param userId 用户ID
     * @param answerId 问卷ID
     * @return Answer封装的对象
     */
    @Select("select * from answer where answer_id = #{answerId} and answer_user_id = #{userId}")
    Answer getAnswerByUserId(int userId, int answerId);

    /**
     * Dao层 根据答卷ID获取答卷
     * @param answerId 答卷ID
     * @return null或者answerForPreview对象
     */
    @Select("select q.questionnaire_title, a.questionnaire_id, a.answer_user_id, a.comment, a.answer_id\n" +
            "from (" +
            "         select *" +
            "         from answer" +
            "         where answer_id = 1" +
            "     ) as a" +
            "         join questionnaire q on q.questionnaire_id = a.questionnaire_id;")
    AnswerForPreview getUncommentAnswerPreviewByAnswerId(int answerId);
}
