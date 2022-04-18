package com.parrotanalytics.api.data.repo.api.cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.interceptor.SimpleKeyGenerator;

/**
 * Cache Key Generator for Spring Cache Abstraction (We are using RedisCache
 * Implementation)
 * 
 * @author Minh Vu
 *
 */
public class CacheKeyGenerator extends SimpleKeyGenerator implements org.springframework.cache.interceptor.KeyGenerator
{

    @Override
    public Object generate(final Object target, final Method method, final Object... params)
    {

        try
        {
            List<Object> filteredParams = Arrays.asList(params).stream().collect(Collectors.toList());
            Object generateKey = generateKey(filteredParams);
            return method.getName() + ":" + generateKey;
        }
        catch (Exception e)
        {
            throw new RuntimeException("null key got generated for method : " + method);
        }

    }
}