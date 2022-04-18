package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.List;

import lombok.Data;

@Data
public class IntInputFilter
{

    private Integer eq;
    private Integer ne;
    private Integer gt;
    private Integer ge;
    private Integer lt;
    private Integer le;
    private List<Integer> between;
}
