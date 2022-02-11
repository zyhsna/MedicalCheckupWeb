package edu.xj.medicalcheckupweb.controller;

import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.User;
import edu.xj.medicalcheckupweb.service.AdminService;
import edu.xj.medicalcheckupweb.util.JSONData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理员功能接口
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 获取用户信息列表
     * @param pageNum 页数
     * @param pageSize 大小
     * @return list
     */
    @GetMapping("/getUserInfoList")
    public JSONData getUserInfoList(int pageNum, int pageSize){
        //TODO 管理员登录验证
        PageInfo<User> userInfoList = adminService.getUserInfoList(pageNum, pageSize);
        if (userInfoList.getList() == null){
            return JSONData.buildError(21,"管理员获取用户信息列表失败");
        }
        return JSONData.buildSuccess(userInfoList);
    }

    /**
     * 删除用户
     * @param userId 要删除的用户ID
     * @return 状态码0删除成功， 22删除失败
     */
    @GetMapping("/deleteUser")
    public JSONData deleteUser(int userId){
        int result = adminService.deleteUser(userId);
        return JSONData.buildSuccess();
    }

}
