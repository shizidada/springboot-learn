package org.moose.provider.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 20:40
 * @see org.moose.provider.user.controller
 */
@Slf4j
@RestController
public class IndexController {

  @GetMapping("/")
  public String index() {
    return "provider user service : " + System.currentTimeMillis();
  }
}
