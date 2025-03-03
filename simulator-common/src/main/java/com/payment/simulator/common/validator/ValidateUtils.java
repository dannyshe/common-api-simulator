package com.payment.simulator.common.validator;

import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentException;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.*;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述信息
 *
 * @author Danny She
 * @createTime 2021-11-01
 */
public class ValidateUtils {
    // 线程安全的，直接构建也可以，这里使用静态代码块一样的效果
    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ValidateUtils() {
    }


    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            constraintViolations.forEach(action -> {
                validateError.append(action.getMessage()).append(";");
            });
            throw new PaymentException(ErrorCode.VALIDATE_ERROR,validateError.toString());
        }
    }

    public static <T> void validate(T t,Class<?>... var) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t,var);
        if (constraintViolations.size() > 0) {

            StringBuilder validateError = new StringBuilder();
            validateError.append(constraintViolations.size()).append(" error(s): ");
            constraintViolations.forEach(action -> {
                validateError.append(action.getMessage()).append(";");
            });
            throw new PaymentException(ErrorCode.VALIDATE_ERROR,validateError.toString());
        }
    }


    public static <T> void validateForOneBack(T t) {
        Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false) // 为true，则有一个错误就结束校验
                .buildValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        StringBuilder validateError = new StringBuilder();
        validateError.append(constraintViolations.size()).append(" error(s): ");
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            validateError.append(constraintViolation.getMessage()).append(";");
        }
        throw new ValidationException(validateError.toString());
    }

    public static <T> void validateOneBack(T t,Class<?>... var) {
        Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false) // 为true，则有一个错误就结束校验
                .buildValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        StringBuilder validateError = new StringBuilder();
        validateError.append(constraintViolations.size()).append(" error(s): ");
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            validateError.append(constraintViolation.getMessage()).append(";");
        }
        throw new ValidationException(validateError.toString());
    }


    /**
     * 校验指定实体的指定属性是否存在异常
     *
     * @param obj
     * @param propertyName
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validateProperty(T obj, String propertyName) {
        Set<ConstraintViolation<T>> validateSet = validator.validateProperty(obj, propertyName, Default.class);
        return buildValidationResult(validateSet);
    }

    /**
     * 校验实体，返回实体所有属性的校验结果
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validateEntity(T obj) {
        // 解析校验结果
        Set<ConstraintViolation<T>> validateSet = validator.validate(obj, Default.class);
        return buildValidationResult(validateSet);
    }


    /**
     * 将异常结果封装返回
     *
     * @param validateSet
     * @param <T>
     * @return
     */
    private static <T> ValidationResult buildValidationResult(Set<ConstraintViolation<T>> validateSet) {
       ValidationResult validationResult = new ValidationResult();
        if (!CollectionUtils.isEmpty(validateSet)) {
            validationResult.setHasErrors(true);
            Map<String, String> errorMsgMap = new HashMap<>();
            for (ConstraintViolation<T> constraintViolation : validateSet) {
                errorMsgMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            validationResult.setErrorMsg(errorMsgMap);
        }
        return validationResult;
    }

    public static <T> ValidationResult validateEntity1(T obj) {
       ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (!CollectionUtils.isEmpty(set)) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }

    public static <T> ValidationResult validateProperty1(T obj, String propertyName) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (!CollectionUtils.isEmpty(set)) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }
}
