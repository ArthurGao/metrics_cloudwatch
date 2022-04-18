package com.parrotanalytics.api.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class AdditionalClaims.
 *
 * @author Sanjeev Sharma
 */
public class AdditionalClaims
{
    private final static String ACCESS_FROM = "accessfrom";

    private final static String MODULES = "modules";

    private final Map<String, Object> claims = new HashMap<>();

    /**
     * Set the accessible modules.
     *
     * @param paramValue
     *            the param value
     * @return the additional claims
     */
    public AdditionalClaims withModules(Object paramValue)
    {
        claims.put(MODULES, paramValue);
        return this;
    }

    /**
     * starts the data access start.
     *
     * @param paramValue
     *            the param value
     * @return the additional claims
     */
    public AdditionalClaims withDataAccessStart(Object paramValue)
    {
        claims.put(ACCESS_FROM, paramValue);
        return this;
    }

    /**
     * Gets the claims.
     *
     * @return the claims
     */
    public Map<String, Object> getClaims()
    {
        return Collections.unmodifiableMap(claims);
    }
}
