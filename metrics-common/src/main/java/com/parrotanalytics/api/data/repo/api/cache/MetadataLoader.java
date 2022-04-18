package com.parrotanalytics.api.data.repo.api.cache;

import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.data.repo.api.TalentSubscriptionsRepository;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.commons.constants.SubscriptionType;
import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.MarketsRepository;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.custom.impl.TitlesRepositoryImpl;
import com.parrotanalytics.api.services.CatalogESService;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.utils.APIHelper;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;
import com.parrotanalytics.commons.constants.ParrotConstants;
import com.parrotanalytics.commons.core.annotations.ParrotAnnotations;
import com.parrotanalytics.commons.exceptions.service.ParrotServiceException;
import com.parrotanalytics.commons.service.Alerting;
import com.parrotanalytics.commons.utils.QuickTimeMeasurement;
import com.parrotanalytics.datasourceint.constants.Environment;

/**
 * @author Sanjeev Sharma
 * @author Minh Vu
 */
@Component
@Scope(value = "singleton")
public class MetadataLoader {

    private static final Logger logger = LogManager.getLogger(MetadataCache.class);

    private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

    private static int METADATA_REFRESH_FREQUENCY_IN_MINS = apiConfig
            .readInteger(APIConfig.METADATA_REFRESH_FREQUENCY_MINS);

    @Autowired(required = true)
    private SubscriptionsRepository subscriptionRepo;

    @Autowired(required = true)
    private TalentSubscriptionsRepository talentSubscriptionRepo;

    @Autowired(required = true)
    private MetadataCache metadataCache;

    @Autowired
    protected AccountRepository accountRepo;

    @Autowired
    private MarketsRepository marketRepo;

    protected static Timer refreshTimer;

    @Autowired
    private CatalogESService catalogESService;


    public void initialize() {

        logger.info("--> INITIALIZING CATALOG CACHE....");

        loadMetadata(true);

        if (refreshTimer == null) {
            MetadataRefreshTask keepAliveTask = this.new MetadataRefreshTask();

            if (ConfigurationService.isDevMode()) {
                // override metadata refresh in development
                METADATA_REFRESH_FREQUENCY_IN_MINS = 180;
            }

            refreshTimer = new Timer(Boolean.TRUE);
            refreshTimer.scheduleAtFixedRate(keepAliveTask,
                    TimeUnit.MINUTES.toMillis(METADATA_REFRESH_FREQUENCY_IN_MINS),
                    TimeUnit.MINUTES.toMillis(METADATA_REFRESH_FREQUENCY_IN_MINS));
        }
    }

    /**
     * This task refreshes the metadata periodically in catalog metadata cache
     *
     * @author Sanjeev Sharma
     */
    class MetadataRefreshTask extends TimerTask {
        @Override
        public void run() {
            loadMetadata(false);
        }
    }

    public void loadMetadata(boolean isInitialLoad) {
        logger.info("Loading Metadata in {} mode", isInitialLoad ? "API Start" : "Refresh Cycle");
        AtomicInteger loadAttemptCount = new AtomicInteger();

        boolean successfullyLoaded = false;

        while (!successfullyLoaded && loadAttemptCount.incrementAndGet() <= 3) {

            successfullyLoaded = performLoad(loadAttemptCount);

        }

        if (!successfullyLoaded && !ConfigurationService.isDevMode()) {
            Alerting.sendSlackAlarm("[FATAL] Failed all 3 attempts of Metadata Loading Data API",
                    "Env: [ " + APIHelper.runningEnvironment() + "]");

            if (isInitialLoad) {
                throw new RuntimeException("##############  CANNOT CONTINUE as METADATA LOAD FAILED ############## ");
            }
        }
    }

    protected List<CatalogItem> fetchCatalogItemsInCache(String contentType) {
        logger.info("Fetching catalog items in cache of type {}...", contentType);
        List<Long> subscribedShortIDs = subscriptionRepo.findSubscriptionShortIds(getSubscriptionType(contentType), Entity.TV_SERIES);

        List<List<Long>> partitioned = Lists.partition(subscribedShortIDs, 500);

        List<CatalogItem> receivedItems = new ArrayList<>();

        for (List<Long> parrotIDs : partitioned) {
            receivedItems.addAll(fetchNRegisterToCache(parrotIDs, contentType));
        }
        logger.info("Fetched {} items in cache of type {}", receivedItems.size(), contentType);

        return receivedItems;

    }

