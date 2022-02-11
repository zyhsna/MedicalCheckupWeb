package edu.xj.medicalcheckupweb.dao;

import edu.xj.medicalcheckupweb.POJO.AnswerForPreview;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDao {
    /**
     * 医师对问卷进行评测
     *
     * @param comment  评测内容
     * @param answerId 答卷ID
     * @param userId   用户ID
     * @param doctorId 医师ID
     * @return 1代表评测成功 其他无
     */
    @Insert("update answer set comment_doctor_id = #{doctorId} , comment=#{comment} where answer_id = #{answerId};")
    int addComment(String comment, int answerId, int userId, int doctorId);



    @Select("select q.questionnaire_title, a.questionnaire_id, a.answer_user_id, a.comment, a.answer_id\n" +
            "from (" +
            "         select *" +
            "         from answer" +
            "         where  comment is null or comment = ' ' " +
            "     ) as a" +
            "         join questionnaire q on q.questionnaire_id = a.questionnaire_id")
    List<AnswerForPreview> getNoComment();


    /**
     * 根据问卷ID来获取未评测的答卷ID
     *
     * @param qnId 问卷ID
     * @return List 答卷ID
     */
    @Select("select answer_id from answer where comment is null and questionnaire_id =#{qnId};")
    List<Integer> getNoCommentByQNId(int qnId);

    /**
     * 根据问卷ID来获取未评测的答卷ID
     *
     * @param qnId 排除掉的问卷ID
     * @return List 答卷ID
     */
    @Select("<script>" +
            "select answer_id from answer where comment is null and questionnaire_id not in" +
                "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>" +
                    "#{item}" +
                "</foreach>" +
            "</script>")
    List<Integer> getNoCommentExcludeQNId(@Param("qnId") List<Integer> qnId);
}
