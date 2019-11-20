package org.excel.operator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:06
 * @see org.excel.operator.controller
 */
@RestController
public class IndexController{
  private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

  @GetMapping("/")
  public String index() {
    int a = 1/0;
    logger.info("a", a);
    return "success " + LocalDate.now();
  }
}
