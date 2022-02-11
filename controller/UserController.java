package edu.xj.medicalcheckupweb.controller;

import edu.xj.medicalcheckupweb.POJO.User;
import edu.xj.medicalcheckupweb.service.UserService;
import edu.xj.medicalcheckupweb.util.JSONData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyhsna
 * <p>用户controller，负责处理和用户个人信息相关的一切事物</p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     *
     * @param telephone 登录用户的手机号
     * @param password  用户的密码
     * @return JsonFormat Data 0 代表登录成功，1代表不存在该用户，2代表用户手机号和密码不匹配  3代表用户手机号或者密码为空
     */
    @RequestMapping("/login")
    public JSONData login(String telephone, String password) {
        if ("".equals(telephone) || "".equals(password)) {
            return JSONData.buildError(3, "用户手机号或者密码为空");
        }
        //登录结果
        int result = userService.login(telephone, password);
        switch (result) {
            case -1:
                return JSONData.buildError(1, "手机号为空");
            case -2:
                return JSONData.buildError(2, "手机号和密码不匹配");
            default:
                return JSONData.buildSuccess(result);
        }
    }


    /**
     * 用户注册接口
     * 需要用post请求进行提交
     *
     * @param user user相关属性
     * @return 3 手机号或者密码为空为空 4 用户手机号已注册  5 注册失败
     */
    @RequestMapping("/register")
    public JSONData register(@RequestBody User user) {
        if (user.getTelephone() == null || user.getPassword() == null) {
            return JSONData.buildError(3, "手机号或密码为空");
        }
        int result = userService.register(user);
        switch (result) {
            case 0:
                //注册成功
                return JSONData.buildSuccess();
            case 1:
                return JSONData.buildError(4, "手机号已经注册");
            default:
                return JSONData.buildError(5, "注册失败，请稍后再试");
        }
    }

    /**
     * controller层用户修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @RequestMapping("/pri/changePassword")
    public JSONData changePassword(String oldPassword, String newPassword, int userId) {
        if (oldPassword.equals(newPassword)) {
            return JSONData.buildError(6, "新旧密码不能一样");
        }
        int result = userService.changePassword(oldPassword, newPassword, userId);
        switch (result) {
            case 0:
                return JSONData.buildSuccess("密码修改成功");
            case 1:
                return JSONData.buildError(8, "用户未登录");
            case 2:
                return JSONData.buildError(9, "旧密码输入错误");
            default:
                return JSONData.buildError(7, "修改失败");

        }
    }

    /**
     * controller层获取个人信息
     * @param userId 用户个人id
     * @return JSON相关信息
     */
    @RequestMapping("/pri/getPersonInfo")
    public JSONData getPersonInfo(int userId) {
        User user = userService.getPersonInfo(userId);
        if (user != null) {
            return JSONData.buildSuccess(user);
        }else{
            return JSONData.buildError(11,"获取个人信息失败");
        }
    }


}





















