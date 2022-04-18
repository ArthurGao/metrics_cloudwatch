package com.parrotanalytics.api.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.User;

import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.api.exceptions.MissingTokenException;
import com.parrotanalytics.api.security.TokenHandler;
import com.parrotanalytics.apicore.commons.constants.CustomHTTPHeaders;
import com.parrotanalytics.apicore.config.MessageBundleService;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.constants.App;
import com.parrotanalytics.datasourceint.util.Tuple;

/**
 * This class retrieve, validate OR and sets the token headers from the HTTP
 * request.
 * 
 * @author Chris
 * @author Sanjeev Sharma
 * @author minhvu
 */
public class AuthenticationService
{
    protected static final Logger logger = LogManager.getLogger(AuthenticationService.class);

    /*
     * Static value for external API secret.
     */
    public static final String PROXY_SECRET_TOKEN = ConfigFactory.getConfig(App.TV360_API)
            .readProperty(APIConfig.PROXY_SECRET_TOKEN);

    /* I18n message service */
    @Autowired(required = true)
    protected MessageBundleService messageBundle;

    private UserService userService;

    private final TokenHandler tokenHandler;

    /**
     * Instantiates a new token authentication service.
     *
     * @param userService
     *            the user service
     */
    public AuthenticationService(UserService userService)
    {
        this.userService = userService;
        tokenHandler = new TokenHandler();
    }

    /**
     * Gets the user from the token within the http request.
     *
     * @param httpRequest
     *            the request
     * @return the authentication
     */
    public Tuple<InternalUser, String> loadUser(HttpServletRequest httpRequest)
    {

        String authToken = httpRequest.getHeader(CustomHTTPHeaders.AUTH_TOKEN.value());
        String refreshToken = null;

        User springUser = null;
        InternalUser appUser = null;
        /*
         * A : Internal API call
         */
        if (authToken != null)
        {
            try
            {
                springUser = tokenHandler.parseUserFromToken(authToken);

                appUser = userService.loadAppUserByEmail(springUser.getUsername());
                refreshToken = handleRefreshTokenIfRequired(authToken, springUser);

                logger.debug("[AuthenticationFilter] 'VALID' token for Call: [{}] , Token : {}",
                        httpRequest.getServletPath(), authToken);
            }
            catch (CredentialsExpiredException e)
            {
                logger.warn("[AuthenticationFilter] 'EXPIRED' token for Call: [{}] , Token : {}",
                        httpRequest.getServletPath(), authToken);

                throw e;
            }
            catch (Exception e)
            {
                logger.warn("[AuthenticationFilter] 'INVALID' token for Call: [{}] , Token : {}",
                        httpRequest.getServletPath(), authToken);

                throw new RuntimeException("Invalid authentication credentials");
            }

            return new Tuple<>(appUser, refreshToken);
        }
        else
        {
            String authSecret = httpRequest.getHeader(CustomHTTPHeaders.EXTERNAL_API_SECRET.value());

            if (authSecret != null)
            {
                if (isValidSecret(authSecret))
                {
                    String apiKey = httpRequest.getParameter("api_key");

                    if (apiKey == null)
                    {
                        apiKey = httpRequest.getHeader(CustomHTTPHeaders.EXTERNAL_API_KEY.value());
                    }

                    if (apiKey != null)
                    {
                        try
                        {
                            appUser = userService.loadExternalAPIUser(apiKey);
                            springUser = appUser.toSpringUser();

                            logger.debug(
                                    "[AuthenticationFilter] 'VALID' AUTH Header for Call: [{}] , SECRET: {}, API-KEY: {}",
                                    httpRequest.getServletPath(), authSecret, apiKey);
                        }
                        catch (Exception e)
                        {
                            logger.warn(
                                    "[AuthenticationFilter] 'INVALID_AUTH_HEADERS' AUTH Header for Call: [{}] , SECRET: {}, API-KEY: {}",
                                    httpRequest.getServletPath(), authSecret, apiKey);

                            logger.error("Error parsing api key", e);
                        }
                    }

                    return new Tuple<InternalUser, String>(appUser, null); // user

                }
                else
                {
                    logger.warn("[AuthenticationFilter] 'INVALID_AUTH_SECRET' for Call: [{}] , SECRET: {}",
                            httpRequest.getServletPath(), authSecret);
                }
            }

            logger.warn("[AuthenticationFilter] 'MISSING_AUTH_SECRET' for Call: [{}] , SECRET: {}",
                    httpRequest.getServletPath(), authSecret);

            throw new MissingTokenException("Missing authentication header.");
        }
    }

    private synchronized String handleRefreshTokenIfRequired(String accessToken, User user)
    {
        String refreshToken = null;

        if (tokenHandler.isRefreshTokenRequired(accessToken))
        {
            refreshToken = tokenHandler.createTokenForUser(user);
            InternalUser appUser = userService.loadAppUserByEmail(user.getUsername());

            tokenHandler.registerToken(accessToken, refreshToken);

            return refreshToken;
        }
        return null;
    }

    private boolean isValidSecret(String authSecret)
    {
        if (PROXY_SECRET_TOKEN.equals(authSecret))
        {
            return true;
        }
        return false;
    }
}