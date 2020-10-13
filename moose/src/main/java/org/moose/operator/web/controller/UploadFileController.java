package org.moose.operator.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.web.service.UploadFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author taohua
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/upload")
public class UploadFileController {

  @Resource
  private UploadFileService uploadFileService;

  @PostMapping(value = "/file")
  public ResponseResult<Object> uploadFile(@RequestParam("file") MultipartFile file) {
    uploadFileService.uploadFile(file);
    return new ResponseResult<>(Boolean.TRUE, "文件上传");
  }
}
