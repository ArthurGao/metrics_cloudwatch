package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.datasourceint.dsdb.model.base.NonManagedEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@SqlResultSetMapping(name = "MetricRankExpressions", classes =
{
        @ConstructorResult(targetClass = MetricRankExpressions.class, columns =
        {
                @ColumnResult(name = "computed_on", type = Date.class),

                @ColumnResult(name = "range_key", type = String.class),

                @ColumnResult(name = "country", type = String.class),

                @ColumnResult(name = "short_id", type = Long.class),

                @ColumnResult(name = "genre", type = String.class),

                @ColumnResult(name = "value", type = Double.class),

                @ColumnResult(name = "rank", type = Integer.class)

        })
})
public class MetricRankExpressions implements NonManagedEntity
{
    private static final long serialVersionUID = -7875574157498637786L;
    @Id
    @JsonProperty("computed_on")
    private Date computed_on;

    @Id
    @JsonProperty("range_key")
    private String range_key;

    @Id
    @JsonProperty("country")
    private String country;

    @Id
    @JsonProperty("short_id")
    private long short_id;

    @Id
    @JsonProperty("genre")
    private String genre;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("value")
    private Double value;

    public MetricRankExpressions()
    {

    }

    public MetricRankExpressions(Date computed_on, String range_key, String country, long short_id, String genre,
            Double value, int rank)
    {
        this.computed_on = computed_on;
        this.range_key = range_key;
        this.country = country;
        this.short_id = short_id;
        this.genre = genre;
        this.value = value;
        this.rank = rank;
    }

    public String getRange_key()
    {
        return range_key;
    }

    public void setRange_key(String range_key)
    {
        this.range_key = range_key;
    }

    public Date getComputed_on()
    {
        return computed_on;
    }

    public void setComputed_on(Date computed_on)
    {
        this.computed_on = computed_on;
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

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

}
