/**
 * 
 */
package com.manyi.hims.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc
 */
public class CollectionUtils {

	protected static final Logger logger = LoggerFactory.getLogger(CollectionUtils.class);
	
	public static List<String> getProperty(Collection<?> values, String property) {
        if (values == null || values.isEmpty()) {
            return Collections.<String> emptyList();
        }

        List<String> valueResult = new ArrayList<String>(values.size());
        for (Object value : values) {
            try {
                String propertyValue = BeanUtils.getProperty(value, property);
                valueResult.add(propertyValue);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueResult;
    }
}
