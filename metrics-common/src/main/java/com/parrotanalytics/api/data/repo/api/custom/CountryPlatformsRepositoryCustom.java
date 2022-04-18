package com.parrotanalytics.api.data.repo.api.custom;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedPlatformInfo;
import com.parrotanalytics.api.commons.constants.APICacheConstants;

@Repository
public interface CountryPlatformsRepositoryCustom
{
    @Cacheable(cacheNames = APICacheConstants.CACHE_PLATFORMS, key = "'pf:' + #clientReady")
    public List<ExtendedPlatformInfo> fetchPlatforms(int clientReady, List<String> catalogStates);

}
