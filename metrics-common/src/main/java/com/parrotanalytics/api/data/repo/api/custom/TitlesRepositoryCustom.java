package com.parrotanalytics.api.data.repo.api.custom;

import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.Subscription;
import com.parrotanalytics.api.commons.constants.ReleaseDateFilterType;
import com.parrotanalytics.api.data.repo.api.TitlesRepository;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface for custom methods in {@link TitlesRepository}
 *
 * @author Sanjeev Sharma
 * @author Minh Vu
 * @since v1
 */
public interface TitlesRepositoryCustom
{
    /**
     * convert content GUID to short ID
     *
     * @param parrotUUID
     * @return
     */
    Long shortID(String parrotUUID);

    /**
     * convert content GUIDs to short IDs
     *
     * @param parrotUUIDs
     * @return
     */
    List<Long> shortIDs(List<String> parrotUUIDs);

    /**
     * convert content short ID to GUID
     *
     * @param shortID
     * @return
     */
    String longUUIDs(Long shortID);

    /**
     * convert content short IDs to long GUIDs
     *
     * @param shortIDs
     * @return
     */
    List<String> longUUIDs(List<Long> shortIDs);

    /**
     * @return
     */
    List<String> findAllParrotIDs();

    /**
     * @param dynamicFilterQuery
     * @return
     */
    List<Long> filteredShows(String dynamicFilterQuery);

    List<Long> filterShowsByGenes(List<Long> contentShortIDs, Map<String, String> filtersMap,
            ConditionalOperator operator);

    List<Long> filterShowsByGenes(List<Long> contentShortIDs, Map<String, String> filtersMap);

    /**
     * Filtering shows by latest episode release date
     *
     * @param fromDate
     * @param toDate
     * @param filterType
     * @return
     */
    List<Long> filteredShowsByReleaseDate(DateTime fromDate, DateTime toDate, ReleaseDateFilterType filterType,
            List<Long> shortIDs);

    /**
     * Returns title metadata bean for given content id
     *
     * @param contentID
     * @return
     * @throws APIException
     */
    CatalogItem resolveItem(Object contentID) throws APIException;

    /**
     * @param parrotID
     * @return
     * @throws APIException
     */
    CatalogItem titleMetadata(String parrotID) throws APIException;

    /**
     * returns the master catalog titles
     *
     * @return
     * @throws APIException
     */
    List<CatalogItem> getAllTitles() throws APIException;

    /**
     * returns the master catalog titles
     *
     * @param parrotIDsList
     * @return
     */
    List<CatalogItem> subscriptionTitles(List<String> parrotIDsList);

    /**
     * @param account
     * @return
     */
    List<CatalogItem> accountTitles(Account account) throws APIException;

    /**
     * @param idAccount
     * @return
     * @throws APIException
     */
    List<CatalogItem> accountTitles(Integer idAccount) throws APIException;

    /**
     * @param account
     * @param isInternal
     * @return
     */
    List<CatalogItem> accountTitles(Account account, boolean isInternal) throws APIException;

    /**
     * @param portfolioID
     * @return
     */
    Page<CatalogItem> portfolioTitles(Integer portfolioID) throws APIException;

    /**
     * @param idPortfolio
     */
    void invalidatePortfolioTitlesCache(Integer idPortfolio);

    /**
     * removes {@link CatalogItem} for given parrotID in accountTitlesCache for given account
     *
     * @param subscription
     * @throws APIException
     */
    void removeTitleFromAccount(Subscription subscription) throws APIException;

    /**
     * adds {@link CatalogItem} for given parrotID in accountTitlesCache for given account
     *
     * @param subscription
     * @throws APIException
     */
    void addTitleToAccount(Subscription subscription) throws APIException;
    

}
