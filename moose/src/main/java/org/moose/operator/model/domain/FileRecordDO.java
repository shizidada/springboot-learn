package org.moose.operator.model.domain;

import lombok.Data;

/**
 * @author taohua
 */
@Data
public class FileRecordDO extends BaseDO {

  private String frId;

  private String userId;

  private String eTag;

  private String fileUrl;
}
