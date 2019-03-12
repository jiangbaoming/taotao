package cn.taotao.sso.service;


import cn.taotao.mapper.UserMapper;
import cn.taotao.pojo.User;
import cn.taotao.utils.CookieUtils;
import cn.taotao.utils.JsonUtils;
import cn.taotao.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster cluster;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String content, Integer type) {
        //创建查询条件
        Map<String, String> params = new HashMap<>();
        //对数据进行校验：1、2、3分别代表username、phone、email
        //用户名校验
        if (1 == type) {
            params.put("username", content);
            //电话校验
        } else if (2 == type) {
            params.put("phone", content);
            //email校验
        } else {
            params.put("email", content);
        }
        //执行查询
        List<User> list = userMapper.getUserByParam(params);
        if (list == null || list.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(User user) {
        user.setUpdated(new Date());
        user.setCreated(new Date());
        //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        List<User> list = userMapper.getUserByParam(params);
        //如果没有此用户名
        if (null == list || list.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        User user = list.get(0);
        //比对密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户之前，把用户对象中的密码清空。
        user.setPassword(null);

       /* 保存用户token
        cluster.set(REDIS_USER_SESSION_KEY+":"+user.getId(), token);
        把用户信息写入redis中的token
        cluster.set(token, JsonUtils.objectToJson(user));
        //设置token的过期时间
        cluster.expire(token, SSO_SESSION_EXPIRE);
        */

        //把用户信息写入redis
        cluster.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        cluster.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        //cookie中保存用户token
        CookieUtils.setCookie(request,response,"TT_TOKEN" , token );
        //返回token
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {

        //根据token从redis中查询用户信息
        String json = cluster.get(REDIS_USER_SESSION_KEY + ":" + token);
        //判断是否为空
        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "此session已经过期，请重新登录");
        }
        //更新过期时间
        cluster.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        //返回用户信息
        return TaotaoResult.ok(JsonUtils.jsonToPojo(json, User.class));
    }

    @Override
    public TaotaoResult logout(String token,HttpServletRequest request, HttpServletResponse response) {
        cluster.del(REDIS_USER_SESSION_KEY + ":" + token);
        CookieUtils.deleteCookie(request, response, "TT_TOKEN");
        return TaotaoResult.ok();
    }
}
