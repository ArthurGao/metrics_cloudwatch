package com.parrotanalytics.api.request.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.commons.constants.ReleaseDateFilterType;
import com.parrotanalytics.api.data.repo.api.TitlesRepository;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import com.parrotanalytics.commons.utils.sql.SQLQueryUtil;

@Service
public class FiltersBuilder
{
    @Autowired
    private TitlesRepository titlesRepo;

    public FiltersBuilder()
    {
    }

    /**
     * @param filters
     * @return
     */
    public String filterShowsQuery(Map<String, String> filters, ConditionalOperator operator)
    {
        Map<String, String> genesFilters = filters;

        String geneCountry = filters.get("genecountry");

        filters.remove("genecountry");

        Iterator<String> genesItr = genesFilters.keySet().iterator();

        int filterSetIndex = 0;
        boolean firstFilterAdded = false;

        StringBuilder filterQuery = new StringBuilder();
        filterQuery.append("SELECT DISTINCT f1.`short_id` FROM ");

        while (genesItr.hasNext())
        {
            filterSetIndex++;

            String gene = genesItr.next();

            String valueStr = genesFilters.get(gene);

            if (valueStr != null)
            {
                List<String> originalGeneValues = Arrays.asList(valueStr.split(","));
                List<String> geneValues = new ArrayList<>(originalGeneValues);
                boolean exclusionGene = gene.startsWith(APIConstants.FILTER_GENE_EXCLUDE);
                if (CollectionUtils.isNotEmpty(geneValues))
                {
                    if (firstFilterAdded)
                    {
                        filterQuery.append(operator == ConditionalOperator.AND ? " JOIN " : " UNION ");
                    }
                    else
                    {
                        firstFilterAdded = true;
                    }
                    gene = exclusionGene ? gene.substring(APIConstants.FILTER_GENE_EXCLUDE.length()) : gene;
                    /* level 1 filter */
                    filterQuery.append("(SELECT cg.`short_id` FROM `portal`.`catalog_genes` cg  WHERE (`gene` = ");
                    filterQuery.append(String.format("'%s'", gene));
                    if (exclusionGene)
                    {
                        Map<String, String> negatedFilters = new HashMap<>();
                        negatedFilters.put(gene, valueStr);
                        List<Long> negatedShortIds = titlesRepo
                                .filteredShows(filterShowsQuery(negatedFilters, ConditionalOperator.OR));

                        filterQuery.append(String.format(" AND cg.`short_id` NOT IN (%s)", negatedShortIds.stream()
                                .map(i -> Long.toString(i)).collect(Collectors.joining(APIConstants.DELIM_COMMA))));
                    }
                    else
                    {
                        filterQuery.append(" AND `genevalue` IN (");
                        filterQuery.append("'" + String.join("','", geneValues) + "')");
                    }

                    /* filter child info */
                    if ("platform".equals(gene))
                    {
                        if (null == geneCountry)
                        {
                            filterQuery.append(" AND `genecountry` IS NOT NULL");
                        }
                        else
                        {
                            filterQuery.append(" AND `genecountry` IN (");
                            filterQuery.append("'" + String.join("','", geneCountry) + "')");
                        }

                    }
                    else if ("country".equals(gene))
                    {
                        filterQuery.append(" AND `priority` = 1");
                    }

                    filterQuery.append(")) f");
                    filterQuery.append(filterSetIndex);
                }
            }
        }

        boolean firstJoinConditionAdded = false;

        for (int joinIndex = 1; joinIndex < filterSetIndex; joinIndex++)
        {
            if (firstJoinConditionAdded)
            {
                filterQuery.append(" AND ");
            }
            else
            {
                filterQuery.append(" ON ");
                firstJoinConditionAdded = true;
            }

            filterQuery.append("f");
            filterQuery.append(joinIndex);
            filterQuery.append(".short_id = ");
            filterQuery.append(" f");
            filterQuery.append(joinIndex + 1);
            filterQuery.append(".short_id");
        }

        return filterQuery.toString();
    }

