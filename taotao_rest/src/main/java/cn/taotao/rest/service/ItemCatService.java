package cn.taotao.rest.service;

import cn.taotao.rest.utils.ItemCatResult;

import java.util.List;

public interface ItemCatService {

    ItemCatResult queryAllCategory() throws Exception;

    List<?> getItemCatList(long parentid);
}
