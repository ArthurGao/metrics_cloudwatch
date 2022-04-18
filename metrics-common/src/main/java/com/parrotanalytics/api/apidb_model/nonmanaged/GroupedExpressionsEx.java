package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.Date;
import java.util.List;

/**
 * Extension of GroupedExpressions to support associated date range
 * 
 * @author Minh Vu
 *
 */
public class GroupedExpressionsEx extends GroupedExpressions
{

    /**
     * 
     */
    private static final long serialVersionUID = -6309562990306253866L;

    private List<Date> dateRange;

    public GroupedExpressionsEx(GroupedExpressions ge, List<Date> dateRange)
    {
        super(ge.getDate(), ge.getShort_id(), ge.getCountry(), ge.getDexpercapita(), ge.getPeak_dexpercapita(),
                ge.getDex(), ge.getPeak_dex(), ge.getOverall_rank(), ge.getFilter_rank(), ge.getRank_by_peak(), ge.getBest_rank(),
                ge.getNum_days());
        this.dateRange = dateRange;
    }

    public List<Date> getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(List<Date> dateRange)
    {
        this.dateRange = dateRange;
    }

}
