package com.parrotanalytics.metrics.service.config;

import com.parrotanalytics.commons.config.IConfig;

public enum APIConfig implements IConfig
{
    // @formatter:off

    @Deprecated
    JWT_SECRET("jwt_secret"),

    D360_JWT_SECRET("d360_jwt_secret"),

    TOKEN_EXPIRY_MINS("token_expiry_mins"),

    GO_LIVE_TIME("go_live_utc_time"),

    PROXY_SECRET_TOKEN("proxy_secret_token"),

    CONNECTION_TIMEOUT_SECS("connection_timeout_secs"),

    API_CONNECTION_POOL_SIZE("api_connection_pool_size"),

    EXPECTED_CATALOG_SIZE("expected_catalog_size"),

    METADATA_REFRESH_FREQUENCY_MINS("metadata_refresh_frequency_mins"),

    DS_CONNECTION_POOL_SIZE("ds_connection_pool_size"),

    CORS_PREFLIGHT_MAX_AGE_IN_SECS("cors_preflight_max_age_secs"),

    CORS_ALLOWED_DOMAINS("cors.allowed.domains"),

    CORS_DOMAINS_DELIMITER("cors.domains.delimiter"),

    SUUPORTED_RESOURCE_URLS("supported.resource_domains"),

    ES_HOST("elasticsearch.host"),

    CATALOGAPI_PROTOCOL("catalogapi.protocol"),

    CATALOGAPI_HOST("catalogapi.host"),

    HISTORICAL_MODULE_START_DATE("historical_module_start_date"),

    CACHE_SIZE_USERS("cache_size_users"),

    CACHE_SIZE_MARKETS("cache_size_markets"),

    CACHE_SIZE_SUBSCRIPTIONS("cache_size_subscriptions"),

    CACHE_SIZE_PORTFOLIOS("cache_size_portfolios"),

    CACHE_SIZE_RATINGS("cache_size_ratings"),

    DEC_MULTIPLIER("dec_multiplier"),

    RESOURCE_URL("parrot_resource_url"),

    REDIS_CACHE_HOST("redis.cache_host"),

    REDIS_CACHE_PORT("redis.cache_port"),

    VIDEOS_URL("parrot_video_url"),

    CS_CALL_URL("parrot_cs_call"),

    PORTAL_URL("portal_url"),

    DISABLED_TOPSHOWS("disabled_topshows"),

    DISABLED_TOPSHOWS_DATAAPI("disabled_topshows_dataapi"),

    FILTERS_CONFIG("filters_config"),

    AWS_ACCOUNT_ID("aws_account_id"),

    WORKFLOWS("workflows"),

    HUBSPOT_API_KEY("hubspot.api_key"),

    ASKNICELY_API_KEY("asknicely.api_key"),

    STRIPE_TEST_KEY("stripe.test.api_key"),

    STRIPE_LIVE_KEY("stripe.live.api_key"),

    STRIPE_TEST_PUBLISHABLE_KEY("stripe.test.publishable_key"),

    STRIPE_LIVE_PUBLISHABLE_KEY("stripe.live.publishable_key"),

    DEV_HOST_USE_REDSHIFT("dev_host.use_redshift"),

    UAT_HOST_USE_REDSHIFT("uat_host.use_redshift"),

    PROD_HOST_USE_REDSHIFT("prod_host.use_redshift"),

    NEGLIGIBLE_DEMAND_BREAKDOWN_THRESHOLD("negligible_demand_breakdown_threshold");

    // @formatter:on

    private String key;

    private APIConfig(final String key)
    {
        this.key = key;
    }

    @Override
    public String key()
    {
        return key;
    }
}
