package com.parrotanalytics.api.apidb_model.comparators;

import java.util.Comparator;

import org.joda.time.DateTime;

import com.parrotanalytics.api.apidb_model.ExpressionsDaily;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class DateComparator implements Comparator<ExpressionsDaily>
{

    @Override
    public int compare(ExpressionsDaily p1, ExpressionsDaily p2)
    {
        if (new DateTime(p1.getDate()).isBefore(new DateTime(p2.getDate())))
            return -1;
        if (new DateTime(p1.getDate()).isAfter(new DateTime(p2.getDate())))
            return 1;
        return 0;
    }

}
