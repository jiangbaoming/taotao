package cn.taotao.rest.controller;

import cn.taotao.rest.service.ContentService;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/category/{cid}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable Long cid) {
        TaotaoResult result = null;
        try {
            result = contentService.getContentList(cid);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, e.getMessage());
        }
        return result;
    }
}
