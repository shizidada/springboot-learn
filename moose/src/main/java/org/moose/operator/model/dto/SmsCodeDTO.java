package org.moose.operator.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 23:22:23:22
 * @see org.moose.operator.model.dto
 */
public class SmsCodeDTO implements Serializable {

  private static final long serialVersionUID = 8181529586466090339L;
  private String code;
  /**
   * 过期时间
   */
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime expireTime;

  private Boolean expired;

  private String type;

  public SmsCodeDTO() {
  }

  public SmsCodeDTO(String code, String smsType, Integer expireIn) {
    this.code = code;
    this.type = smsType;
    this.expireTime = LocalDateTime.now().plusMinutes(expireIn);
  }

  public void setExpired(Boolean expired) {
    this.expired = expired;
  }

  public Boolean getExpired() {
    return LocalDateTime.now().isAfter(expireTime);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDateTime getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(LocalDateTime expireTime) {
    this.expireTime = expireTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override public String toString() {
    return "SmsCodeDTO{" +
        "code='" + code + '\'' +
        ", expireTime=" + expireTime +
        ", expired=" + expired +
        ", type='" + type + '\'' +
        '}';
  }
}
