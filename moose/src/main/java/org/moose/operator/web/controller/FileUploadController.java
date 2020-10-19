package org.moose.operator.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.R;
import org.moose.operator.web.service.FileUploadService;
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
@RequestMapping(value = "/api/v1/file")
public class FileUploadController {

  @Resource
  private FileUploadService fileUploadService;

  @PostMapping(value = "/upload")
  public R<Object> uploadFile(@RequestParam("files") MultipartFile[] files) {
    return R.ok(fileUploadService.uploadFile(files),"文件上传");
  }

  @PostMapping(value = "/signature")
  public R<Object> signature() {
    return R.ok(fileUploadService.crateSignature(),"获取上传签名");
  }
}
