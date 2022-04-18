package com.parrotanalytics.api.apidb_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.nonmanaged.DateInputFilter;
import com.parrotanalytics.api.apidb_model.nonmanaged.IntInputFilter;
import com.parrotanalytics.api.apidb_model.nonmanaged.StringInputFilter;
import com.parrotanalytics.api.commons.constants.TVSeriesType;
import com.parrotanalytics.apicore.commons.constants.SeriesStatusType;
import com.parrotanalytics.apicore.config.APIConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

@Data
public class DataQueryFilter implements Serializable {

  /**
   * These are legacy filters
   */
  @JsonProperty(APIConstants.FILTER_LABEL)
  private String label;

  @JsonProperty(APIConstants.FILTER_SEASON_NUMBER)
  private String seasonNumber;

  @JsonProperty(APIConstants.FILTER_ORIGINAL_LANGUAGE)
  private String originalLanguage;

  @JsonProperty(APIConstants.FILTER_TV_SERIES_TYPE)
  private TVSeriesType tvSeriesType;

  @JsonProperty(APIConstants.FILTER_SERIES_STATUS)
  private String seriesStatus;

  @JsonProperty(APIConstants.FILTER_RELEASE_DATE)
  private String releaseDate;

  @JsonProperty(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_LABEL)
  private String notLabel;

  @JsonProperty(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_SEASON_NUMBER)
  private String notSeasonNumber;

  @JsonProperty(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_ORIGINAL_LANGUAGE)
  private String notOriginalLanguage;

  @JsonProperty(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_TV_SERIES_TYPE)
  private TVSeriesType notTvSeriesType;

  @JsonProperty(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_SERIES_STATUS)
  private String notSeriesStatus;

  @JsonProperty(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_RELEASE_DATE)
  private String notReleaseDate;

  @JsonProperty(APIConstants.FILTER_GENRE_TAGS)
  private String genreTags;

  @JsonProperty(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_GENRE_TAGS)
  private String notGenreTags;

  @JsonProperty(APIConstants.FILTER_NETWORK)
  private String network;

  @JsonProperty(APIConstants.FILTER_PLATFORM)
  private String platform;

  @JsonProperty(APIConstants.FILTER_COUNTRY)
  private String country;

  @JsonProperty(APIConstants.FILTER_GENE_COUNTRY)
  private String geneCountry;

  @JsonProperty(APIConstants.FILTER_LANGUAGE)
  private String language;

  @JsonProperty(APIConstants.FILTER_RELEASE_TYPE)
  private String releaseType;

  @JsonProperty(APIConstants.FILTER_MOOD)
  private String mood;

  /**
   * These are new, advanced filters
   */
  @JsonProperty(APIConstants.FILTER_ADVANCED_GENRE_TAGS)
  private StringInputFilter adGenreTags;

  @JsonProperty(APIConstants.FILTER_PRODUCTION_COMPANY)
  private StringInputFilter adProductionCompany;

  @JsonProperty(APIConstants.FILTER_ADVANCED_ORIGINAL_LANGUAGE)
  private StringInputFilter adOriginalLanguage;

  @JsonProperty(APIConstants.FILTER_ADVANCED_ORIGINAL_COUNTRY)
  private StringInputFilter adOriginalCountry;

  @JsonProperty(APIConstants.FILTER_ADVANCED_RELEASE_YEAR)
  private IntInputFilter adReleaseYear;

  @JsonProperty(APIConstants.FILTER_ADVANCED_RELEASE_DATE)
  private DateInputFilter adReleaseDate;

  @JsonProperty(APIConstants.FILTER_DISTRIBUTION_COMPANY)
  private StringInputFilter adDistributionCompany;

  @JsonProperty(APIConstants.FILTER_ADVANCED_OCCUPATIONS)
  private StringInputFilter adOccupations;

  @JsonProperty(APIConstants.FILTER_ADVANCED_GENDER)
  private StringInputFilter adGender;

