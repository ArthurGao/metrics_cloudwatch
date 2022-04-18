package com.parrotanalytics.api.data.repo.api.custom;

import java.util.List;

public interface LabelRepositoryCustom
{
    List<Long> filterShowsByLabels(List<String> labels, List<Long> filterShortIDs);
}
