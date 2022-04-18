package com.parrotanalytics.api.services;

import com.parrotanalytics.api.apidb_model.DataQueryFilter;
import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.nonmanaged.PortfolioType;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.apicore.config.APIConstants;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@Data
@Log4j
public class PortfolioItemsProvider implements Supplier<Portfolio> {

  private Portfolio p;
  private DataQuery dataQuery;
  private DemandService demandService;
  private List<Long> catalogShortIDs;

  public PortfolioItemsProvider(DataQuery dataQuery,
      List<Long> catalogShortIDs, Portfolio p, DemandService demandService) {
    this.dataQuery = dataQuery;
    this.catalogShortIDs = catalogShortIDs;
    this.p = p;
    this.demandService = demandService;
  }

  @Override
  public Portfolio get() {
    if (p.getType() != PortfolioType.custom) {
      HashMap<String, String> simpleFilters = new HashMap<>();
      simpleFilters.put(p.getType().value(), p.getFilterValue());
      try {
        //D360-6826: For Platform Portfolios,
        //investigate it can be achieved on the API side using the current genecountry filter
        if (p.getType() == PortfolioType.platform && dataQuery.hasValidFilter()
            && dataQuery.getFilters().hasGeneCountry()) {
          simpleFilters.put(APIConstants.FILTER_GENE_COUNTRY,
              dataQuery.getFilters().getGeneCountry());
        }
        DataQueryFilter filter = new DataQueryFilter();
        filter.setSimpleFilters(simpleFilters);
        List<Long> shortIDList = demandService.filterTitles(dataQuery.getEntityEnum(), filter,
            catalogShortIDs);
        p.setShortIDList(shortIDList);
      } catch (Exception ex) {
        log.error(String.format("Failed to load portfolio ID %s shortIDList. Exception {}",
            p.getIdPortfolio()), ex);
      }
    }
    return p;
  }
}
