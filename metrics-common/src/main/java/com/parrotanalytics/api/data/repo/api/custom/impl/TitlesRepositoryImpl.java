package com.parrotanalytics.api.data.repo.api.custom.impl;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.PortfolioItem;
import com.parrotanalytics.api.apidb_model.Subscription;
import com.parrotanalytics.api.apidb_model.SubscriptionPK;
import com.parrotanalytics.api.apidb_model.comparators.DisplayNameSort;
import com.parrotanalytics.api.commons.constants.ReleaseDateFilterType;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.PortfolioItemsRepository;
import com.parrotanalytics.api.data.repo.api.PortfoliosRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.data.repo.api.custom.TitlesRepositoryCustom;
import com.parrotanalytics.api.request.util.FiltersBuilder;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVData;
import com.parrotanalytics.apicore.utils.APIHelper;
import com.parrotanalytics.commons.constants.ParrotConstants;
import com.parrotanalytics.commons.service.Alerting;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;

/**
 * @author Sanjeev Sharma
 * @author Jackson Lee
 * @author Minh Vu
 */
public class TitlesRepositoryImpl implements TitlesRepositoryCustom
{

    private static final Logger logger = LogManager.getLogger(TitlesRepositoryImpl.class);

    @PersistenceContext(unitName = "PARROT_API")
    private EntityManager entityManager;

    @Autowired
    private MetadataCache metadataCache;

    @Autowired
    private PortfoliosRepository portfoliosRepo;

    @Autowired
    private PortfolioItemsRepository portfolioItemRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private FiltersBuilder filtersBuilder;

    @PostConstruct
    public void init()
    {
        logger.debug("--> INITIALIZING TRImpl ....");
    }

    @Override
    public Long shortID(String parrotUUID)
    {
        return metadataCache.shortID(parrotUUID);
    }

    @Override
    public String longUUIDs(Long shortID)
    {
        return metadataCache.longUUID(shortID);
    }

    /**
     *
     */
    public List<Long> shortIDs(List<String> parrotUUIDs)
    {
        List<Long> shortIDs = parrotUUIDs.stream().map(parrotUUID -> {
            try
            {
                return metadataCache.shortID(parrotUUID);
            }
            catch (Exception e)
            {
                logger.error("failed to resolve UUID : {}", parrotUUID);
                return (long) 0;
            }
        }).filter(shortId -> shortId != 0).collect(Collectors.toList());
        return shortIDs;
    }

    public List<String> longUUIDs(List<Long> shortIDs)
    {
        final List<String> longUUIDs = new ArrayList<String>();

        shortIDs.stream().forEach(new Consumer<Long>()
        {
            @Override
            public void accept(Long shortID)
            {
                try
                {

                    String parrotID = metadataCache.longUUID(shortID);

                    if (parrotID == null)
                    {
                        CatalogItem resolveItem = resolveItem(shortID);

                        if (resolveItem != null)
                        {
                            parrotID = resolveItem.getParrotID();
                        }

                    }

                    if (parrotID != null)
                    {
                        longUUIDs.add(metadataCache.longUUID(shortID));
                    }

                }
                catch (APIException e)
                {
                    logger.error("failed to resolve ShortID : {}", shortIDs);
                }
            }
        });

        return longUUIDs;
    }

    /**
     * resolves the catalog item for given parrot id
     */
    @Override
    public CatalogItem resolveItem(Object parrotID) throws APIException
    {
        CatalogItem catalogItem = metadataCache.resolveItem(parrotID);

        /* if item is missing */
        if (catalogItem == null)
        {
            logger.warn("resolving {} from Elastic Seach series_metadata", parrotID);
            catalogItem = metadataCache.fetchMetadata(parrotID, ParrotConstants.SHOW);

        }
        if (catalogItem == null)
        {
            Alerting.sendSlackAlarm("Portal Metadata Error", "Metadata Missing for show : " + parrotID + "[ "
                    + APIHelper.runningEnvironment() + "] in Global Account");
        }

        return catalogItem;
    }

