package cn.taotao.portal.service;


import cn.taotao.pojo.Item;

public interface ItemService {
    Item getItemById(Long itemId);

    String getItemDescById(Long itemId);

    String getItemParam(Long itemId);
}
