package com.parrotanalytics.api.commons.constants;

import java.io.Serializable;

/**
 * Measure of defining density of data points for particular title
 * 
 * @author Sanjeev Sharma
 * @author Minh Vu
 *
 */
public enum PreComputed implements Serializable
{

    RANGE("range"), WEEKLY("weekly"), MONTHLY("monthly"), QUARTERLY("quarterly"), YEARLY("yearly");

    private String value;

    private PreComputed(String type)
    {
        this.value = type;
    }

    /**
     * Value.
     * 
     * @return the string
     */
    public String value()
    {
        return this.value;
    }

    public static PreComputed fromValue(String value)
    {
        if (value != null)
        {
            for (PreComputed instance : PreComputed.values())
            {
                if (value.equalsIgnoreCase(instance.value()))
                {
                    return instance;
                }
            }
        }
        return PreComputed.RANGE;
    }

    @Override
    public String toString()
    {
        return this.value;
    }

}
