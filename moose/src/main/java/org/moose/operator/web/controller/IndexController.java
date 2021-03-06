package org.moose.operator.web.controller;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:06
 * @see org.moose.operator.web.controller
 */
@RestController
public class IndexController {
  @GetMapping("/")
  public String index() {
    return "Excel Operator Service " + LocalDate.now();
  }

  //@GetMapping("/admin")
  //@PreAuthorize("hasRole('ADMIN')")
  //public String admin() {
  //  return "Excel Operator Service admin permission";
  //}
}
