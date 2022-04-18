package com.parrotanalytics.api.apidb_model.comparators;

import java.util.Comparator;

import org.springframework.data.domain.Sort.Direction;

import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.apicore.config.APIConstants;

/**
 * 
 * @author Minh Vu
 *
 */
public final class GroupedExpressionsComparator implements Comparator<GroupedExpressions>
{

    protected enum CompareOperator
    {
        //this is the heating up sort by new logic:
        //sort by best positive change in rank
        //we should change the sort_by: heating_up to natually fits the purpose
        DEMAND_CHANGE("demand_change")
        {
            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                return Double.compare(o1.getRank_change(), o2.getRank_change());
            }
        },
        DEXPERCAPITA(APIConstants.DEXPERCAPITA)
        {

            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                return Double.compare(o1.getDexpercapita(), o2.getDexpercapita());
            }

        },
        PEAK_DEXPERCAPITA(APIConstants.PEAK_DEXPERCAPITA)
        {

            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                return Double.compare(o1.getPeak_dexpercapita(), o2.getPeak_dexpercapita());
            }

        },
        SUM_DEXPERCAPITA(APIConstants.SUM_DEXPERCAPITA)
        {
            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                Portfolio p1 = o1.getPortfolio();
                Portfolio p2 = o2.getPortfolio();
                if (p1 != null && p2 != null)
                {
                    return Double.compare(o1.getDexpercapita() * p1.getShortIDList().size(),
                            o2.getDexpercapita() * p2.getShortIDList().size());
                }
                return Double.compare(o1.getDexpercapita() * o1.getNum_days(), o2.getDexpercapita() * o2.getNum_days());
            }

        },
        DEX(APIConstants.DEX)
        {
            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                return Double.compare(o1.getDex(), o2.getDex());
            }
        },
        PEAK_DEX(APIConstants.PEAK_DEX)
        {

            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                return Double.compare(o1.getPeak_dex(), o2.getPeak_dex());
            }

        },
        SUM_DEX(APIConstants.SUM_DEX)
        {

            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                Portfolio p1 = o1.getPortfolio();
                Portfolio p2 = o2.getPortfolio();
                if (p1 != null && p2 != null)
                {
                    return Double.compare(o1.getDex() * p1.getShortIDList().size(),
                            o2.getPeak_dex() * p2.getShortIDList().size());
                }
                return Double.compare(o1.getDex() * o1.getNum_days(), o2.getDex() * o2.getNum_days());
            }

        },
        DATE(APIConstants.DATE)
        {

            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                return o1.getDate().compareTo(o2.getDate());
            }
        },
        AVG_RAW_DE("AVG(e.raw_de)")
        {

            @Override
            public int compare(GroupedExpressions o1, GroupedExpressions o2)
            {
                return Double.compare(o1.getDexpercapita(), o2.getDexpercapita());
            }
        };

        private CompareOperator(String sortBy)
        {
            this.sortBy = sortBy;
        }

        public static CompareOperator fromValue(String value)
        {
            if (value != null)
            {
                for (CompareOperator instance : CompareOperator.values())
                {
                    if (value.equalsIgnoreCase(instance.sortBy))
                    {
                        return instance;
                    }
                }
            }
            return null;
        }

        private String sortBy;

        public abstract int compare(GroupedExpressions o1, GroupedExpressions o2);

    }

    private Direction dir;
    private CompareOperator compareOperator;

    public GroupedExpressionsComparator(String sortBy, Direction dir)
    {
        this.dir = dir;
        this.compareOperator = CompareOperator.fromValue(sortBy);
    }

    @Override
    public int compare(GroupedExpressions o1, GroupedExpressions o2)
    {
        int compare = compareOperator != null ? compareOperator.compare(o1, o2) : 0;
        return dir == Direction.ASC ? compare : -compare;
    }
}