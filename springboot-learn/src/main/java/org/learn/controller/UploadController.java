package org.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.AjaxResult;
import org.learn.utils.QiniuClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class UploadController {

    @RequestMapping(value = "/api/v1/upload/file", method = {RequestMethod.POST})
    @ResponseBody
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        byte[] imgBytes = new byte[1024];
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            imgBytes = multipartFile.getBytes();
            // MultipartFile 转 字节数组
            String imgUrl = QiniuClientUtil.upLoadImage(imgBytes);
            map.put("imgUrl", imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success("", map);
    }
}
