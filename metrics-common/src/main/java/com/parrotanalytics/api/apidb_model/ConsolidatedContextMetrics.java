package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import javax.persistence.Transient;

/**
 * 
 * @author minhvu
 *
 */
@Entity
@Table(name = TableConstants.CONSOLIDATED_CONTEXTMETRICS, schema = TableConstants.METRICS_SCHEMA)
@IdClass(ConsolidatedContextMetricsPK.class)
public class ConsolidatedContextMetrics extends RDSBaseEntity
{

    private static final long serialVersionUID = -3250855023602114463L;

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

    @Column(name = "`rank`")
    private int rank;

    @Id
    @Column(name = "short_id")
    private long shortId;

    @Column(name = "times_average")
    private Double timesAverage;

    @Column(name = "value")
    private Double value;

    @Transient
    private int filterRank;

    public String getCountry()
    {
        return country;
    }

    public Date getDate()
    {
        return date;
    }

    public Integer getGenreId()
    {
        return genreId;
    }

    public String getMetric()
    {
        return metric;
    }

    public String getRangeKey()
    {
        return rangeKey;
    }

    public int getRank()
    {
        return rank;
    }

    public long getShortId()
    {
        return shortId;
    }

    public Double getTimesAverage()
    {
        return timesAverage;
    }

    public Double getValue()
    {
        return value;
    }

    public int getFilterRank()
    {
        return filterRank;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

    public void setRangeKey(String rangeKey)
    {
        this.rangeKey = rangeKey;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public void setShortId(long shortId)
    {
        this.shortId = shortId;
    }

    public void setTimesAverage(Double timesAverage)
    {
        this.timesAverage = timesAverage;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

    public void setFilterRank(int filterRank)
    {
        this.filterRank = filterRank;
    }

}
