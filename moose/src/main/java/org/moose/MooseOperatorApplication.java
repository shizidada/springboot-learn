package org.moose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author taohua
 */
@SpringBootApplication
@EnableTransactionManagement
public class MooseOperatorApplication {

  public static void main(String[] args) {
    System.setProperty("es.set.netty.runtime.available.processors", "false");
    SpringApplication.run(MooseOperatorApplication.class, args);
  }
}
