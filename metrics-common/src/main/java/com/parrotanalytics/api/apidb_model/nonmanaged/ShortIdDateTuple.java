package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.Date;

public class ShortIdDateTuple
{

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((shortId == null) ? 0 : shortId.hashCode());
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
        ShortIdDateTuple other = (ShortIdDateTuple) obj;
        if (date == null)
        {
            if (other.date != null)
                return false;
        }
        else if (!date.equals(other.date))
            return false;
        if (shortId == null)
        {
            if (other.shortId != null)
                return false;
        }
        else if (!shortId.equals(other.shortId))
            return false;
        return true;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Long getShortId()
    {
        return shortId;
    }

    public void setShortId(Long shortId)
    {
        this.shortId = shortId;
    }

    public Date date;

    public Long shortId;

    public ShortIdDateTuple(Date date, Long shortId)
    {
        this.date = date;
        this.shortId = shortId;
    }
}
