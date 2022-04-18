package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.api.apidb_model.ExpressionsDailyPK;
import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.datasourceint.dsdb.model.base.NonManagedEntity;
import com.rits.cloning.Cloner;
import org.apache.commons.math3.util.Precision;
import org.parboiled.common.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Bean for holding average ratings records from JPQL constructor based queries.
 * This should be used when {@link ExpressionsDaily} cannot be used for mapping
 * JPA/JPQL queries results directly.
 * 
 * 
 * @author Sanjeev Sharma
 * @author Minh Vu
 *
 */
@Entity
@SqlResultSetMapping(name = "GroupedExpressions", classes =
{
        @ConstructorResult(targetClass = GroupedExpressions.class, columns =
        {
                @ColumnResult(name = "date", type = Date.class),

                @ColumnResult(name = "short_id", type = Long.class),

                @ColumnResult(name = "country", type = String.class),

                @ColumnResult(name = "dexpercapita", type = Double.class),

                @ColumnResult(name = "peak_dexpercapita", type = Double.class),

                @ColumnResult(name = "dex", type = Double.class),

                @ColumnResult(name = "peak_dex", type = Double.class),

                @ColumnResult(name = "overall_rank", type = Integer.class),

                @ColumnResult(name = "best_rank", type = Integer.class),

                @ColumnResult(name = "rank_by_peak", type = Integer.class),

                @ColumnResult(name = "num_days", type = Integer.class),

        })
})
@IdClass(value = ExpressionsDailyPK.class)
public class GroupedExpressions implements NonManagedEntity
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
    private String country;

    @Id
    private long short_id;

    private Portfolio portfolio;

    @JsonProperty("dexpercapita")
    private double dexpercapita;

    @JsonProperty("peak_dexpercapita")
    private double peak_dexpercapita;

    @JsonProperty("dex")
    private double dex;

    @JsonProperty("peak_dex")
    private double peak_dex;

    @JsonProperty("overall_rank")
    private int overall_rank;

    @JsonProperty("filter_rank")
    private int filter_rank;

    @JsonProperty("best_rank")
    private int best_rank;

    @JsonProperty("rank_by_peak")
    private int rank_by_peak;

    private Double demand_change = 0.0;

    private Integer rank_change = 0;

    private double time_average;

    // by default the num_days for this group expressions is 1 day
    private int num_days = 1;

    public GroupedExpressions()
    {
        super();
    }

    public GroupedExpressions(Date date, long short_id, String country, Double dexpercapita, Double peak_dexpercapita,
            Double dex, Double peak_dex, Integer overall_rank, Integer filter_rank, Integer rank_by_peak, Integer best_rank, Long num_days)
    {
        this(date, short_id, country, dexpercapita, peak_dexpercapita, dex, peak_dex, overall_rank, filter_rank, rank_by_peak,
                best_rank, num_days != null ? (Integer) num_days.intValue() : 0);
    }

    public GroupedExpressions(Date date, long short_id, String country, Double dexpercapita, Double peak_dexpercapita,
            Double dex, Double peak_dex, Integer overall_rank, Integer filter_rank, Integer rank_by_peak, Integer best_rank,
            Integer num_days)
    {
        this.date = date;
        this.short_id = short_id;
        this.country = StringUtils.isNotEmpty(country) ? country : null;
        this.dexpercapita = dexpercapita != null ? Precision.round(dexpercapita, ROUNDING_UPTO8) : 0.0;
        this.peak_dexpercapita = peak_dexpercapita != null ? Precision.round(peak_dexpercapita, ROUNDING_UPTO8) : 0.0;
        this.dex = dex != null ? Precision.round(dex, ROUNDING_UPTO2) : 0.0;
        this.peak_dex = peak_dex != null ? Precision.round(peak_dex, ROUNDING_UPTO2) : 0.0;
        this.overall_rank = overall_rank != null ? overall_rank.intValue() : 0;
        this.filter_rank = filter_rank != null ? filter_rank.intValue() : 0;
        this.rank_by_peak = rank_by_peak != null ? rank_by_peak.intValue() : 0;
        this.best_rank = best_rank != null ? best_rank.intValue() : 0;
        this.num_days = num_days != null ? num_days.intValue() : 0;
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

    public Portfolio getPortfolio()
    {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio)
    {
        this.portfolio = portfolio;
    }

    public double getDexpercapita()
    {
        return dexpercapita;
    }

    public void setDexpercapita(double dexpercapita)
    {
        this.dexpercapita = dexpercapita;
    }

    public double getPeak_dexpercapita()
    {
        return peak_dexpercapita;
    }

    public void setPeak_dexpercapita(double peak_dexpercapita)
    {
        this.peak_dexpercapita = peak_dexpercapita;
    }

    public double getDex()
    {
        return dex;
    }

    public void setDex(double dex)
    {
        this.dex = dex;
    }

    public double getPeak_dex()
    {
        return peak_dex;
    }

    public void setPeak_dex(double peak_dex)
    {
        this.peak_dex = peak_dex;
    }

    public int getOverall_rank()
    {
        return overall_rank;
    }

    public void setOverall_rank(int overall_rank)
    {
        this.overall_rank = overall_rank;
    }

    public int getFilter_rank()
    {
        return filter_rank;
    }

    public void setFilter_rank(int filter_rank)
    {
        this.filter_rank = filter_rank;
    }

    public GroupedExpressions withOverall_rank(int overall_rank)
    {
        this.overall_rank = overall_rank;
        return this;
    }

    public Double getDemand_change()
    {
        return demand_change;
    }

    public void setDemand_change(Double demand_change)
    {
        this.demand_change = demand_change;
    }

    public double getTime_average()
    {
        return time_average;
    }

    public void setTime_average(double time_average)
    {
        this.time_average = time_average;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((portfolio == null) ? 0 : portfolio.hashCode());
        result = prime * result + (int) (short_id ^ (short_id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupedExpressions other = (GroupedExpressions) obj;
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
        if (portfolio == null)
        {
            if (other.portfolio != null)
                return false;
        }
        else if (!portfolio.equals(other.portfolio))
            return false;
        if (short_id != other.short_id)
            return false;
        return true;
    }

    @Override
    public GroupedExpressions clone()
    {
        return new Cloner().deepClone(this);
    }

    public Integer getRank_change()
    {
        return rank_change;
    }

    public void setRank_change(Integer rank_change)
    {
        this.rank_change = rank_change;
    }

    public int getBest_rank()
    {
        return best_rank;
    }

    public void setBest_rank(int best_rank)
    {
        this.best_rank = best_rank;
    }

    public int getRank_by_peak()
    {
        return rank_by_peak;
    }

    public void setRank_by_peak(int rank_by_peak)
    {
        this.rank_by_peak = rank_by_peak;
    }

    public int getNum_days()
    {
        return num_days;
    }

    public void setNum_days(int num_days)
    {
        this.num_days = num_days;
    }
}
