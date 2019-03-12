package cn.taotao.portal.pojo;

import cn.taotao.pojo.Item;

public class ItemInfo extends Item {

    public String[] getImages() {
        String image = getImage();
        if (image != null) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}