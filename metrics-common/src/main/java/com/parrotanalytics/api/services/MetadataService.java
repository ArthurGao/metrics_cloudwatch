package com.parrotanalytics.api.services;

import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.MovieItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVItem;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataService {

  protected static final Logger logger = LogManager.getLogger(MetadataService.class);

  @Autowired
  private MetadataCache metadataCache;

  public List<Long> filterShowsByOriginalLanguage(String originalLanguageFilterValue,
      List<Long> contentShortIDs) {

    List<String> values = StringUtils.isNotEmpty(originalLanguageFilterValue) ? Arrays.asList(
        originalLanguageFilterValue.split(APIConstants.DELIM_COMMA)) : Collections.emptyList();
    if (CollectionUtils.isEmpty(values)) {
      return Collections.emptyList();
    }

    return contentShortIDs.stream().map(shortID -> {
      CatalogItem item = metadataCache.resolveItem(shortID);
      if (item instanceof TVItem && item.getData() instanceof TVData) {
        TVItem tvItem = (TVItem) item;
        Map<Integer, String> lang = tvItem.getOriginalLanguage();
        for (String value : values) {
          if (lang != null && lang.containsValue(value)) {
            return shortID;
          }
        }
        return null;
      }

      return null;
    }).filter(Objects::nonNull).collect(Collectors.toList());

  }

  public List<Long> filterShowsByTvSeriesType(String tvSeriesTypeFilterValue,
      List<Long> contentShortIDs) {
    boolean isScripted = "scripted".equalsIgnoreCase(tvSeriesTypeFilterValue);

    return contentShortIDs.stream().map(shortID -> {
      CatalogItem item = metadataCache.resolveItem(shortID);
      if (item instanceof TVItem && item.getData() instanceof TVData) {
        String tvSeriesType = item.getData().getTvseriesType();
        if (tvSeriesType == null) {
          return null;
        }
        if (isScripted) {
          return "scripted".equalsIgnoreCase(tvSeriesType) ? shortID : null;
        } else {
          return !"scripted".equalsIgnoreCase(tvSeriesType) ? shortID : null;
        }
      }

      return null;
    }).filter(Objects::nonNull).collect(Collectors.toList());

  }

  public String getOriginalCountry(CatalogItem cI) {
    String homeCountryISO = null;
    if (cI instanceof TVItem) {
      homeCountryISO = ((TVItem) cI).getOriginalCountry().firstEntry().getValue();
    } else if (cI instanceof MovieItem) {
      homeCountryISO = ((MovieItem) cI).getOriginalCountry().get(0).getValue();
    }
    return homeCountryISO;
  }

}
