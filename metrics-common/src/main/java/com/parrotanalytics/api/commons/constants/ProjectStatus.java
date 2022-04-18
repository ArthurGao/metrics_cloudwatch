package com.parrotanalytics.api.commons.constants;

/**
 * Rating type for parrotratings API endpoint
 * 
 * @author Sanjeev Sharma
 *
 */
public enum ProjectStatus
{

    Active("active"), COMPLETED("completed"), CANCELLED("cancelled"), ONHOLD("onhold");

    private String value;

    private ProjectStatus(String type)
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

    public static ProjectStatus fromValue(String value)
    {
        if (value != null)
        {
            for (ProjectStatus instance : ProjectStatus.values())
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
