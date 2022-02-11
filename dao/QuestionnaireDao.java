package edu.xj.medicalcheckupweb.dao;

import edu.xj.medicalcheckupweb.POJO.Questionnaire;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionnaireDao {
    /**
     * 根据问卷ID来查询问卷是否存在
     * @param questionnaireId 问卷ID
     * @return 问卷是否存在 1存在 0不存在
     */
    @Select("select count(*) from questionnaire where questionnaire_id = #{questionnaireId}")
    int checkQuestionnaireExistenceById(int questionnaireId);

    /**
     * 查询医师自己出的问卷
     * @param doctorId 医师ID
     * @return list int
     */
    @Select("select questionnaire_id from questionnaire where doctor_id = #{doctorId};")
    List<Integer> getQuestionnaireIdByDoctorId(int doctorId);


    /**
     * 根据问卷ID来查询问卷
     * @param questionnaireId 问卷ID
     * @return 问卷信息
     */
    @Select("select questionnaire_id, doctor_id, questionnaire_type,accomplishment_num ,questionnaire_title from questionnaire " +
            "where questionnaire_id = #{questionnaireId}")
    Questionnaire findQuestionnaireById(int questionnaireId);

    /**
     * 增加新的问卷
     * @param newQuestionnaire 新问卷
     * @return 1代表成功，其他代表失败
     */
    @Insert("insert into questionnaire(doctor_id, questionnaire_type, accomplishment_num,questionnaire_title) VALUE " +
            "(#{questionnaire.doctorId}, #{questionnaire.questionnaireType}, #{questionnaire.accomplishmentNum},#{questionnaire.questionnaireTitle}) ")
    @Options(useGeneratedKeys = true, keyProperty = "questionnaireId", keyColumn = "questionnaire_id")
    int addQuestionnaire(@Param("questionnaire") Questionnaire newQuestionnaire);

    /**
     * 根据搜索信息对问卷题目搜索之后返回相关信息
     * @param searchData 搜索信息
     * @return 搜索到的list集合
     */
    @Select("select * from questionnaire where questionnaire_title like #{searchData}")
    List<Questionnaire> searchQuestionnaire(String searchData);


    /**
     * 增加答卷的答题人数
     * @param questionnaireId 问卷ID
     * @return 1修改成功
     */
    @Update("update questionnaire set accomplishment_num = accomplishment_num + 1 where questionnaire_id = #{questionnaireId}")
    int increaseAccomplishmentNum(int questionnaireId);

    /**
     * 查询完成人数较多的问卷用于展示
     * @param questionnaireLimitNum 展示前几个
     * @return List
     */
    @Select("select * from questionnaire order by accomplishment_num desc limit #{questionnaireLimitNum}")
    List<Questionnaire> getHotQuestionnaire(int questionnaireLimitNum);
}
