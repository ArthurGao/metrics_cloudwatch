package com.parrotanalytics.api.request.decorators;

import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.apidb_model.RangeKeyLookup;
import com.parrotanalytics.api.apidb_model.Role;
import com.parrotanalytics.api.apidb_model.UserRole;
import com.parrotanalytics.api.commons.constants.TimePeriod;
import com.parrotanalytics.api.data.repo.api.DemandRepository;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.api.request.ingenreperformance.InGenrePerformanceDataQuery;
import com.parrotanalytics.api.request.ingenreperformance.InGenrePerformanceRequest;
import com.parrotanalytics.api.services.DemandService;
import com.parrotanalytics.api.services.UserService;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.exceptions.InvalidInputException;
import com.parrotanalytics.apicore.model.request.APIRequest;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import com.parrotanalytics.commons.utils.date.DateUtils;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InGenrePerformanceRequestDecorator implements Decorator<InGenrePerformanceRequest> {

  @Autowired
  private DemandRepository demandDataRepo;

  @Autowired
  private DemandService demandService;

  /**
   * @param apiRequest
   * @throws APIException
   */
  public void decorateRequest(InGenrePerformanceRequest apiRequest) throws APIException {
    InternalUser callUser = UserService.callUser();
    fillCallOwner(callUser, apiRequest);
    apiRequest.setCallUser(callUser);

    if (CollectionUtils.isEmpty(apiRequest.getQuery())) {
      throw new InvalidInputException("No query object passed in expressions request");
    }
    InGenrePerformanceDataQuery dataQuery = apiRequest.getQuery().get(0);

    /* identify content */
    dataQuery.setListContentIDs(Collections.singletonList(dataQuery.getContent()));

    /* identify time period */
    parseTime(apiRequest, dataQuery);

    // Fetch short ids
    List<Long> contentShortIDs = demandService.extractIDs(
        Collections.singletonList(dataQuery.getContent()));
    dataQuery.setShortIDsList(contentShortIDs);
  }


  /**
   * @param apiRequest
   * @param dataQuery
   */
  private void parseTime(InGenrePerformanceRequest apiRequest,
      InGenrePerformanceDataQuery dataQuery) {
    // A: Check for Keys based Date Range
    if (dataQuery.getPeriod() != null) {
      TimePeriod timePeriod = TimePeriod.fromValue(dataQuery.getPeriod());

      if (timePeriod != null) {
        dataQuery.setDateFrom(timePeriod.dateFrom());
        dataQuery.setDateTo(timePeriod.dateTo());
        dateAdjustmentByLatest(dataQuery, timePeriod, demandDataRepo.latestDemandDate());
      } else if (dataQuery.getDateFrom() == null || dataQuery.getDateTo() == null) {
        RangeKeyLookup rangeKeyLookup = demandDataRepo.findRangeKeyLookupByRangeKey(
            dataQuery.getPeriod());
        if (rangeKeyLookup != null) {
          dataQuery.setDateFrom(new DateTime(rangeKeyLookup.getStart()));
          dataQuery.setDateTo(new DateTime(rangeKeyLookup.getEnd()));
        }
      }
    }
    apiRequest.setDateRangeList(
        DateUtils.toDateRangeList(dataQuery.getDateFrom(), dataQuery.getDateTo()));
  }

  private void dateAdjustmentByLatest(
      DataQuery dataQuery, TimePeriod timePeriod, Date latestDate) {
    int daysDelta = 0;

    if (timePeriod.dateTo().isAfter(new DateTime(latestDate))) {
      daysDelta = daysDiff(new DateTime(latestDate), timePeriod.dateTo()) - 1;
      dataQuery.setDateTo(timePeriod.dateTo().minusDays(daysDelta));
    } else {
      dataQuery.setDateTo(timePeriod.dateTo());
    }
    /* prevent we set the date from before accessiable rating from */
    if (daysDelta > 0 && TimePeriod.ALLTIME.dateFrom().isBefore(timePeriod.dateFrom())) {
      dataQuery.setDateFrom(timePeriod.dateFrom().minusDays(daysDelta));
    } else {
      dataQuery.setDateFrom(timePeriod.dateFrom());
    }
  }

  private Integer daysDiff(DateTime start, DateTime end) {
    if (start != null && end != null) {
      return CommonsDateUtil.daysInBetween(start, end);
    }
    return 0;
  }


  /**
   * @param callUser
   * @param apiRequest
   * @throws APIException
   */
  private void fillCallOwner(InternalUser callUser, APIRequest apiRequest) {
    /*
     * API user ID
     */
    String userID = callUser.getEmailAddress();
    apiRequest.setApiUserId(userID);

    /*
     * API user account ID
     */
    Integer accountID = callUser.getAccount().getIdAccount();
    apiRequest.setApiAccountId(accountID);

    if (!callUser.getUserRoles().isEmpty()) {
      hasHelpDeskAdminRole(callUser.getUserRoles(), apiRequest);
    }
  }

  private void hasHelpDeskAdminRole(List<UserRole> userRoles, APIRequest apiRequest) {
    for (UserRole uR : userRoles) {
      if (Role.HELPDESK_VIEWER_ROLE_ID.equals(uR.getRole().getIdRole())) {
        apiRequest.setReadUser(true);

      } else if (Role.HELPDESK_ADMIN_ROLE_ID.equals(uR.getRole().getIdRole())) {
        apiRequest.setSuperUser(true);

      }
    }
  }
}
