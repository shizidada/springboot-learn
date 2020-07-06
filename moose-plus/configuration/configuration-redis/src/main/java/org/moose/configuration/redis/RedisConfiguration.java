package org.moose.configuration.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @link https://blog.csdn.net/weixin_44467172/article/details/103014884
 *
 * RedisTemplate 配置
 * @author taohua
 */
@Configuration
public class RedisConfiguration {

  @Bean
  @SuppressWarnings("all")
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(factory);

    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer(Object.class);

    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);

    // value 序列化方式采用 jackson
    template.setValueSerializer(jackson2JsonRedisSerializer);
    // hash 的 value 序列化方式采用 jackson
    template.setHashValueSerializer(jackson2JsonRedisSerializer);

    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    // key 采用 String 的序列化方式
    template.setKeySerializer(stringRedisSerializer);
    // hash 的 key 也采用 String 的序列化方式
    template.setHashKeySerializer(stringRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }
}
