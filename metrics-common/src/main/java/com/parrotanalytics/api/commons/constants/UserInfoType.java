package com.parrotanalytics.api.commons.constants;

/**
 * 
 * @author minhvu
 * User Info Type
 */
public enum UserInfoType
{
    BASIC("basic"),

    COMPLETED("completed");

    private String value;

    private UserInfoType(String type)
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

    public static UserInfoType fromValue(String value)
    {
        if (value != null)
        {
            for (UserInfoType instance : UserInfoType.values())
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
