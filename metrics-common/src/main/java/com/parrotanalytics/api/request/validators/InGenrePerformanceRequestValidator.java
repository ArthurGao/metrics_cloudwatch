package com.parrotanalytics.api.request.validators;

import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.request.ingenreperformance.InGenrePerformanceDataQuery;
import com.parrotanalytics.api.request.ingenreperformance.InGenrePerformanceRequest;
import com.parrotanalytics.api.response.ingenreperformance.InGenrePerformanceResponse;
import com.parrotanalytics.api.services.UserService;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.commons.constants.APIStatus;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.config.MessageBundleService;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.exceptions.InvalidInputException;
import com.parrotanalytics.apicore.model.response.APIResponse;
import com.parrotanalytics.apicore.model.response.NotificationHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InGenrePerformanceRequestValidator implements
    Validator<InGenrePerformanceRequest, InGenrePerformanceResponse> {

  @Autowired
  private UserService userService;

  @Autowired
  private SubscriptionsRepository subscriptionsRepo;

  @Autowired
  public MetadataCache metadataCache;

  /* I18n message service */
  @Autowired(required = true)
  private MessageBundleService messageBundle;

  @Override
  public InGenrePerformanceResponse validateCall(InGenrePerformanceRequest request) throws APIException {
    InternalUser callUser = UserService.callUser();
    InGenrePerformanceResponse apiResponse = null;

    if (CollectionUtils.isEmpty(request.getQuery())) {
      throw new InvalidInputException("missing data query in request");
    }
    InGenrePerformanceDataQuery dataQuery = request.getQuery().get(0);

    // Date Range Validation
    validDateRange(callUser, dataQuery.getDateFrom(), dataQuery.getDateTo());

    // Markets Validation
    if (StringUtils.isNotEmpty(dataQuery.getMarketStr())
        && !(dataQuery.getMarketStr().equalsIgnoreCase(APIConstants.SUBSCRIPTION_MARKETS)
        || dataQuery.getMarketStr().equalsIgnoreCase(APIConstants.ALL_MARKETS))) {
      validateMarkets(callUser, Collections.singletonList(dataQuery.getMarketStr()),
          dataQuery.getEntityEnum());
    }

    // Content validation
    if (dataQuery.getContent() != null && dataQuery.isTVSeriesQuery()
        && !(dataQuery.getContent().equalsIgnoreCase(APIConstants.SUBSCRIPTION_SHOWS)
        || dataQuery.getContent().equalsIgnoreCase(APIConstants.ALL_SHOWS))) {
      apiResponse = validateTitles(callUser, dataQuery.getListContentIDs());
    } else if (CollectionUtils.isEmpty(dataQuery.getShortIDsList())) {
      apiResponse = new InGenrePerformanceResponse();
      apiResponse.setWarning(new NotificationHolder());
      apiResponse.getWarning().setCode(APIStatus.NOT_FOUND);
      apiResponse.getWarning().setMessage("no content found matching given request");
      return apiResponse;
    }
    return apiResponse;
  }


  private void validDateRange(InternalUser callUser, DateTime dateFrom, DateTime dateTo)
      throws APIException {

    if (dateFrom.isAfter(dateTo)) {
      throw new InvalidInputException(messageBundle.getMessage("commons.invalid.daterange"));
    } else {
      DateTime ratingsAccessFrom = userService.ratingsAccessFrom(callUser.getAccount());

      if (dateFrom.isBefore(ratingsAccessFrom) || dateTo.isBefore(ratingsAccessFrom)) {
        throw new InvalidInputException(
            messageBundle.getMessage("commons.non.licensed.daterange",
                new Object[]{ratingsAccessFrom}));
      }
    }

  }

  private void validateMarkets(InternalUser callUser, List<String> listMarkets,
      Entity entity) throws APIException {
    List<String> subscriptionMarkets = subscriptionsRepo.getMarketsISOCodesByAccount(
        callUser.getAccount().getIdAccount(), !userService.isMonitorTierUser(callUser), entity);

    for (String mktISO : listMarkets) {
      if (!subscriptionMarkets.contains(mktISO) && !DemandHelper.isWorldwideMarket(mktISO)) {
        throw new InvalidInputException(
            messageBundle.getMessage("commons.non.licensed.market", new Object[]{mktISO}));
      }
    }
  }

  private InGenrePerformanceResponse validateTitles(InternalUser callUser, List<String> listContentIDs)
      throws APIException {

    List<String> subscriptionIDs = metadataCache.accountTitles(callUser.getAccount()).stream()
        .map(cI -> cI.getParrotID()).collect(Collectors.toList());

    if (!listContentIDs.isEmpty()) {
      List<String> requestedTitles = listContentIDs.stream().distinct()
          .collect(Collectors.toList());

      if (requestedTitles != null) {
        /*
         * license verification check
         */
        List<String> titlesNotSubscribed = extractTitlesNotSubscribed(subscriptionIDs,
            new ArrayList<String>(requestedTitles));

        if (!CollectionUtils.isEmpty(titlesNotSubscribed)) {
          /**
           * Remove titles non-licensed titles from API request
           * content items
           */
          return removeNonLicensedTitles(requestedTitles, titlesNotSubscribed);
        }

      }
    }
    return null;
  }

  private List<String> extractTitlesNotSubscribed(List<String> subscriptionIDs,
      List<String> requestedTitles) {
    requestedTitles.removeAll(subscriptionIDs);
    return requestedTitles;
  }

  private InGenrePerformanceResponse removeNonLicensedTitles(List<String> requestedTitles,
      List<String> titlesNotSubscribed) {
    requestedTitles.removeAll(titlesNotSubscribed);

    if (requestedTitles.isEmpty()) {
      InGenrePerformanceResponse validationResponse = new InGenrePerformanceResponse();

      /**
       * Adds non-licensed items in response error holder
       */
      validationResponse.setError(new NotificationHolder());
      validationResponse.getError().setCode(APIStatus.NON_LICENSED);
      validationResponse.getError()
          .setMessage(messageBundle.getMessage("security.non.licensed.title"));

      for (String parrotID : titlesNotSubscribed) {
        validationResponse.getError().addItem(parrotID);
      }
      return validationResponse;
    } else {
      InGenrePerformanceResponse validationResponse = new InGenrePerformanceResponse();

      /**
       * Adds non-licensed items in response warning holder
       */
      validationResponse.setWarning(new NotificationHolder());
      validationResponse.getWarning().setCode(APIStatus.NON_LICENSED);
      validationResponse.getWarning()
          .setMessage(messageBundle.getMessage("security.non.licensed.title"));

      for (String parrotID : titlesNotSubscribed) {
        validationResponse.getWarning().addItem(parrotID);
      }

      return validationResponse;
    }
  }
}
