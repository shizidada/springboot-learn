package org.moose.operator.model.domain;

import java.util.List;
import lombok.Data;

/**
 * @author taohua
 */
@Data
public class DynamicRecordDO extends BaseDO {

  private String drId;

  private String userId;

  private String title;

  private String content;

  private UserInfoDO author;

  private List<AttachmentRecordDO> attachmentRecords;
}
