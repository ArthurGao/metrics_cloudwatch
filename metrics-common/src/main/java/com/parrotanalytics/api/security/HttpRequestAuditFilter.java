package com.parrotanalytics.api.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

public class HttpRequestAuditFilter implements Filter
{

    private static final Logger logger = LogManager.getLogger(HttpRequestAuditFilter.class);

    private static int MAX_PAYLOAD_LENGTH = 10000;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        if ((request instanceof HttpServletRequest) && !(request instanceof ContentCachingRequestWrapper))
        {
            request = new ContentCachingRequestWrapper((HttpServletRequest) request);
        }
        try
        {
            chain.doFilter(request, response);
        }
        finally
        {
            if (request instanceof HttpServletRequest)
            {
                performRequestAudit((HttpServletRequest) request);
            }
        }
    }

    public void performRequestAudit(HttpServletRequest httpRequest)
    {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(httpRequest,
                ContentCachingRequestWrapper.class);
        String payload = "";
        if (wrapper != null)
        {
            byte[] requestBuffer = wrapper.getContentAsByteArray();
            if (requestBuffer.length > 0)
            {
                int length = Math.min(requestBuffer.length, MAX_PAYLOAD_LENGTH);
                try
                {
                    payload = new String(requestBuffer, 0, length, wrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException unex)
                {
                    payload = "[Unsupported-Encoding]";
                }
            }
        }
        HttpHeaders headers = new ServletServerHttpRequest(httpRequest).getHeaders();
        logger.trace("{}|{}", payload, headers);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub

    }

}
