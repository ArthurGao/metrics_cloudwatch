package com.parrotanalytics.api.services;

import com.parrotanalytics.api.apidb_model.ConsolidatedContextMetrics;
import com.parrotanalytics.api.apidb_model.ConsolidatedContextMetricsV2;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.data.repo.api.ContextMetricRepository;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.api.request.demand.DemandRequest;
import com.parrotanalytics.api.request.metricrank.MetricRankRequest;
import com.parrotanalytics.api.request.titlecontext.ContextMetricType;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MetricRankService {

    protected static final Logger logger = LogManager.getLogger(MetricRankService.class);

    @Autowired
    private ContextMetricRepository contextMetricRepo;

    @Autowired
    private DemandService demandService;

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    public List<?> getMetricRankExpressions(MetricRankRequest apiRequest, ContextMetricType contextMetricType,
                                            PageRequest pageRequest) throws APIException {

        List<?> rankExpResult = null;

        DataQuery dataQuery = apiRequest.getDataQuery();
        if (contextMetricType == ContextMetricType.Dexpercapita) {

            if (apiRequest.isOverallHeatingUp()) {
                rankExpResult = demandService.findOverallHeatingUp(apiRequest, ContextMetricType.Dexpercapita.value(),
                        Integer.valueOf(APIConstants.CUSTOMER_READY_ACCOUNT), pageRequest);
            } else if (apiRequest.isOverallChangeRank()) {
                rankExpResult = demandService.findOverallChangeRank(apiRequest, ContextMetricType.Dexpercapita.value(),
                        Integer.valueOf(APIConstants.CUSTOMER_READY_ACCOUNT), pageRequest);
            } else {
                /*
                 * Request for demand data without any percent change
                 */
                dataQuery.setShowranks(true);
                dataQuery.setShowFilterRanks(true);
                rankExpResult = demandService.fetchDemandData(apiRequest, pageRequest).getContent();
            }
        } else if (Arrays.asList(ContextMetricType.Franchisability, ContextMetricType.Longevity, ContextMetricType.Momentum,
                ContextMetricType.Reach, ContextMetricType.Travelability).contains(contextMetricType)
                && Interval.OVERALL.value().equals(apiRequest.getInterval())) {
            // overall rank for pulse metrics
            List<ConsolidatedContextMetricsV2> result = contextMetricRepo.multiContextMetricsByRangeKeyCountryShortIdOverall(dataQuery.getPeriod(),
                    dataQuery.getMarketsList(), dataQuery.getShortIDsList(), Arrays.asList(contextMetricType.value()),
                    pageRequest);
            // use times average as value
            result.forEach(metric -> metric.setValue(metric.getTimesAverage()));
            setFilterRankV2(result);
            rankExpResult = result;
        } else {

            if (Interval.DAILY.value().equals(apiRequest.getInterval())) {
                // ask Sudip to create daily table for
                // rankExpResult =
                // contextMetricRepo.contextMetricsByRangeKeyCountryShortIdGenreAsOverall(apiRequest,
                // contextMetricType, pageRequest);

            } else if (Interval.OVERALL.value().equals(apiRequest.getInterval())) {
                List<ConsolidatedContextMetrics> result = contextMetricRepo.contextMetricsByRangeKeyCountryShortIdAsOverall(dataQuery.getPeriod(),
                        dataQuery.getMarketsList(), dataQuery.getShortIDsList(), apiRequest.getContextMetric(),
                        pageRequest);
                setFilterRank(result);
                rankExpResult = result;
            }
        }
        // create new brand new array list here to avoid SerializableException
        return new ArrayList<>(rankExpResult);
    }

    private List<GroupedExpressions> getGroupedExpressionWithFilterRank(List<GroupedExpressions> metrics, MetricRankRequest apiRequest, PageRequest pageRequest) {
        DemandRequest clonedRequest = apiRequest.clone();
        DataQuery dataQuery = clonedRequest.getDataQuery();
        dataQuery.setShowranks(true);
        return demandService.sortFilterRank(new ArrayList<>(metrics), pageRequest.getSort(), clonedRequest.getSortBy(), clonedRequest, pageRequest);
    }

    private void setFilterRankV2(List<ConsolidatedContextMetricsV2> metrics) {
        List<ConsolidatedContextMetricsV2> sorted = new ArrayList<>(metrics);
        sorted.sort(Comparator.comparing(ConsolidatedContextMetricsV2::getRank));
        int filterRank = 0;
        for (ConsolidatedContextMetricsV2 metric : sorted) {
            metric.setFilterRank(++filterRank);
        }
    }

    private void setFilterRank(List<ConsolidatedContextMetrics> metrics) {
        List<ConsolidatedContextMetrics> sorted = new ArrayList<>(metrics);
        sorted.sort(Comparator.comparing(ConsolidatedContextMetrics::getRank));
        int filterRank = 0;
        for (ConsolidatedContextMetrics metric : sorted) {
            metric.setFilterRank(++filterRank);
        }
    }

}
