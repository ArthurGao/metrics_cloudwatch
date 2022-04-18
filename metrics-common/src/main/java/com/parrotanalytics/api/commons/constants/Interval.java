package com.parrotanalytics.api.commons.constants;

/**
 * Measure of defining density of data points for particular title
 * 
 * @author Sanjeev Sharma
 *
 */
public enum Interval
{

    OVERALL("overall"), DAILY("daily"), WEEKLY("weekly"), MONTHLY("monthly"), QUARTERLY("quarterly"), YEARLY("yearly"), CUSTOM("custom");

    private String value;

    private Interval(String type)
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

    public static Interval fromValue(String value)
    {
        if (value != null)
        {
            for (Interval instance : Interval.values())
            {
                if (value.equalsIgnoreCase(instance.value()))
                {
                    return instance;
                }
            }
        }
        return DAILY;
    }

    public static String convertIntervalType(String interval)
    {
        if (Interval.YEARLY.value().equals(interval))
        {
            return "year";
        }
        else if (Interval.QUARTERLY.value().equals(interval))
        {
            return "quarter";
        }
        else if (Interval.MONTHLY.value().equals(interval))
        {
            return "month";
        }
        else if (Interval.WEEKLY.value().equals(interval))
        {
            return "week";
        }
        return "day";
    }

    @Override
    public String toString()
    {
        return this.value;
    }

}
