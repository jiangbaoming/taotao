package cn.taotao.controller;

import cn.taotao.service.ItemService;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品描述管理
 */
@RestController
@RequestMapping("rest/item")
public class ItemDescController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("query/item/desc/{id}")
    public TaotaoResult getItemDesc(@PathVariable Long id){
        TaotaoResult itemDestRuselt=itemService.getItemDesc(id);
        return itemDestRuselt;
    }
}
