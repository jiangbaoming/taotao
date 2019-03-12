package cn.taotao.mapper;

import cn.taotao.pojo.ContentCategory;

import java.util.List;

public interface ContentCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ContentCategory record);

    int insertSelective(ContentCategory record);

    ContentCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContentCategory record);

    int updateByPrimaryKey(ContentCategory record);

    List<ContentCategory>getContentCategoryListByPid(long parentid);

    int deleteByPid(Long parentId);
}