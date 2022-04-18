package com.parrotanalytics.api.data.repo.api.custom;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.apidb_model.ISubscription;
import com.parrotanalytics.api.apidb_model.Subscription;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.SubscriptionType;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.response.markets.AccountMarketResponse;
import com.parrotanalytics.api.response.subscriptions.ModuleResponse;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.security.auth.login.AccountNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * Interface for custom methods in {@link SubscriptionsRepository}.
 *
 * @author Sanjeev Sharma
 * @since v1
 */
public interface SubscriptionsRepositoryCustom {

  /**
   * initiates an account info load request
   *
   * @param accountID
   * @return
   * @throws APIException
   */
  boolean loadAccountData(Integer accountID) throws APIException;

  /**
   * Account Id for given user.
   *
   * @param userId
   * @return
   */
  Integer findAccountId(String userId) throws AccountNotFoundException;

  /**
   * @param accountId
   * @return
   */
  boolean hasAccountAccess(String userId, Integer accountId) throws APIException;

  /**
   * @param accountID
   * @return
   * @throws ExecutionException
   */
  List<AccountMarketResponse> getAccountMarkets(Integer accountID, Entity entity)
      throws APIException;

  /**
   * @param accountID
   * @return
   */
  List<String> getMarketsISOCodesByAccount(Integer accountID, Entity entity) throws APIException;

  List<String> getMarketsISOCodesByAccount(Integer accountID, boolean isMonitorTierIncluded,
      Entity entity)
      throws APIException;

  /**
   * @param account
   * @return
   * @throws ExecutionException
   */
  List<CatalogItem> getAccountTitles(Account account, Entity entity) throws APIException;

  List<CatalogItem> getAccountTitles(Account account, boolean isInternal, Entity entity)
      throws APIException;

  List<CatalogItem> accountCatalogItems(Integer idAccount, Entity entity);

  List<CatalogItem> accountCatalogItems(Integer idAccount, SubscriptionType subscriptionType,
      Entity entity);

  List<Long> accountTitleShortIDs(Integer idAccount, Entity entity) throws APIException;

  List<CatalogItem> getAllTitles(Entity entity) throws APIException;

  @CacheEvict(cacheNames = {APICacheConstants.CACHE_ACCOUNT_MARKETS,
      APICacheConstants.CACHE_ACCOUNT_TITLES}, allEntries = true)
  Subscription saveSubscription(Subscription subscription) throws APIException;

  @CacheEvict(cacheNames = {APICacheConstants.CACHE_ACCOUNT_MARKETS,
      APICacheConstants.CACHE_ACCOUNT_TITLES}, allEntries = true)
  void removeSubscription(Subscription subscription) throws APIException;

  @CacheEvict(cacheNames = APICacheConstants.CACHE_SUBSCRIPTIONS, allEntries = true)
  void invalidateSubscriptionsCache();

  List<ModuleResponse> getModuleResponse(Account account) throws APIException;

  List<Integer> findMarketIDsByAccount(Integer idAccount,
      SubscriptionType subscriptionType, Entity entity);

  List<Country> findMarketsByAccount(Integer idAccount, Entity entity);

  ISubscription findSubscription(Integer idAccount, SubscriptionType subscriptionType,
      Long subscriptionRefId,
      Entity entity);

  ISubscription findSubscriptionById(Integer idAccount, SubscriptionType subscriptionType,
      Long subscriptionRefId, Entity entity);

  @Cacheable(cacheNames = APICacheConstants.CACHE_SUBSCRIPTIONS, keyGenerator = "cacheKeyGenerator")
  List<ISubscription> findSubscriptions(Integer idAccount, SubscriptionType subscriptionType,
      Entity entity);

  List<ISubscription> findAccountSubscriptionTitles(Entity entity);

  List<Long> findSubscribedTitles(Entity entity);

  List<Long> findSubscriptionShortIds(SubscriptionType subscriptionType, Entity entity);
}
