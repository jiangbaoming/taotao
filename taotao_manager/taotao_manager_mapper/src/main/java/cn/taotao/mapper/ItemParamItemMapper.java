package cn.taotao.mapper;

import cn.taotao.pojo.ItemParamItem;

import java.util.List;

public interface ItemParamItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemParamItem record);

    int insertSelective(ItemParamItem record);

    ItemParamItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemParamItem record);

    int updateByPrimaryKeyWithBLOBs(ItemParamItem record);

    int updateByPrimaryKey(ItemParamItem record);

    ItemParamItem getItemParamItemByitemId(Long itemId);

    List<ItemParamItem> getItemParamItem(Long itemId);
}