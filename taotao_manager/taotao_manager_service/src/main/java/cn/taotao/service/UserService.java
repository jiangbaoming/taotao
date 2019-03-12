package cn.taotao.service;

import cn.taotao.pojo.User;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019/1/6 19:31
 * @Description:
 */
public interface UserService {
    /**
     * 根据id获取user
     * @param uid
     * @return
     */
    User getUserById(Long uid);

    List<User> getUserList();
}
