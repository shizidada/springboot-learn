package org.moose.operator.model.dto;

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
   * 动态标题
   */
  private String title;

  /**
   * 动态描述
   */
  private String description;

  /**
   * is like
   */
  private Integer like;
}
