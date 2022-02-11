package edu.xj.medicalcheckupweb.dao;

import edu.xj.medicalcheckupweb.POJO.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zyhsna
 */
@Mapper
public interface QuestionDao {
    /**
     * 根据问卷的ID来查询属于其的问题信息
     *
     * @param questionnaireId 问卷ID
     * @return 问答题的List
     */
    @Select("select * from question where questionnaire_id = #{questionnaireId}")
    List<Question> findQuestionsByQuestionnaireId(int questionnaireId);

    /**
     * 新增问题（不是问卷）
     *
     * @param question 新增的问题
     * @return 1代表新增成功 0代表出错
     */
    @Insert("insert into question(questionnaire_id, question_text, question_type, " +
            "question_option_1, question_option_2, question_option_3, question_option_4, question_option_5, question_option_6) " +
            "value( #{question.questionnaireId}," +
            "#{question.questionText}, #{question.questionType}, #{question.questionOption1}, " +
            "#{question.questionOption2}, #{question.questionOption3},#{question.questionOption4}, " +
            "#{question.questionOption5}, #{question.questionOption6}) ")
    int addQuestion(@Param("question") Question question);
}
