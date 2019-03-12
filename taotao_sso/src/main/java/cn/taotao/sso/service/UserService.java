package cn.taotao.sso.service;


import cn.taotao.pojo.User;
import cn.taotao.utils.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    /**
     * 数据检查
     * @param content
     * @param type
     * @return
     */
    TaotaoResult checkData(String content, Integer type);

    /**
     * 用户注册
     * @param user
     * @return
     */
    TaotaoResult createUser(User user);

    /**
     * 用户登陆
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据token从redis中查询用户信息
     * @param token
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 用户安全退出
     * @param token
     * @return
     */
    TaotaoResult logout(String token,HttpServletRequest request, HttpServletResponse response);
}
