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
@IdClass(value = ExpressionsYearlyPK.class)
@Table(name = TableConstants.MOVIE_EXPRESSIONS_COMPUTED_YEARLY, schema = TableConstants.METRICS_SCHEMA)
public class MovieExpressionsYearly extends RDSBaseEntity {

  private static final long serialVersionUID = -7053798042525427155L;

  @Column(name = "best_rank")
  private Integer best_rank;

  @Id
  @Column(name = "country")
  private String country;

  @Id
  @Temporal(TemporalType.DATE)
  @Column(name = "date")
  private Date date;

  @Column(name = "num_days")
  private int num_days;

  @Column(name = "overall_rank")
  private Integer overall_rank;

  @Column(name = "peak_raw_de")
  private double peak_raw_de;

  @Column(name = "peak_real_de")
  private double peak_real_de;

  @Id
  @Column(name = "range_key")
  private String range_key;

  @Column(name = "rank_by_peak")
  private Integer rank_by_peak;

  @Column(name = "raw_de")
  private double raw_de;

  @Column(name = "real_de")
  private double real_de;

  @Id
  @Column(name = "short_id")
  private long short_id;

  @Override
  public MovieExpressionsYearly clone() {
    return new Cloner().deepClone(this);
  }

}
