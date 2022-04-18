package com.parrotanalytics.api.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Role holds the Role information for a user.
 * 
 * @author Chris
 */
public class Role implements GrantedAuthority
{
    private static final long serialVersionUID = -4793279641830010306L;

    @JsonProperty
    private String role;

    /**
     * Instantiates a new role.
     */
    protected Role()
    {
        // Only for use with mapping
    }

    /**
     * Instantiates a new role.
     *
     * @param role
     *            the role
     */
    public Role(String role)
    {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    public String getAuthority()
    {
        return role;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj instanceof Role)
        {
            return role.equals(((Role) obj).role);
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return this.role.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return this.role;
    }

}
