package cn.taotao.service;

import cn.taotao.pojo.Content;
import cn.taotao.utils.EasyUIResult;
import cn.taotao.utils.TaotaoResult;

public interface ContentService {

    EasyUIResult getContentList(long catId, Integer page, Integer rows) throws Exception;

    TaotaoResult addContent(Content content) throws Exception;

    TaotaoResult delete(Long[] ids);

    TaotaoResult edit(Content content);
}
