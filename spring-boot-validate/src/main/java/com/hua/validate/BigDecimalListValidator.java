package com.hua.validate;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BigDecimal列表校验器
 * @type BigDecimalListValidator
 * @author qianye.zheng
 */
public class BigDecimalListValidator implements ConstraintValidator<BigDecimalListCheck, List<BigDecimal>> {

	private BigDecimal min;
	
	private BigDecimal max;
	
	@Override
	public boolean isValid(List<BigDecimal> values, ConstraintValidatorContext context) {
		for (BigDecimal e : values) {
				if (e.compareTo(min) < 0 || e.compareTo(max) > 0) {
					return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void initialize(final BigDecimalListCheck constraintAnnotation) {
		min = BigDecimal.valueOf(Double.valueOf(constraintAnnotation.min()));
		max = BigDecimal.valueOf(Double.valueOf(constraintAnnotation.max()));
	}

}
