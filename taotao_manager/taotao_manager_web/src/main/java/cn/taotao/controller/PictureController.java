package cn.taotao.controller;

import cn.taotao.service.PictureService;
import cn.taotao.utils.PictureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *图片上传管理
 * @author 江宝明
 * @create 2019/1/9
 * @since 1.0.0
 */
@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public PictureResult upload(MultipartFile uploadFile) {
        PictureResult result = pictureService.uploadPicture(uploadFile);
        return result;
    }

}
