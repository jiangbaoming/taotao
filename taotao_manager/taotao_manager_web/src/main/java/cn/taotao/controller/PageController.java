package cn.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 〈一句话功能简述〉<br>
 * 页面跳转管理
 *
 * @author 江宝明
 * @create 2019/1/6
 * @since 1.0.0
 */
@Controller
public class PageController {

    /**
     * 跳转进首页
     *
     * @return
     */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    /**
     * 跳转到其他页面
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
