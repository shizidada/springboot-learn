package org.moose.operator.model.dto;

import java.util.List;
import lombok.Data;

/**
 * @author taohua
 */
@Data
public class DynamicRecordDTO extends BaseDTO {

  /**
   * record Id
   */
  private String drId;

  /**
   * author
   */
  private UserBaseInfoDTO author;

  /**
   * 动态标题
   */
  private String title;

  /**
   * 动态内容
   */
  private String content;

  /**
   * is like
   */
  private Integer like;

  List<FileUploadDTO> attachments;
}
