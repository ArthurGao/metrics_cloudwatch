package com.parrotanalytics.api.apidb_model.nonmanaged;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateRangeKey implements Serializable
{

    private DateTime start;

    public DateRangeKey(DateTime start, DateTime end, String rangeKey)
    {
        super();
        this.start = start;
        this.end = end;
        this.rangeKey = rangeKey;
    }

    private DateTime end;

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((rangeKey == null) ? 0 : rangeKey.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRangeKey other = (DateRangeKey) obj;
        if (end == null)
        {
            if (other.end != null)
                return false;
        }
        else if (!end.equals(other.end))
            return false;
        if (rangeKey != other.rangeKey)
            return false;
        if (start == null)
        {
            if (other.start != null)
                return false;
        }
        else if (!start.equals(other.start))
            return false;
        return true;
    }

    public DateTime getStart()
    {
        return start;
    }

    public void setStart(DateTime start)
    {
        this.start = start;
    }

    public DateTime getEnd()
    {
        return end;
    }

    public void setEnd(DateTime end)
    {
        this.end = end;
    }

    public String getRangeKey()
    {
        return rangeKey;
    }

    public void setRangeKey(String rangeKey)
    {
        this.rangeKey = rangeKey;
    }

    private String rangeKey;

    public List<Date> toDateRangeList()
    {
        List<Date> dateRangeList = new ArrayList<Date>();

        if (start != null && end == null)
        {
            dateRangeList.add(start.toDate());
        }
        else if (start != null && end != null)
        {
            for (DateTime d = start; d.isBefore(end.plusDays(1)); )
            {
                dateRangeList.add(d.toDate());
                d = d.plusDays(1);
            }
        }

        return dateRangeList;
    }

    @Override
    public String toString()
    {
        return "DateRangePartition [start=" + start + ", end=" + end + ", interval=" + rangeKey + "]";
    }

}