    @Override
    public Page<CatalogItem> portfolioTitles(Integer portfolioId) throws APIException
    {

        List<PortfolioItem> portfolioItems = portfolioItemRepo.findByPortfolios(Arrays.asList(portfolioId));

        List<CatalogItem> masterList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(portfolioItems))
        {
            for (PortfolioItem pi : portfolioItems)
            {
                try
                {
                    CatalogItem catItem = resolveItem(pi.getShortId());
                    if (!Objects.isNull(catItem))
                        masterList.add(catItem);
                }
                catch (Exception ex)
                {
                    logger.warn("failed resolving {} from metadata cache", pi.getShortId());
                }
            }

        }
        if (!CollectionUtils.isEmpty(masterList))
        {
            return new PageImpl<CatalogItem>(masterList);
        }
        else
        {
            return null;
        }
    }

    @Deprecated
    @Override
    public void invalidatePortfolioTitlesCache(Integer idPortfolio)
    {
        // dont need this method now since we no longer cache this portfolio
    }

    /**
     * @throws APIException
     */
    @Override
    public List<CatalogItem> accountTitles(Account account) throws APIException
    {
        return metadataCache.accountTitles(account);
    }

    /**
     * @throws APIException
     */
    @Override
    public List<CatalogItem> accountTitles(Account account, boolean isInternal) throws APIException
    {
        return metadataCache.accountTitles(account, isInternal);
    }

    @Override
    public CatalogItem titleMetadata(String parrotID) throws APIException
    {
        try
        {
            return metadataCache.getCacheCatalogTitles().get(parrotID);
        }
        catch (ExecutionException e)
        {
            throw new APIException(e.getLocalizedMessage());
        }
    }

    @Override
    public void removeTitleFromAccount(Subscription subscription) throws APIException
    {
        Integer accountId = subscription.getIdAccount();
        long shortID = subscription.getSubscriptionRefId();

        removeTitleFromAccount(accountId, shortID);
    }

    private void removeTitleFromAccount(Integer accountId, long shortID) throws APIException
    {

        CatalogItem catalogItem = metadataCache.resolveItem(shortID);
        SubscriptionPK subscriptionPK = new SubscriptionPK(accountId, shortID);
        // metadataCache.getMapSubscriptionsData().remove(subscriptionPK);
    }

    @Override
    public void addTitleToAccount(Subscription subscription) throws APIException
    {
        Integer accountId = subscription.getIdAccount();
        Long shortID = subscription.getSubscriptionRefId();

        CatalogItem catalogItem = metadataCache.resolveItem(shortID);

        if (catalogItem != null)
        {
            SubscriptionPK subscriptionPK = new SubscriptionPK(accountId, shortID);
            // metadataCache.getMapSubscriptionsData().put(subscriptionPK,
            // subscription);
        }
    }

    public List<CatalogItem> subscriptionTitles(List<String> parrotIDsList)
    {
        parrotIDsList = parrotIDsList.stream().distinct().collect(Collectors.toList());

        List<CatalogItem> subsTitles = new ArrayList<CatalogItem>();

        parrotIDsList.stream().forEach(new Consumer<String>()
        {
            @Override
            public void accept(String parrotID)
            {
                try
                {
                    CatalogItem item = metadataCache.getCacheCatalogTitles().get(parrotID);

                    if (item != null)
                    {
                        subsTitles.add(item);
                    }
                    else
                    {
                        logger.error("failed to fetch metadata for : [{}]", parrotID);
                    }
                }
                catch (Exception e)
                {
                    logger.error("error in title fetching : {}", e);
                }
            }
        });

        /* sort catalog items by display name */
        Collections.sort(subsTitles, new DisplayNameSort());

        return subsTitles;
    }

    @Override
    public List<CatalogItem> getAllTitles() throws APIException
    {

        Collection<CatalogItem> alltitles = metadataCache.getCacheCatalogTitles().asMap().values();

        if (CollectionUtils.isNotEmpty(alltitles))
        {
            return (new ArrayList<CatalogItem>(alltitles));
        }
        else
        {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> findAllParrotIDs()
    {
        List<String> parrotIDs = new ArrayList<>();

        Iterator<String> showItr = metadataCache.getCacheCatalogTitles().asMap().keySet().iterator();

        while (showItr.hasNext())
        {
            parrotIDs.add(showItr.next());
        }

        return parrotIDs;
    }

    @Override
    public List<Long> filterShowsByGenes(List<Long> requestShortIDs, Map<String, String> filtersMap)
    {
        return filterShowsByGenes(requestShortIDs, filtersMap, ConditionalOperator.AND);
    }

    @Override
    public List<Long> filterShowsByGenes(List<Long> requestShortIDs, Map<String, String> filtersMap,
            ConditionalOperator operator)
    {
        List<Long> filteredShortIDs = null;

        if (CollectionUtils.isNotEmpty(requestShortIDs))
        {
            List<Long> filterShortIDs = filteredShows(filtersBuilder.filterShowsQuery(filtersMap, operator));
            if (CollectionUtils.isEmpty(filterShortIDs))
            {
                return new ArrayList<>();
            }
            else
            {
                filteredShortIDs = filterShortIDs.stream().filter(short_id -> requestShortIDs.contains(short_id))
                        .collect(Collectors.toList());

                if (CollectionUtils.isEmpty(filteredShortIDs))
                {
                    return new ArrayList<>();
                }

                return filteredShortIDs;
            }
        }
        return new ArrayList<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> filteredShows(String dynamicFilterQuery)
    {
        return entityManager.createNativeQuery(dynamicFilterQuery).getResultList();
    }

    @Override
    public List<Long> filteredShowsByReleaseDate(DateTime fromDate, DateTime toDate, ReleaseDateFilterType filterType,
            List<Long> shortIDs)
    {
        List<Long> filteredShortIDs = shortIDs.stream().filter(shortID -> {
            try
            {
                CatalogItem item = resolveItem(shortID);
                if (!Objects.isNull(item) && item.getData() instanceof TVData)
                {
                    TVData tvData = (TVData) item.getData();
                    String targetDateStr = null, anotherTargetDateStr = null;
                    switch (filterType)
                    {
                    case PREMIERE:
                        targetDateStr = !StringUtils.isEmpty(tvData.getStartDate()) ? tvData.getStartDate() : null;
                        break;

                    case SEASON_EPISODE:
                        targetDateStr = !StringUtils.isEmpty(tvData.getLatestEpisodeDate())
                                ? tvData.getLatestEpisodeDate()
                                : tvData.getLatestSeasonDate();
                        break;
                    case PREMIERE_OR_SEASON_EPISODE:
                        targetDateStr = !StringUtils.isEmpty(tvData.getStartDate()) ? tvData.getStartDate() : null;
                        anotherTargetDateStr = !StringUtils.isEmpty(tvData.getLatestEpisodeDate())
                                ? tvData.getLatestEpisodeDate()
                                : tvData.getLatestSeasonDate();
                        break;
                    default:
                        targetDateStr = !StringUtils.isEmpty(tvData.getLatestEpisodeDate())
                                ? tvData.getLatestEpisodeDate()
                                : tvData.getLatestSeasonDate();
                        break;

                    }
                    DateTime targetDate = CommonsDateUtil.parseDateStr(targetDateStr);

                    boolean matched = !Objects.isNull(targetDate) && (fromDate.compareTo(targetDate) <= 0)
                            && (toDate.compareTo(targetDate) >= 0);
                    if (!matched && filterType == ReleaseDateFilterType.PREMIERE_OR_SEASON_EPISODE)
                    {
                        DateTime anotherTargetDate = CommonsDateUtil.parseDateStr(anotherTargetDateStr);

                        matched = !Objects.isNull(anotherTargetDate) && (fromDate.compareTo(anotherTargetDate) <= 0)
                                && (toDate.compareTo(anotherTargetDate) >= 0);
                    }
                    return matched;
                }
            }
            catch (APIException e)
            {
                e.printStackTrace();
                return false;
            }
            return false;
        }).collect(Collectors.toList());
        return filteredShortIDs;
    }

    @Override
    public List<CatalogItem> accountTitles(Integer idAccount) throws APIException
    {
        Account account = accountRepo.findByIdAccount(idAccount);

        return metadataCache.accountTitles(account);
    }

}
