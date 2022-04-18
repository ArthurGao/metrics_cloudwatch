package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import java.io.Serializable;
import lombok.Data;

@Data
public class LiveAffinityIndexPK implements Serializable
{

    @Column(name = "country")
    private String country;

    @Column(name = "range_key")
    private String range_key;

    @Column(name = "short_id_from")
    private long short_id_from;

    @Column(name = "short_id_to")
    private long short_id_to;

}
