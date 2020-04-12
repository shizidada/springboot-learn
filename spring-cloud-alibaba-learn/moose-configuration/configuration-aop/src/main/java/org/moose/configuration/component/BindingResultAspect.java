package org.moose.configuration.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 15:38
 * @see org.moose.configuration.component
 */
@Aspect
@Component
@Order(2)
public class BindingResultAspect {

  //@Pointcut("execution(public * org.moose.*.*.controller.*.*(..))")
  @Pointcut(
      "@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
          "||@annotation(org.springframework.web.bind.annotation.GetMapping)" +
          "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
          "||@annotation(org.springframework.web.bind.annotation.PutMapping)"
  )
  public void validateAnnotation() {
  }

  @Around("validateAnnotation()")
  public Object doAround(ProceedingJoinPoint point) throws Throwable {
    Object[] args = point.getArgs();
    for (Object arg : args) {
      if (arg instanceof BindingResult) {
        BindingResult result = (BindingResult) arg;
        if (result.hasErrors()) {
          FieldError fieldError = result.getFieldError();
          String message = ResultCode.VALIDATE_PARAM_FAIL.getMessage();
          if (fieldError != null) {
            message = fieldError.getDefaultMessage();
          }
          throw new BusinessException(ResultCode.VALIDATE_PARAM_FAIL, message);
        }
      }
    }
    return point.proceed();
  }
}
