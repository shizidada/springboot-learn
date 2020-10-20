package org.moose.operator.model.dto;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-14 23:46:23:46
 * @see org.moose.operator.model.dto
 */
@Data
public class FileUploadDTO {

  private String attachmentUrl;

  private String attachmentId;

  private String tag;

  private Integer success;

  private String errMessage;

}
