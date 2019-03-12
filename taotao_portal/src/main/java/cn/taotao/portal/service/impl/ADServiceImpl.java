package cn.taotao.portal.service.impl;

import cn.taotao.pojo.Content;
import cn.taotao.portal.pojo.ADItem;
import cn.taotao.portal.service.AdService;
import cn.taotao.utils.HttpClientUtil;
import cn.taotao.utils.JsonUtils;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ADServiceImpl implements AdService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;

    @Override
    public String getAdItemList() throws Exception {
        //调用服务层的服务查询大广告位的数据
        String result = HttpClientUtil.doGet(REST_BASE_URL + INDEX_AD1_URL);
        //把json数据转换成对象
        TaotaoResult taotaoResult = TaotaoResult.formatToList(result, Content.class);
        List<ADItem> itemList = new ArrayList<>();
        if (taotaoResult.getStatus() == 200 ) {
            List<Content> contentList = (List<Content>) taotaoResult.getData();
            for (Content tbContent : contentList) {
                ADItem item = new ADItem();
                item.setHeight(240);
                item.setWidth(670);
                item.setSrc(tbContent.getPic());
                item.setHeightB(240);
                item.setWidthB(550);
                item.setSrcB(tbContent.getPic2());
                item.setAlt(tbContent.getTitleDesc());
                item.setHref(tbContent.getUrl());
                itemList.add(item);
            }

        }
        return JsonUtils.objectToJson(itemList);
    }
}
