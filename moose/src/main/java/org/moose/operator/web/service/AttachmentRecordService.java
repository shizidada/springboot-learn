package org.moose.operator.web.service;

import java.util.List;
import org.moose.operator.model.dto.AttachmentUploadDTO;

/**
 * @author taohua
 */
public interface AttachmentRecordService {

  /**
   * find attachment record
   *
   * @param userId user id
   * @param tag    upload tag
   * @param attachId   attachment record id
   * @return FileUploadDTO
   */
  AttachmentUploadDTO getAttachmentRecord(String userId, String attachId, String tag);

  /**
   * batch save file upload info
   *
   * @param attachmentUploadDTOList upload info
   */
  void batchSaveAttachmentRecord(List<AttachmentUploadDTO> attachmentUploadDTOList);
}
