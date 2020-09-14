package org.moose.operator.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhc
 * @date 2019/9/10
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  public SpringContextUtils() {
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (null != applicationContext) {
      SpringContextUtils.applicationContext = applicationContext;
    }
  }

  public static <T> T getBean(Class<T> var1) throws BeansException {
    return applicationContext.getBean(var1);
  }

  public static Object getBean(String name) throws BeansException {
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return applicationContext.getBean(name, requiredType);
  }
}