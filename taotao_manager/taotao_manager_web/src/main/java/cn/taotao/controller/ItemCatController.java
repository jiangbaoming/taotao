package cn.taotao.controller;

import cn.taotao.pojo.ItemCat;
import cn.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品类目管理
 */
@Controller
@RequestMapping("item/cat")
public class ItemCatController {
    @Autowired
    private ItemService itemService;


    /**
     * 获取上平类目列表
     * @param parentId 类目节点parentId
     * @return node节点集合
     * @throws Exception
     */
    @RequestMapping("list")
    @ResponseBody
    //设置相应的内容为json数据
    public List categoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) throws Exception {
        List catList = new ArrayList();
        //查询数据库
        List<ItemCat> list = itemService.getItemCatList(parentId);
        for (ItemCat itemCat : list) {
            Map node = new HashMap<>();
            node.put("id", itemCat.getId());
            node.put("text", itemCat.getName());
            //如果是父节点的话就设置成关闭状态，如果是叶子节点就是open状态
            node.put("state", itemCat.getIsParent() ? "closed" : "open");
            catList.add(node);
        }
        return catList;
    }
}
