package edu.xj.medicalcheckupweb.service;

import edu.xj.medicalcheckupweb.POJO.User;

/**
 * @author zyhsna
 */
public interface UserService {

    /**
     * Service层 用户登录
     *
     * @param telephone 手机号
     * @param password  密码
     * @return -1代表不存在该用户 -2代表用户手机号和密码不匹配 其他大于0的均为用户的ID
     */
    int login(String telephone, String password);

    /**
     * service层的用户注册接口
     * @param registerUserInfo 用户注册信息
     * @return 0 注册成功，1 手机号已经存在，2 注册失败
     */
    int register(User registerUserInfo);

    /**
     * service层用户修改密码接口
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userId 用户id
     * @return 0 修改成功 1 用户未登录  2 旧密码输入错误  3 修改失败
     */
    int changePassword(String oldPassword, String newPassword, int userId);

    /**
     * 根据UserId来查询用户的个人信息
     * @param userId 用户id
     * @return 除了用户密码外的其他属性封装的对象
     */
    User getPersonInfo(int userId);
}
