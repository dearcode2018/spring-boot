/**
  * @filename DefaultImportSelector.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @type DefaultImportSelector
 * @description 
 * @author qianye.zheng
 */
public class DefaultImportSelector implements ImportSelector {

	/**
	 * @description 
	 * @param importingClassMetadata
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		
		return new String[] {"com.hua.bean.Person"};
	}

}
