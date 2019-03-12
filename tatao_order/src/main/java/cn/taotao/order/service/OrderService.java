package cn.taotao.order.service;

import cn.taotao.pojo.Order;
import cn.taotao.pojo.OrderItem;
import cn.taotao.pojo.OrderShipping;
import cn.taotao.utils.TaotaoResult;

import java.util.List;

public interface OrderService {
    /**
     * 创建订单
     * @param order
     * @param itemList
     * @param orderShipping
     * @return
     */
    TaotaoResult createOrder(Order order, List<OrderItem> itemList, OrderShipping orderShipping);

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    TaotaoResult info(String orderId);

    /**
     * 根据用户获取订单列表
     * @param userID
     * @param page
     * @param count
     * @return
     */
    TaotaoResult list(Long userID, Long page, Long count);

    /**
     * 更改订单状态
     * @param order
     * @return
     */
    TaotaoResult changeStatus(Order order);
}
