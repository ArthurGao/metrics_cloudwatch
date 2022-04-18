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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@SqlResultSetMapping(name = "GenePerformance", classes =
{
        @ConstructorResult(targetClass = GenePerformance.class, columns =
        {
                @ColumnResult(name = "date", type = Date.class),

                @ColumnResult(name = "country", type = String.class),

                @ColumnResult(name = "gene", type = String.class),

                @ColumnResult(name = "genevalue", type = String.class),

                @ColumnResult(name = "country_avg", type = Double.class),

                @ColumnResult(name = "global_avg", type = Double.class),

                @ColumnResult(name = "vs_percent", type = Double.class)
        })
})
@JsonPropertyOrder(
{
        "date", "country", "gene", "genevalue", "country_avg", "global_avg", "vs_percent"
})
@JsonInclude(Include.NON_NULL)
public class GenePerformance implements NonManagedEntity
{

    /**
     * 
     */
    private static final long serialVersionUID = 5834267487843434313L;

    @JsonIgnore
    private static final int ROUNDING_UPTO2 = 2;

    @Id
    @Temporal(TemporalType.DATE)
    @JsonProperty("date")
    private Date date;

    @Id
    @JsonProperty("country")
    private String country;

    @Id
    @JsonProperty("gene")
    private String gene;

    @Id
    @JsonProperty("genevalue")
    private String genevalue;

    @Column
    @JsonProperty("country_avg")
    private double country_avg;

    @Column
    @JsonProperty("global_avg")
    private double global_avg;

    @Column
    @JsonProperty("vs_percent")
    private double vs_percent;

    public GenePerformance()
    {
        super();
    }

    public GenePerformance(Date date, String country, String gene, String genevalue, double country_avg,
            double global_avg, double vs_percent)
    {
        this.date = date;
        this.country = country;
        this.gene = gene;
        this.genevalue = genevalue;
        this.country_avg = Precision.round(country_avg, ROUNDING_UPTO2);
        this.global_avg = Precision.round(global_avg, ROUNDING_UPTO2);
        this.vs_percent = Precision.round(vs_percent, ROUNDING_UPTO2);
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

    public String getGene()
    {
        return gene;
    }

    public void setGene(String gene)
    {
        this.gene = gene;
    }

    public String getGenevalue()
    {
        return genevalue;
    }

    public void setGenevalue(String genevalue)
    {
        this.genevalue = genevalue;
    }

    public double getCountry_avg()
    {
        return country_avg;
    }

    public void setCountry_avg(double country_avg)
    {
        this.country_avg = country_avg;
    }

    public double getGlobal_avg()
    {
        return global_avg;
    }

    public void setGlobal_avg(double global_avg)
    {
        this.global_avg = global_avg;
    }

    public double getVs_percent()
    {
        return vs_percent;
    }

    public void setVs_percent(double vs_percent)
    {
        this.vs_percent = vs_percent;
    }

}
