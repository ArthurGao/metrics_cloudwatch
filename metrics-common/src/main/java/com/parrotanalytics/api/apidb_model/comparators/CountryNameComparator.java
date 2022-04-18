package com.parrotanalytics.api.apidb_model.comparators;

import java.util.Comparator;

import com.parrotanalytics.api.apidb_model.Country;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class CountryNameComparator implements Comparator<Country>
{

    @Override
    public int compare(Country c1, Country c2)
    {
        return c1.getDisplayName().compareToIgnoreCase(c2.getDisplayName());
    }

}
