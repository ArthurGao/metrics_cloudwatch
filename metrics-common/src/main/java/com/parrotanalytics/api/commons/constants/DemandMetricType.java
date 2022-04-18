package com.parrotanalytics.api.commons.constants;

import com.parrotanalytics.apicore.config.APIConstants;

/**
 * Rating type for parrotratings API endpoint
 * 
 * @author Sanjeev Sharma
 *
 */
public enum DemandMetricType
{
    // @formatter:off

    DEX(APIConstants.DEX),

    PEAK_DEX(APIConstants.PEAK_DEX),

    DEXPERCAPITA(APIConstants.DEXPERCAPITA),

    PEAK_DEXPERCAPITA(APIConstants.PEAK_DEXPERCAPITA),

    SUM_DEXPERCAPITA(APIConstants.SUM_DEXPERCAPITA),

    SUM_DEX(APIConstants.SUM_DEX),

    SOURCE_EVENT_TOTAL("source_event_total");

    // @formatter:on
    private String value;

    private DemandMetricType(String type)
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
