package org.moose.operator.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author taohua
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Document(indexName = "shi_ci_ming_ju", type = "poetry")
public class PoetryEntity extends BaseEntity {
  /**
   * 主键 id
   */
  @Id()
  private String id;

  @JsonProperty("poetry_name")
  @Field(name = "poetry_name")
  private String poetryName;

  @JsonProperty("poetry_content")
  @Field(name = "poetry_content")
  private String poetryContent;

  @JsonProperty("poetry_author")
  @Field(name = "poetry_author")
  private String poetryAuthor;

  @JsonProperty("poetry_num")
  @Field(name = "poetry_num")
  private String poetryNum;

  private String type;
}
