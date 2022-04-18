package com.parrotanalytics.api.data.repo.api.custom.impl;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.apidb_model.ISubscription;
import com.parrotanalytics.api.apidb_model.Product;
import com.parrotanalytics.api.apidb_model.ProductSpec;
import com.parrotanalytics.api.apidb_model.Subscription;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.SubscriptionType;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.MarketsRepository;
import com.parrotanalytics.api.data.repo.api.ProductRepository;
import com.parrotanalytics.api.data.repo.api.ProductSpecsRepository;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.TitlesRepository;
import com.parrotanalytics.api.data.repo.api.UserRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.data.repo.api.custom.SubscriptionsRepositoryCustom;
import com.parrotanalytics.api.response.markets.AccountMarketResponse;
import com.parrotanalytics.api.response.subscriptions.ModuleResponse;
import com.parrotanalytics.api.response.subscriptions.ProductResponse;
import com.parrotanalytics.api.response.subscriptions.ProductSpecResponse;
import com.parrotanalytics.api.services.UserService;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.auth.login.AccountNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation for custom methods in {@link SubscriptionsRepositoryCustom} for {@link
 * SubscriptionsRepository}
 *
 * @author Sanjeev Sharma
 * @author Minh Vu
 */
public class SubscriptionsRepositoryImpl implements SubscriptionsRepositoryCustom {

  private static final Logger logger = LogManager.getLogger(SubscriptionsRepositoryImpl.class);

  @Autowired
  UserRepository userRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired(required = true)
  private TitlesRepository titlesRepo;

  @Autowired
  private MarketsRepository marketsRepo;

  @Autowired(required = true)
  private SubscriptionsRepository subscriptionsRepo;

  @Autowired(required = true)
  private MetadataCache metadataCache;

  @Autowired
  protected ProductSpecsRepository productSpecsRepo;

  @Autowired
  protected ProductRepository productRepo;

  @Autowired
  protected UserService userService;

  @PersistenceContext(unitName = "PARROT_API")
  protected EntityManager entityManager;

