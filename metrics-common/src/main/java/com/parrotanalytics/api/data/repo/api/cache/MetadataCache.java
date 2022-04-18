package com.parrotanalytics.api.data.repo.api.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.LoadingCache;
import com.google.gson.reflect.TypeToken;
import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.ISubscription;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.SubscriptionType;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.MovieSubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.TalentSubscriptionsRepository;
import com.parrotanalytics.api.services.CatalogESService;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.exceptions.ResourceNotFoundException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVItem;
import com.parrotanalytics.apicore.model.response.CatalogGenes;
import com.parrotanalytics.apicore.utils.APIHelper;
import com.parrotanalytics.apicore.utils.GenesBuilder;
import com.parrotanalytics.commons.service.Alerting;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Sanjeev Sharma
 * @author Minh Vu
 */
@Component
@Scope(value = "singleton")
public class MetadataCache {

  private static final Logger logger = LogManager.getLogger(MetadataCache.class);

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private SubscriptionsRepository subscriptionsRepo;

  @Autowired
  private TalentSubscriptionsRepository talentSubscriptionsRepo;

  @Autowired
  private MovieSubscriptionsRepository movieSubscriptionsRepo;

  private static Type catalogListType = new TypeToken<List<CatalogItem>>() {
  }.getType();

  private LoadingCache<String, CatalogItem> uuidToCatalogItem;

  private LoadingCache<Long, CatalogItem> shortIDToCatalogItem;

  private Map<Integer, CatalogGenes> _accountGenes = new LinkedHashMap<>();

  private Map<Long, List<Account>> _mapTitleToAccount = Collections.synchronizedMap(
      new HashMap<>());

  @Autowired
  private CatalogESService catalogESService;

  @PostConstruct
  public void init() {
    /*
     * Configure lookup cache from Title ShortID/UUID --> CatalogItem
     */
    configureIDLookupCaches();

  }

  /**
   * Configure lookup Guava {@link LoadingCache} from Title ShortID/UUID --> {@link CatalogItem}
   *
   * <ul>
   * <li>key: ParrotID</li>
   * <li>value: {@link CatalogItem}</li>
   * </ul>
   * *
   */
  private void configureIDLookupCaches() {
    uuidToCatalogItem = CacheBuilder.newBuilder().build(new CacheLoader<String, CatalogItem>() {
      @Override
      public CatalogItem load(String parrotID) throws Exception {
        /*
         * CatalogItem cI = catalogESService.searchMetadata(parrotID,
         * ParrotConstants.SHOW); if (cI == null) { cI =
         * catalogESService.searchMetadata(parrotID, ParrotConstants.TALENT); } if (cI
         * != null) { shortIDToCatalogItem.put(cI.getShortID(), cI); return cI; }
         */
        throw new ResourceNotFoundException("parrot_id not found");
      }
    });

    shortIDToCatalogItem = CacheBuilder.newBuilder().build(new CacheLoader<Long, CatalogItem>() {
      @Override
      public CatalogItem load(Long shortID) throws Exception {

        /*
         * CatalogItem cI = catalogESService.searchMetadata(shortID,
         * ParrotConstants.SHOW); if (cI == null) { cI =
         * catalogESService.searchMetadata(shortID, ParrotConstants.TALENT); } if (cI !=
         * null) { shortIDToCatalogItem.put(cI.getShortID(), cI); return cI; }
         */

        throw new ResourceNotFoundException("short_id not found");
      }
    });
  }

