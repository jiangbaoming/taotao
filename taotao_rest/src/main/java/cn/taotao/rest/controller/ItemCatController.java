package cn.taotao.rest.controller;

import cn.taotao.rest.service.ItemCatService;
import cn.taotao.rest.utils.ItemCatResult;
import cn.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 首页分类列表接口
     *
     * @param callback
     * @return
     * @throws Exception
     */
    @RequestMapping("itemcat/list")
    @ResponseBody
    public Object queryAll(String callback) throws Exception {
        //查询分类列表
        ItemCatResult result = itemCatService.queryAllCategory();
        //包装jsonp
        MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
        //设置包装的回调方法名
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }


   /* fastjson jsonp 方法
   @RequestMapping("itemcat/list")
   @ResponseBody
    public JSONPObject queryAll(String callback) throws Exception {
        //查询分类列表
        ItemCatResult result = itemCatService.queryAllCategory();
        //设置包装的回调方法名
        JSONPObject jsonpObject= new JSONPObject(callback);
        //包装jsonp
        jsonpObject.addParameter(result);
        return  jsonpObject;
    }*/
}
