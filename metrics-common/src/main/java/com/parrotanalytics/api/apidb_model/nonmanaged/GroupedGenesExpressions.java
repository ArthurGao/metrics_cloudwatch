package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;

import org.apache.commons.math3.util.Precision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.datasourceint.dsdb.model.base.GroupedDataEntity;

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
@SqlResultSetMapping(name = "GroupedGenesExpressions", classes =
{
        @ConstructorResult(targetClass = GroupedGenesExpressions.class, columns =
        {
                @ColumnResult(name = "date", type = Date.class),

                @ColumnResult(name = "country", type = String.class),

                @ColumnResult(name = "firstlevelgene", type = String.class),

                @ColumnResult(name = "secondlevelgene", type = String.class),

                @ColumnResult(name = "aggregated_dex", type = Double.class)
        })
})
public class GroupedGenesExpressions extends GroupedDataEntity implements Serializable
{
    private static final long serialVersionUID = -3976869290812933554L;

    @JsonIgnore
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @JsonIgnore
    private static final int ROUNDING_UPTO2 = 2;

    @JsonIgnore
    private static final int ROUNDING_UPTO8 = 8;

    private String date;

    private String country;

    private String firstlevelgene;

    private String secondlevelgene;

    @JsonProperty("aggregated_dex")
    private double aggregated_dex;

    public GroupedGenesExpressions()
    {
        super();
    }

    public GroupedGenesExpressions(Date date, String country, String firstlevelgene, Double aggregated_dex)
    {
        this.date = dateFormat.format(date);
        this.country = country;
        this.firstlevelgene = firstlevelgene;
        this.aggregated_dex = Precision.round(aggregated_dex, ROUNDING_UPTO8);
    }

    public GroupedGenesExpressions(Date date, String country, String firstlevelgene, String secondlevelgene,
            Double aggregated_dex)
    {
        this.date = dateFormat.format(date);
        this.country = country;
        this.firstlevelgene = firstlevelgene;
        this.secondlevelgene = secondlevelgene;
        this.aggregated_dex = Precision.round(aggregated_dex, ROUNDING_UPTO8);
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
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

    public String getFirstlevelgene()
    {
        return firstlevelgene;
    }

    public void setFirstlevelgene(String firstlevelgene)
    {
        this.firstlevelgene = firstlevelgene;
    }

    public String getSecondlevelgene()
    {
        return secondlevelgene;
    }

    public void setSecondlevelgene(String secondlevelgene)
    {
        this.secondlevelgene = secondlevelgene;
    }

    public double getAggregated_dex()
    {
        return aggregated_dex;
    }

    public void setAggregated_dex(double aggregated_dex)
    {
        this.aggregated_dex = aggregated_dex;
    }
}
