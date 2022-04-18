package com.parrotanalytics.api.data.repo.api.custom.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedBreakdown;
import com.parrotanalytics.api.data.repo.api.custom.BreakdownRepositoryCustom;

public class BreakdownRepositoryImpl implements BreakdownRepositoryCustom
{

    private static final String BREAKDOWN_BUCKET_CHANGE_QUERY = "sql/breakdown_bucket_change_query.sql";
    private static String breadownBucketChangeQuery;
    static
    {
        try
        {

            breadownBucketChangeQuery = IOUtils.toString(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(BREAKDOWN_BUCKET_CHANGE_QUERY),
                    "UTF-8");
        }
        catch (IOException e)
        {
        }
    }

    @PersistenceContext(unitName = "PARROT_API")
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<GroupedBreakdown> showDemandBreakdownAverageChange(List<Date> lastPeriodDateRange,
            List<Date> thisPeriodDateRange, List<String> marketsList, Long shortID, PageRequest pageRequest)
    {
        String liveQuery = new String(breadownBucketChangeQuery);

        Sort sort = pageRequest.getSort();

        Iterator<Order> sortItr = sort.iterator();
        while (sortItr.hasNext())
        {
            Order order = sortItr.next();
            String orderByField = "( a." + order.getProperty() + "- b." + order.getProperty() + ") / a."
                    + order.getProperty();

            liveQuery = liveQuery.replaceAll(":order_by_field:", "abs(" + orderByField + ")");
            liveQuery = liveQuery.replaceAll(":direction:", order.getDirection().isAscending() ? "ASC" : "DESC");
        }
        liveQuery = liveQuery.replaceAll(":date:",
                new DateTime(thisPeriodDateRange.get(thisPeriodDateRange.size() - 1)).toString("yyyy-MM-dd"));
        liveQuery = liveQuery.replaceAll(":page:", String.valueOf(pageRequest.getPageNumber()));
        liveQuery = liveQuery.replaceAll(":size:", String.valueOf(pageRequest.getPageSize()));

        Query nativeQuery = entityManager.createNativeQuery(liveQuery, GroupedBreakdown.class)
                .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        nativeQuery.setParameter("short_id", shortID);
        nativeQuery.setParameter("countries", marketsList);
        nativeQuery.setParameter("this_date_range", thisPeriodDateRange);
        nativeQuery.setParameter("last_date_range", lastPeriodDateRange);

        List<GroupedBreakdown> resultList = nativeQuery.getResultList();

        return resultList;
    }

}
