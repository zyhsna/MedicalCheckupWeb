package edu.xj.medicalcheckupweb.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.xj.medicalcheckupweb.POJO.Doctor;
import edu.xj.medicalcheckupweb.POJO.User;
import edu.xj.medicalcheckupweb.dao.UserDao;
import edu.xj.medicalcheckupweb.service.UserService;
import edu.xj.medicalcheckupweb.util.CustomHttpSession;
import edu.xj.medicalcheckupweb.util.ProjectToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;


/**
 * @author zyhsna
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 通过手机号依次查询用户、医师、管理员数据库，看是否有符合的用户
     *
     * @param telephone 手机号
     * @return 0代表存在该用户，-1代表手机号不存在也就是用户未注册
     */
    public int findByTel(String telephone) {
        if (userDao.findUserByTel(telephone) > 0) {
            return 0;
        }
        return -1;
    }


    /**
     * 用户登录Service
     *
     * @param telephone 手机号
     * @param password  密码
     * @return -1代表不存在该用户 -2代表用户手机号和密码不匹配 其他大于0的均为用户的ID
     */
    @Override
    public int login(String telephone, String password) {
        //先到redis中查询
        Object alreadyLoginUserInfo = redisTemplate.opsForValue().get("user:login:" + telephone);
        if (alreadyLoginUserInfo != null) {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.convertValue(alreadyLoginUserInfo, User.class);
            if (user.getPassword().equals(password)) {
                updateToSession(user);
                return user.getUserId();
            } else {
                //redis中password不匹配
                return -2;
            }
        }
        //查询是否有此人
        int findUserResult = findByTel(telephone);
        if (findUserResult == -1) {
            //查无此人
            return -1;
        }
        //没有结果再到MySQL中查询
        User user = userDao.userLogin(telephone, password);
        if (user != null) {
            //将数据更新到redis当中
            updateLoginUserToRedis(user);
            updateToSession(user);
            return user.getUserId();
        } else {
            return -2;
        }
    }

    /**
     * service层的用户注册接口
     *
     * @param registerUserInfo
     * @return 0 注册成功，1 手机号已经存在，2 注册失败
     */
    @Override
    public int register(User registerUserInfo) {
        if (findByTel(registerUserInfo.getTelephone()) == 0) {
            return 1;
        }
        int result = userDao.register(registerUserInfo);
        if (result == 1) {
            return 0;
        } else {
            return 2;
        }
    }


    @Override
    public int changePassword(String oldPassword, String newPassword, int userId) {
        HttpSession httpSession = CustomHttpSession.getHttpSession();
        Object sessionAttribute = httpSession.getAttribute(CustomHttpSession.USER_TOKEN + userId);
        User user = (User) sessionAttribute;
        if (!oldPassword.equals(user.getPassword())) {
            //用户旧密码不匹配
            return 2;
        }
        try {
            int result = userDao.changePassword(newPassword, userId);
            if (result == 1) {
                //修改成功,将redis缓存删掉并且session重置重新要求登录
                deleteInfoAfterChangingPassword(user);
                httpSession.removeAttribute(CustomHttpSession.USER_TOKEN + userId);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
        return 3;
    }

    @Override
    public User getPersonInfo(int userId) {
        if (findUserByUserId(userId)) {
            return userDao.getPersonInfo(userId);
        } else {
            return null;
        }
    }

    /**
     * service层  用户登录成功后将相关信息更新到redis中，方便下次访问
     *
     * @param user 登录成功的相关用户信息
     */
    private void updateLoginUserToRedis(User user) {
        //将数据更新到redis当中，设置TTL时间为24小时
        redisTemplate.opsForValue().set(ProjectToken.UPDATE_USER + user.getTelephone(), user, 60 * 24, TimeUnit.MINUTES);
    }

    /**
     * 根据用户ID来查询是否存在这个用户
     *
     * @param userId 用户id
     * @return 存在为true 不存在为false
     */
    private boolean findUserByUserId(int userId) {
        int result = userDao.findUserByUserId(userId);
        return result > 0;
    }


    /**
     * service层 将登录后的结果放到session中，方便后续操作时进行验证
     *
     * @param user 登录成功的相关用户信息
     */
    private void updateToSession(User user) {
        HttpSession session = CustomHttpSession.getHttpSession();
        String userToken = CustomHttpSession.USER_TOKEN + user.getUserId();
        session.setAttribute(userToken, user);
    }

    /**
     * 在修改密码成功之后将redis中数据清除掉
     *
     * @param user user信息
     */
    private void deleteInfoAfterChangingPassword(User user) {
        redisTemplate.delete(ProjectToken.UPDATE_USER + user.getTelephone());
    }
}
