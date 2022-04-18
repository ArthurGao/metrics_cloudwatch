package com.parrotanalytics.api.apidb_model.comparators;

import java.util.Comparator;

import com.parrotanalytics.api.apidb_model.ExpressionsDaily;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class PDEComparator implements Comparator<ExpressionsDaily>
{

    @Override
    public int compare(ExpressionsDaily p1, ExpressionsDaily p2)
    {
        if (p1.getReal_de() < p2.getReal_de())
            return -1;
        if (p1.getReal_de() > p2.getReal_de())
            return 1;
        return 0;
    }

}
