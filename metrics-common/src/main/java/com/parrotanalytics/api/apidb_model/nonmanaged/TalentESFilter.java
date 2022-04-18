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
public class TalentESFilter extends MetadataESFilter {

  private static final long serialVersionUID = -8412891704971539288L;

  private StringInputFilter adOccupations;

  private StringInputFilter adGender;

  private DateInputFilter adDateOfBirth;

  private StringInputFilter adOriginalCountry;

  private StringInputFilter adCitizenOf;


  public TalentESFilter(DataQueryFilter dataQueryFilter) {
    this(dataQueryFilter, MergeLogicOperator.AND);
  }

  public TalentESFilter(DataQueryFilter dataQueryFilter, MergeLogicOperator operator) {
    super(Entity.TALENT);
    this.operator = operator;
    this.adOccupations = dataQueryFilter.getAdOccupations();
    this.adGender = dataQueryFilter.getAdGender();
    this.adDateOfBirth = dataQueryFilter.getAdDateOfBirth();
    this.adOriginalCountry = dataQueryFilter.getAdOriginalCountry();
    this.adCitizenOf = dataQueryFilter.getAdCitizenOf();

    setAnd(dataQueryFilter.getAnd());
    setOr(dataQueryFilter.getOr());
  }

  @Override
  public BoolQueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = super.buildQuery();

    if (adOccupations != null) {
      TermESArrayFieldQueryBuilder builder = new TermESArrayFieldQueryBuilder(adOccupations,
          "data.primary_occupation");
      mergeQuery(boolQuery, builder.buildQuery());
    }

    if (adGender != null) {
      TermESArrayFieldQueryBuilder builder = new TermESArrayFieldQueryBuilder(adGender,
          "data.gender");
      mergeQuery(boolQuery, builder.buildQuery());
    }

    if (adDateOfBirth != null) {
      DateESDateFieldQueryBuilder builder = new DateESDateFieldQueryBuilder(adDateOfBirth,
          "data.date_of_birth");
      mergeQuery(boolQuery, builder.buildQuery());
    }
    if (adOriginalCountry != null) {
      TermESArrayFieldQueryBuilder builder = new TermESArrayFieldQueryBuilder(adOriginalCountry,
          "data.original_country.0");
      mergeQuery(boolQuery, builder.buildQuery());
    }
    if (adCitizenOf != null) {
      TermESArrayFieldQueryBuilder builder = new TermESArrayFieldQueryBuilder(adCitizenOf,
          "data.citizen_of");
      mergeQuery(boolQuery, builder.buildQuery());
    }
    return boolQuery;
  }
}
