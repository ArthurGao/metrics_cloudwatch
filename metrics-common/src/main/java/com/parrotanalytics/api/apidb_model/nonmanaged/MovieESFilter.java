package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.api.apidb_model.DataQueryFilter;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.MergeLogicOperator;
import com.parrotanalytics.apicore.config.APIConstants;
import java.util.Arrays;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieESFilter extends MetadataESFilter {

  private static final long serialVersionUID = 7918479675462940470L;

  private StringInputFilter adDistributionCompany;

  private StringInputFilter adProductionCompany;

  private StringInputFilter adGenreTags;

  private StringInputFilter adOriginalLanguage;

  private StringInputFilter adOriginalCountry;

  private IntInputFilter adReleaseYear;

  private DateInputFilter adReleaseDate;

  public MovieESFilter(DataQueryFilter dataQueryFilter) {
    this(dataQueryFilter, MergeLogicOperator.AND);
  }

  public MovieESFilter(DataQueryFilter dataQueryFilter, MergeLogicOperator operator) {
    super(Entity.MOVIE);
    this.operator = operator;

    //advanced filter
    this.adDistributionCompany = dataQueryFilter.getAdDistributionCompany();
    this.adProductionCompany = dataQueryFilter.getAdProductionCompany();
    this.adGenreTags = dataQueryFilter.getAdGenreTags();
    this.adOriginalLanguage = dataQueryFilter.getAdOriginalLanguage();
    this.adOriginalCountry = dataQueryFilter.getAdOriginalCountry();
    this.adReleaseYear = dataQueryFilter.getAdReleaseYear();
    this.adReleaseDate = dataQueryFilter.getAdReleaseDate();
    setAnd(dataQueryFilter.getAnd());
    setOr(dataQueryFilter.getOr());

    //we wont support simple filter for movie
    //only show is backward compatibility

  }

  @Override
  public BoolQueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = super.buildQuery();

    if (adDistributionCompany != null) {
      MovieDistributionCompanyQueryBuilder builder = new MovieDistributionCompanyQueryBuilder(
          adDistributionCompany);
      mergeQuery(boolQuery, builder.buildQuery());
    }

    if (adProductionCompany != null) {
      MovieProductionCompanyQueryBuilder builder = new MovieProductionCompanyQueryBuilder(
          adProductionCompany);
      mergeQuery(boolQuery, builder.buildQuery());
    }

    if (adGenreTags != null) {
      TermESArrayFieldQueryBuilder builder = new TermESArrayFieldQueryBuilder(adGenreTags,
          "data.genre_tags");
      mergeQuery(boolQuery, builder.buildQuery());
    }

    if (adOriginalLanguage != null) {
      MovieOriginalLanguageQueryBuilder builder = new MovieOriginalLanguageQueryBuilder(
          adOriginalLanguage);
      mergeQuery(boolQuery, builder.buildQuery());
    }

    if (adOriginalCountry != null) {
      MovieOriginalCountryQueryBuilder builder = new MovieOriginalCountryQueryBuilder(
          adOriginalCountry);
      mergeQuery(boolQuery, builder.buildQuery());
    }

    if (adReleaseYear != null) {
      IntESIntFieldQueryBuilder builder = new IntESIntFieldQueryBuilder(adReleaseYear,
          "data.release_year");
      mergeQuery(boolQuery, builder.buildQuery());
    }
    if (adReleaseDate != null) {
      DateESDateFieldQueryBuilder builder = new DateESDateFieldQueryBuilder(adReleaseDate,
          "data.start_date");
      mergeQuery(boolQuery, builder.buildQuery());
    }
    return boolQuery;
  }
}
