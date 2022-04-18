package com.parrotanalytics.api.apidb_model.comparators;

import java.util.Comparator;

import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class DisplayNameSort implements Comparator<CatalogItem>
{
    @Override
    public int compare(CatalogItem i1, CatalogItem i2)
    {
        return i1.getDisplayName().compareToIgnoreCase(i2.getDisplayName());
    }
}
