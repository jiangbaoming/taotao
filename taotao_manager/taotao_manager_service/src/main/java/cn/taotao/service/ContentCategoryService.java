package cn.taotao.service;

import cn.taotao.utils.EasyUITreeNode;
import cn.taotao.utils.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    public List<EasyUITreeNode> getContentCategoryListByPid(long parentid) throws Exception ;

    TaotaoResult addNode(Long parentid, String name);

    TaotaoResult update(Long id, String name);
    public TaotaoResult deleteContentCategory(Long id);
}
