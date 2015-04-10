/**
 * 
 */
package com.manyi.ihouse.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author leo.li
 * 
 */
@Validated
@SessionAttributes({ Global.SESSION_UID_KEY })
public abstract class BaseController {

	protected boolean assertSubmit(String suffix) {
		return StringUtils.isNotBlank(suffix);
	}

	@ExceptionHandler(Throwable.class)
	public String handleIOException(Throwable e, ModelMap modelMap) {
		modelMap.put("system_errors", e.getMessage());
		return "global.errors";
	}

}
