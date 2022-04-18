package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import lombok.Data;

@Data
@Entity
@Table(name = TableConstants.LIVE_AFFINITY_INDEX, schema = TableConstants.METRICS_SCHEMA)
@IdClass(value = LiveAffinityIndexPK.class)
public class LiveAffinityIndex extends RDSBaseEntity
{

    private static final long serialVersionUID = -6763738973074281475L;

    @Id
    @Column(name = "country")
    private String country;

    @Id
    @Column(name = "range_key")
    private String range_key;

    @Id
    @Column(name = "short_id_from")
    private long short_id_from;

    @Id
    @Column(name = "short_id_to")
    private long short_id_to;

    @Column(name = "affinityindex")
    private double affinityindex;

    @Column(name = "rank")
    private int rank;


}
