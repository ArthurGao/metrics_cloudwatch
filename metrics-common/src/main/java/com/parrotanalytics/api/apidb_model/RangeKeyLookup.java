package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;

@Entity
@IdClass(value = RangeKeyLookupPK.class)
@Table(name = TableConstants.RANGE_KEY_LOOKUP, schema = TableConstants.METRICS_SCHEMA)
public class RangeKeyLookup extends RDSBaseEntity
{

    private static final long serialVersionUID = -1854327358032159466L;
    @Id
    @Column(name = "interval_type")
    private String intervalType;

    @Temporal(TemporalType.DATE)
    private Date start;

    @Temporal(TemporalType.DATE)
    private Date end;

    @Id
    @Column(name = "range_key")
    private String rangeKey;

    @Column(name = "precomputed")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean precomputed;

    public RangeKeyLookup()
    {
        // default constructor
    }

    public String getIntervalType()
    {
        return intervalType;
    }

    public void setIntervalType(String intervalType)
    {
        this.intervalType = intervalType;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getEnd()
    {
        return end;
    }

    public void setEnd(Date end)
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

    public boolean isPrecomputed()
    {
        return precomputed;
    }

    public void setPrecomputed(boolean precomputed)
    {
        this.precomputed = precomputed;
    }

}
