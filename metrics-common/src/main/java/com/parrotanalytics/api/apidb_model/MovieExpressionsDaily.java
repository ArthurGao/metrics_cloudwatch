package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import com.rits.cloning.Cloner;
import lombok.Data;

/**
 * 
 * @author minhvu
 *
 */
@Entity
@Table(name = TableConstants.MOVIE_EXPRESSIONS_DAILY, schema = TableConstants.METRICS_SCHEMA)
@IdClass(value = ExpressionsDailyPK.class)
@Data
public class MovieExpressionsDaily extends RDSBaseEntity
{
    private static final long serialVersionUID = 8180482549050907025L;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Id
    @Column(name = "country")
    private String country;

    @Id
    @Column(name = "short_id")
    private long short_id;

    @Column(name = "raw_de")
    private double raw_de;

    @Column(name = "real_de")
    private double real_de;

    @Column(name = "overall_rank")
    private Integer overall_rank;

    @Override
    public MovieExpressionsDaily clone()
    {
        return new Cloner().deepClone(this);
    }

}
