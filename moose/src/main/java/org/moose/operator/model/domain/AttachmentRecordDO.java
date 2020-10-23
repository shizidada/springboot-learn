package org.moose.operator.model.domain;

import lombok.Data;

/**
 * @author taohua
 */
@Data
public class AttachmentRecordDO extends BaseDO {

  private String attachId;

  private String userId;

  private String eTag;

  private String fileUrl;
}
