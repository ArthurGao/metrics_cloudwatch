package com.parrotanalytics.api.commons.constants;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Measure of defining density of data points for particular title
 * 
 * @author Sanjeev Sharma
 * @author Minh Vu
 *
 */
public enum TimePeriod
{
    LATESTDAY("latest", 1, "Last 24 Hours"),

    LAST7DAYS("last7days", 7, "Last 7 Days"),

    LAST30DAYS("last30days", 30, "Last 30 Days"),

    LAST60DAYS("last60days", 60, "Last 60 Days"),

    LAST90DAYS("last90days", 90, "Last 90 Days"),

    LAST30DAYS_30("last30days-30", -1, "Last 30 Days - 30"),

    LAST60DAYS_60("last60days-60", -1, "Last 60 Days - 60"),

    THISMONTH("thismonth", -1, "This Month"),

    THISYEAR("thisyear", -1, "This Year"),

    LASTMONTH("lastmonth", -1, "Last Month"),

    ALLTIME("alltime", -1, "All Time"),

    LAST12MONTHS("last12months", -1, "Last 12 Months");

    private String value;

    private int offset;

    private String text;

    private TimePeriod(String value, int offset, String text)
    {
        this.value = value;
        this.offset = offset;
        this.text = text;
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

    public static TimePeriod fromValue(String value)
    {
        if (value != null)
        {
            for (TimePeriod instance : TimePeriod.values())
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

    public String getText()
    {
        return this.text;
    }

    public DateTime dateFrom()
    {
        if (this == TimePeriod.LATESTDAY || this == TimePeriod.LAST7DAYS || this == TimePeriod.LAST30DAYS
                || this == TimePeriod.LAST60DAYS || this == TimePeriod.LAST90DAYS)
        {
            return new DateTime(DateTimeZone.UTC).minusDays(this.offset).withTimeAtStartOfDay();
        }
        else if (this == TimePeriod.THISMONTH)
        {
            DateTime currentDate = new DateTime(DateTimeZone.UTC);

            return new DateTime().withYear(currentDate.getYear()).withMonthOfYear(currentDate.getMonthOfYear())
                    .withDayOfMonth(currentDate.dayOfMonth().getMinimumValue()).withTimeAtStartOfDay();

        }
        else if (this == TimePeriod.LASTMONTH)
        {
            DateTime lastMonthDate = new DateTime(DateTimeZone.UTC).minusMonths(1);

            return new DateTime().withYear(lastMonthDate.getYear()).withMonthOfYear(lastMonthDate.getMonthOfYear())
                    .withDayOfMonth(lastMonthDate.dayOfMonth().getMinimumValue()).withTimeAtStartOfDay();
        }
        else if (this == TimePeriod.THISYEAR)
        {
            DateTime currentDate = new DateTime(DateTimeZone.UTC);

            return new DateTime().withYear(currentDate.getYear()).withMonthOfYear(1).withDayOfMonth(1)
                    .withTimeAtStartOfDay();

        }
        else if (this == TimePeriod.ALLTIME)
        {
            return new DateTime(DateTimeZone.UTC).withYear(2015).withMonthOfYear(04).withDayOfMonth(01)
                    .withTimeAtStartOfDay();
        }
        else if (this == TimePeriod.LAST12MONTHS)
        {
            DateTime last12MonthDate = new DateTime(DateTimeZone.UTC).minusMonths(12);
            return new DateTime().withYear(last12MonthDate.getYear()).withMonthOfYear(last12MonthDate.getMonthOfYear())
                .withDayOfMonth(last12MonthDate.dayOfMonth().getMinimumValue()).withTimeAtStartOfDay();
        }

        return new DateTime();
    }

    public DateTime dateTo()
    {
        DateTime currentDate = new DateTime(DateTimeZone.UTC).withTimeAtStartOfDay();
        if (this == TimePeriod.LATESTDAY || this == TimePeriod.LAST7DAYS || this == TimePeriod.LAST30DAYS
                || this == TimePeriod.LAST60DAYS || this == TimePeriod.LAST90DAYS || this == TimePeriod.THISMONTH)
        {
            return currentDate.minusDays(1);
        }
        else if (this == TimePeriod.LASTMONTH || this == TimePeriod.LAST12MONTHS)
        {
            DateTime lastMonthDate = currentDate.minusMonths(1);

            return new DateTime().withYear(lastMonthDate.getYear()).withMonthOfYear(lastMonthDate.getMonthOfYear())
                    .withDayOfMonth(lastMonthDate.dayOfMonth().getMaximumValue()).millisOfDay().withMaximumValue();
        }
        else if (this == TimePeriod.THISYEAR)
        {
            return currentDate.minusDays(1);
        }
        else if (this == TimePeriod.ALLTIME)
        {
            return currentDate.minusDays(1);
        }

        return currentDate.minusDays(1);
    }
}
