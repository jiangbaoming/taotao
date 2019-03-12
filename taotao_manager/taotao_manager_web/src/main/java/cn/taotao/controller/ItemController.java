package cn.taotao.controller;

import cn.taotao.pojo.*;
import cn.taotao.service.ItemService;
import cn.taotao.utils.EasyUIResult;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 〈一句话功能简述〉<br>
 * 〈商品〉
 *商品管理
 * @author 江宝明
 * @create 2019/1/7
 * @since 1.0.0
 */
@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("list")
    @ResponseBody
    //设置相应的内容为json数据
    public EasyUIResult getItemList(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "30") Integer rows) throws Exception {
        //查询商品列表
        EasyUIResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult addItem(Item item, String desc, String itemParams) throws Exception {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        ItemParamItem itemParamItem =new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        TaotaoResult result = itemService.addItem(item, itemDesc,itemParamItem);
        return result;
    }

}
