package com.parrotanalytics.api.commons.constants;

/**
 * request type for api call
 * 
 * @author Sanjeev Sharma
 *
 */
public enum OperationType
{
    READ("read"),

    CREATE("create"),

    UPDATE("update"),
    
    SAVE("save"),

    DELETE("delete"),

    /*
     * setting VALUE level
     */
    ADD("add"),

    REMOVE("remove"),

    /* Others */

    READALL("readall"),

    DELETEROLE("deleterole");

    private String value;

    private OperationType(String type)
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

    public static OperationType fromValue(String value)
    {
        if (value != null)
        {
            for (OperationType instance : OperationType.values())
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
