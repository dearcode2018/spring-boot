package com.hua.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @type ObjectCheck
 * @author qianye.zheng
 */
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { BigDecimalListValidator.class })
@Documented
public @interface BigDecimalListCheck {

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	/**
	 * 
	 * @description 最小值
	 * @return
	 * @author qianye.zheng
	 */
	String min();
	
	/***
	 * 
	 * @description 最大值
	 * @return
	 * @author qianye.zheng
	 */
	String max();
}