  /**
   *
   */
  @Override
  public boolean loadAccountData(Integer accountID) throws APIException {
    /*
     * Loads data to account data cache if not available in cache for given account
     */
    if (getAccountTitles(accountRepo.findByIdAccount(accountID), Entity.TV_SERIES) != null) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  /**
   * Loads User
   *
   * @param userId
   * @return
   * @throws AccountNotFoundException
   */
  @Override
  public Integer findAccountId(String userId) throws AccountNotFoundException {
    Account account = userRepo.loadUserByEmail(userId).getAccount();

    if (account != null) {
      return account.getIdAccount();
    }

    throw new AccountNotFoundException("No account associated with user " + userId);
  }

  @Override
  public boolean hasAccountAccess(String userId, Integer accountId) {
    Integer userAccountId = null;

    try {
      userAccountId = findAccountId(userId);

      if (accountId.equals(userAccountId)) {
        return true;
      }
    } catch (AccountNotFoundException e) {
    }

    return false;
  }

  /**
   * @return the accountMarkets
   * @throws ExecutionException
   */
  @Override
  public List<AccountMarketResponse> getAccountMarkets(Integer accountID, Entity entity)
      throws APIException {
    return marketsRepo.getAccountMarkets(accountID, entity);
  }

  @Override
  public List<String> getMarketsISOCodesByAccount(Integer accountID, Entity entity)
      throws APIException {
    return marketsRepo.getMarketsISOCodesByAccount(accountID, entity);
  }

  @Override
  public List<String> getMarketsISOCodesByAccount(Integer accountID, boolean isMonitorTierIncluded,
      Entity entity)
      throws APIException {
    List<String> accountMarketISOs = marketsRepo.getMarketsISOCodesByAccount(accountID, entity);
    if (isMonitorTierIncluded) {
      List<String> monitorMarketISOs = marketsRepo.getMarketsISOCodesByAccount(
          APIConstants.MONITOR_ACCOUNT, entity);
      accountMarketISOs.addAll(monitorMarketISOs);
      accountMarketISOs = accountMarketISOs.stream().distinct().collect(Collectors.toList());
    }

    return accountMarketISOs;
  }

  /**
   * @return the accountTitles
   * @throws ExecutionException
   */
  @Override
  public List<CatalogItem> getAccountTitles(Account account, Entity entity) throws APIException {
    return titlesRepo.accountTitles(account);
  }

  /**
   * @return the accountTitles
   * @throws ExecutionException
   */
  @Override
  public List<CatalogItem> getAccountTitles(Account account, boolean isInternal, Entity entity)
      throws APIException {
    return titlesRepo.accountTitles(account, isInternal);
  }

  @Override
  public Subscription saveSubscription(Subscription subscription) throws APIException {
    Subscription sub = null;
    Account account = accountRepo.findByIdAccount(subscription.getIdAccount());

    if (SubscriptionType.TITLE.value().equals(subscription.getSubscriptionType())) {
      if (account.getAccountType().equals("subscription")) {
        handleCustomerReadyTitle(subscription);
      }
      account.addSubscription(subscription);
      accountRepo.save(account);
      titlesRepo.addTitleToAccount(subscription);
    } else if (SubscriptionType.MARKET.value().equals(subscription.getSubscriptionType())) {
//      account.addSubscription(subscription);
      subscriptionsRepo.save(subscription);
    }

    return sub;
  }

  public void handleCustomerReadyTitle(Subscription subscription) throws APIException {
    boolean isInCustomerReady = false;
    Account customerReadyAccount = accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT);
    List<Subscription> customerReadySubs = customerReadyAccount.getSubscriptions();
    for (Subscription item : customerReadySubs) {
      if (item.getSubscriptionRefId() == subscription.getSubscriptionRefId()) {
        isInCustomerReady = true;
        break;
      }
    }

    if (isInCustomerReady == false) {
      Subscription newSubscription = new Subscription();
      newSubscription.setIdAccount(customerReadyAccount.getIdAccount());
      newSubscription.setSubscriptionRefId(subscription.getSubscriptionRefId());
      newSubscription.setSubscriptionStart(new Date());
      newSubscription.setSubscriptionType(SubscriptionType.TITLE.value());
      newSubscription.setIdAccount(customerReadyAccount.getIdAccount());
      customerReadyAccount.addSubscription(newSubscription);
      accountRepo.save(customerReadyAccount);
      titlesRepo.addTitleToAccount(newSubscription);
    }
  }

  @Override
  public void removeSubscription(Subscription subscription) throws APIException {
    if (SubscriptionType.TITLE.value().equals(subscription.getSubscriptionType())) {
      subscriptionsRepo.delete(subscription);
    } else if (SubscriptionType.MARKET.value().equals(subscription.getSubscriptionType())) {
      subscriptionsRepo.delete(subscription);
    } else if (SubscriptionType.MODULE.value().equals(subscription.getSubscriptionType())) {
      removeModule(subscription);
    }
  }

  /**
   * This method use @CacheEvict to invalidate the SUBSCRIPTIONS cache
   */
  @Override
  public void invalidateSubscriptionsCache() {
    // empty subscription cache
  }

  @Override
  public List<CatalogItem> getAllTitles(Entity entity) throws APIException {
    return titlesRepo.getAllTitles();

  }

  @Override
  public List<ModuleResponse> getModuleResponse(Account account) throws APIException {
    List<ModuleResponse> modulesResponse = new LinkedList<>();

    List<ProductSpec> productSpecs = productSpecsRepo.getAccountModules(account.getIdAccount());

    Map<Integer, ModuleResponse> moduleLookup = new HashMap<>();

    if (CollectionUtils.isNotEmpty(productSpecs)) {
      for (ProductSpec specs : productSpecs) {
        ModuleResponse module = null;

        if (moduleLookup.get(specs.getIdProduct()) != null) {
          module = moduleLookup.get(specs.getIdProduct());
        } else {

          module = new ModuleResponse();

          module.setAccountId(account.getIdAccount());
          module.setSubscriptionStart(CommonsDateUtil.formatDate(account.getSubscriptionStart()));

          ProductResponse productResponse = new ProductResponse();
          productResponse.setIdProductOfferingId(specs.getIdProduct());
          Product product = specs.getProduct();

          if (null == product) {
            product = productRepo.findProductById(specs.getIdProduct());

          }
          productResponse.setProductName(product.getName());
          productResponse.setProductDescription(product.getDescription());
          productResponse.setProductStatus(product.getStatus());

          module.setProductOffering(productResponse);

          moduleLookup.put(specs.getIdProduct(), module);
          modulesResponse.add(module);
        }

        ProductSpecResponse config = new ProductSpecResponse();
        config.setSpecificationName(specs.getSpecificationName());
        config.setSpecificationValue(specs.getSpecificationValue());

        module.addProductConfig(config);

      }
    }
    return modulesResponse;

  }

