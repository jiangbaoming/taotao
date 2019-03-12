package cn.taotao.rest.service;


import cn.taotao.mapper.ItemCatMapper;
import cn.taotao.pojo.ItemCat;
import cn.taotao.rest.utils.ItemCatResult;
import cn.taotao.rest.utils.CatNode;
import cn.taotao.utils.JsonUtils;
import cn.taotao.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {


    @Autowired
    private ItemCatMapper itemCatMapper;
    @Value("${TB_CAT_KEY}")
    private String TB_CAT_KEY;
    @Autowired
    private JedisCluster cluster;


    @Override
    public ItemCatResult queryAllCategory() throws Exception {
        //取
        try {
            String itemList = cluster.hget(TB_CAT_KEY, "");
            if (!StringUtils.isBlank(itemList)) {
                return JsonUtils.jsonToPojo(itemList, ItemCatResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查
        ItemCatResult result = new ItemCatResult();
        result.setData(getItemCatList(0));
        //存
        try {
            cluster.hset(TB_CAT_KEY, "", JsonUtils.objectToJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<?> getItemCatList(long parentid) {
        //查询parentid为0的分类信息
        List<ItemCat> list = itemCatMapper.getItemCatList(parentid);
        List dataList = new ArrayList();
        int i = 0;
        for (ItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                catNode.setUrl("/category/" + tbItemCat.getId() + ".html");
                catNode.setName(tbItemCat.getName());
                //递归调用
                catNode.setItem(getItemCatList(tbItemCat.getId()));
                //添加到列表
                dataList.add(catNode);
            } else {
                String catItem = "/item/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
                dataList.add(catItem);
            }
            i++;
            if ((i > 13)) {
                break;
            }
        }
        return dataList;
    }
}
