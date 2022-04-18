package com.parrotanalytics.api.services;

import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void evictSingleCacheValue(String cacheName, String cacheKey) {
        try {
            cacheManager.getCache(cacheName).evict(cacheKey);
        } catch (Exception ex) {
            log.warn("Failed to evict cache for cache_name: {}, cache_key: {}. \n " +
                "Error: {}", cacheName, cacheKey, ex);
        }
    }

    public void invalidateUsersCacheByEmail(String emailAddress) {
        evictSingleCacheValue(APICacheConstants.CACHE_USERS, "emailid:" + emailAddress);
    }

    public void invalidateUsersCacheByUserId(Integer idUser) {
        evictSingleCacheValue(APICacheConstants.CACHE_USERS, "userid:" + idUser);
    }

    public void invalidateUser(InternalUser internalUser) {
        invalidateUsersCacheByEmail(internalUser.getEmailAddress());
        invalidateUsersCacheByUserId(internalUser.getIdUser());
    }

    public void invalidateAllAccounts() {
        boolean isCacheFound = false;
        Cache foundCache = null;
        try {
            foundCache = cacheManager.getCache(APICacheConstants.CACHE_ACCOUNTS);
            isCacheFound = foundCache != null;
        } catch (Exception ex) {
            log.warn("Catch not found for cache accounts.");
        }

        if (isCacheFound) {
            try {
                foundCache.clear();
            } catch (Exception ex) {
                log.warn("Failed to clear cache accounts,\n error: {}", ex);
            }

        }
    }

    public void invalidateAllAccountMarkets() {
        boolean isCacheFound = false;
        Cache foundCache = null;
        try {
            foundCache = cacheManager.getCache(APICacheConstants.CACHE_ACCOUNT_MARKETS);
            isCacheFound = foundCache != null;
        } catch (Exception ex) {
            log.warn("Catch not found for cache accounts markets.");
        }

        if (isCacheFound) {
            try {
                foundCache.clear();
            } catch (Exception ex) {
                log.warn("Failed to clear cache account markets,\n error: {}", ex);
            }
        }
    }

}
