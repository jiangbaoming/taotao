package cn.taotao.portal.service;


import cn.taotao.pojo.User;

public interface UserService {

    /**
     * 从token中获取用户信息
     *
     * @param token
     * @return
     */
    User getUserByToken(String token);
}
