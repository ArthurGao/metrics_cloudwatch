package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.parrotanalytics.api.commons.constants.Interval;

public class DateRangePartition
{

    private DateTime start;

    public DateRangePartition(DateTime start, DateTime end, String interval)
    {
        super();
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    private DateTime end;

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((interval == null) ? 0 : interval.hashCode());
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
        DateRangePartition other = (DateRangePartition) obj;
        if (end == null)
        {
            if (other.end != null)
                return false;
        }
        else if (!end.equals(other.end))
            return false;
        if (interval != other.interval)
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

    public String getInterval()
    {
        return interval;
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    public Interval getIntervalEnum()
    {
        return Interval.fromValue(interval);
    }

    private String interval;

    public List<Date> toDateRangeList()
    {
        List<Date> dateRangeList = new ArrayList<Date>();

        if (start != null && end == null)
        {
            dateRangeList.add(start.toDate());
        }
        else if (start != null && end != null)
        {
            for (DateTime d = start; d.isBefore(end.plusDays(1));)
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
        return "DateRangePartition [start=" + start + ", end=" + end + ", interval=" + interval + "]";
    }

}
