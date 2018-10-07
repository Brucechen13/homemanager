package com.cc.homeserver.controller;

import com.cc.homeserver.handle.BusinessException;
import com.cc.homeserver.handle.BusinessException.ResultEnum;
import com.cc.homeserver.utils.ImageUtil;
import com.cc.homeserver.utils.JsonResponse;
import com.cc.homeserver.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/images")
public class ImagesController {
    private static final transient Logger logger = LoggerFactory.getLogger(ImagesController.class);

    @Value("${img.location}")
    private String location;

    @RequestMapping
    public String index() {
        return "hello world";
    }

    // @RequestParam 简单类型的绑定，可以出来get和post
    @RequestMapping(value = "/map")
    public HashMap<String, Object> get(@RequestParam String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello world");
        map.put("name", name);
        return map;
    }

    @PutMapping("/article/img/upload")
    public JsonResponse uploadImg(@RequestParam("editormd-image-file") MultipartFile[] multipartFiles)  throws Exception{
        for(MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
                throw new BusinessException(ResultEnum.IMG_NOT_EMPTY);
            }
            String contentType = multipartFile.getContentType();
            if (!contentType.contains("")) {
                throw new BusinessException(ResultEnum.IMG_FORMAT_ERROR);
            }
            String root_fileName = multipartFile.getOriginalFilename();
            logger.info("上传图片:name={},type={}", root_fileName, contentType);

            //获取路径
            String filePath = location;
            logger.info("图片保存路径={}", filePath);
            String file_name = null;
            try {
                file_name = ImageUtil.saveImg(multipartFile, filePath);
            } catch (IOException e) {
                throw new BusinessException(ResultEnum.SAVE_IMG_ERROE);
            }
        }
        return JsonResponse.succ("上传成功");
    }


}
