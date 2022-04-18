package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.api.commons.constants.DemandMetricType;

public enum CollectionType
{
    PRESET_PORTFOLIO("preset_portfolio"),

    PLATFORM("platform");

    private String value;

    private CollectionType(String type)
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

    public static DemandMetricType fromValue(String value)
    {
        if (value != null)
        {
            for (DemandMetricType instance : DemandMetricType.values())
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
