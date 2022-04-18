package com.parrotanalytics.metrics.service.config;

import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class CorsFilter implements Filter
{

    private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

    private static final String DOMAINS_DELIMITER = apiConfig.readProperty(APIConfig.CORS_DOMAINS_DELIMITER);

    /**
     * Default Allowed Domain for API access
     */
    private static final String DEFAULT_ALLOWED_DOMAIN = "https://portal.parrotanalytics.com";

    private static final Logger logger = LogManager.getLogger(CorsFilter.class);

    /**
     * 
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        String httpMethod = ((HttpServletRequest) req).getMethod();

        HttpServletResponse response = (HttpServletResponse) res;

        String origin = ((HttpServletRequest) req).getHeader("Origin");
        logger.debug("Received HTTP request from origin: {}", origin);

        Set<String> allowedDomains = allowedCorsDomains();

        if (allowedDomains.contains(origin))
        {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        else
        {
            response.setHeader("Access-Control-Allow-Origin", DEFAULT_ALLOWED_DOMAIN);
        }

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        response.setHeader("Access-Control-Max-Age", apiConfig.readProperty(APIConfig.CORS_PREFLIGHT_MAX_AGE_IN_SECS));

        response.setHeader("Access-Control-Allow-Headers",
                "accept, content-type,cache-control, Authorization, X-AUTH-TOKEN, X-FIRST-LOGIN, NOTIFICATION-FLAG");

        response.setHeader("Access-Control-Expose-Headers",
                "Authorization, X-AUTH-TOKEN, X-REFRESH-TOKEN, X-FIRST-LOGIN, NOTIFICATION-FLAG");

        if (HttpMethod.OPTIONS.equals(HttpMethod.valueOf(httpMethod)))
        {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
        {
            chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig filterConfig)
    {
    }

    public void destroy()
    {
    }

    public Set<String> allowedCorsDomains()
    {
        String[] allowedDomains = apiConfig.readProperty(APIConfig.CORS_ALLOWED_DOMAINS).split(DOMAINS_DELIMITER);

        return new HashSet<String>(Arrays.asList(allowedDomains));
    }
}
