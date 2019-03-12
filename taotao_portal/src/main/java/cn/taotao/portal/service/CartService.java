package cn.taotao.portal.service;

import cn.taotao.portal.pojo.CartItem;
import cn.taotao.utils.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    /**
     * 添加商品到购物车
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    TaotaoResult addCartItem(long itemId, int num,
                             HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取购物车列表
     * @param request
     * @param response
     * @return
     */
    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    TaotaoResult update(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

    /**
     * 删除购物车商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    TaotaoResult delete(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
