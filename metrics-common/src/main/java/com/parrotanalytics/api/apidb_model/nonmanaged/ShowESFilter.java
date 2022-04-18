package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.api.apidb_model.DataQueryFilter;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.MergeLogicOperator;
import com.parrotanalytics.api.commons.constants.TVSeriesType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;

@Data
public class ShowESFilter extends MetadataESFilter {

  /* original country */
  private String country;

  /* genre tags */
  private String genreTags;

  /* original language */
  private String originalLanguage;

  /* original network */
  private String network;

  /* total seasons */
  private String seasonNumber;

  /* tv series typee */
  private TVSeriesType tvSeriesType;

  /* tv series status */
  private String seriesStatus;

  /* platform */
  private String platform;

  /* gene country for platform filter */
  private String geneCountry;

  /* release_type */
  private String releaseType;

  /* mood */
  private String mood;

  /* release_date */
  private String releaseDate;

  /* not season number*/
  private String notSeasonNumber;

  /* not original language */
  private String notOriginalLanguage;

  private TVSeriesType notTvSeriesType;

  private String notSeriesStatus;

  private String notGenreTags;

  private String notReleaseDate;


  public ShowESFilter(DataQueryFilter dataQueryFilter) {
    this(dataQueryFilter, MergeLogicOperator.AND);
  }

  public ShowESFilter(DataQueryFilter dataQueryFilter, MergeLogicOperator operator) {
    super(Entity.SHOW);
    this.operator = operator;

    setAnd(dataQueryFilter.getAnd());
    setOr(dataQueryFilter.getOr());

    //simple, legacy filter
    this.country = dataQueryFilter.getCountry();
    this.genreTags = dataQueryFilter.getGenreTags();
    this.originalLanguage = dataQueryFilter.getOriginalLanguage();
    this.network = dataQueryFilter.getNetwork();
    this.seasonNumber = dataQueryFilter.getSeasonNumber();
    this.platform = dataQueryFilter.getPlatform();
    this.geneCountry = dataQueryFilter.getGeneCountry();
    this.releaseType = dataQueryFilter.getReleaseType();
    this.mood = dataQueryFilter.getMood();
    this.tvSeriesType = dataQueryFilter.getTvSeriesType();
    this.seriesStatus = dataQueryFilter.getSeriesStatus();
    this.releaseDate = dataQueryFilter.getReleaseDate();

    //negate of simple, legacy filter
    this.notSeasonNumber = dataQueryFilter.getNotSeasonNumber();
    this.notOriginalLanguage = dataQueryFilter.getNotOriginalLanguage();
    this.notTvSeriesType = dataQueryFilter.getNotTvSeriesType();
    this.notSeriesStatus = dataQueryFilter.getNotSeriesStatus();
    this.notGenreTags = dataQueryFilter.getNotGenreTags();
    this.notReleaseDate = dataQueryFilter.getNotReleaseDate();

  }

  @Override
  public BoolQueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = super.buildQuery();

    if (StringUtils.isNotEmpty(country)) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(country,
          "data.original_country.0").buildQuery());
    }

    if (StringUtils.isNotEmpty(genreTags)) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(genreTags,
          "data.genre_tags").buildQuery());
    }

    if (StringUtils.isNotEmpty(originalLanguage)) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(
          originalLanguage, "data.original_language.0").buildQuery());
    }
    if (StringUtils.isNotEmpty(network)) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(network,
          "data.original_network.0").buildQuery());
    }
    if (StringUtils.isNotEmpty(seasonNumber)) {
      mergeQuery(boolQuery, new ShowSeasonNumberQueryBuilder(seasonNumber, false).buildQuery());
    }
    if (StringUtils.isNotEmpty(platform)) {
      ShowPlatformCountryQueryBuilder builder = new ShowPlatformCountryQueryBuilder(platform,
          geneCountry);
      mergeQuery(boolQuery, builder.buildQuery());
    }
    if (StringUtils.isNotEmpty(releaseType)) {
      mergeQuery(boolQuery, new ShowReleaseTypeQueryBuilder(releaseType).buildQuery());
    }
    if (StringUtils.isNotEmpty(mood)) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(mood,
          "data.mood").buildQuery());
    }
    if (StringUtils.isNotEmpty(releaseDate)) {
      mergeQuery(boolQuery, new ShowReleaseDateQueryBuilder(releaseDate, false).buildQuery());
    }
    if (tvSeriesType != null) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(tvSeriesType.value(),
          "data.tv_series_type").buildQuery());
    }
    if (seriesStatus != null) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(seriesStatus,
          "data.status").buildQuery());
    }
    if (StringUtils.isNotEmpty(notSeasonNumber)) {
      mergeQuery(boolQuery, new ShowSeasonNumberQueryBuilder(notSeasonNumber, true).buildQuery());
    }
    if (StringUtils.isNotEmpty(notOriginalLanguage)) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(
          notOriginalLanguage, "data.original_language.0", true).buildQuery());
    }
    if (notTvSeriesType != null) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(notTvSeriesType.value(),
          "data.tv_series_type", true).buildQuery());
    }
    if (notSeriesStatus != null) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(notSeriesStatus,
          "data.status", true).buildQuery());
    }
    if (StringUtils.isNotEmpty(notGenreTags)) {
      mergeQuery(boolQuery, new StringESStringFieldQueryBuilder(notGenreTags,
          "data.genre_tags", true).buildQuery());
    }
    if (StringUtils.isNotEmpty(notReleaseDate)) {
      mergeQuery(boolQuery, new ShowReleaseDateQueryBuilder(releaseDate, true).buildQuery());
    }
    return boolQuery;
  }

}
