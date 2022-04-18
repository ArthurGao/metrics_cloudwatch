package com.parrotanalytics.api.request.demand;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.JpaSort;

import com.parrotanalytics.api.request.titlecontext.ContextMetricType;
import com.parrotanalytics.apicore.config.APIConstants;

/**
 * Object to hold parrotratings sorting information
 *
 * @author Sanjeev Sharma
 * @author Minh Vu
 */
public class SortInfo
{

        public static final String DEX = "e.real_de";

        public static final String AVG_DEX = "SUM(e.real_de)/SUM(1)";

        public static final String SUM_DEX_BY_SUM_NUM_DAYS = "SUM(e.real_de)/SUM(e.num_days)";

        public static final String SUM_DEX = "SUM(e.real_de";

        public static final String PEAK_DEX = "MAX(e.peak_real_de)";

        public static final String MAX_DEX = "MAX(e.real_de)";

        public static final String DEXPERCAPITA = "e.raw_de";

        public static final String AVG_DEXPERCAPITA = "SUM(e.raw_de)/SUM(1)";

        public static final String SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS = "SUM(e.raw_de)/SUM(e.num_days)";

        public static final String PEAK_DEXPERCAPITA = "MAX(e.peak_raw_de)";

        public static final String MAX_DEXPERCAPITA = "MAX(e.raw_de)";

        public static final String SUM_DEXPERCAPITA = "SUM(e.raw_de)";

        public static final String MIN_OVERALL_RANK = "MIN(e.overall_rank)";

        public static final String MIN_RANK_BY_PEAK = "MIN(e.rank_by_peak)";

        public static final String MIN_BEST_RANK = "MIN(e.best_rank)";

        public static final String SUM_ONE = "SUM(1)";

        public static final String SUM_NUM_DAYS = "SUM(e.num_days)";

        public static final String DATE = "date";

        public static final String TIME_AVERAGES = "timesAverage";

        public static final String VALUE = "value";

        public static final String COUNTRY = "country";

        public static final String NATIVE_AVG_DEXPERCAPITA = "AVG(raw_de)";

        public static final String NATIVE_MAX_DEXPERCAPITA = "MAX(raw_de)";

        public static final String NATIVE_AVG_DEX = "AVG(real_de)";

        public static final String NATIVE_SUM_DEX_BY_SUM_NUM_DAYS = "SUM(real_de)/SUM(num_days)";

        public static final String NATIVE_MAX_DEX = "MAX(real_de)";

        public static final String NATIVE_PEAK_DEX = "MAX(peak_real_de)";

        public static final String NATIVE_PEAK_DEXPERCAPITA = "MAX(peak_raw_de)";

        public static final String NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS = "SUM(raw_de)/SUM(num_days)";

        public static final String NATIVE_MIN_OVERALL_RANK = "MIN(overall_rank)";

        public static final String NATIVE_MIN_BEST_RANK = "MIN(best_rank)";

        public static final String NATIVE_MIN_RANK_BY_PEAK = "MIN(rank_by_peak)";

        public static final String NATIVE_SUM_DAYS = "SUM(num_days)";

        public static final String NATIVE_DEXPERCAPITA = "raw_de";

        public static final String DE_PIRACY = ContextMetricType.De_Piracy.value();
        public static final String DE_RESEARCH = ContextMetricType.De_Research.value();
        public static final String DE_VIDEO = ContextMetricType.De_Video.value();
        public static final String DE_SOCIAL = ContextMetricType.De_Social.value();

        public static final String GTREND = ContextMetricType.GTrend.value();
        public static final String MOMENTUM = ContextMetricType.Momentum.value();
        public static final String TRAVELABILITY = ContextMetricType.Travelability.value();
        public static final String REACH = ContextMetricType.Reach.value();

        public static final String FRANCHISABILITY = ContextMetricType.Franchisability.value();
        public static final String LONGEVITY = ContextMetricType.Longevity.value();

        public static Sort TITLE_ASCENDING;

        public static Sort TITLE_DESCENDING;

        /*
         * raw demand expressions sorts
         */
        public static Sort DEXPERCAPITA_ASCENDING;

        public static Sort AVG_DEXPERCAPITA_ASCENDING;

        public static Sort PEAK_DEXPERCAPITA_ASCENDING;

        public static Sort MAX_DEXPERCAPITA_ASCENDING;

        public static Sort DEXPERCAPITA_DESCENDING;

        public static Sort AVG_DEXPERCAPITA_DESCENDING;

        public static Sort PEAK_DEXPERCAPITA_DESCENDING;