    /**
     * @param contentShortIDs
     * @return
     */
    public static String groupShowsQuery(DataQuery dataQuery, List<Long> contentShortIDs, Interval interval,
            String aggregation)
    {
        return groupByGenes(dataQuery.getGroupingGenes(), dataQuery.getSimpleFilters(), dataQuery.getMarketsList(),
                dataQuery.getDateRangeList(), contentShortIDs, interval, aggregation);
    }

    private static String groupByGenes(List<String> groupingGenes, Map<String, String> filters,
            List<String> countries, List<Date> dateRange, List<Long> contentShortIDs, Interval interval,
            String aggregation)
    {
        boolean isSingleGrouping = groupingGenes.size() == 1;

        Iterator<String> genesItr = groupingGenes.iterator();

        int dataSetIndex = 0;
        boolean firstDataSetAdded = false;

        StringBuilder filterQuery = new StringBuilder();

        /*
         * A: create SELECT clause
         */
        filterQuery.append("SELECT max(`date`), `country`, ");
        filterQuery.append("`" + String.join("`,`", groupingGenes) + "`");
        filterQuery.append(" ,").append(aggregationFunction(aggregation)).append("(real_de) AS 'aggregated_dex'");

        filterQuery.append(" FROM (");
        filterQuery.append(" SELECT d1.date, d1.`country`, d1.`short_id`, d1.`raw_de`, d1.`real_de`"
                + joinedColumns(groupingGenes));

        /*
         * B: create FROM clause (from)
         */
        while (genesItr.hasNext())
        {
            dataSetIndex++;

            if (firstDataSetAdded)
            {
                filterQuery.append(" JOIN ");
            }
            else
            {
                filterQuery.append(" FROM ");
                firstDataSetAdded = true;
            }

            String gene = genesItr.next();

            ArrayList<String> geneValues = null;

            filterQuery.append(" ( SELECT d.date, d.`country`, d.`short_id`, d.`raw_de`, d.`real_de`, g.`genevalue`")
                    .append(" FROM `metrics`.`expressions_daily` d JOIN `portal`.`catalog_genes` g ON d.`short_id` = g.`short_id`")
                    .append(" WHERE d.date IN (")
                    .append(SQLQueryUtil.inClauseStr(
                            dateRange.stream().map(d -> CommonsDateUtil.formatDate(d)).collect(Collectors.toList())))
                    .append(") AND d.`short_id` IN (").append(StringUtils.join(contentShortIDs, ","))
                    .append(") AND `gene` = '").append(gene).append("'");

            if (filters != null && !filters.isEmpty())
            {
                if (filters.get(gene) != null)
                    geneValues = new ArrayList<>(Arrays.asList(filters.get(gene).split(APIConstants.DELIM_COMMA)));

                if (CollectionUtils.isNotEmpty(geneValues))
                    filterQuery.append(" AND `genevalue` IN (").append("'" + String.join("','", geneValues) + "'")
                            .append(")");
            }

            filterQuery.append(" AND d.`country` IN (").append(SQLQueryUtil.inClauseStr(countries))
                    .append(" )) d" + dataSetIndex);

        }
        boolean firstJoinConditionAdded = false;

        /*
         * C: create JOIN clause
         */
        for (int joinIndex = 1; joinIndex < dataSetIndex; joinIndex++)
        {
            if (firstJoinConditionAdded)
            {
                filterQuery.append(" AND ");
            }
            else
            {
                filterQuery.append(" ON ");
                firstJoinConditionAdded = true;
            }

            filterQuery.append("d").append(joinIndex).append(".date = ").append(" d").append(joinIndex + 1)
                    .append(".date");

            filterQuery.append(" AND ");

            filterQuery.append("d").append(joinIndex).append(".country = ").append(" d").append(joinIndex + 1)
                    .append(".country");

            filterQuery.append(" AND ");

            filterQuery.append("d").append(joinIndex).append(".short_id = ").append(" d").append(joinIndex + 1)
                    .append(".short_id");
        }
        filterQuery.append(" ) dataset");

        /*
         * D: create GROUP BY clause
         */
        if (isSingleGrouping && Interval.DAILY == interval)
            filterQuery.append(" GROUP BY DATE, " + String.join(",", groupingGenes));
        else if (isSingleGrouping && Interval.OVERALL == interval)
            filterQuery.append(" GROUP BY " + String.join(",", groupingGenes));
        else
            filterQuery.append(" GROUP BY ").append("`" + String.join("`,`", groupingGenes) + "`");

        /*
         * E: create ORDER BY clause
         */
        if (isSingleGrouping && Interval.DAILY == interval)
            filterQuery.append(" ORDER BY DATE ASC");
        else if (isSingleGrouping && Interval.OVERALL == interval)
            filterQuery.append(" ORDER BY aggregated_dex DESC");
        else
            filterQuery.append(" ORDER BY ").append("`" + String.join("`,`", groupingGenes) + "`");

        return filterQuery.toString();
    }

