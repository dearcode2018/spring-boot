/**
  * @filename BigDecimalSerialize.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.format;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @type BigDecimalSerialize
 * @description 
 * @author qianye.zheng
 */
public class BigDecimalSerialize extends JsonSerializer<BigDecimal> {

	/** 小数点位数 */
	private static final int DECIMAL_POINT_LEN = 2;
	
	/**
	 * @description 
	 * @param value
	 * @param gen
	 * @param serializers
	 * @throws IOException
	 * @author qianye.zheng
	 */
	@Override
	public void serialize(BigDecimal value, JsonGenerator gen,
			SerializerProvider serializers) throws IOException {
		if (null != value) {
			gen.writeNumber(value.setScale(DECIMAL_POINT_LEN, BigDecimal.ROUND_HALF_DOWN));
		} else
		{
			gen.writeNumber(0.00D);
		}
	}



	
	
}