  @Override
  public List<Integer> findMarketIDsByAccount(Integer idAccount, SubscriptionType subscriptionType,
      Entity entity) {
    Class<?> tableEntity = DemandHelper.getSubscriptionTableEntity(entity);
    String queryStr = String.format(
        "SELECT TRIM(s.subscriptionRefId) from %s s WHERE s.idAccount = :idAccount AND s.subscriptionType = :subscriptionType",
        tableEntity.getSimpleName());
    Query query = entityManager.createQuery(queryStr);
    query.setParameter("idAccount", idAccount);
    query.setParameter("subscriptionType", subscriptionType);
    return query.getResultList();
  }

  @Override
  public List<Country> findMarketsByAccount(Integer idAccount, Entity entity) {

    Class<?> tableEntity = DemandHelper.getSubscriptionTableEntity(entity);
    String queryStr = String.format(
        "SELECT c from Country c join %s s on c.idCountries = s.subscriptionRefId AND s.subscriptionType = 'market' WHERE s.idAccount = :idAccount ORDER BY c.displayName ASC",
        tableEntity.getSimpleName());
    Query query = entityManager.createQuery(queryStr);
    query.setParameter("idAccount", idAccount);
    return query.getResultList();
  }

  @Override
  public ISubscription findSubscription(Integer idAccount, SubscriptionType subscriptionType,
      Long subscriptionRefId, Entity entity) {
    Class<? extends ISubscription> tableEntity = DemandHelper.getSubscriptionTableEntity(entity);
    String queryStr = String.format(
        "SELECT s from %s s WHERE s.idAccount = :idAccount AND s.subscriptionType = :subscriptionType AND s.subscriptionRefId  = :subscriptionRefId",
        tableEntity.getSimpleName());
    Query query = entityManager.createQuery(queryStr, tableEntity);
    query.setParameter("idAccount", idAccount);
    query.setParameter("subscriptionType", subscriptionType.value());
    query.setParameter("subscriptionRefId", subscriptionRefId);
    List<?> result = query.getResultList();
    return CollectionUtils.isNotEmpty(result) ? (ISubscription) result.get(0) : null;
  }

  @Override
  public ISubscription findSubscriptionById(Integer idAccount, SubscriptionType subscriptionType,
      Long subscriptionRefId, Entity entity) {
    Class<? extends ISubscription> tableEntity = DemandHelper.getSubscriptionTableEntity(entity);
    String queryStr = String.format(
        "SELECT s from %s s WHERE s.idAccount = :idAccount AND s.subscriptionType = :subscriptionType AND s.subscriptionRefId  = :subscriptionRefId",
        tableEntity.getSimpleName());
    Query query = entityManager.createQuery(queryStr, tableEntity);
    query.setParameter("idAccount", idAccount);
    query.setParameter("subscriptionType", subscriptionType.value());
    query.setParameter("subscriptionRefId", subscriptionRefId);
    List<?> result = query.getResultList();
    return CollectionUtils.isNotEmpty(result) ? (ISubscription) result.get(0) : null;
  }

