package org.moose.operator.web.service;

import java.util.List;
import org.moose.operator.model.dto.FileUploadDTO;

/**
 * @author taohua
 */
public interface FileRecordService {

  /**
   * find file record
   *
   * @param userId user id
   * @param tag    upload tag
   * @param frId   file record id
   * @return FileUploadDTO
   */
  FileUploadDTO getFileRecord(String userId, String frId, String tag);

  /**
   * batch save file upload info
   *
   * @param fileUploadDTOList upload info
   */
  void batchSaveFileRecord(List<FileUploadDTO> fileUploadDTOList);
}
