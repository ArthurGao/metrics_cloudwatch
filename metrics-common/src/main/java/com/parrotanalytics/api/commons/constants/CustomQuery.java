package com.parrotanalytics.api.commons.constants;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public enum CustomQuery
{
    ALLMARKETS("allmarkets"), LOWHIGH("lowhigh"), SELECTEDMARKETS("selectedmarkets");

    private String value;

    private CustomQuery(String type)
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

    public static CustomQuery fromValue(String value)
    {
        if (value != null)
        {
            for (CustomQuery instance : CustomQuery.values())
            {
                if (value.equalsIgnoreCase(instance.value()))
                {
                    return instance;
                }
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return this.value;
    }
}
