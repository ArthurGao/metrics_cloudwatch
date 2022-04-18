package com.parrotanalytics.api.data.repo.api.custom.impl;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.apidb_model.ISubscription;
import com.parrotanalytics.api.apidb_model.Subscription;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.SubscriptionType;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.MarketsRepository;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.TitlesRepository;
import com.parrotanalytics.api.data.repo.api.custom.MarketsRepositoryCustom;
import com.parrotanalytics.api.response.markets.AccountMarketResponse;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Sanjeev Sharma
 * @author Minh Vu
 */
public class MarketsRepositoryImpl implements MarketsRepositoryCustom {

  private static final Logger logger = LogManager.getLogger(MarketsRepositoryImpl.class);

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  TitlesRepository titlesRepository;

  @Autowired
  private MarketsRepository marketsRepository;

  @Autowired
  private SubscriptionsRepository subscriptionsRepository;

  private Map<String, Country> countryCache = new HashMap<>();

  public Country resolveMarket(String isoCode) {
    if (isoCode.equalsIgnoreCase(APIConstants.WORLDWIDE_CODE)) {
      return Country.worlwide();
    }
    return countryCache.get(isoCode);
  }

  @Override
  public List<String> getMarketsISOCodesByAccount(Integer accountID, Entity entity)
      throws APIException {
    List<String> isoCodes = null;

    List<AccountMarketResponse> acMarkets = getAccountMarkets(accountID, entity);

    if (acMarkets != null && !acMarkets.isEmpty()) {
      isoCodes = new ArrayList<>();

      for (AccountMarketResponse pM : acMarkets) {
        isoCodes.add(pM.getIso());
      }
      return isoCodes;
    }
    return null;
  }

  /**
   * This method has been cached by redis with Cachable annotation. It will replace the Guava Cache
   * mechanism.
   *
   * @param accountID
   * @return
   * @throws APIException
   */
  @Override
  public List<AccountMarketResponse> getAccountMarkets(Integer accountID, Entity entity)
      throws APIException {

    Account account = accountRepository.findByIdAccount(accountID);

    List<AccountMarketResponse> accountMarkets = findSubscriptionMarkets(account, entity);

    logger.info("loaded {} markets for account {}", accountMarkets.size(), accountID);

    return accountMarkets;

  }

  /**
   * @param account
   * @return
   */
  private List<AccountMarketResponse> findSubscriptionMarkets(Account account, Entity entity) {

    List<AccountMarketResponse> productMarkets = new ArrayList<>();

    List<Country> accountMarkets = subscriptionsRepository.findMarketsByAccount(
        account.getIdAccount(), entity);

    List<ISubscription> marketSubscriptions = subscriptionsRepository.findSubscriptions(
        account.getIdAccount(),
        SubscriptionType.MARKET, entity);

    Map<Long, ISubscription> accountSubscriptionsByMarkets = marketSubscriptions.stream()
        .collect(Collectors.toMap(ISubscription::getSubscriptionRefId, item -> item));

    Iterator<Country> mktItr = accountMarkets.iterator();

    while (mktItr.hasNext()) {
      Country country = mktItr.next();

      if (!countryCache.containsKey(country.getIso())) {
        countryCache.put(country.getIso(), country);
      }

      AccountMarketResponse pMarket = asProductMarket(account, country,
          accountSubscriptionsByMarkets.get((long) country.getIdCountries()));

      productMarkets.add(pMarket);

    }

    return productMarkets;
  }

  public AccountMarketResponse asProductMarket(Account account, Country country,
      ISubscription subscription) {

    AccountMarketResponse accountMarket = new AccountMarketResponse()
        .withId(Integer.valueOf(country.getIdCountries())).withIso(country.getIso())
        .withIso3(country.getIso3())
        .withDisplayName(country.getDisplayName()).withRegion(country.getRegion())
        .withSubRegion(country.getSub_region()).withStatus(country.getActive())
        .withAccessibleTier(country.getAccessibleTier());

    accountMarket.withSubscriptionStart(
        subscription != null ? CommonsDateUtil.formatDate(subscription.getSubscriptionStart())
            : null);

    if (account.getHomeMarket().equals(accountMarket.getId())) {
      accountMarket.withHomeMarket(Boolean.TRUE);
    }
    return accountMarket;
  }

  public AccountMarketResponse asProductMarket(Account account, Country country) {

    ISubscription subscription = subscriptionsRepository.findSubscriptionById(
        account.getIdAccount(),
        SubscriptionType.MARKET, country.getIdCountries().longValue(), Entity.TV_SERIES);

    return asProductMarket(account, country, (Subscription) subscription);
  }

  public Map<String, Country> getAllIsoCountryMap() {
    Map<String, Country> mapCountries = marketsRepository.findAll().stream()
        .collect(Collectors.toMap(Country::getIso, Function.identity()));
    return mapCountries;
  }

}
