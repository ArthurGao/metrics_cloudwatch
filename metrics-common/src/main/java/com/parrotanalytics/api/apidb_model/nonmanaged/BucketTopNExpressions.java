package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.math3.util.Precision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.datasourceint.dsdb.model.base.NonManagedEntity;

/**
 * Bean for holding average ratings records from JPQL constructor based queries.
 * This should be used when {@link ExpressionsDaily} cannot be used for mapping
 * JPA/JPQL queries results directly.
 * 
 * 
 * @author Sanjeev Sharma
 *
 */
@Entity
@SqlResultSetMapping(name = "BucketTopNExpressions", classes =
{
        @ConstructorResult(targetClass = BucketTopNExpressions.class, columns =
        {
                @ColumnResult(name = "date", type = Date.class),

                @ColumnResult(name = "bucket", type = String.class),

                @ColumnResult(name = "country", type = String.class),

                @ColumnResult(name = "short_id", type = Long.class),

                @ColumnResult(name = "bucket_demand", type = Double.class)
        })
})
public class BucketTopNExpressions implements NonManagedEntity
{

    /**
     * 
     */
    private static final long serialVersionUID = 5834267487843434313L;

    @JsonIgnore
    private static final int ROUNDING_UPTO2 = 2;

    @JsonIgnore
    private static final int ROUNDING_UPTO8 = 8;

    @Id
    @Temporal(TemporalType.DATE)
    private Date date;

    @Id
    private String bucket;

    @Id
    private String country;

    @Id
    private long short_id;

    @Column
    @JsonProperty("dexpercapita")
    private double dexpercapita;

    @Column
    @JsonProperty("bucket_demand")
    private double bucket_demand;

    public BucketTopNExpressions()
    {
        super();
    }

    public BucketTopNExpressions(Date date, String bucket, String country, long short_id, double bucket_demand)
    {
        this.date = date;
        this.bucket = bucket;
        this.country = country;
        this.short_id = short_id;
        this.bucket_demand = Precision.round(bucket_demand, ROUNDING_UPTO2);
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getBucket()
    {
        return bucket;
    }

    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(long short_id)
    {
        this.short_id = short_id;
    }

    public double getBucket_demand()
    {
        return bucket_demand;
    }

    public void setBucket_demand(double bucket_demand)
    {
        this.bucket_demand = bucket_demand;
    }

}
