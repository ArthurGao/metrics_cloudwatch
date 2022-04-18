package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.BreakdownDailyPK;
import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.datasourceint.dsdb.model.base.NonManagedEntity;
import com.rits.cloning.Cloner;

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
@SqlResultSetMapping(name = "GroupedBreakdown", classes =
{
        @ConstructorResult(targetClass = GroupedBreakdown.class, columns =
        {
                @ColumnResult(name = "date", type = Date.class),

                @ColumnResult(name = "country", type = String.class),

                @ColumnResult(name = "short_id", type = Long.class),

                @ColumnResult(name = "social", type = Double.class),

                @ColumnResult(name = "video", type = Double.class),

                @ColumnResult(name = "piracy", type = Double.class),

                @ColumnResult(name = "research", type = Double.class)
        })
})
@IdClass(value = BreakdownDailyPK.class)
public class GroupedBreakdown implements NonManagedEntity
{

    /**
     * 
     */
    private static final long serialVersionUID = 5173456978880491418L;

    @JsonIgnore
    private static final int ROUNDING_UPTO2 = 2;

    @JsonIgnore
    private static final int ROUNDING_UPTO8 = 8;

    @Id
    @Temporal(TemporalType.DATE)
    private Date date;

    @Id
    private String country;

    @Id
    private long short_id;

    @JsonProperty("social")
    private double social;

    @JsonProperty("video")
    private double video;

    @JsonProperty("research")
    private double research;

    @JsonProperty("piracy")
    private double piracy;

    public GroupedBreakdown()
    {
        super();
    }

    public GroupedBreakdown(String country, long short_id, double social, double video, double research, double piracy)
    {
        this(null, country, short_id, social, video, research, piracy);
    }

    public GroupedBreakdown(Date date, String country, long short_id, double social, double video, double research,
            double piracy)
    {
        this.date = date;
        this.country = country;
        this.short_id = short_id;
        this.social = social;
        this.video = video;
        this.research = research;
        this.piracy = piracy;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
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

    public double getSocial()
    {
        return social;
    }

    public void setSocial(double social)
    {
        this.social = social;
    }

    public double getVideo()
    {
        return video;
    }

    public void setVideo(double video)
    {
        this.video = video;
    }

    public double getResearch()
    {
        return research;
    }

    public void setResearch(double research)
    {
        this.research = research;
    }

    @Override
    public GroupedBreakdown clone()
    {
        return new Cloner().deepClone(this);
    }

    public double getPiracy()
    {
        return piracy;
    }

    public void setPiracy(double piracy)
    {
        this.piracy = piracy;
    }
}
