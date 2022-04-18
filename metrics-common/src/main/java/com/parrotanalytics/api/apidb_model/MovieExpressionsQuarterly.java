package com.parrotanalytics.api.apidb_model;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import com.rits.cloning.Cloner;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * @author Minh Vu
 */
@Data
@Entity
@IdClass(value = ExpressionsQuarterlyPK.class)
@Table(name = TableConstants.MOVIE_EXPRESSIONS_COMPUTED_QUARTERLY, schema = TableConstants.METRICS_SCHEMA)
public class MovieExpressionsQuarterly extends RDSBaseEntity {

  private static final long serialVersionUID = -7053799042525427155L;

  @Id
  @Temporal(TemporalType.DATE)
  @Column(name = "date")
  private Date date;

  @Id
  @Column(name = "range_key")
  private String range_key;

  @Id
  @Column(name = "country")
  private String country;

  @Id
  @Column(name = "short_id")
  private long short_id;

  @Column(name = "raw_de")
  private double raw_de;

  @Column(name = "peak_raw_de")
  private double peak_raw_de;

  @Column(name = "real_de")
  private double real_de;

  @Column(name = "peak_real_de")
  private double peak_real_de;

  @Column(name = "overall_rank")
  private Integer overall_rank;

  @Column(name = "rank_by_peak")
  private Integer rank_by_peak;

  @Column(name = "best_rank")
  private Integer best_rank;

  @Column(name = "num_days")
  private int num_days;

  @Override
  public MovieExpressionsQuarterly clone() {
    return new Cloner().deepClone(this);
  }

}
