package cn.taotao.service;

import cn.taotao.pojo.*;
import cn.taotao.utils.EasyUIResult;
import cn.taotao.utils.TaotaoResult;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019/1/7 14:30
 * @Description:
 */
public interface ItemService {

    public EasyUIResult getItemList(Integer page, Integer rows) throws Exception;

    public EasyUIResult getItemParamList(Integer page, Integer rows) throws Exception;

    public List<ItemCat> getItemCatList(Long parentId) throws Exception;

    public TaotaoResult addItem(Item item, ItemDesc itemDesc, ItemParamItem itemParamItem) throws Exception;

    public TaotaoResult deleteItemById(Long ids[]) throws Exception;

    public TaotaoResult upperAndLowerItem(Long ids[], byte status) throws Exception;

    public TaotaoResult getItemDesc(Long id);

    public TaotaoResult getItemParamByCid(long cid);

    public TaotaoResult insertItemParam(ItemParam itemParam);

    public TaotaoResult getItemParamItemByitemId(Long itemId);

    public TaotaoResult updateItem(Item item, ItemDesc itemDesc, ItemParamItem itemParamItem);

    public TaotaoResult delateItemParamById(Long ids);
}
