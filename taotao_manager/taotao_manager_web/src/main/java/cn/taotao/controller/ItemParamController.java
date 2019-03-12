package cn.taotao.controller;

import cn.taotao.pojo.ItemParam;
import cn.taotao.service.ItemService;
import cn.taotao.utils.EasyUIResult;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品模板管理
 */
@RestController
@RequestMapping("item/param")
public class ItemParamController {
    @Autowired
    private ItemService itemService;


    @RequestMapping("list")
    public EasyUIResult getItemParamList(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "30") Integer rows) throws Exception {
        //查询商品列表
        EasyUIResult result = itemService.getItemParamList(page, rows);
        return result;
    }

    @RequestMapping("query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
        TaotaoResult result = itemService.getItemParamByCid(itemCatId);
        return result;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
        //创建pojo对象
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        TaotaoResult result = itemService.insertItemParam(itemParam);
        return result;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteItemParamById(Long ids){
        TaotaoResult result = itemService.delateItemParamById(ids);
        return result;
    }
}
