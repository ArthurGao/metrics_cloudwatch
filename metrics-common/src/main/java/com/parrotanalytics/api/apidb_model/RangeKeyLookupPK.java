package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class RangeKeyLookupPK implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 8329135192660126343L;

    @Id
    @Column(name = "interval_type")
    private String intervalType;

    @Id
    @Column(name = "range_key")
    private String rangeKey;

    public String getIntervalType()
    {
        return intervalType;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((intervalType == null) ? 0 : intervalType.hashCode());
        result = prime * result + ((rangeKey == null) ? 0 : rangeKey.hashCode());
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
        RangeKeyLookupPK other = (RangeKeyLookupPK) obj;
        if (intervalType == null)
        {
            if (other.intervalType != null)
                return false;
        }
        else if (!intervalType.equals(other.intervalType))
            return false;
        if (rangeKey == null)
        {
            if (other.rangeKey != null)
                return false;
        }
        else if (!rangeKey.equals(other.rangeKey))
            return false;
        return true;
    }

    public void setIntervalType(String intervalType)
    {
        this.intervalType = intervalType;
    }

    public String getRangeKey()
    {
        return rangeKey;
    }

    public void setRangeKey(String rangeKey)
    {
        this.rangeKey = rangeKey;
    }
}
