package com.parrotanalytics.api.data.repo.api.custom.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.parrotanalytics.api.apidb_model.Label;
import com.parrotanalytics.api.data.repo.api.LabelRepository;
import com.parrotanalytics.api.data.repo.api.custom.LabelRepositoryCustom;

public class LabelRepositoryImpl implements LabelRepositoryCustom
{

    @Autowired
    private LabelRepository labelRepo;

    @Override
    public List<Long> filterShowsByLabels(List<String> labels, List<Long> filterShortIDs)
    {
        if (!CollectionUtils.isEmpty(labels))
        {
            List<Long> labelShortIDs = labelRepo.findTitlesWithLabels(labels).stream().map(Label::getShortId).distinct()
                    .collect(Collectors.toList());
            filterShortIDs = filterShortIDs.stream().filter(labelShortIDs::contains).collect(Collectors.toList());
        }

        return filterShortIDs;
    }

}
