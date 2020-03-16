package org.moose.account.model.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.moose.commons.base.entity.BaseEntity;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/11 23:06
 * @see org.moose.account.model.domain
 */
@Getter
@Setter
@ToString
public class PasswordDO extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -5182470846148201141L;

  private String passwordId;

  private String accountId;

  private String password;
}
