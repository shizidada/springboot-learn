package org.moose.operator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTests {

  public static void main(String[] args) {
    LocalDateTime now = LocalDateTime.now();
    System.out.println(now);

    DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyyMM");

    String format = now.format(yyyyMM);
    System.out.println(yyyyMM);
    System.out.println(format);

    LocalDate date = LocalDate.now();
    System.out.println(date);
    int offset = date.getDayOfMonth() - 1;
    System.out.println(offset);
  }
}
