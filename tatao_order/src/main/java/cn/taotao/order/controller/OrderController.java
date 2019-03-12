package cn.taotao.order.controller;

import cn.taotao.order.pojo.Orders;
import cn.taotao.order.service.OrderService;
import cn.taotao.pojo.Order;
import cn.taotao.utils.ExceptionUtil;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createOrder(@RequestBody Orders order) {
        try {
            TaotaoResult result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("/info/{orderId}")
    @ResponseBody
    public TaotaoResult info(@PathVariable String orderId) {
        try {
            TaotaoResult result = orderService.info(orderId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }


    @RequestMapping("/list/{userID}/{page}/{count}")
    @ResponseBody
    public TaotaoResult list(@PathVariable Long userID,@PathVariable() Long page,@PathVariable Long count) {
        try {
            TaotaoResult result = orderService.list(userID,page,count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }


    @RequestMapping(" /changeStatus")
    @ResponseBody
    public TaotaoResult changeStatus(@RequestBody()Order order) {
        try {
            TaotaoResult result = orderService.changeStatus(order);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
