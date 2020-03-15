package org.moose.user.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/8 14:09
 * @see org.moose.user.controller
 */
@RestController(value = "user")
public class UserController {

  @PostMapping("/echo/{message}")
  public String echo(
      @PathVariable("message") String message) {
    return "Echo " + message;
  }
}