  public void setupSubscriptionCaches(SubscriptionsRepository subscriptionRepo) {
    List<Account> accounts = accountRepo.loadAllAccounts();

    Map<Integer, Account> _idAccount2Accounts = accounts.stream()
        .collect(Collectors.toMap(Account::getIdAccount, a -> a));

    /*
     * this query takes a significant time. removed bidirectional maps in
     * subscripiton/account speeds up fast
     */
    List<ISubscription> subscriptionItems = subscriptionRepo.findAccountSubscriptionTitles(
        Entity.TV_SERIES);

    Map<Long, List<Account>> _localMapTitleToAccount = Collections.synchronizedMap(new HashMap<>());

    logger.info("Registering {} items to Subscription Caches", subscriptionItems.size());

    final AtomicInteger countRegistered = new AtomicInteger(0);

    if (!CollectionUtils.isEmpty(subscriptionItems)) {
      subscriptionItems.stream().forEach(new Consumer<ISubscription>() {
        @Override
        public void accept(ISubscription subscriptionItem) {
          countRegistered.incrementAndGet();
          try {
            Long shortID = subscriptionItem.getSubscriptionRefId();

            Integer idAccount = subscriptionItem.getIdAccount();

            Account account = _idAccount2Accounts.get(idAccount);

            if (subscriptionItem != null) {
              if (!Objects.isNull(account)) {
                intoTitleToAccountsDictionary(shortID, account);
              } else {
                logger.error("Empty Account Object for Subscription {} [DELETED ACCOUNT ?]",
                    subscriptionItem);
              }
            }
            if (countRegistered.get() % 10000 == 0) {
              logger.debug("Registered  {} items so far ", countRegistered.get());

            }
          } catch (Exception e) {
            logger.error("Error registering subscription item {}",
                org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
          }
        }

        private void intoTitleToAccountsDictionary(Long shortID, Account account) {
          _localMapTitleToAccount.computeIfAbsent(shortID, id -> new ArrayList<>());
          _localMapTitleToAccount.get(shortID).add(account);
        }
      });

      logger.info("Registering {} items.", CollectionUtils.size(subscriptionItems));
    }

    _mapTitleToAccount = _localMapTitleToAccount;
  }

  public synchronized void register(CatalogItem item) {
    if (item != null) {

      /*
       * set extra attributes
       */
      if (item instanceof TVItem) {
        appendExtraFields((TVItem) item);
      }

      /* cache by long UUIDS */
      if (item.getParrotID() != null) {
        uuidToCatalogItem.put(item.getParrotID(), item);
      }

      if (item.getShortID() != null) {
        shortIDToCatalogItem.put(item.getShortID(), item);
      }
    } else {
      logger.error("Bad NULL item sent to register");
    }
  }

  private void appendExtraFields(TVItem item) {

    if (item.getMainGenre() != null) {
      item.addGenre(item.getMainGenre());
    }

    if (item.getSubGenre() != null) {
      item.addGenre(item.getSubGenre());
    }
  }

  public void registerToAccountGenes(Integer accountID, List<CatalogItem> accountTitles) {
    GenesBuilder genesBuilder = null;

    try {
      genesBuilder = new GenesBuilder();

      for (CatalogItem cI : accountTitles) {
        if (cI instanceof TVItem) {
          genesBuilder.registerGenes((TVItem) cI);
        }
      }
      _accountGenes.put(accountID, genesBuilder.catalogGenes());
    } catch (Exception e) {
      logger.error("Failed to register ");
      e.printStackTrace();
    }
  }

  /**
   * @return the cacheCatalogTitles
   */
  public LoadingCache<String, CatalogItem> getCacheCatalogTitles() {
    return uuidToCatalogItem;
  }

  @CacheEvict(cacheNames = APICacheConstants.CACHE_ACCOUNT_TITLES, allEntries = true)
  public void invalidateCacheAccountTitles() {
    // empty body
  }

  @CacheEvict(cacheNames = APICacheConstants.CACHE_ACCOUNT_MARKETS, allEntries = true)
  public void invalidateCacheAccountMarkets() {
    // empty body
  }

  //@Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_TITLES, key = "'accountid:' + #p0.idAccount", unless = "#result == null", cacheManager = "mapCacheManager")
  public List<CatalogItem> accountTitles(Account account) {
    return accountTitles(account, false);
  }

  //@Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_TITLES, key = "'accountid:' + #p0.idAccount + ':shortIDs'", unless = "#result == null", cacheManager = "mapCacheManager")
  public List<Long> accountTitleShortIds(Account account) throws APIException {
    List<Long> shortIds = null;
    Entity entity = Entity.TV_SERIES;
    if (null != account.getAccountLevel() && account.getAccountLevel()
        .equals(APIConstants.ACCOUNT_LEVEL_ALL_ACCESS)) {

      if (account.getAccountType().equals(APIConstants.ACCOUNT_TYPE_INTERNAL)) {
        shortIds = subscriptionsRepo.accountTitleShortIDs(account.getIdAccount(), entity);
      } else {
        shortIds = subscriptionsRepo.accountTitleShortIDs(APIConstants.CUSTOMER_READY_ACCOUNT,
            entity);
      }
    } else {
      shortIds = subscriptionsRepo.accountTitleShortIDs(account.getIdAccount(), entity);
    }

    return shortIds;

  }
  private  Collection<CatalogItem> allAccessTitles;
  //@Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_TITLES, key = "'accountid:' + #account.idAccount", unless = "#result == null", cacheManager = "mapCacheManager")
  public List<CatalogItem> accountTitles(Account account, boolean isInternal) {
    List<CatalogItem> accountTitles = new ArrayList<>();

    Collection<CatalogItem> accountItems = null;
    Entity entity = Entity.TV_SERIES;
    if (null != account.getAccountLevel() && account.getAccountLevel()
        .equals(APIConstants.ACCOUNT_LEVEL_ALL_ACCESS)) {
      accountItems = subscriptionsRepo.accountCatalogItems(account.getIdAccount(),
          SubscriptionType.TITLE, entity);
      accountTitles.addAll(accountItems);
      if (!isInternal) {
        if(allAccessTitles == null) {
          allAccessTitles = subscriptionsRepo.accountCatalogItems(
              APIConstants.CUSTOMER_READY_ACCOUNT, SubscriptionType.TITLE, entity);
        }
        accountTitles.addAll(allAccessTitles);
      }
    } else {
      accountTitles = subscriptionsRepo.accountCatalogItems(account.getIdAccount(),
          SubscriptionType.TITLE, entity);
    }

    if (CollectionUtils.isNotEmpty(accountTitles)) {
      // we should register account genes here
      registerToAccountGenes(account.getIdAccount(), accountTitles);
      logger.info("loaded {} titles for account {}", accountTitles.size(), account.getIdAccount());
    } else if (!isInternal) {
      Alerting.sendSlackAlarm("[FATAL]",
          "API cache for Account" + account.getIdAccount() + " Empty : [ "
              + APIHelper.runningEnvironment()
              + "], Report to PORTAL TEAM");
    }

    return accountTitles;
  }

  public List<ISubscription> accountTitleSubscriptions(Integer idAccount) {
    return subscriptionsRepo.findSubscriptions(idAccount, SubscriptionType.TITLE, Entity.TV_SERIES);
  }

  @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_TALENTS, key = "'accountid:' + #account.idAccount", unless = "#result == null")
  public List<CatalogItem> accountTalents(Account account) {
    List<CatalogItem> accountTalents = new ArrayList<>();

    Collection<CatalogItem> accountItems = subscriptionsRepo.accountCatalogItems(
        account.getIdAccount(),
        SubscriptionType.TALENT, Entity.TALENT);
    if (CollectionUtils.isNotEmpty(accountItems)) {
      accountTalents = new ArrayList<>(accountItems);
      logger.info("loaded {} talents for account {}", accountTalents.size(),
          account.getIdAccount());
    }

    return accountTalents;
  }

