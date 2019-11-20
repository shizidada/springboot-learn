package org.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author taohua
 */
@SpringBootApplication
@EnableTransactionManagement
public class ExcelOperatorApplication {
  private static final Logger logger = LoggerFactory.getLogger(ExcelOperatorApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ExcelOperatorApplication.class, args);
    logger.info("init success ...");
  }
}
