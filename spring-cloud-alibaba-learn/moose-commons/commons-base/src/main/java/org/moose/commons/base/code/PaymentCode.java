package org.moose.commons.base.code;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 11:11
 * @see org.moose.commons.base.code
 */
public enum PaymentCode {
  // 800001
  PAYMENT_ACCOUNT_BALANCE_MUST_NOT_BE_NULL(800001, "账户余额信息不能为空"),

  PAYMENT_INFO_MUST_NOT_BE_NULL(800002, "支付信息不能为空"),

  PAYMENT_INCREASE_ACCOUNT_BALANCE_FAIL(800003, "充值余额失败"),

  PAYMENT_REDUCE_ACCOUNT_BALANCE_FAIL(800004, "扣减余额失败"),

  PAYMENT_ACCOUNT_NOT_FOUND(800005, "该账户不存在"),

  PAYMENT_BALANCE_NOT_ENOUGH(800006, "账户余额不足");

  private final Integer code;

  private final String message;

  PaymentCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
