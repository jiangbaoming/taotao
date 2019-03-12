package cn.taotao.portal.controller;


import cn.taotao.portal.pojo.CartItem;
import cn.taotao.portal.service.CartService;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId,
                              @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
        return "redirect:/cart/cartSuccess.html";
    }


    @RequestMapping("/cartSuccess")
    public String cartSuccess() {
        return "cartSuccess";
    }


    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> list = cartService.getCartItemList(request, response);
        model.addAttribute("cartList", list);
        return "cart";
    }

    @RequestMapping("/update/{itemId}/{num}")
    public String update(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = cartService.update(itemId, num, request, response);
        return "redirect:/cart/cart.html";
    }

    @RequestMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = cartService.delete(itemId, request, response);
        return "redirect:/cart/cart.html";
    }
}
