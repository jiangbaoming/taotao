package cn.taotao.service;

import cn.taotao.utils.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: Administrator
 * @Date: 2019/1/9 0:12
 * @Description:
 */
public interface PictureService {
    PictureResult uploadPicture(MultipartFile uploadFile);
}
