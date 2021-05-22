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
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ObjectValidator.class })
@Documented
public @interface ObjectCheck {
	
	 	String message();
	 
	  Class<?>[] groups() default {};
	  
	 Class<? extends Payload>[] payload() default {};
}
