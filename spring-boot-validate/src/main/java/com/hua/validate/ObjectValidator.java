package com.hua.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hua.bean.PersonSearchBean;

/**
 * @type ObjectValidator
 * @author qianye.zheng
 */
public class ObjectValidator implements ConstraintValidator<ObjectCheck, PersonSearchBean> {

	@Override
	public boolean isValid(PersonSearchBean value, ConstraintValidatorContext context) {
		System.out.println("ObjectValidator.isValid()");
		return false;
	}

}
