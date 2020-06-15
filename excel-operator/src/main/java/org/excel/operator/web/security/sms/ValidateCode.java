package org.excel.operator.web.security.sms;

import java.time.LocalDateTime;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 23:22:23:22
 * @see org.excel.operator.web.security.sms
 */
public class ValidateCode {
  private String code;
  /**
   * 过期时间
   */
  private LocalDateTime expireTime;

  public ValidateCode(String code, Integer expireIn) {
    this.code = code;
    this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
  }

  public ValidateCode(String code, LocalDateTime expireTime) {
    this.code = code;
    this.expireTime = expireTime;
  }

  public boolean isExpried() {
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
