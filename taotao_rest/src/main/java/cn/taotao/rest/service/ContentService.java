package cn.taotao.rest.service;

import cn.taotao.utils.TaotaoResult;

public interface ContentService {

    TaotaoResult getContentList(long cid) throws Exception;
}
