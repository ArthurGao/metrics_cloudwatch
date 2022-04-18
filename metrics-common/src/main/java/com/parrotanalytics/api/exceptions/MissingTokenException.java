package com.parrotanalytics.api.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token is not found in REST API call.
 * 
 * @author Sanjeev Sharma
 *
 */
public class MissingTokenException extends AuthenticationException
{
    /**
     * 
     */
    private static final long serialVersionUID = 6995282314260137594L;

    public MissingTokenException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs a <code>MissingTokenException</code> with the specified
     * message and root cause.
     *
     * @param msg
     *            the detail message
     * @param t
     *            root cause
     */
    public MissingTokenException(String msg, Throwable t)
    {
        super(msg, t);
    }
}
