package com.parrotanalytics.api.security;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.jsonwebtoken.Claims;

/**
 * The Class UserClaimsJsonMap holds the user claims for serde of a JWT token.
 * 
 * @author Chris
 * @author minhvu
 */
public class UserClaimsJsonMap
{
    @JsonProperty(value = Claims.EXPIRATION)
    private long expirationTime;

    @JsonProperty(value = Claims.ISSUED_AT)
    private long issuedAt;

    @JsonProperty(value = Claims.ISSUER)
    private String issuer;

    @JsonProperty(value = Claims.ID)
    private String tokenUuid;

    @JsonProperty(value = "pur")
    @JsonDeserialize(contentAs = Role.class)
    private Collection<GrantedAuthority> roles;

    @JsonProperty(value = "pun")
    private String username;

    /**
     * Instantiates a new user claims json map.
     */
    protected UserClaimsJsonMap()
    {
        // Create, for object mapper use only
    }

    /**
     * Instantiates a new user claims json map.
     *
     * @param user
     *            the user
     * @param tokenUuid
     *            the token uuid
     * @param issuedAt
     *            the issued at
     * @param expirationTime
     *            the expiration time
     * @param issuer
     *            the issuer
     */
    public UserClaimsJsonMap(User user, String tokenUuid, long issuedAt, long expirationTime, String issuer)
    {
        this.issuer = issuer;
        this.tokenUuid = tokenUuid;
        this.issuedAt = issuedAt;
        this.expirationTime = expirationTime;
        this.roles = user.getAuthorities();
        this.username = user.getUsername();
    }

    /**
     * Gets the expiration time.
     *
     * @return the expiration time
     */
    public long getExpirationTime()
    {
        return expirationTime;
    }

    /**
     * Gets the issued at.
     *
     * @return the issued at
     */
    public long getIssuedAt()
    {
        return issuedAt;
    }

    /**
     * Gets the issuer.
     *
     * @return the issuer
     */
    public String getIssuer()
    {
        return issuer;
    }

    /**
     * Gets the token uuid.
     *
     * @return the token uuid
     */
    public String getTokenUuid()
    {
        return tokenUuid;
    }

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    public Collection<GrantedAuthority> getRoles()
    {
        return roles;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * To basic user.
     *
     * @return the user
     */
    @JsonIgnore
    public User toBasicUser()
    {
        boolean credsNonExpired = areCredsValidNonExpired();
        User user = new User(username, "this-is-random-text-123", true, true, credsNonExpired, true, roles);
        user.eraseCredentials();
        return user;
    }

    /**
     * Are creds valid non expired.
     *
     * @return true, if successful
     */
    @JsonIgnore
    public boolean areCredsValidNonExpired()
    {
        boolean credsValid = true;
        // If exp time is less than current time, it's expired
        if (secondsToExpire() < 0)
        {
            credsValid = false; // Creds expired!
        }
        return credsValid;
    }

    @JsonIgnore
    public Long secondsToExpire()
    {
        return this.expirationTime - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

}