        public static Sort MAX_DEXPERCAPITA_DESCENDING;

        /*
         * real demand expressions sorts
         */

        public static Sort DEX_ASCENDING;

        public static Sort AVG_DEX_ASCENDING;

        public static Sort PEAK_DEX_ASCENDING;

        public static Sort MAX_DEX_ASCENDING;

        public static Sort DEX_DESCENDING;

        public static Sort AVG_DEX_DESCENDING;

        public static Sort PEAK_DEX_DESCENDING;

        public static Sort MAX_DEX_DESCENDING;

        /*
         * date sort fields
         */

        public static Sort DATE_ASCENDING;

        public static Sort DATE_ASCENDING_BYID;

        public static Sort DATE_DESCENDING;

        public static Sort DATE_DESCENDING_BYID;

        /*
         * default sort if sort info is not provided in requests
         */
        public static Sort DEFAULT_SORT;

        public static final Map<String, Sort> sortLookup = new HashedMap<String, Sort>();

        static
        {
                init();
        }

        private static void init()
        {
                /*
                 * demand expressions (dex) sorts
                 */
                sortLookup.put(lookupKey(Direction.ASC, "none", "dex"), Sort.by(new Order(Direction.ASC, "dex")));

                sortLookup.put(lookupKey(Direction.ASC, "avg", "dex"),
                                Sort.by(new Order(Direction.ASC, NATIVE_SUM_DEX_BY_SUM_NUM_DAYS)));

                sortLookup.put(lookupKey(Direction.ASC, "peak", "peak_dex"),
                                Sort.by(new Order(Direction.ASC, NATIVE_PEAK_DEX)));

                sortLookup.put(lookupKey(Direction.ASC, "max", "peak_dex"),
                                Sort.by(new Order(Direction.ASC, NATIVE_MAX_DEX)));

                sortLookup.put(lookupKey(Direction.DESC, "none", "dex"), JpaSort.unsafe(Direction.DESC, "dex"));

                sortLookup.put(lookupKey(Direction.DESC, "avg", "dex"),
                                JpaSort.unsafe(Direction.DESC, NATIVE_SUM_DEX_BY_SUM_NUM_DAYS));

                sortLookup.put(lookupKey(Direction.DESC, "peak", "peak_dex"),
                                JpaSort.unsafe(Direction.DESC, NATIVE_PEAK_DEX));

                sortLookup.put(lookupKey(Direction.DESC, "max", "peak_dex"),
                                JpaSort.unsafe(Direction.DESC, NATIVE_MAX_DEX));

                /*
                 * demand expressions per capita (dexpercapita) sorts
                 */
                sortLookup.put(lookupKey(Direction.ASC, "none", APIConstants.DEXPERCAPITA),
                                Sort.by(new Order(Direction.ASC, APIConstants.DEXPERCAPITA)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", APIConstants.DEXPERCAPITA),
                                JpaSort.unsafe(Direction.ASC, NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS));

                sortLookup.put(lookupKey(Direction.ASC, "peak", APIConstants.PEAK_DEXPERCAPITA),
                                Sort.by(new Order(Direction.ASC, NATIVE_PEAK_DEXPERCAPITA)));

                sortLookup.put(lookupKey(Direction.ASC, "max", APIConstants.PEAK_DEXPERCAPITA),
                                Sort.by(new Order(Direction.ASC, NATIVE_MAX_DEXPERCAPITA)));

                sortLookup.put(lookupKey(Direction.ASC, "sum", APIConstants.SUM_DEXPERCAPITA),
                                Sort.by(new Order(Direction.ASC, NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS)));

                sortLookup.put(lookupKey(Direction.DESC, "none", APIConstants.DEXPERCAPITA),
                                Sort.by(new Order(Direction.DESC, APIConstants.DEXPERCAPITA)));

                sortLookup.put(lookupKey(Direction.DESC, "avg", APIConstants.DEXPERCAPITA),
                                JpaSort.unsafe(Direction.DESC, NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS));

                sortLookup.put(lookupKey(Direction.DESC, "peak", APIConstants.PEAK_DEXPERCAPITA),
                                JpaSort.unsafe(Direction.DESC, NATIVE_PEAK_DEXPERCAPITA));

                sortLookup.put(lookupKey(Direction.DESC, "max", APIConstants.PEAK_DEXPERCAPITA),
                                JpaSort.unsafe(Direction.DESC, NATIVE_MAX_DEXPERCAPITA));

                sortLookup.put(lookupKey(Direction.DESC, "sum", APIConstants.SUM_DEXPERCAPITA),
                                Sort.by(new Order(Direction.DESC, NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS)));

                /*
                 * date sorts
                 */
                sortLookup.put(lookupKey(Direction.ASC, "none", "date"), Sort.by(new Order(Direction.ASC, DATE)));

                sortLookup.put(lookupKey(Direction.DESC, "none", "date"), Sort.by(new Order(Direction.DESC, DATE)));

                /*
                 * country sorts
                 */
                sortLookup.put(lookupKey(Direction.ASC, "none", "country"), Sort.by(new Order(Direction.ASC, COUNTRY)));

                sortLookup.put(lookupKey(Direction.DESC, "none", "country"),
                                Sort.by(new Order(Direction.DESC, COUNTRY)));

                /*
                 * breakdown sorts
                 */
                sortLookup.put(lookupKey(Direction.ASC, "avg", DE_SOCIAL), Sort.by(new Order(Direction.ASC, VALUE)));

                sortLookup.put(lookupKey(Direction.DESC, "avg", DE_SOCIAL), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", DE_VIDEO), Sort.by(new Order(Direction.ASC, VALUE)));

                sortLookup.put(lookupKey(Direction.DESC, "avg", DE_VIDEO), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", DE_PIRACY), Sort.by(new Order(Direction.ASC, VALUE)));

                sortLookup.put(lookupKey(Direction.DESC, "avg", DE_PIRACY), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", DE_RESEARCH), Sort.by(new Order(Direction.ASC, VALUE)));

                sortLookup.put(lookupKey(Direction.DESC, "avg", DE_RESEARCH),
                                Sort.by(new Order(Direction.DESC, VALUE)));

                /*
                 * context metric sort
                 *
                 */
                sortLookup.put(lookupKey(Direction.ASC, "avg", GTREND), Sort.by(new Order(Direction.ASC, VALUE)));
                sortLookup.put(lookupKey(Direction.DESC, "avg", GTREND), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", MOMENTUM), Sort.by(new Order(Direction.ASC, VALUE)));
                sortLookup.put(lookupKey(Direction.DESC, "avg", MOMENTUM), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", TRAVELABILITY),
                                Sort.by(new Order(Direction.ASC, VALUE)));
                sortLookup.put(lookupKey(Direction.DESC, "avg", TRAVELABILITY),
                                Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", REACH), Sort.by(new Order(Direction.ASC, VALUE)));
                sortLookup.put(lookupKey(Direction.DESC, "avg", REACH), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", FRANCHISABILITY),
                                Sort.by(new Order(Direction.ASC, VALUE)));
                sortLookup.put(lookupKey(Direction.DESC, "avg", FRANCHISABILITY),
                                Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", LONGEVITY), Sort.by(new Order(Direction.ASC, VALUE)));
                sortLookup.put(lookupKey(Direction.DESC, "avg", LONGEVITY), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", VALUE), Sort.by(new Order(Direction.ASC, VALUE)));
                sortLookup.put(lookupKey(Direction.DESC, "avg", VALUE), Sort.by(new Order(Direction.DESC, VALUE)));

                sortLookup.put(lookupKey(Direction.ASC, "avg", "demand_change"),
                                Sort.by(new Order(Direction.ASC, "demand_change")));

                sortLookup.put(lookupKey(Direction.DESC, "avg", "demand_change"),
                                Sort.by(new Order(Direction.DESC, "demand_change")));

                sortLookup.put(lookupKey(Direction.ASC, "sum", "source_event_total"),
                                JpaSort.unsafe(Direction.ASC, "SUM(e.source_event_total)"));

                sortLookup.put(lookupKey(Direction.DESC, "sum", "source_event_total"),
                                JpaSort.unsafe(Direction.DESC, "SUM(source_event_total)"));

                DEFAULT_SORT = sortLookup.get(lookupKey(Direction.DESC, "none", "date"));
        }

        private static String lookupKey(Direction direction, String aggregation, String sortBy)
        {
                if ("date".equalsIgnoreCase(sortBy) || "country".equalsIgnoreCase(sortBy))
                        return direction.toString() + "-" + sortBy;
                else
                        return direction.toString() + "-" + aggregation + "-" + sortBy;
        }

        public static Sort createSort(String aggregateFunction, String sortBy, String order)
        {
                Direction direction = null;

                try
                {
                        direction = Direction.fromString(order);
                }
                catch (Exception e)
                {
                        direction = Direction.DESC;
                }

                return sortLookup.get(lookupKey(direction, aggregateFunction, sortBy)) != null
                                ? sortLookup.get(lookupKey(direction, aggregateFunction, sortBy))
                                : DEFAULT_SORT;
        }

}
