package org.moose.operator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-16 13:26:13:26
 * @see org.moose.operator
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTests {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;
}
