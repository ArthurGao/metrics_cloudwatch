package com.parrotanalytics.api.data.repo.api.custom;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedBreakdown;

public interface BreakdownRepositoryCustom
{
    public List<GroupedBreakdown> showDemandBreakdownAverageChange(List<Date> lastPeriodDateRange,
            List<Date> thisPeriodDateRange, List<String> marketsList, Long shortID, PageRequest pageRequest);

}
