package org.moose.operator.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.util.OSSClientUtils;
import org.moose.operator.web.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author taohua
 */
@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {

  @Resource
  private ObjectMapper objectMapper;

  @Resource
  private OSSClientUtils ossClientUtils;

  @Override public void uploadFile(MultipartFile[] files) {
    if (ObjectUtils.isEmpty(files)) {
      throw new BusinessException(ResultCode.FILE_NOT_EMPTY);
    }

    for (MultipartFile file : files) {
      String originalFilename = file.getOriginalFilename();
      if (StringUtils.isEmpty(originalFilename)) {
        throw new BusinessException(ResultCode.FILE_LEGITIMATE_ERROR);
      }

      // OSSConstants.USER_AVATAR_BUCKET_NAME_KEY,
      String fileName = ossClientUtils.uploadFile(file, originalFilename);
      log.info("文件上传成功 : {} ", fileName);
    }
  }
}
