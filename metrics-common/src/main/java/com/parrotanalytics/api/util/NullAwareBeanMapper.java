package com.parrotanalytics.api.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * The Class NullAwareBeanMapper maps one object to another, ignoring nulls.
 * 
 * @author http://stackoverflow.com/questions/1301697/helper-in-order-to-copy-
 *         non-null-properties-from-object-to-another-java?%20(Java)
 */
public class NullAwareBeanMapper extends BeanUtilsBean
{

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.commons.beanutils.BeanUtilsBean#copyProperty(java.lang.Object,
     * java.lang.String, java.lang.Object)
     */
    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException
    {
        if (value == null)
            return;
        super.copyProperty(dest, name, value);
    }

}