  @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_MOVIES, key = "'accountid:' + #account.idAccount", unless = "#result == null")
  public List<CatalogItem> accountMovies(Account account) {
    List<CatalogItem> accountMovies = new ArrayList<>();

    Collection<CatalogItem> accountItems = subscriptionsRepo.accountCatalogItems(
        account.getIdAccount(),
        SubscriptionType.MOVIE, Entity.MOVIE);
    if (CollectionUtils.isNotEmpty(accountItems)) {
      accountMovies = new ArrayList<>(accountItems);
      logger.info("loaded {} movies for account {}", accountMovies.size(), account.getIdAccount());
    }
    return accountMovies;
  }

  @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_TALENTS, key = "'accountid:' + #account.idAccount + ':shortIDs'", unless = "#result ==null")
  public List<Long> accountTalentShortIds(Account account) throws APIException {
    List<Long> shortIds = null;
    Entity entity = Entity.TALENT;
    if (null != account.getAccountLevel() && account.getAccountLevel()
        .equals(APIConstants.ACCOUNT_LEVEL_ALL_ACCESS)) {

      if (account.getAccountType().equals(APIConstants.ACCOUNT_TYPE_INTERNAL)) {
        shortIds = subscriptionsRepo.accountTitleShortIDs(account.getIdAccount(), entity);
      } else {
        shortIds = subscriptionsRepo.accountTitleShortIDs(
            APIConstants.CUSTOMER_READY_ACCOUNT, entity);
      }
    } else {
      shortIds = subscriptionsRepo.accountTitleShortIDs(account.getIdAccount(), entity);
    }

    return shortIds;
  }

  public CatalogGenes getAccountGenes(Integer accountId) {
    Account account = accountRepo.findByIdAccount(accountId);
    if (null != account.getAccountLevel() && account.getAccountLevel().equals("allaccess")) {
      accountId = accountRepo.loadAllAccessRootAccount().getIdAccount();
    }
    return _accountGenes.get(accountId);
  }

  /**
   * helper method to convert long uuid to short id
   *
   * @param longUUID
   * @return
   */
  public long shortID(String longUUID) {
    try {
      return uuidToCatalogItem.get(longUUID).getShortID();
    } catch (ExecutionException e) {
      logger.error("Could not resolve ParrotID {} ", longUUID);
    }

    return 0;
  }

  /**
   * helper method to return the metadata object by long uuid
   *
   * @param titleID
   * @return CatalogItem
   * @throws APIException
   */
  public CatalogItem resolveItem(Object titleID) {
    try {
      if (titleID instanceof String) {
        return uuidToCatalogItem.get((String) titleID);
      } else if (titleID instanceof Long) {
        return shortIDToCatalogItem.get((Long) titleID);
      }

    } catch (ExecutionException e) {
    }
    return null;
  }

  public CatalogItem fetchMetadata(Object parrotID, String contentType) throws APIException {
    CatalogItem showItem = null;

    try {
      showItem = catalogESService.searchMetadata(parrotID.toString(), contentType);

      if (showItem != null) {
        register(showItem);
      } else {
        throw new RuntimeException("Empty Metadata Response : " + parrotID);
      }
    } catch (Exception ex) {
      logger.error("[FAILED] fetching metadata for show : {} ", parrotID);

      Alerting.sendSlackAlarm("Metadata Error",
          "Metadata failed for show : " + parrotID + "[ " + APIHelper.runningEnvironment() + "]");
      return showItem;
    }

    return showItem;
  }

  /**
   * helper method to convert short id to long uuid
   *
   * @param shortID
   * @return
   */
  public String longUUID(long shortID) {
    try {
      CatalogItem catalogItem = shortIDToCatalogItem.get(shortID);
      return !Objects.isNull(catalogItem) ? catalogItem.getParrotID() : null;
    } catch (ExecutionException | InvalidCacheLoadException e) {
      logger.error("Could not resolve ShortID {} ", shortID);
    }

    return null;
  }

  public List<Account> fetchTitleAccounts(long shortID) {
    return _mapTitleToAccount.get(shortID);
  }

  @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNT_MOVIES, key = "'accountid:' + #account.idAccount", unless = "#result == null")
  public List<Long> accountMovieShortIds(Account account) throws APIException {
    List<Long> shortIds = null;
    Entity entity = Entity.MOVIE;
    if (null != account.getAccountLevel() && account.getAccountLevel()
        .equals(APIConstants.ACCOUNT_LEVEL_ALL_ACCESS)) {

      if (account.getAccountType().equals(APIConstants.ACCOUNT_TYPE_INTERNAL)) {
        shortIds = subscriptionsRepo.accountTitleShortIDs(account.getIdAccount(), entity);
      } else {
        shortIds = subscriptionsRepo.accountTitleShortIDs(APIConstants.CUSTOMER_READY_ACCOUNT,
            entity);
      }
    } else {
      shortIds = subscriptionsRepo.accountTitleShortIDs(account.getIdAccount(), entity);
    }

    return shortIds;
  }
}
