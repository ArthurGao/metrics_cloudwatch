package com.parrotanalytics.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parrotanalytics.api.apidb_model.TV360Region;
import com.parrotanalytics.api.data.repo.api.RegionRepository;

@Service
public class MarketRegionService
{
    @Autowired
    protected RegionRepository regionRepo;

    public List<String> mergeToMarkets(List<String> markets, List<TV360Region> regions)
    {
        List<String> mergedList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(markets))
        {
            mergedList.addAll(markets);
        }
        if (CollectionUtils.isNotEmpty(regions))
        {
            mergedList.addAll(
                    regions.stream().flatMap(region -> region.getCountryList().stream()).collect(Collectors.toList()));
        }
        return mergedList;
    }

}
