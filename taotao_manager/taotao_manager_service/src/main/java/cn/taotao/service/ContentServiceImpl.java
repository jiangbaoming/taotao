package cn.taotao.service;

import cn.taotao.mapper.ContentMapper;
import cn.taotao.pojo.Content;
import cn.taotao.utils.EasyUIResult;
import cn.taotao.utils.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public EasyUIResult getContentList(long catId, Integer page, Integer rows) throws Exception {
        //根据category_id查询内容列表
        //分页处理
        PageHelper.startPage(page, rows);
        List<Content> list = contentMapper.selectByCatId(catId);
        //取分页信息
        PageInfo<Content> pageInfo = new PageInfo<>(list);
        EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), list);
        return result;
    }

    @Override
    public TaotaoResult addContent(Content content) throws Exception {
        //把图片信息保存至数据库
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //把内容信息添加到数据库
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delete(Long[] ids) {
        for (Long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult edit(Content content) {
        Date date = new Date();
        content.setUpdated(date);
        contentMapper.updateByPrimaryKeySelective(content);
        return TaotaoResult.ok();
    }

}
