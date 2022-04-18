package com.parrotanalytics.api.commons.constants;

public enum ReportQueryType
{

    REPORTS("reports"), REPORT_INSIGHTS("report_insights"), REPORT_IMAGES("report_images");

    private String value;

    private ReportQueryType(String type)
    {
        this.value = type;
    }

    public String value()
    {
        return value;
    }

    public static ReportQueryType fromValue(String value)
    {
        if (value != null)
        {
            for (ReportQueryType instance : ReportQueryType.values())
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
