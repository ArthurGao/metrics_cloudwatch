package com.parrotanalytics.metrics.service.config.data;

import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.apicore.utils.APIHelper;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.CloudConfiguration;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;
import com.parrotanalytics.datasourceint.config.DBConfig;
import com.parrotanalytics.datasourceint.constants.Environment;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Class ApiPersistenceConfig.
 * 
 * @author chris
 * @author Minh Vu
 * @since v1
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.parrotanalytics.api.data.repo.api", entityManagerFactoryRef = "apiEntityManagerFactory", transactionManagerRef = "apiTransactionManager")
@EnableTransactionManagement
public class ApiPersistenceConfig {
    private static final Logger logger = LogManager.getLogger(ApiPersistenceConfig.class);

    private static final String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private static ConfigurationService dbLibraryConfig = ConfigFactory.getConfig(App.DBLIBRARY);

    private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

    /**
     * Eclipselink persistence file configuration
     */

    /**
     * 
     */
    public final static String API_PERSISTENCE_UNIT_NAME = "PARROT_API";

    /**
     * 
     */
    public static final String API_PERSISTENCE_XML_LOCATION = "classpath*:META-INF/dataapi_persistence.xml";

    /**
     * Dynamic datasource config from {@link CloudConfiguration}
     */
    private static final String DB_NAME = "metrics";

    /*
     * PRODUCTION API Configuration
     */
    private static final String PROD_API_HOST = dbLibraryConfig.readProperty(DBConfig.PROD_DATAAPI_HOST);

    private static final String PROD_API_PORT = dbLibraryConfig.readProperty(DBConfig.PROD_DATAAPI_PORT);

    private static final String PROD_DB_USER = dbLibraryConfig.readProperty(DBConfig.PROD_DATAAPI_DB_USER);

    private static final String PROD_DB_PASSWORD = dbLibraryConfig.readProperty(DBConfig.PROD_DATAAPI_DB_PASSWORD);

    /*
     * UAT API Configuration
     */
    private static final String UAT_API_HOST = dbLibraryConfig.readProperty(DBConfig.UAT_API_HOST);

    private static final String UAT_API_PORT = dbLibraryConfig.readProperty(DBConfig.UAT_API_PORT);

    private static final String UAT_DB_USER = dbLibraryConfig.readProperty(DBConfig.UAT_DB_USER);

    private static final String UAT_DB_PASSWORD = dbLibraryConfig.readProperty(DBConfig.UAT_DB_PASSWORD);

    /*
     * DEV API Configuration
     */
    private static final String DEV_API_HOST = dbLibraryConfig.readProperty(DBConfig.DEV_DATAAPI_HOST);

    private static final String DEV_API_PORT = dbLibraryConfig.readProperty(DBConfig.DEV_DATAAPI_PORT);

    private static final String DEV_DB_USER = dbLibraryConfig.readProperty(DBConfig.DEV_DATAAPI_DB_USER);

    private static final String DEV_DB_PASSWORD = dbLibraryConfig.readProperty(DBConfig.DEV_DATAAPI_DB_PASSWORD);

    /*
     * connection pooling config
     */
    private static final int CONNECTION_POOL_SIZE = Integer
            .valueOf(apiConfig.readProperty(APIConfig.API_CONNECTION_POOL_SIZE));

    private static final int CONNECTION_TIMEOUT = Integer
            .valueOf(apiConfig.readProperty(APIConfig.CONNECTION_TIMEOUT_SECS));

    /**
     * Api data source.
     *
     * @return the data source
     */
    @Primary
    @Bean(name = "apids")
    public DataSource apiDataSource() {
        try {
            Class.forName(MYSQL_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        HikariConfig config = new HikariConfig();

        /**
         * Step A : Connection Configuration
         */
        config.setDriverClassName(MYSQL_DRIVER_CLASS);
        String jdbcUrl = null;
        String username = null;
        String password = null;

        if (APIHelper.isRunningEnvironment(Environment.PRODUCTION)) {
            logger.info("######## Connected to PRODUCTION Environment");
            jdbcUrl = "jdbc:mysql://" + PROD_API_HOST + ":" + PROD_API_PORT + "/" + DB_NAME;
            username = PROD_DB_USER;
            password = PROD_DB_PASSWORD;
        } else if (APIHelper.isRunningEnvironment(Environment.UAT)) {
            logger.info("######## Connected to UAT Environment");

            jdbcUrl = "jdbc:mysql://" + UAT_API_HOST + ":" + UAT_API_PORT + "/" + DB_NAME;
            username = UAT_DB_USER;
            password = UAT_DB_PASSWORD;
        } else {
            logger.info("######## Connected to DEV Environment");
            jdbcUrl = "jdbc:mysql://" + DEV_API_HOST + ":" + DEV_API_PORT + "/" + DB_NAME;
            username = DEV_DB_USER;
            password = DEV_DB_PASSWORD;
        }

        config.setJdbcUrl(jdbcUrl + "?zeroDateTimeBehavior=convertToNull");
        config.setUsername(username);
        config.setPassword(password);

        /**
         * Database Connection Configuration
         */
        config.setAutoCommit(true);
        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(CONNECTION_TIMEOUT));

        config.setIdleTimeout(9000000);
        // prevent zombie connection in dev mode
        config.setMaxLifetime(ConfigurationService.isDevMode() ? 60000 : 72000000);
        config.setValidationTimeout(TimeUnit.SECONDS.toMillis(10));
        config.setConnectionInitSql("SELECT 1");

        config.addDataSourceProperty("prepStmtCacheSize", 300);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("cachePrepStmts", true);

        /**
         * Health & Metrics
         */
        config.addHealthCheckProperty("connectivityCheckTimeoutMs", "1000");
        config.addHealthCheckProperty("expected99thPercentileMs", "10");

        /**
         * Step B : Setting up Connection Pool
         */
        HikariDataSource dataSource = new HikariDataSource(config);

        // dataSource.setPoolName("API-CP");
        dataSource.setMaximumPoolSize(CONNECTION_POOL_SIZE);

        return dataSource;
    }

    /**
     * Entity manager factory api.
     *
     * @return the local container entity manager factory bean
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean apiEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        EclipseLinkJpaVendorAdapter vendor = new EclipseLinkJpaVendorAdapter();
        vendor.setDatabase(Database.MYSQL);
        emfb.setPackagesToScan("com.parrotanalytics.api.apidb_model");
        emfb.setDataSource(apiDataSource());
        emfb.setJpaVendorAdapter(vendor);
        emfb.setJpaDialect(new EclipseLinkJpaDialect());
        emfb.setPersistenceXmlLocation(API_PERSISTENCE_XML_LOCATION);
        emfb.setPersistenceUnitName(API_PERSISTENCE_UNIT_NAME);

        if (APIHelper.isRunningEnvironment(Environment.PRODUCTION)) {
            logger.info("loading api data from [prod apidb]");
        } else {
            logger.info("loading api data from [dev apidb]");
        }

        Map<String, String> overWrittenProps = new HashMap<>();
        overWrittenProps.put("eclipselink.weaving", "false");
        emfb.setJpaPropertyMap(overWrittenProps);

        return emfb;
    }

    /**
     * Transaction manager api.
     *
     * @return the platform transaction manager
     */
    @Bean
    @Primary
    public PlatformTransactionManager apiTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(apiEntityManagerFactory().getObject());
        transactionManager.setDataSource(apiDataSource());
        return transactionManager;
    }

}
