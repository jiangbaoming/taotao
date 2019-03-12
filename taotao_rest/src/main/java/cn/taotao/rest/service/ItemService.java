package cn.taotao.rest.service;


import cn.taotao.utils.TaotaoResult;

public interface ItemService {
    TaotaoResult getItemBaseInfo(long itemId);

    TaotaoResult getItemDesc(long itemId);

    TaotaoResult getItemParam(long itemId);
}
