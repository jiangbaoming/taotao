package cn.taotao.portal.service.impl;

import cn.taotao.pojo.Order;
import cn.taotao.portal.service.OrderService;
import cn.taotao.utils.HttpClientUtil;
import cn.taotao.utils.JsonUtils;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;


    @Override
    public String createOrder(Order order) {
        //调用taotao-order的服务提交订单。
        String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
        //把json转换成taotaoResult
        System.err.println(">>>>>>>>>>>>>>>>"+json+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        TaotaoResult taotaoResult = TaotaoResult.format(json);
        if (taotaoResult.getStatus() == 200) {
            System.out.println("++++++++++++++++++++"+taotaoResult.getData());
            Integer orderId = (Integer) taotaoResult.getData();
            return orderId.toString();
        }
        return "";
    }
}
