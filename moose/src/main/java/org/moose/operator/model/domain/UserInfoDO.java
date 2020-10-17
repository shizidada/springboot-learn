package org.moose.operator.model.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author taohua
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoDO extends BaseDO implements Serializable {

  private static final long serialVersionUID = 7836466254399105986L;
  private String userId;

  private String userName;

  private String accountId;

  private String accountName;

  private String phone;

  private String avatar;

  private String email;

  private String job;

  private String address;

  private String description;

  private String gender;
}