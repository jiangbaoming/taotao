package cn.taotao.order.service;

import cn.taotao.mapper.OrderItemMapper;
import cn.taotao.mapper.OrderMapper;
import cn.taotao.mapper.OrderShippingMapper;
import cn.taotao.order.pojo.Orders;
import cn.taotao.pojo.Order;
import cn.taotao.pojo.OrderItem;
import cn.taotao.pojo.OrderShipping;
import cn.taotao.utils.TaotaoResult;
import jdk.nashorn.internal.ir.LiteralNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisCluster jedisClient;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;
    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;
    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;


    @Override
    public TaotaoResult createOrder(Order order, List<OrderItem> itemList, OrderShipping orderShipping) {
        //向订单表中插入记录
        //获得订单号
        String string = jedisClient.get(ORDER_GEN_KEY);
        if (StringUtils.isBlank(string)) {
            jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        long orderId = jedisClient.incr(ORDER_GEN_KEY);
        //补全pojo的属性
        order.setOrderId(orderId + "");
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        //0：未评价 1：已评价
        order.setBuyerRate(0);
        //向订单表插入数据
        orderMapper.insert(order);
        //插入订单明细
        for (OrderItem tbOrderItem : itemList) {
            //补全订单明细
            //取订单明细id
            long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            tbOrderItem.setId(orderDetailId + "");
            tbOrderItem.setOrderId(orderId + "");
            //向订单明细插入记录
            orderItemMapper.insert(tbOrderItem);
        }
        //插入物流表
        //补全物流表的属性
        orderShipping.setOrderId(orderId + "");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingMapper.insert(orderShipping);
        return TaotaoResult.ok(orderId);
    }

    @Override
    public TaotaoResult info(String orderId) {
        //获取订单
        Order order = orderMapper.selectByPrimaryKey(orderId);
        //封装数据
        Orders orders = (Orders) order;
        //获取订单详情
        List<OrderItem> itemList = orderItemMapper.selectByorderId(orderId);
        //获取物流信息
        OrderShipping orderShipping = orderShippingMapper.selectByPrimaryKey(orderId);
        orders.setOrderItems(itemList);
        orders.setOrderShipping(orderShipping);
        return TaotaoResult.ok(orders);
    }

    @Override
    public TaotaoResult list(Long userID, Long page, Long count) {
        List<Order> orderList = orderMapper.selectByuserID(userID,(page-1)*count,count);
        return TaotaoResult.ok(orderList);
    }

    @Override
    public TaotaoResult changeStatus(Order order) {
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKey(order);
        return TaotaoResult.ok();
    }

}