package com.parrotanalytics.api.data.repo.api.custom;

import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.data.repo.api.MarketsRepository;
import com.parrotanalytics.api.response.markets.AccountMarketResponse;
import com.parrotanalytics.apicore.exceptions.APIException;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Interface for custom methods in {@link MarketsRepository}
 *
 * @author Sanjeev Sharma
 * @author Minh Vu
 * @since v1
 */
public interface MarketsRepositoryCustom
{

    Country resolveMarket(String isoCode);

    /**
     * @param accountID
     * @return
     */
    @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_MARKETS, keyGenerator = "cacheKeyGenerator")
    List<String> getMarketsISOCodesByAccount(Integer accountID, Entity entity) throws APIException;

    /**
     * @param accountID
     * @return
     * @throws ExecutionException
     */
    @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_MARKETS, keyGenerator = "cacheKeyGenerator")
    List<AccountMarketResponse> getAccountMarkets(Integer accountID, Entity entity) throws APIException;

    Map<String, Country> getAllIsoCountryMap();

}
