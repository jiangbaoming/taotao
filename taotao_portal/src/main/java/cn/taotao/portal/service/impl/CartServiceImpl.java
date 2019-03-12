package cn.taotao.portal.service.impl;

import cn.taotao.pojo.Item;
import cn.taotao.portal.pojo.CartItem;
import cn.taotao.portal.service.CartService;
import cn.taotao.utils.CookieUtils;
import cn.taotao.utils.HttpClientUtil;
import cn.taotao.utils.JsonUtils;
import cn.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITME_INFO_URL}")
    private String ITME_INFO_URL;


    /**
     * 添加购物车商品
     * <p>Title: addCartItem</p>
     * <p>Description: </p>
     *
     * @param itemId
     * @param num
     * @return
     * @see com.taotao.portal.service.CartService#addCartItem(long, int)
     */
    @Override
    public TaotaoResult addCartItem(long itemId, int num,
                                    HttpServletRequest request, HttpServletResponse response) {
        //取商品信息
        CartItem cartItem = null;
        //取购物车商品列表
        List<CartItem> itemList = getCartItemList(request);
        //判断购物车商品列表中是否存在此商品
        for (CartItem cItem : itemList) {
            //如果存在此商品
            if (cItem.getId() == itemId) {
                //增加商品数量
                cItem.setNum(cItem.getNum() + num);
                cartItem = cItem;
                break;
            }
        }
        if (cartItem == null) {
            cartItem = new CartItem();
            //根据商品id查询商品基本信息。
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
            //把json转换成java对象
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, Item.class);
            if (taotaoResult.getStatus() == 200) {
                Item item = (Item) taotaoResult.getData();
                cartItem.setId(item.getId());
                cartItem.setTitle(item.getTitle());
                cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cartItem.setNum(num);
                cartItem.setPrice(item.getPrice());
            }
            //添加到购物车列表
            itemList.add(cartItem);
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);

        return TaotaoResult.ok();
    }

    /**
     * 从cookie中取商品列表
     * <p>Title: getCartItemList</p>
     * <p>Description: </p>
     *
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request) {
        //从cookie中取商品列表
        String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
        System.out.println(">>>>>>>>>>>>>>>>>"+cartJson);
        if (cartJson == null) {
            return new ArrayList<>();
        }
        //把json转换成商品列表
        try {
            List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 展示购物车列表
     *
     * @param request
     * @param response
     * @return
     */

    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList = getCartItemList(request);
        return itemList;
    }

    @Override
    public TaotaoResult update(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList = getCartItemList(request);
        for (CartItem cartItem : itemList) {
            if (cartItem.getId() == itemId) {
                cartItem.setNum(num);
            }
        }
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delete(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车商品列表
        List<CartItem> itemList = getCartItemList(request);
        //从列表中找到此商品
        for (CartItem cartItem : itemList) {
            if (cartItem.getId() == itemId) {
                itemList.remove(cartItem);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
        return TaotaoResult.ok();
    }

    /**
     * 批量删除
     * @param itemIds
     * @param request
     * @param response
     * @return
     */
    private TaotaoResult deleteByitemIds(Long[] itemIds, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList = getCartItemList(request);
        Map<Long, CartItem> ietmMap = new HashMap<>();
        for (CartItem cartItem : itemList) {
            ietmMap.put(cartItem.getId(), cartItem);
        }
        for (Long itemId : itemIds) {
            ietmMap.remove(itemId);
        }
        List<CartItem> newItemList = new ArrayList<>();
        for (CartItem value : ietmMap.values()) {
            newItemList.add(value);
        }
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(newItemList), true);
        return TaotaoResult.ok();
    }

}