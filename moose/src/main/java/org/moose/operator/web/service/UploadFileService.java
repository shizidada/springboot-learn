package org.moose.operator.web.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author taohua
 */
public interface UploadFileService {
  /**
   * 上传文件 ali oss
   * @param file 文件
   * @return 是否上传成功
   */
  void uploadFile(MultipartFile [] file);
}
