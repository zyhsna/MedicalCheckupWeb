package edu.xj.medicalcheckupweb.dao;

import edu.xj.medicalcheckupweb.POJO.AnswerDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 问卷答题详情的dao
 * @author zyhsna
 */
@Mapper
public interface AnswerDetailDao {
    /**
     * 回答问卷的新增
     * @param answerDetail 答卷
     * @return 1成功 其他失败
     */
    @Insert("insert into answer_detail(user_id, question_id, answer_id,answer_option_1, answer_option_2, " +
            "answer_option_3, answer_option_4, answer_option_5, answer_option_6) value (" +
            "#{ad.userId}, #{ad.questionId}, #{ad.answerId},#{ad.answerOption1}, #{ad.answerOption2}, " +
            "#{ad.answerOption3}, #{ad.answerOption4}, #{ad.answerOption5}, #{ad.answerOption6})")
    @Options(useGeneratedKeys = true, keyProperty = "detailId", keyColumn = "detail_id")
    int addAnswerDetail(@Param("ad") AnswerDetail answerDetail);

    /**
     * 根据答卷ID来获取该答卷id下的所有的答题详情
     * @param answerId 答卷ID
     * @return 封装的answer
     */
    @Select("select * from answer_detail where answer_id = #{answerId}")
    List<AnswerDetail> getAnswerDetailByUserId(int answerId);
}
