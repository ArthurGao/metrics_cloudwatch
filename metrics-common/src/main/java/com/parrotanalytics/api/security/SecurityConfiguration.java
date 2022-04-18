/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parrotanalytics.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.config.CorsFilter;
import com.parrotanalytics.api.data.repo.api.UserRepository;
import com.parrotanalytics.api.services.AuthenticationService;
import com.parrotanalytics.api.services.UserService;

/**
 * The Class SecurityConfiguration defines the Spring security configuration
 * options, including setting the data source repository and REST security
 * settings such as basic and token authentication.
 * 
 * @author Chris
 * @since v1
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Instantiates a new security configuration.
     */
    public SecurityConfiguration()
    {
        super(true);
    }

    public static BCryptPasswordEncoder getPasswordEncoder()
    {
        return passwordEncoder;
    }

    /**
     * This section defines the user accounts which can be used for
     * authentication as well as the roles each user has.
     *
     * @param auth
     *            the auth
     * @throws Exception
     *             the exception
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter#configure(org.springframework.security.
     * config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        // Allow all access to /auth for login
        // Also prevents client from caching

        // @formatter:off

        userService.setUserRepo(userRepo);

        httpSecurity.exceptionHandling().and().servletApi().and().headers().cacheControl().and().and().anonymous().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // Add the CORS filter
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)

                /*
                 * Custom Token based authentication based on the header
                 * previously given to the client
                 */
                .addFilterBefore(new StatelessAuthenticationFilter(new AuthenticationService(userService)),
                        UsernamePasswordAuthenticationFilter.class)

                // Requests to /authenticate are ignored in the filter!
                .authorizeRequests()//
                .antMatchers(Endpoint.HEALTH).permitAll() //
                .antMatchers(Endpoint.AUTH_STRING).permitAll()//
                .antMatchers(Endpoint.MOCK_ENPOINT + "/**").permitAll()//
                .antMatchers(Endpoint.USER_STRING + "/password/**").permitAll()//
                .anyRequest().authenticated();

        // @formatter:on
    }

}
