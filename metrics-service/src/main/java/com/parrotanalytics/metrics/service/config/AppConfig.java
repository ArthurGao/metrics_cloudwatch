package com.parrotanalytics.metrics.service.config;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.data.repo.api.cache.CacheKeyGenerator;
import com.parrotanalytics.apicore.commons.constants.Service;
import com.parrotanalytics.apicore.utils.APIHelper;
import com.parrotanalytics.apicore.utils.ServiceCaller;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;
import com.parrotanalytics.datasourceint.constants.Environment;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * The AppConfig class holds the app config, especially dictating how JSON
 * should be parsed and formatted, and to enable caching.
 *
 * @author Chris
 * @author Sanjeev Sharma
 * @since v1
 */
@Configuration
@EnableCaching
@EnableWebMvc
@ComponentScan(basePackages =
{
        "com.parrotanalytics.apicore.controllers", "com.parrotanalytics.apicore.config",
         "com.parrotanalytics.api.data.repo.api", "com.parrotanalytics.api.services", "com.parrotanalytics.api.util",
          "com.parrotanalytics.api.request.util",
         "com.parrotanalytics.metrics.service.services"
})
@AutoConfigurationPackage
public class AppConfig extends CachingConfigurerSupport implements CachingConfigurer
{

    private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

    private static final Logger logger = LogManager.getLogger(AppConfig.class);

    /* http protocol for es host */
    private static final String PROTOCOL = "https";

    /* aws region name for elasticsearch cluster */
    public static final String REGION = "us-east-1";

    /* service name for elasticsearch cluster */
    public static final String SERVICE = "es";

    public static final int ES_WINDOW_LIMIT = 10000;

    public static final String SCROLL_TIMEOUT = "1m";

    private static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    /**
     * Jackson2 converter.
     *
     * @return the mapping jackson2 http message converter
     */
    public MappingJackson2HttpMessageConverter jackson2Converter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());

        return converter;
    }

    /**
     * Object mapper.
     *
     * @return the object mapper
     */
    public static ObjectMapper objectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();

        /* Pretty print: */
        // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        /* Don't serialize nulls: */
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        /* use joda-time for date conversion */
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        /* register the jodatime module */
        objectMapper.registerModule(new JodaModule());

        return objectMapper;
    }

    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/APIMessageSource");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleResolver sessionLocaleResolver()
    {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en_US"));

        return localeResolver;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        RedisStandaloneConfiguration redisStandaloneConfiguration = null;

        redisStandaloneConfiguration = new RedisStandaloneConfiguration(cacheClusterHost(), cacheClusterPort());

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Primary
    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory)
    {
        String appPrefix = "";

        if (APIHelper.isRunningEnvironment(Environment.PRODUCTION))
        {
            appPrefix = App.TV360_API.appName() + "-p::";
        }
        else if (APIHelper.isRunningEnvironment(Environment.UAT))
        {
            appPrefix = App.TV360_API.appName() + "-d::";
        }
        else
        {
            appPrefix = App.TV360_API.appName() + "-d::";
        }

        if (ConfigurationService.isDevMode())
        {
            appPrefix = App.TV360_API.appName() + "-local::";
        }

        logger.info("Configuraing Redis Cache for prefix :[{}]", appPrefix);

        Map<String, RedisCacheConfiguration> cacheConfigurationMap = configureCacheConfig(appPrefix);

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .withInitialCacheConfigurations(cacheConfigurationMap).transactionAware().build();

        return cacheManager;
    }

    private Map<String, RedisCacheConfiguration> configureCacheConfig(String appPrefix)
    {

        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();

        cacheConfigurationMap.put(APICacheConstants.CACHE_EXPRESSIONS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_EXPRESSIONS + "::")
                        .entryTtl(Duration.ofDays(7)).disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_ACCOUNTS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_ACCOUNTS + "::")
                        .entryTtl(Duration.ofDays(7)).disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_USERS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_USERS + "::").entryTtl(Duration.ofHours(12))
                        .disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_MARKETS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_MARKETS + "-").entryTtl(Duration.ofDays(7))
                        .disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_PORTFOLIOS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_PORTFOLIOS + "-")
                        .entryTtl(Duration.ofDays(7)).disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_SUBSCRIPTIONS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_SUBSCRIPTIONS + "-")
                        .entryTtl(Duration.ofDays(1)).disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_ACCOUNT_MARKETS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_ACCOUNT_MARKETS + "-")
                        .entryTtl(Duration.ofDays(7)).disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_ACCOUNT_TITLES,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_ACCOUNT_TITLES + "-")
                        .entryTtl(Duration.ofDays(7)).disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_NEWS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_NEWS + "-").entryTtl(Duration.ofDays(1))
                        .disableCachingNullValues());

        cacheConfigurationMap.put(APICacheConstants.CACHE_PLATFORMS,
                RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(appPrefix + APICacheConstants.CACHE_PLATFORMS + "-")
                        .entryTtl(Duration.ofDays(7)).disableCachingNullValues());

        return cacheConfigurationMap;
    }

    @Override
    @Bean("cacheKeyGenerator")
    public KeyGenerator keyGenerator()
    {
        return new CacheKeyGenerator();
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler()
    {
        return new CustomCacheErrorHandler();
    }

    public static String cacheClusterHost()
    {
        if (System.getProperty("redis.cache-host") != null)
            return System.getProperty("redis.cache-host");

        if (ConfigurationService.isDevMode())
        {
            return "localhost";
        }

        return apiConfig.readProperty(APIConfig.REDIS_CACHE_HOST);
    }


    public static int cacheClusterPort()
    {
        return apiConfig.readInteger(APIConfig.REDIS_CACHE_PORT);

    }

    private JestClientFactory jestFactory()
    {
        return new JestClientFactory()
        {
            @Override
            protected HttpClientBuilder configureHttpClient(HttpClientBuilder builder)
            {
                AWS4Signer signer = new AWS4Signer();
                signer.setServiceName(SERVICE);
                signer.setRegionName(REGION);
                HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(SERVICE, signer,
                        credentialsProvider);
                builder.addInterceptorLast(interceptor);
                return builder;
            }
        };
    }

    @Bean
    public JestClient jestClient()
    {
        HttpClientConfig clientConfig = new HttpClientConfig.Builder(
                PROTOCOL + "://" + ServiceCaller.serviceURL(Service.ES_PA_APPS.value())).multiThreaded(true)
                        .maxTotalConnection(10).connTimeout(300000).build();
        JestClientFactory factory = jestFactory();
        factory.setHttpClientConfig(clientConfig);
        return factory.getObject();

    }

    @Bean
    public CacheManager mapCacheManager() {
        return new ConcurrentMapCacheManager(APICacheConstants.CACHE_ACCOUNT_TITLES);
    }
}
