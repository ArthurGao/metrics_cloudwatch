package com.parrotanalytics.metrics.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * @author Minh Vu
 * There are several times the redis server could not be up during the operation of Data API
 * This strategy class helps the application work seamlessly when the cache is down. It is up to
 * us to later implementing logging the exceptions. For now, it simply ignores the cache error and call the function directly
 * When the cache server goes online, the cache mechanism works as normal
 */
@Slf4j
public class CustomCacheErrorHandler implements CacheErrorHandler
{

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object key)
    {
        //Do something on Get Error in cache
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value)
    {
        //Do something on Put error in cache
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)
    {
        //Do something on error while Evict cache
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache)
    {
        //Do something on error while clearing cache
        log.error(e.getMessage(), e);
    }
}
