package org.excel.operator.model.params;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.excel.operator.annotation.EnumValidator;
import org.excel.operator.model.emun.SmsCodeEnum;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 17:15:17:15
 * @see org.excel.operator.model.params
 */
@Data
public class SmsCodeParam implements Serializable {
  private static final long serialVersionUID = -3613427894460743407L;

  @NotBlank(message = "手机号码不能为空")
  private String phone;

  @NotBlank(message = "短信类型不能为空")
  @EnumValidator(value = SmsCodeEnum.class, message = "短信类型不正确")
  private String type;
}
