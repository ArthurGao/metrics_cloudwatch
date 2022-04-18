package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ConsolidatedContextMetricsPK implements Serializable
{

    private static final long serialVersionUID = -105680996339552545L;

    @Id
    @Column(name = "country")
    private String country;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Id
    @Column(name = "genre_id")
    private Integer genreId;

    @Id
    @Column(name = "metric")
    private String metric;

    @Id
    @Column(name = "range_key")
    private String rangeKey;

    @Id
    @Column(name = "short_id")
    private long shortId;

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getGenreId()
    {
        return genreId;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }

    public String getMetric()
    {
        return metric;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

    public String getRangeKey()
    {
        return rangeKey;
    }

    public void setRangeKey(String rangeKey)
    {
        this.rangeKey = rangeKey;
    }

    public long getShortId()
    {
        return shortId;
    }

    public void setShortId(long shortId)
    {
        this.shortId = shortId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((genreId == null) ? 0 : genreId.hashCode());
        result = prime * result + ((metric == null) ? 0 : metric.hashCode());
        result = prime * result + ((rangeKey == null) ? 0 : rangeKey.hashCode());
        result = prime * result + (int) (shortId ^ (shortId >>> 32));
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
        ConsolidatedContextMetricsPK other = (ConsolidatedContextMetricsPK) obj;
        if (country == null)
        {
            if (other.country != null)
                return false;
        }
        else if (!country.equals(other.country))
            return false;
        if (date == null)
        {
            if (other.date != null)
                return false;
        }
        else if (!date.equals(other.date))
            return false;
        if (genreId == null)
        {
            if (other.genreId != null)
                return false;
        }
        else if (!genreId.equals(other.genreId))
            return false;
        if (metric == null)
        {
            if (other.metric != null)
                return false;
        }
        else if (!metric.equals(other.metric))
            return false;
        if (rangeKey == null)
        {
            if (other.rangeKey != null)
                return false;
        }
        else if (!rangeKey.equals(other.rangeKey))
            return false;
        if (shortId != other.shortId)
            return false;
        return true;
    }

}
