package cn.taotao.controller;

import cn.taotao.pojo.Content;
import cn.taotao.service.ContentService;
import cn.taotao.utils.EasyUIResult;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIResult getContentList(Long categoryId, Integer page, Integer rows) throws Exception {
        EasyUIResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }


    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult addContent(Content content) throws Exception {
        TaotaoResult result = contentService.addContent(content);
        return result;
    }

    @RequestMapping("/content/delete")
    @ResponseBody
    public TaotaoResult delete(Long[] ids) throws Exception {
        TaotaoResult result = contentService.delete(ids);
        return result;
    }

    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public TaotaoResult edit(Content content) throws Exception {
        TaotaoResult result = contentService.edit(content);
        return result;
    }
}