package org.moose.operator.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.constant.CommonConstants;
import org.moose.operator.mapper.AttachmentRecordMapper;
import org.moose.operator.model.domain.AttachmentRecordDO;
import org.moose.operator.model.dto.AttachmentUploadDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.web.service.AttachmentRecordService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author taohua
 */
@Service
public class AttachmentRecordServiceImpl implements AttachmentRecordService {

  @Resource
  private UserInfoService userInfoService;

  @Resource
  private AttachmentRecordMapper attachmentRecordMapper;

  @Override public AttachmentUploadDTO getAttachmentRecord(String userId, String attachId, String tag) {
    AttachmentRecordDO
        attachmentRecordDO = attachmentRecordMapper.selectByUserIdAndAttachIdAndEtag(userId, attachId, tag);
    if (ObjectUtils.isEmpty(attachmentRecordDO)) {
      return null;
    }
    AttachmentUploadDTO attachmentUploadDTO = new AttachmentUploadDTO();
    attachmentUploadDTO.setTag(attachmentRecordDO.getETag());
    attachmentUploadDTO.setAttachmentUrl(attachmentRecordDO.getFileUrl());
    attachmentUploadDTO.setAttachmentId(attachmentRecordDO.getAttachId());
    return attachmentUploadDTO;
  }

  @Override public void batchSaveAttachmentRecord(List<AttachmentUploadDTO> attachmentUploadDTOList) {
    UserInfoDTO userInfo = userInfoService.getUserInfo();

    List<AttachmentRecordDO> attachmentRecordDOList = attachmentUploadDTOList.stream()
        .filter(fileUploadDTO -> fileUploadDTO.getSuccess().equals(CommonConstants.SUCCESS))
        .map((fileUploadDTO -> {
          AttachmentRecordDO attachmentRecordDO = new AttachmentRecordDO();
          attachmentRecordDO.setUserId(userInfo.getUserId());
          attachmentRecordDO.setFileUrl(fileUploadDTO.getAttachmentUrl());
          attachmentRecordDO.setAttachId(fileUploadDTO.getAttachmentId());
          attachmentRecordDO.setETag(fileUploadDTO.getTag());
          return attachmentRecordDO;
        }))
        .collect(Collectors.toList());
    // TODO: judge logic save success
    attachmentRecordMapper.batchInsertAttachmentRecord(attachmentRecordDOList);
  }
}
