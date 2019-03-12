package cn.taotao.service;

import cn.taotao.mapper.ContentCategoryMapper;
import cn.taotao.pojo.ContentCategory;
import cn.taotao.utils.EasyUITreeNode;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryListByPid(long parentid) throws Exception {

        //根据parentid查询内容分类列表
        List<ContentCategory> list = contentCategoryMapper.getContentCategoryListByPid(parentid);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (ContentCategory contentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            //判断是否是父节点
            if (contentCategory.getIsParent()) {
                node.setState("closed");
            } else {
                node.setState("open");
            }
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult addNode(Long parentid, String name) {
        Date date = new Date();
        //添加一个新节点
        //创建一个节点对象
        ContentCategory node = new ContentCategory();
        node.setName(name);
        node.setParentId(parentid);
        node.setIsParent(false);
        node.setCreated(date);
        node.setUpdated(date);
        node.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        node.setStatus(1);
        //插入新节点。需要返回主键
        contentCategoryMapper.insert(node);
        //判断如果父节点的isparent不是true修改为true
        //取父节点的内容
        ContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentid);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        //把新节点返回
        return TaotaoResult.ok(node);
    }

    @Override
    public TaotaoResult update(Long id, String name) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCategory(Long id) {

        ContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        //判断删除的节点是否为父类
        if(contentCategory.getIsParent()){
            List<ContentCategory> list=getContentCategoryListByParentId(id);
            //递归删除
            for(ContentCategory tbContentCategory : list){
                deleteContentCategory(tbContentCategory.getId());
            }
        }
        //判断父类中是否还有子类节点，没有的话，把父类改成子类
        if(getContentCategoryListByParentId(contentCategory.getParentId()).size()==1)
        {
            ContentCategory parentCategory=contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
            parentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }
        contentCategoryMapper.deleteByPrimaryKey(id);
        return  TaotaoResult.ok();
    }

    /**
     * 获取该节点下的孩子节点
     * @param id
     * @return 父节点下的所有孩子节点
     */
    //通过父节点id来查询所有子节点，这是抽离出来的公共方法
    private List<ContentCategory> getContentCategoryListByParentId(Long pid){
        List<ContentCategory> list = contentCategoryMapper.getContentCategoryListByPid(pid);
        return list;
    }

}