    protected List<CatalogItem> fetchDemandCatalogItemsInCache(String contentType) {
        logger.info("Fetching catalog items in cache of type {}...", contentType);

        List<CatalogItem> receivedItems = catalogESService.searchCatalogItems(catalogESService.getESIndex(contentType), Arrays.asList(APIConstants.CLIENT_READY, "demand_generation"));

        final AtomicBoolean throwException = new AtomicBoolean(false);

        if (CollectionUtils.isNotEmpty(receivedItems)) {
            receivedItems.stream().forEach(item -> {
                try {
                    metadataCache.register(item);

                } catch (Throwable t) {
                    logger.error("[FAILED] Registering metadata for show : {}, Stacktrace : {}",
                            item != null ? item.getParrotID() : "NULL item",
                            org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(t));

                    throwException.set(true);
                }
            });
        }

        logger.info("Fetched {} items in cache of type {}", receivedItems.size(), contentType);

        return receivedItems;

    }

    private boolean performLoad(AtomicInteger loadAttemptCount) {
        QuickTimeMeasurement qtm = new QuickTimeMeasurement(TitlesRepositoryImpl.class);

        logger.info("Initiating METADATA LOAD, ATTEMPT #{}", loadAttemptCount);

        try {

            List<CatalogItem> tvItems = fetchDemandCatalogItemsInCache(ParrotConstants.SHOW);

            List<CatalogItem> talentItems = fetchDemandCatalogItemsInCache(ParrotConstants.TALENT);

            List<CatalogItem> moviesItems = fetchDemandCatalogItemsInCache(ParrotConstants.MOVIE);

            List<Long> subscriptionShortIDs = new ArrayList<>();
            subscriptionShortIDs.addAll(subscriptionRepo.findSubscribedTitles(Entity.TV_SERIES));
            subscriptionShortIDs.addAll(subscriptionRepo.findSubscribedTitles(Entity.TALENT));
            subscriptionShortIDs.addAll(subscriptionRepo.findSubscribedTitles(Entity.MOVIE));

            int catalogItemsCount = CollectionUtils.size(tvItems);

            if (CollectionUtils.isNotEmpty(tvItems)) {
                List<CatalogItem> catalogItems = new ArrayList<>();
                catalogItems.addAll(tvItems);
                catalogItems.addAll(talentItems);
                catalogItems.addAll(moviesItems);

                metadataCache.setupSubscriptionCaches(subscriptionRepo);

                List<Long> catalogShortIDs = catalogItems.stream().map(item -> item.getShortID())
                        .collect(Collectors.toList());

                List<Long> missingSubscriptionIDs = new ArrayList<>(subscriptionShortIDs);
                missingSubscriptionIDs.removeAll(catalogShortIDs);

                logger.info("Subscription Items : [{}], Catalog API Items : [{}], ", subscriptionShortIDs.size(),
                        catalogShortIDs.size());

                if (!CollectionUtils.isEmpty(missingSubscriptionIDs)) {
                    catalogShortIDs.addAll(missingSubscriptionIDs);

                    if (!ConfigurationService.isDevMode() && APIHelper.isRunningEnvironment(Environment.PRODUCTION)) {
                        Alerting.sendSlackAlarm("Data API[ " + APIHelper.runningEnvironment() + "]",
                                "CatalogAPI returned " + catalogItemsCount + " items instead of expected "
                                        + catalogShortIDs.size() + ".Missing items shortIDs:"
                                        + missingSubscriptionIDs.toString());
                    }
                }

                registerToAccounts();

                logger.info("METADATA LOADED {} titles in {} secs", metadataCache.getCacheCatalogTitles().size(),
                        qtm.timeTakenSecs());
            } else {
                throw new RuntimeException("--> Recieved ZERO items for level 1 /tv/catalog call");
            }

        } catch (Throwable t) {
            logger.error("METADATA FAILED, ATTEMPT #{}, Cause : {}", loadAttemptCount, t);
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private SubscriptionType getSubscriptionType(String contentType) {
        if (contentType.equalsIgnoreCase(ParrotConstants.TALENT)) {
            return SubscriptionType.TALENT;
        } else if (contentType.equalsIgnoreCase(ParrotConstants.MOVIE)) {
            // ParrotConstants.MOVIE.toLowerCase();
            // for some legacy movies that fall into TV catalog before
            return SubscriptionType.TITLE;
        }
        return SubscriptionType.TITLE;
    }

    private void registerToAccounts() {
        Iterable<Account> accounts = accountRepo.findAll();

        // invalidate the caches of both account titles and markets
        subscriptionRepo.invalidateSubscriptionsCache();

        metadataCache.invalidateCacheAccountTitles();

        metadataCache.invalidateCacheAccountMarkets();

        for (Account account : accounts) {

            if (account.getAccountType().equals("churned") || (ConfigurationService.isDevMode()
                    && !account.getIdAccount().equals(APIConstants.CUSTOMER_READY_ACCOUNT))) {
                continue;
            }
            try {
                /*
                 * Trigger refresh for Account TITLES
                 */
                metadataCache.accountTitles(account);

                /*
                 * Trigger refresh for Account Talents
                 */
                metadataCache.accountTalents(account);

                /*
                 * Trigger refresh for Account MARKETS
                 */
                marketRepo.getAccountMarkets(account.getIdAccount(), Entity.TV_SERIES);
            } catch (APIException e) {
                logger.error("Error REFRESHING markets info for account {}", account.getIdAccount());
            }
        }

    }

    /**
     * Performs REST GET call to CATALOGAPI /metadata to fetch metadata of given
     * parrotIDs
     *
     * @param requestedShortIDs
     * @throws ParrotServiceException
     */
    private List<CatalogItem> fetchNRegisterToCache(List<Long> requestedShortIDs, String contentType) {

        List<CatalogItem> receivedItems = catalogESService.searchCatalogItemsByQueryTerms(contentType, null,
                requestedShortIDs, null, null);

        final AtomicBoolean throwException = new AtomicBoolean(false);

        if (CollectionUtils.isNotEmpty(receivedItems)) {
            receivedItems.stream().forEach(item -> {
                try {
                    metadataCache.register(item);

                } catch (Throwable t) {
                    logger.error("[FAILED] Registering metadata for show : {}, Stacktrace : {}",
                            item != null ? item.getParrotID() : "NULL item",
                            org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(t));

                    throwException.set(true);
                }
            });
        }

        List<Long> receivedShortIDs = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(receivedItems)) {
            receivedShortIDs = receivedItems.stream().filter(x -> x != null && x.getShortID() != null)
                    .map(i -> i.getShortID()).collect(Collectors.toList());
        }

        int recievedItemsCount = CollectionUtils.size(receivedItems);
        int requestedItemsCount = CollectionUtils.size(receivedShortIDs);

        if (recievedItemsCount != requestedItemsCount) {
            receivedItems.addAll(handleFailedShows(requestedShortIDs, receivedShortIDs, contentType));
        }
        return receivedItems;

    }

    /**
     * @param requestedParrotIDs
     * @param receivedParrotIDs
     */
    private List<CatalogItem> handleFailedShows(List<Long> requestedParrotIDs, List<Long> receivedParrotIDs,
                                                String contentType) {
        List<Long> missingShortIDs = new ArrayList<>(requestedParrotIDs);
        missingShortIDs.removeAll(receivedParrotIDs);

        if (!CollectionUtils.isEmpty(missingShortIDs)) {

            List<CatalogItem> receivedItems = catalogESService.searchCatalogItemsByQueryTerms(contentType, null,
                    missingShortIDs, null, null);
            // there are some movies have been merged into subscription as
            // tvtitle so we have to search them from movie_metadata too.
            if (contentType.equalsIgnoreCase(ParrotConstants.SHOW)) {
                receivedItems.addAll(catalogESService.searchCatalogItemsByQueryTerms(ParrotConstants.MOVIE, null,
                        missingShortIDs, null, null));
            }
            if (CollectionUtils.isNotEmpty(receivedItems)) {
                receivedItems.forEach(item -> metadataCache.register(item));
            }
            return receivedItems;

        }
        return new ArrayList<>();
    }

    /**
     * @return the metadataCache
     */
    public MetadataCache getMetadataCache() {
        if (metadataCache == null) {
            logger.error("FATAL: null metadatacache detected!!!");
        }

        return metadataCache;
    }
}
