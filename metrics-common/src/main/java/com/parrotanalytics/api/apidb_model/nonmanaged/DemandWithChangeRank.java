package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.io.Serializable;

public class DemandWithChangeRank extends GroupedExpressions implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -8754736611174103250L;

    private Double demand_change;

    private double time_average;
    private Integer rank;

    public DemandWithChangeRank(GroupedExpressions expr, Double demand_change, Integer rank)
    {
        super(expr.getDate(), expr.getShort_id(), expr.getCountry(), expr.getDexpercapita(),
                expr.getPeak_dexpercapita(), expr.getDex(), expr.getPeak_dex(), expr.getOverall_rank(),
                expr.getFilter_rank(), expr.getRank_by_peak(), expr.getBest_rank(), expr.getNum_days());
        this.demand_change = demand_change;
        this.rank = rank;
    }

    public Double getDemand_change()
    {
        return demand_change;
    }

    public void setDemand_change(Double demand_change)
    {
        this.demand_change = demand_change;
    }

    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    public double getTime_average()
    {
        return time_average;
    }

    public void setTime_average(double time_average)
    {
        this.time_average = time_average;
    }
}