    private static String aggregationFunction(String aggregation)
    {
        return "avg";
    }

    private static String joinedColumns(List<String> groupingGenes)
    {
        StringBuilder joinColumns = new StringBuilder();
        int dataSetIndex = 0;

        for (String gene : groupingGenes)
        {
            joinColumns.append(" ,d" + ++dataSetIndex + ".`genevalue` AS '" + gene + "'");
        }

        return joinColumns.toString();
    }

    public static boolean hasValidReleaseDateFilter(Map<String, String> filters)
    {
        if (filters == null)
            return false;
        String releaseDateFilter = filters.get(APIConstants.FILTER_RELEASE_DATE);
        boolean valid = filters.containsKey(APIConstants.FILTER_RELEASE_DATE)
                && (!StringUtils.isEmpty(releaseDateFilter));
        if (valid)
        {
            releaseDateFilter = releaseDateFilter.trim();
            String[] split = releaseDateFilter.split(",");
            String type = split[0].trim();
            valid = split.length == 3 && (ReleaseDateFilterType.SEASON_EPISODE.value().equals(type)
                    || ReleaseDateFilterType.PREMIERE.value().equals(type)
                    || ReleaseDateFilterType.PREMIERE_OR_SEASON_EPISODE.value().equals(type));
            return valid;
        }
        return false;
    }

    public static DateTime getFilterReleaseDateEnd(Map<String, String> filters)
    {
        DateTime releaseDateEnd = null;
        if (hasValidReleaseDateFilter(filters))
        {
            String releaseDateFilter = filters.get(APIConstants.FILTER_RELEASE_DATE).trim();
            String[] split = releaseDateFilter.split(",");
            boolean hasReleaseDateEnd = releaseDateFilter.contains(",") && split.length == 3;
            releaseDateEnd = hasReleaseDateEnd ? CommonsDateUtil.parseDateStr(split[2]) : DateTime.now();
        }
        return releaseDateEnd;
    }

    public static DateTime getFilterReleaseDateStart(Map<String, String> filters)
    {
        DateTime releaseDateStart = null;
        if (hasValidReleaseDateFilter(filters))
        {
            String releaseDateFilter = filters.get(APIConstants.FILTER_RELEASE_DATE).trim();
            String[] split = releaseDateFilter.split(",");
            boolean hasReleaseDateStart = releaseDateFilter.contains(",") && split.length == 3;
            releaseDateStart = hasReleaseDateStart ? CommonsDateUtil.parseDateStr(split[1]) : DateTime.now();
        }
        return releaseDateStart;
    }

    public static ReleaseDateFilterType getReleaseDateFilterType(Map<String, String> filters)
    {
        ReleaseDateFilterType filterType = ReleaseDateFilterType.SEASON_EPISODE;
        if (hasValidReleaseDateFilter(filters))
        {
            String releaseDateFilter = filters.get(APIConstants.FILTER_RELEASE_DATE).trim();
            String[] split = releaseDateFilter.split(",");
            boolean hasFilterType = releaseDateFilter.contains(",") && split.length == 3;
            if (hasFilterType)
            {
                filterType = ReleaseDateFilterType.fromValue(split[0]);
            }
        }
        return filterType;
    }

}
