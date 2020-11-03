package org.moose.operator.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

/**
 * @author taohua
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PoetryEntity extends BaseEntity {
  /**
   * 主键 id
   */
  @Id()
  private String id;

  @JsonProperty("poetry_name")
  private String poetryName;

  @JsonProperty("poetry_content")
  private String poetryContent;

  @JsonProperty("poetry_author")
  private String poetryAuthor;

  @JsonProperty("poetry_num")
  private String poetryNum;

  private String type;
}
