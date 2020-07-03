package org.excel.operator;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-07-01 23:32:23:32
 * @see org.excel.operator
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器
 */
public class CountUtils {
  /**
   * 假设秒杀商品总数=10
   */
  public static final AtomicInteger TOTAL_COUNT = new AtomicInteger(10);

  /**
   * 秒杀成功，商品数量减1
   */
  public static int decrease() {
    return TOTAL_COUNT.decrementAndGet();
  }
}
