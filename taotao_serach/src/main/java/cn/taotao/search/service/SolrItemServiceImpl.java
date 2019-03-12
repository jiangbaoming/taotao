package cn.taotao.search.service;

import cn.taotao.search.mapper.SolrItemMapper;
import cn.taotao.search.pojo.SolrItem;
import cn.taotao.utils.ExceptionUtil;
import cn.taotao.utils.TaotaoResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolrItemServiceImpl implements SolrItemService {

    @Autowired
    private SolrItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importAllItems() {
        try {
            //查询商品列表
            List<SolrItem> list = itemMapper.getItemList();
            //把商品信息写入索引库
            for (SolrItem item : list) {
                //创建一个SolrInputDocument对象
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSell_point());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategory_name());
                document.setField("item_desc", item.getItem_des());
                //写入索引库
                solrServer.add(document);
            }
            //提交修改
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

}
