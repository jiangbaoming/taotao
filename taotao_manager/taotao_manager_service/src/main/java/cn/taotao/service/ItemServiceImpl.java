package cn.taotao.service;

import cn.taotao.mapper.*;
import cn.taotao.pojo.*;
import cn.taotao.utils.EasyUIResult;
import cn.taotao.utils.ExceptionUtil;
import cn.taotao.utils.IDUtils;
import cn.taotao.utils.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 江宝明
 * @create 2019/1/7
 * @since 1.0.0
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemParamMapper itemParamMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    /**
     * 商品列表
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public EasyUIResult getItemList(Integer page, Integer rows) throws Exception {
        //设置分页
        PageHelper.startPage(page, rows);
        List<Item> list = itemMapper.getItemList();
        //取分页信息
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        //封装结果对象
        EasyUIResult result = new EasyUIResult(total, list);
        return result;
    }

    /**
     * 商品类目模板列表
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public EasyUIResult getItemParamList(Integer page, Integer rows) throws Exception {
        //设置分页
        PageHelper.startPage(page, rows);
        List<ItemParam> list = itemParamMapper.getItemParamList();
        //取分页信息
        PageInfo<ItemParam> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        //封装结果对象
        EasyUIResult result = new EasyUIResult(total, list);
        return result;
    }

    /**
     * 根据parentId获取类目表
     * @param parentId
     * @return
     * @throws Exception
     */
    public List<ItemCat> getItemCatList(Long parentId) throws Exception {
        return itemCatMapper.getItemCatList(parentId);
    }

    /**
     * 添加商品
     * @param item
     * @param itemDesc
     * @return
     */
    public TaotaoResult addItem(Item item, ItemDesc itemDesc, ItemParamItem itemParamItem) {
        try {
            //生成商品id
            //可以使用redis的自增长key，在没有redis之前使用时间+随机数策略生成
            Long itemId = IDUtils.genItemId();
            //补全不完整的字段
            item.setId(itemId);
            item.setStatus((byte) 1);
            Date date = new Date();
            item.setCreated(date);
            item.setUpdated(date);
            //把数据插入到商品表
            itemMapper.insert(item);
            //添加商品描述
            itemDesc.setItemId(itemId);
            itemDesc.setCreated(date);
            itemDesc.setUpdated(date);
            //把数据插入到商品描述表
            itemDescMapper.insert(itemDesc);
            //添加规格参数
            itemParamItem.setCreated(new Date());
            itemParamItem.setUpdated(new Date());
            itemParamItem.setItemId(itemId);
            //添加数据到对应数据表
            itemParamItemMapper.insert(itemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult deleteItemById(Long ids[]) {
        for (Long id:ids) {
            try {
                itemMapper.deleteByPrimaryKey(id);
            }catch (Exception e) {
                e.printStackTrace();
                return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
            }
        }
        return TaotaoResult.ok();
    }

    /**
     * 上下架商品
     * @param ids
     * @param status
     * @return
     */
    @Override
    public TaotaoResult upperAndLowerItem(Long ids[],byte status) {
        for (Long id:ids) {
            try {
                Item item=new Item();
                item.setId(id);
                item.setStatus(status);
                item.setUpdated(new Date());
                itemMapper.updateByPrimaryKeySelective(item);
            } catch (Exception e) {
                e.printStackTrace();
                return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
            }
        }
        return TaotaoResult.ok();

    }

    @Override
    public TaotaoResult getItemDesc(Long id) {
        ItemDesc itemDesc=itemDescMapper.selectByPrimaryKey(id);
        return TaotaoResult.ok(itemDesc);
    }

    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        List<ItemParam> list = itemParamMapper.getItemParamByCid(cid);
        //判断是否查询到结果
        if (list != null && list.size() > 0) {
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(ItemParam itemParam) {
        try {
            itemParam.setCreated(new Date());
            itemParam.setUpdated(new Date());
            itemParamMapper.insert(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult getItemParamItemByitemId(Long itemId) {
        ItemParamItem itemParamItem=itemParamItemMapper.getItemParamItemByitemId(itemId);
        return TaotaoResult.ok(itemParamItem);
    }

    @Override
    public TaotaoResult updateItem(Item item, ItemDesc itemDesc, ItemParamItem itemParamItem) {
        Date date=new Date();
        item.setUpdated(date);
        itemMapper.updateByPrimaryKeySelective(item);
        itemDesc.setUpdated(date);
        itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
        itemParamItem.setUpdated(date);
        itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delateItemParamById(Long ids) {
        itemParamMapper.deleteByPrimaryKey(ids);
        return TaotaoResult.ok();
    }
}
