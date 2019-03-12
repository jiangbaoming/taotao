package cn.taotao.order.pojo;

import cn.taotao.pojo.Order;
import cn.taotao.pojo.OrderItem;
import cn.taotao.pojo.OrderShipping;

import java.util.List;

public class Orders extends Order {

    private List<OrderItem> orderItems;
    private OrderShipping orderShipping;
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public OrderShipping getOrderShipping() {
        return orderShipping;
    }
    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }


}
