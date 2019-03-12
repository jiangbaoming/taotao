package cn.taotao.controller;

import cn.taotao.pojo.Item;
import cn.taotao.pojo.ItemDesc;
import cn.taotao.pojo.ItemParamItem;
import cn.taotao.service.ItemService;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 修改商品信息管理
 */
@Controller
@RequestMapping("rest/item")
public class RestItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 通过ID删除商品
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public TaotaoResult deleteItemById(Long ids[]) throws Exception {
        TaotaoResult deleteResult=itemService.deleteItemById(ids);
        return deleteResult;
    }
    /**
     * 通过ID下架商品
     * @param ids
     * @return
     */
    @RequestMapping("instock")
    @ResponseBody
    public TaotaoResult instockItemByid(Long ids[]) throws Exception {
        TaotaoResult instockResult=itemService.upperAndLowerItem(ids,(byte) 2);
        return instockResult;
    }
    /**
     * 通过ID上架商品
     * @param ids
     * @return
     */
    @RequestMapping("reshelf")
    @ResponseBody
    public TaotaoResult reshelfItemByid(Long ids[]) throws Exception {
        TaotaoResult instockResult=itemService.upperAndLowerItem(ids, (byte) 1);
        return instockResult;
    }

    /**
     * 通过商品id查询规格参数
     * @param itemId
     * @return
     */
    @RequestMapping("param/item/query/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParamItemByitemId(Long itemId)throws Exception{
        TaotaoResult taotaoResult=itemService.getItemParamItemByitemId(itemId);
        return taotaoResult;
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateItem(Item item, String desc, String itemParams,Long itemParamId) throws Exception {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        ItemParamItem itemParamItem =new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        itemParamItem.setId(itemParamId);
        TaotaoResult result = itemService.updateItem(item, itemDesc,itemParamItem);
        return result;
    }
}
