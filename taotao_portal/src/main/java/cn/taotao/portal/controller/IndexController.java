package cn.taotao.portal.controller;

import cn.taotao.portal.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private AdService adService;

    @RequestMapping("/index.html")
    public Model showIndex(Model model) throws Exception {
        String adResult = adService.getAdItemList();
        model.addAttribute("ad1", adResult);
        return model;
    }
}
