package com.parrotanalytics.api.commons.constants;

/**
 * Rating type for parrotratings API endpoint
 * 
 * @author Sanjeev Sharma
 *
 */
public enum SettingType
{
    PROFILE("profile"),

    FAVORITE("favorite"),

    REGION("region"),

    EXPORT("export"),

    TOPN_EXPORT("topn_export");

    private String value;

    private SettingType(String type)
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

    public static SettingType fromValue(String value)
    {
        if (value != null)
        {
            for (SettingType instance : SettingType.values())
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