  @Override
  public List<ISubscription> findSubscriptions(Integer idAccount, SubscriptionType subscriptionType,
      Entity entity) {
    Class<? extends ISubscription> tableEntity = DemandHelper.getSubscriptionTableEntity(entity);
    String queryStr = String.format(
        "SELECT s from %s s WHERE s.idAccount = :idAccount AND s.subscriptionType = :subscriptionType",
        tableEntity.getSimpleName());
    Query query = entityManager.createQuery(queryStr, tableEntity);
    query.setParameter("idAccount", idAccount);
    query.setParameter("subscriptionType", subscriptionType.value());
    return query.getResultList();
  }

  @Override
  public List<ISubscription> findAccountSubscriptionTitles(Entity entity) {
    Class<? extends ISubscription> tableEntity = DemandHelper.getSubscriptionTableEntity(entity);
    SubscriptionType subscriptionType = DemandHelper.getMetadataSubscriptionType(entity);
    String queryStr = String.format(
        "SELECT s FROM %s s WHERE s.subscriptionType = '%s' ORDER BY s.subscriptionRefId",
        tableEntity.getSimpleName(), subscriptionType.value());
    Query query = entityManager.createQuery(queryStr, tableEntity);
    return query.getResultList();
  }

  @Override
  public List<Long> findSubscribedTitles(Entity entity) {
    String tableName = DemandHelper.getSubscriptionTableName(entity);
    SubscriptionType subscriptionType = DemandHelper.getMetadataSubscriptionType(entity);
    String queryStr = String.format(
        "SELECT DISTINCT s.`subscriptionRefId` "
            + "FROM `subscription`.`account` a JOIN `subscription`.`%s` s ON a.`idAccount` = s.`idAccount` "
            + "WHERE (a.`account_type` IN ('subscription') OR a.`idAccount` = 1009) AND s.`subscriptionType` IN ('%s')",
        tableName, subscriptionType.value());
    Query query = entityManager.createNativeQuery(queryStr);
    return query.getResultList();
  }

  @Override
  public List<Long> findSubscriptionShortIds(SubscriptionType subscriptionType, Entity entity) {
    String tableName = DemandHelper.getSubscriptionTableName(entity);
    String queryStr = String.format("SELECT DISTINCT s.`subscriptionRefId` "
            + "FROM `subscription`.`account` a JOIN `subscription`.`%s` s ON a.`idAccount` = s.`idAccount` "
            + "WHERE (a.`account_type` IN ('subscription') OR a.`idAccount` = 1009) AND s.`subscriptionType` = '%s'",
        tableName, subscriptionType.value());
    Query query = entityManager.createNativeQuery(queryStr);
    return query.getResultList();
  }

  public void removeModule(Subscription subscription) throws APIException {
    Account account = accountRepo.findByIdAccount(subscription.getIdAccount());

    // Subscription Table Deletion
    subscriptionsRepo.delete(subscription);

    // Product Offer Spec Table Deletion
    List<ProductSpec> proOfferSpecs = productSpecsRepo.getAccountModulesByProdut(
        account.getIdAccount(),
        (int) subscription.getSubscriptionRefId());
    if (CollectionUtils.isNotEmpty(proOfferSpecs)) {
      productSpecsRepo.deleteAll(proOfferSpecs);
    }
  }

  @Override
  public List<CatalogItem> accountCatalogItems(Integer idAccount, Entity entity) {
    return accountCatalogItems(idAccount, DemandHelper.getMetadataSubscriptionType(entity), entity);
  }

  @Override
  public List<CatalogItem> accountCatalogItems(Integer idAccount, SubscriptionType subscriptionType,
      Entity entity) {
    List<CatalogItem> result;
    List<ISubscription> subscriptions = subscriptionsRepo.findSubscriptions(idAccount,
        subscriptionType, entity);
    result = subscriptions.stream().map(subscription ->
    {
      return metadataCache.resolveItem(subscription.getSubscriptionRefId());
    }).filter(i -> i != null).collect(Collectors.toList());

    return result;
  }

  @Override
  public List<Long> accountTitleShortIDs(Integer idAccount, Entity entity) throws APIException {
    return subscriptionsRepo.accountCatalogItems(idAccount, entity)
        .stream()
        .map(i -> i.getShortID()).collect(Collectors.toList());
  }
}
