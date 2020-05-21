package org.excel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author taohua
 */
@SpringBootApplication
@EnableTransactionManagement
public class ExcelOperatorApplication {

  public static void main(String[] args) {
    System.setProperty("es.set.netty.runtime.available.processors", "false");
    SpringApplication.run(ExcelOperatorApplication.class, args);
  }
}
