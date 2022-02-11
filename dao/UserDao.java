package edu.xj.medicalcheckupweb.dao;


import edu.xj.medicalcheckupweb.POJO.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zyhsna
 */
@Mapper
public interface UserDao {

    /**
     *根据手机号查询是否有用户存在
     * @param telephone 查询的手机号
     * @return 注册结果
     */
    @Select("select count(*) from user where telephone = #{telephone}")
    int findUserByTel(String telephone);


    /**
     * 根据userId获取到username
     * @param userId 用户ID
     * @return 用户姓名
     */
    @Select("select user_name from user where user_id=#{userId}")
    String getUserNameByUserId(int userId);

    /**
     * 用户进行登录操作
     * @param telephone 手机号
     * @param password  密码
     * @return User对象
     */
    @Select("select * from user where telephone=#{telephone} and password = #{password}")
    User userLogin(String telephone, String password);

    /**
     * Dao层 用户注册
     * @param user 用户注册相关信息
     * @return 注册结果，如果成功返回1
     */
    @Insert("insert into user(user_name, age, gender, telephone, user_level, password) value" +
            " (#{user.userName},#{user.age},#{user.gender},#{user.telephone},#{user.userLevel},#{user.password})")
    int register(@Param("user") User user);


    /**
     * Dao层 用户修改密码
     * @param newPassword 新密码
     * @param userId 用户ID
     * @return 1 修改成功
     */
    @Update("update user set password = #{newPassword} where user_id = #{userId} ")
    int changePassword(String newPassword, int userId);

    /**
     * Dao层 根据用户id来查询用户是否存在
     * @param userId  用户id
     * @return 用户个数，1代表存在，0代表不存在
     */
    @Select("select count(*) from user where user_id = #{userId}")
    int findUserByUserId(int userId);

    /**
     *根据userId来查询用户信息
     * @param userId 用户id
     * @return 除了用户密码外的其他属性封装
     */
    @Select("select user_id, user_name, telephone, gender, age, user_level from user where user_id = #{userId}")
    User getPersonInfo(int userId);

    /**
     * Dao层 查询用户信息
     * @return 用户列表
     */
    @Select("select user_id,  user_name, age, gender, telephone, user_level from user")
    List<User> getUserInfoList();

    /**
     * Dao层 删除用户
     * @param userId 用户ID
     * @return 1删除成功
     */
    @Delete("delete from user where user_id = #{userId}")
    int deleteUser(int userId);
}
