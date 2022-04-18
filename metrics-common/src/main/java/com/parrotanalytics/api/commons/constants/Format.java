package com.parrotanalytics.api.commons.constants;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public enum Format
{

    JSON("json"), XML("xml");

    private String value;

    private Format(String type)
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

    public static Format fromValue(String value)
    {
        if (value != null)
        {
            for (Format instance : Format.values())
            {
                if (value.equalsIgnoreCase(instance.value()))
                {
                    return instance;
                }
            }
        }
        return Format.JSON;
    }

    @Override
    public String toString()
    {
        return this.value;
    }

}
