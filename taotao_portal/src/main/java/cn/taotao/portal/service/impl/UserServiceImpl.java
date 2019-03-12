package cn.taotao.portal.service.impl;


import cn.taotao.pojo.User;
import cn.taotao.portal.service.UserService;
import cn.taotao.utils.HttpClientUtil;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;

    @Override
    public User getUserByToken(String token) {
        try {
            //调用sso系统的服务，根据token取用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
            //把json转换成TaotaoREsult
            TaotaoResult result = TaotaoResult.formatToPojo(json, User.class);
            if (result.getStatus() == 200) {
                User user = (User) result.getData();
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
