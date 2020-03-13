package org.moose.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 12:35:12:35
 * @see org.moose.oauth.controller
 */
@RestController
public class IndexController {

  @GetMapping("/")
  public String index() {
    return "oauth";
  }
}
