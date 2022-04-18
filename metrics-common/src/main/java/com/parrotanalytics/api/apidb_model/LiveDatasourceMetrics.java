package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;

@Entity
@Table(name = TableConstants.LIVE_DATASOURCE_METRICS, schema = TableConstants.METRICS_SCHEMA)
@IdClass(value = LiveDatasourceMetricsPK.class)
public class LiveDatasourceMetrics extends RDSBaseEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -4873361824745789000L;

    @Id
    @Column(name = "short_id")
    private Long short_id;

    @Id
    @Column(name = "range_key")
    private String range_key;

    @Id
    @Column(name = "source")
    private String source;

    @Id
    @Column(name = "metric")
    private String metric;

    @Column(name = "value")
    private double value;

    public String getRange_key()
    {
        return range_key;
    }

    public void setRange_key(String range_key)
    {
        this.range_key = range_key;
    }

    public Long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(Long short_id)
    {
        this.short_id = short_id;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getMetric()
    {
        return metric;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }
}
