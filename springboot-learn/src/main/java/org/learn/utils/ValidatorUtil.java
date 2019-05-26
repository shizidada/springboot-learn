package org.learn.utils;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.learn.common.api.ResultCode;
import org.learn.exception.BusinessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtil {
    private static Validator validator = ((HibernateValidatorConfiguration) Validation
            .byProvider(HibernateValidator.class).configure()).failFast(true).buildValidatorFactory().getValidator();

    /**
     * 实体校验
     *
     * @param obj
     * @throws BusinessException
     */
    public static <T> void validate(T obj) throws BusinessException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, new Class[0]);
        if (constraintViolations.size() > 0) {
            ConstraintViolation<T> validateInfo = (ConstraintViolation<T>) constraintViolations.iterator().next();
            // validateInfo.getMessage() 校验不通过时的信息，即 message 对应的值
            throw new BusinessException(ResultCode.VALIDATE_FAILED, validateInfo.getMessage());
        }
    }
}