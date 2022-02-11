package edu.xj.medicalcheckupweb.service;

import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.User;

import java.util.List;

public interface AdminService {
    /**
     * 管理员获取用户信息接口
     * 返回值不应该存在密码等敏感个人信息
     * @param pageNum 当前页数
     * @param pageSize 页数大小
     * @return 搜索得到的用户信息
     */
    PageInfo<User> getUserInfoList(int pageNum, int pageSize);

    /**
     * 删除用户
     * @param userId 要删除的用户ID
     * @return 1删除成功， 其他删除失败
     */
    int deleteUser(int userId);
}
