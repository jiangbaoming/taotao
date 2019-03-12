package cn.taotao.controller;

import cn.taotao.service.ContentCategoryService;
import cn.taotao.utils.EasyUITreeNode;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id", defaultValue="0")long parentid) throws Exception {
        List<EasyUITreeNode> list = contentCategoryService.getContentCategoryListByPid(parentid);
        return list;
    }
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult addNode(Long parentId, String name) throws Exception {
        TaotaoResult result = contentCategoryService.addNode(parentId, name);
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult update(Long id, String name) throws Exception {
        TaotaoResult result = contentCategoryService.update(id, name);
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){
        TaotaoResult result=contentCategoryService.deleteContentCategory(id);
        return result;
    }
}
