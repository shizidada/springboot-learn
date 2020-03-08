package org.moose.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 19:33
 * @see org.moose.account.controller
 */
@RestController
public class IndexController {

  @GetMapping("/")
  public String index() {
    return "account service : " + System.currentTimeMillis();
  }
}
