package org.moose.operator.sender.model;

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
 * @see org.moose.operator.web.security.sms
 */
public class ValidateCode implements Serializable {

  private String code;
  /**
   * 过期时间
   */
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime expireTime;

  private boolean isExpried;

  public ValidateCode() {
  }

  public ValidateCode(String code, Integer expireIn) {
    this.code = code;
    this.expireTime = LocalDateTime.now().plusMinutes(expireIn);
  }

  public ValidateCode(String code, LocalDateTime expireTime) {
    this.code = code;
    this.expireTime = expireTime;
  }

  public void setExpried(boolean isExpried) {
    this.isExpried = isExpried;
  }

  public boolean getExpried() {
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
}
