package org.moose.operator.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author taohua
 */
@Slf4j
public class ValueInValidator implements ConstraintValidator<ValueIn, Object>, Annotation {
  private final List<Object> values = new ArrayList<>();

  @Override
  public void initialize(ValueIn valueIn) {
    Class<?> clz = valueIn.value();
    Object[] objectArr = clz.getEnumConstants();
    try {
      Method method = clz.getMethod("getValue");
      if (ObjectUtils.isEmpty(method)) {
        throw new Exception(String.format("[枚举对象{%s}缺少字段名为value的字段]", clz.getName()));
      }
      Object value = null;
      for (Object obj : objectArr) {
        value = method.invoke(obj);
        values.add(value);
      }
    } catch (Exception e) {
      log.error("[处理枚举校验异常]", e);
    }
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
    if (value instanceof String) {
      String valueStr = (String) value;
      return StringUtils.isEmpty(valueStr) || values.contains(value);
    }
    return Objects.isNull(value) || values.contains(value);
  }
}

