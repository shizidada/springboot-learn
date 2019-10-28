package org.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author taohua
 */
@SpringBootApplication
public class ExcelOperatorApplication {
  private static final Logger logger = LoggerFactory.getLogger(ExcelOperatorApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ExcelOperatorApplication.class, args);
    logger.info("ExcelOperatorApplication init success ...");
  }
}
