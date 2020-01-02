package org.excel.operator.mongo.entity;

import java.util.Date;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author taohua
 */
@Data
@Document(collection = "user")
public class User {

  @Field("user_id")
  private String userId;

  private String username;

  private String description;

  private String gender;

  private String phone;

  private String avatar;

  private String email;

  private String job;

  private String address;

  @Field("create_date")
  private Date createDate;

  @Field("update_date")
  private Date updateDate;
}