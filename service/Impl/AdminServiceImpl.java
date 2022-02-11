package edu.xj.medicalcheckupweb.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xj.medicalcheckupweb.POJO.User;
import edu.xj.medicalcheckupweb.dao.UserDao;
import edu.xj.medicalcheckupweb.service.AdminService;
import edu.xj.medicalcheckupweb.util.ProjectToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<User> getUserInfoList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userInfoList= userDao.getUserInfoList();


        return new PageInfo<>(userInfoList);
    }

    @Override
    public int deleteUser(int userId) {
        //先查询用户
        User personInfo = userDao.getPersonInfo(userId);
        //再进行删除
        int result = userDao.deleteUser(userId);
        //最后从redis中清除
        deleteUserFromRedis(personInfo);
        return result;
    }

    /**
     * 将用户信息从redis中清除
     * @param user 用户信息
     */
    private void deleteUserFromRedis(User user){
        redisTemplate.delete(ProjectToken.UPDATE_USER + user.getTelephone());
    }
}
