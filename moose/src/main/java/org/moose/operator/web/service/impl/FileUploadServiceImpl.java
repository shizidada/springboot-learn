package org.moose.operator.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.CommonConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.FileRecordMapper;
import org.moose.operator.model.domain.DynamicRecordDO;
import org.moose.operator.model.domain.FileRecordDO;
import org.moose.operator.model.dto.FileUploadDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.util.OSSClientUtils;
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
  private ObjectMapper objectMapper;

  @Resource
  private OSSClientUtils ossClientUtils;

  @Resource
  private FileRecordMapper fileRecordMapper;

  @Override public List<FileUploadDTO> uploadFile(MultipartFile[] files) {
    if (ObjectUtils.isEmpty(files)) {
      throw new BusinessException(ResultCode.FILE_NOT_EMPTY);
    }
    List<FileUploadDTO> fileUploadDTOList = new ArrayList<>();

    for (MultipartFile file : files) {
      String originalFilename = file.getOriginalFilename();
      if (StringUtils.isEmpty(originalFilename)) {
        throw new BusinessException(ResultCode.FILE_LEGITIMATE_ERROR);
      }

      // OSSConstants.USER_AVATAR_BUCKET_NAME_KEY,
      FileUploadDTO fileUploadDTO = ossClientUtils.uploadFile(file, originalFilename);
      fileUploadDTOList.add(fileUploadDTO);
      log.info("文件上传成功 : {} ", fileUploadDTO);
    }

    if (fileUploadDTOList.size() > 0) {
      UserInfoDTO userInfo = userInfoService.getUserInfo();
      List<FileRecordDO> fileRecordDOList = fileUploadDTOList.stream()
          .filter(fileUploadDTO -> fileUploadDTO.getSuccess().equals(CommonConstants.SUCCESS))
          .map((fileUploadDTO -> {
            FileRecordDO fileRecordDO = new FileRecordDO();
            fileRecordDO.setUserId(userInfo.getUserId());
            fileRecordDO.setFileUrl(fileUploadDTO.getAttachmentUrl());
            fileRecordDO.setFrId(fileUploadDTO.getAttachmentId());
            fileRecordDO.setETag(fileUploadDTO.getTag());
            return fileRecordDO;
          }))
          .collect(Collectors.toList());
      // TODO: judge logic save success
      fileRecordMapper.batchSaveFileRecord(fileRecordDOList);
    }
    return fileUploadDTOList;
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
  private FileRecordDO convertDTOFromDO(DynamicRecordDO dynamicRecordDO) {
    return null;
  }
}
