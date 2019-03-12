package cn.taotao.rest.service;

import cn.taotao.mapper.ContentMapper;
import cn.taotao.pojo.Content;
import cn.taotao.utils.JsonUtils;
import cn.taotao.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private JedisCluster cluster;
    @Value("${TB_CONTENT_KEY}")
    String TB_CONTENT_KEY;

    @Override
    public TaotaoResult getContentList(long cid) throws Exception {
        //缓存逻辑，先判断缓存中是否有内容
        try {
            String contentStr = cluster.hget(TB_CONTENT_KEY, cid + "");
            if (!StringUtils.isBlank(contentStr)) {
                //把json字符串转换成对象列表
                List<Content> list = JsonUtils.jsonToList(contentStr, Content.class);
                //返回结果
                return TaotaoResult.ok(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //缓存不能影响正常逻辑
        }
        List<Content> list = contentMapper.selectByCatId(cid);
        //把结果添加到redis数据库中
        try {
            cluster.hset(TB_CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
            //缓存不能影响正常逻辑
        }
        return TaotaoResult.ok(list);
    }
}
