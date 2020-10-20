package org.moose.operator.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.moose.operator.constant.CommonConstants;
import org.moose.operator.mapper.FileRecordMapper;
import org.moose.operator.model.domain.FileRecordDO;
import org.moose.operator.model.dto.FileUploadDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.web.service.FileRecordService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author taohua
 */
@Service
public class FileRecordServiceImpl implements FileRecordService {

  @Resource
  private UserInfoService userInfoService;

  @Resource
  private FileRecordMapper fileRecordMapper;

  @Override public FileUploadDTO findFileRecord(String userId, String tag, String frId) {
    return null;
  }

  @Override public void batchSaveFileRecord(List<FileUploadDTO> fileUploadDTOList) {
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
    fileRecordMapper.batchInsertFileRecord(fileRecordDOList);
  }
}
