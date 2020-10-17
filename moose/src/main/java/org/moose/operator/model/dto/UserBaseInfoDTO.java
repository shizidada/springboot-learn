package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-17 19:05:19:05
 * @see org.moose.operator.model.dto
 */
@Data
public class UserBaseInfoDTO implements Serializable {

  private static final long serialVersionUID = -8286691305212061585L;

   private String userId;

  private String userName;

  private String gender;

  private String avatar;

  private String description;
}
