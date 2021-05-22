package com.hua.bean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import com.hua.validate.BigDecimalListCheck;

import lombok.Data;

/**
 * @type BusinessParamReq
 * @author qianye.zheng
 */
@Data
public final class BusinessParamReq {

	@Size(min = 1, max = 3, message = "集合长度1-3")
	@BigDecimalListCheck(message = "数值范围在[0.01, 10.99]", min = "0.01", max = "10.99")
	private List<BigDecimal> cashs = Collections.emptyList();
	
	@DecimalMin(value = "1.00", message = "数值不能小于1.00")
	@DecimalMax(value = "9999.99", message = "数值不能大于9999.99")
	private BigDecimal cashMin;
	
}
