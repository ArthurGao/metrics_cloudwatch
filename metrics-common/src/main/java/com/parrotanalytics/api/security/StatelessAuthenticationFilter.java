package com.parrotanalytics.api.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.services.AuthenticationService;
import com.parrotanalytics.apicore.commons.constants.APIStatus;
import com.parrotanalytics.apicore.commons.constants.CustomHTTPHeaders;
import com.parrotanalytics.apicore.model.response.APIResponse;
import com.parrotanalytics.datasourceint.util.Tuple;

/**
 * The Class StatelessAuthenticationFilter is the pass-through of each request
 * that checks the validity of the token and provides a new one to return.
 * 
 * @author Chris
 * @since v1
 */
public class StatelessAuthenticationFilter extends GenericFilterBean
{
    private static final Logger logger = LogManager.getLogger(HttpRequestAuditFilter.class);

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final AuthenticationService authenticationService;

    /**
     * Instantiates a new stateless authentication filter.
     *
     * @param authenticationService
     *            the authentication service
     */
    public StatelessAuthenticationFilter(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException
    {
        try
        {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            InternalUser authenticatedUser = null;
            String refreshToken = null;

            if (httpRequest.getServletPath().startsWith(Endpoint.HEALTH)
                    || httpRequest.getServletPath().equals(Endpoint.AUTH_STRING)
                    || httpRequest.getServletPath().startsWith(Endpoint.MOCK_ENPOINT)
                    || httpRequest.getServletPath().startsWith(Endpoint.USER_STRING + "/password"))
            {
                logger.debug("Ignoring tokens for request to {} as it is for authentication",
                        httpRequest.getServletPath());
            }
            else
            {
                Tuple<InternalUser, String> userDetails = authenticationService.loadUser(httpRequest);

                authenticatedUser = userDetails.x;
                refreshToken = userDetails.y;
            }

            UserAuthentication authentication = null;

            /*
             * Checks if the user is authenticated
             */
            if (authenticatedUser != null)
            {
                logger.trace("User {} is making an api call to {}. Processed token security sucessfully.",
                        authenticatedUser.getIdUser(), httpRequest.getServletPath());

                authentication = new UserAuthentication(authenticatedUser);

                if (!StringUtils.isEmpty(refreshToken))
                {
                    // Inject refresh token in header
                    if (response instanceof HttpServletResponse)
                    {
                        ((HttpServletResponse) response).addHeader(CustomHTTPHeaders.REFRESH_TOKEN.value(),
                                refreshToken);
                    }
                }
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            SecurityContextHolder.getContext().setAuthentication(null);

        }
        catch (Exception e)
        {
            logger.info("AutheticationFilter Failed : {}", e.getLocalizedMessage());

            e.printStackTrace();

            APIResponse validationResponse = new APIResponse(true);

            validationResponse.getError().setCode(APIStatus.AUTHENTICATION_FAILED);
            validationResponse.getError().setMessage(e.getLocalizedMessage());

            HttpServletResponse httpResponse = (HttpServletResponse) response;

            httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            httpResponse.getWriter().println(prettifyJson(gson.toJson(validationResponse)));
            httpResponse.flushBuffer();

        }
    }

    /**
     * 
     * @param json
     * @return
     */
    public String prettifyJson(String responseJSON)
    {
        String prettyJSON = responseJSON;
        ObjectMapper objectMapper = objectMapper();
        Object json;

        try
        {
            json = objectMapper.readValue(responseJSON, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        }
        catch (Exception e)
        {
        }
        return prettyJSON;
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
}