  @JsonProperty(APIConstants.FILTER_ADVANCED_DATEOFBIRTH)
  private DateInputFilter adDateOfBirth;

  @JsonProperty(APIConstants.FILTER_ADVANCED_CITIZEN_OF)
  private StringInputFilter adCitizenOf;

  /**
   * Composite AND filter, default
   */
  @JsonProperty("and")
  private List<DataQueryFilter> and;

  @JsonProperty("or")
  private List<DataQueryFilter> or;

  private Map<String, String> simpleFilters;

  public DataQueryFilter() {
  }

  public DataQueryFilter(Map<String, String> simpleFilters) {
    setSimpleFilters(simpleFilters);
  }

  private void destructSimpleFilters() {
    if (simpleFilters == null) {
      return;
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_LABEL)) {
      this.label = simpleFilters.get(APIConstants.FILTER_LABEL);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_LABEL)) {
      this.seasonNumber = simpleFilters.get(APIConstants.FILTER_SEASON_NUMBER);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_GENRE_TAGS)) {
      this.genreTags = simpleFilters.get(APIConstants.FILTER_GENRE_TAGS);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_ORIGINAL_LANGUAGE)) {
      this.originalLanguage = simpleFilters.get(APIConstants.FILTER_ORIGINAL_LANGUAGE);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_TV_SERIES_TYPE)) {
      this.tvSeriesType = TVSeriesType.fromValue(
          simpleFilters.get(APIConstants.FILTER_TV_SERIES_TYPE));
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_RELEASE_DATE)) {
      this.releaseDate = simpleFilters.get(APIConstants.FILTER_RELEASE_DATE);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_NETWORK)) {
      this.network = simpleFilters.get(APIConstants.FILTER_NETWORK);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_PLATFORM)) {
      this.platform = simpleFilters.get(APIConstants.FILTER_PLATFORM);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_COUNTRY)) {
      this.country = simpleFilters.get(APIConstants.FILTER_COUNTRY);
    }
    if (simpleFilters.containsKey(APIConstants.FILTER_GENE_COUNTRY)) {
      this.geneCountry = simpleFilters.get(APIConstants.FILTER_GENE_COUNTRY);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_LANGUAGE)) {
      this.language = simpleFilters.get(APIConstants.FILTER_LANGUAGE);
    }

    if (simpleFilters.containsKey(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_LABEL)) {
      this.notLabel = simpleFilters.get(
          APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_LABEL);
    }

    if (simpleFilters.containsKey(
        APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_SEASON_NUMBER)) {
      this.notSeasonNumber = simpleFilters.get(
          APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_SEASON_NUMBER);
    }

    if (simpleFilters.containsKey(
        APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_ORIGINAL_LANGUAGE)) {
      this.notOriginalLanguage = simpleFilters.get(
          APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_ORIGINAL_LANGUAGE);
    }

    if (simpleFilters.containsKey(
        APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_TV_SERIES_TYPE)) {
      this.notTvSeriesType = TVSeriesType.fromValue(simpleFilters.get(
          APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_TV_SERIES_TYPE));
    }

    if (simpleFilters.containsKey(
        APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_RELEASE_DATE)) {
      this.notReleaseDate = simpleFilters.get(
          APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_RELEASE_DATE);
    }

    if (simpleFilters.containsKey(
        APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_GENRE_TAGS)) {
      this.notGenreTags = simpleFilters.get(
          APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_GENRE_TAGS);
    }
  }

  private void constructSimpleFilters() {
    simpleFilters = new HashMap<>();
    if (StringUtils.isNotEmpty(label)) {
      simpleFilters.put(APIConstants.FILTER_LABEL, label);
    }

    if (StringUtils.isNotEmpty(seasonNumber)) {
      simpleFilters.put(APIConstants.FILTER_SEASON_NUMBER, seasonNumber);
    }

    if (StringUtils.isNotEmpty(originalLanguage)) {
      simpleFilters.put(APIConstants.FILTER_ORIGINAL_LANGUAGE, originalLanguage);
    }

    if (tvSeriesType != null) {
      simpleFilters.put(APIConstants.FILTER_TV_SERIES_TYPE, tvSeriesType.value());
    }

    if (seriesStatus != null) {
      simpleFilters.put(APIConstants.FILTER_SERIES_STATUS, seriesStatus);
    }

    if (StringUtils.isNotEmpty(genreTags)) {
      simpleFilters.put(APIConstants.FILTER_GENRE_TAGS, genreTags);
    }

    if (StringUtils.isNotEmpty(releaseDate)) {
      simpleFilters.put(APIConstants.FILTER_RELEASE_DATE, releaseDate);
    }

    if (StringUtils.isNotEmpty(releaseType)) {
      simpleFilters.put(APIConstants.FILTER_RELEASE_TYPE, releaseType);
    }

    if (StringUtils.isNotEmpty(network)) {
      simpleFilters.put(APIConstants.FILTER_NETWORK, network);
    }

    if (StringUtils.isNotEmpty(geneCountry)) {
      simpleFilters.put(APIConstants.FILTER_GENE_COUNTRY, geneCountry);
    }

    if (StringUtils.isNotEmpty(platform)) {
      simpleFilters.put(APIConstants.FILTER_PLATFORM, platform);
    }

    if (StringUtils.isNotEmpty(country)) {
      simpleFilters.put(APIConstants.FILTER_COUNTRY, country);
    }

    if (StringUtils.isNotEmpty(language)) {
      simpleFilters.put(APIConstants.FILTER_LANGUAGE, language);
    }

    if (StringUtils.isNotEmpty(notLabel)) {
      simpleFilters.put(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_LABEL, notLabel);
    }

    if (StringUtils.isNotEmpty(notSeasonNumber)) {
      simpleFilters.put(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_SEASON_NUMBER,
          notSeasonNumber);
    }

    if (StringUtils.isNotEmpty(notOriginalLanguage)) {
      simpleFilters.put(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_ORIGINAL_LANGUAGE,
          notOriginalLanguage);
    }

    if (notTvSeriesType != null) {
      simpleFilters.put(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_TV_SERIES_TYPE,
          notTvSeriesType.value());
    }

    if (notSeriesStatus != null) {
      simpleFilters.put(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_SERIES_STATUS,
          notSeriesStatus);
    }

    if (StringUtils.isNotEmpty(notReleaseDate)) {
      simpleFilters.put(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_RELEASE_DATE,
          notReleaseDate);
    }

    if (StringUtils.isNotEmpty(notGenreTags)) {
      simpleFilters.put(APIConstants.FILTER_GENE_EXCLUDE + APIConstants.FILTER_GENRE_TAGS,
          notGenreTags);
    }
  }

  public boolean hasSimplyFilters() {
    return MapUtils.isNotEmpty(getSimpleFilters());
  }

  public Map<String, String> getSimpleFilters() {
    if (simpleFilters == null) {
      constructSimpleFilters();
    }
    return simpleFilters;
  }

  public void setSimpleFilters(Map<String, String> simpleFilters) {
    this.simpleFilters = simpleFilters;
    destructSimpleFilters();
  }

  public boolean hasAdvancedFilters() {
    return adGenreTags != null || adDistributionCompany != null || adProductionCompany != null
        || adOriginalCountry != null || adOriginalLanguage != null || adReleaseYear != null
        || adReleaseDate != null || adGender != null || adCitizenOf != null || adOccupations != null
        || adDateOfBirth != null
        || CollectionUtils.isNotEmpty(and) || CollectionUtils.isNotEmpty(
        or);
  }
  public boolean hasGeneCountry(){
    return this.getSimpleFilters().containsKey(APIConstants.FILTER_GENE_COUNTRY);
  }

  public boolean isValid() {
    return hasSimplyFilters() || hasAdvancedFilters();
  }
}
