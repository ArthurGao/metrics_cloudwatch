package com.parrotanalytics.api.data.repo.api.custom;

import java.util.List;

import com.parrotanalytics.api.apidb_model.LiveAffinityIndex;
import com.parrotanalytics.api.apidb_model.LiveDatasourceMetrics;
import com.parrotanalytics.api.commons.constants.Entity;

public interface ContextMetricRepositoryCustom
{
    public List<LiveAffinityIndex> getLiveAffinityIndex(List<Long> shortIDFroms, Entity entity);

    public List<LiveDatasourceMetrics> getLiveDataSourceMetrics(List<Long> shortIDs, Entity entity);

}
