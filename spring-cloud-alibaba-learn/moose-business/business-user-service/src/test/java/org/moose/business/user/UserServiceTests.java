package org.moose.business.user;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.moose.business.user.model.params.LoginParam;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/31 21:39
 * @see org.moose.business.user
 */
@Slf4j
public class UserServiceTests {

  @Test
  public void testEquals() {
    LoginParam param = new LoginParam();
    param.setAccountName("tom");
    param.setPassword("123456");
    param.setPhone("13777777777");
    param.setLoginType("password");
    param.setCreateTime(LocalDateTime.now());
    param.setUpdateTime(LocalDateTime.now());
  }
}
