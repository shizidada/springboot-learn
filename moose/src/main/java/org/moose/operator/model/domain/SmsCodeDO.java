package org.moose.operator.model.domain;

import lombok.Data;

/**
 * @author taohua
 */
@Data
public class SmsCodeDO extends BaseDO {

  private String smsId;

  private String phone;

  private String type;

  private String code;
}
