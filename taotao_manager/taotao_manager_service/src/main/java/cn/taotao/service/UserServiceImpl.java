package cn.taotao.service;

import cn.taotao.mapper.UserMapper;
import cn.taotao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 江宝明
 * @create 2019/1/6
 * @since 1.0.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long uid) {
        User user=userMapper.selectByPrimaryKey(uid);
        return user;
    }

    public List<User> getUserList() {
        List<User> userList=userMapper.getUserList();
        return userList;
    }
}
