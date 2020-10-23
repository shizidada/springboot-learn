package org.moose.operator.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.model.domain.DynamicRecordDO;
import org.moose.operator.model.domain.AttachmentRecordDO;
import org.moose.operator.model.dto.AttachmentUploadDTO;
import org.moose.operator.util.OSSClientUtils;
import org.moose.operator.web.service.AttachmentRecordService;
import org.moose.operator.web.service.FileUploadService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author taohua
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

  @Resource
  private UserInfoService userInfoService;

  @Resource
  private AttachmentRecordService attachmentRecordService;

  @Resource
  private ObjectMapper objectMapper;

  @Resource
  private OSSClientUtils ossClientUtils;

  @Override public List<AttachmentUploadDTO> uploadFile(MultipartFile[] files) {
    if (ObjectUtils.isEmpty(files)) {
      throw new BusinessException(ResultCode.FILE_NOT_EMPTY);
    }
    List<AttachmentUploadDTO> attachmentUploadDTOList = new ArrayList<>();

    for (MultipartFile file : files) {
      String originalFilename = file.getOriginalFilename();
      if (StringUtils.isEmpty(originalFilename)) {
        throw new BusinessException(ResultCode.FILE_LEGITIMATE_ERROR);
      }

      // OSSConstants.USER_AVATAR_BUCKET_NAME_KEY,
      AttachmentUploadDTO attachmentUploadDTO = ossClientUtils.uploadFile(file, originalFilename);
      attachmentUploadDTOList.add(attachmentUploadDTO);
      log.info("文件上传成功 : {} ", attachmentUploadDTO);
    }

    if (attachmentUploadDTOList.size() > 0) {
      attachmentRecordService.batchSaveAttachmentRecord(attachmentUploadDTOList);
    }
    return attachmentUploadDTOList;
  }

  @Override public Map<String, String> crateSignature() {
    return ossClientUtils.getSignature();
  }

  /**
   * convert DO to DTO
   *
   * @param dynamicRecordDO data object
   * @return dto
   */
  private AttachmentRecordDO convertDTOFromDO(DynamicRecordDO dynamicRecordDO) {
    return null;
  }
}
