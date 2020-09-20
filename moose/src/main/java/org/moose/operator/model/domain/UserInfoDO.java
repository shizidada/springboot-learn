package org.moose.operator.model.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author taohua
 */
@Data
@Document(collection = "user_info")
@EqualsAndHashCode(callSuper = true)
public class UserInfoDO extends BaseDO implements Serializable {

  @Field("user_id")
  private String userId;

  @Field("username")
  private String userName;

  @Field("account_id")
  private Long accountId;

  @Field("account_name")
  private String accountName;

  private String phone;

  private String avatar;

  private String email;

  private String job;

  private String address;

  private String description;

  private String gender;
}