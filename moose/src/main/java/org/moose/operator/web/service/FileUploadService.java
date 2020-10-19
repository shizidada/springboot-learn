package org.moose.operator.web.service;

import java.util.List;
import java.util.Map;
import org.moose.operator.model.dto.UploadInfoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author taohua
 */
public interface FileUploadService {
  /**
   * 上传文件 ali oss
   * @param file 文件
   * @return List<UploadInfoDTO> upload data
   */
  List<UploadInfoDTO> uploadFile(MultipartFile [] file);

  /**
   *  create signature
   * @return signature info
   */
  Map<String, String> crateSignature();
}
