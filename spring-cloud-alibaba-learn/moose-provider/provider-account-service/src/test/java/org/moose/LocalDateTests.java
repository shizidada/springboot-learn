package org.moose;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/31 19:20
 * @see org.moose
 */
@Slf4j
public class LocalDateTests {

  @Test
  public void testLocalDate() {
    LocalDate now = LocalDate.now();
    log.info("LocalDate now :: ", now);
  }

  @Test
  public void testLocalDateTime() {
    LocalDateTime now = LocalDateTime.now();
    log.info("LocalDateTime now :: ", now);
  }
}
