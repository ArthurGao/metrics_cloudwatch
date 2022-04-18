package com.parrotanalytics.api.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.parrotanalytics.api.apidb_model.InternalUser;

/**
 * The Class UserAuthentication holds the user details, roles etc.
 * 
 * @author Chris
 */
public class UserAuthentication implements Authentication
{
    private static final long serialVersionUID = -5021540833011795L;

    private User springUser;

    private InternalUser appUser = null;

    private boolean authenticated = true;

    /**
     * Instantiates a new user authentication.
     * 
     * @param internalUser
     */
    public UserAuthentication(InternalUser internalUser)
    {
        this.springUser = internalUser.toSpringUser();
        this.appUser = internalUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.security.Principal#getName()
     */
    @Override
    public String getName()
    {
        return appUser.getEmailAddress();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#getAuthorities()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return springUser.getAuthorities();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#getCredentials()
     */
    @Override
    public Object getCredentials()
    {
        return springUser.getPassword();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#getDetails()
     */
    @Override
    public User getDetails()
    {
        return springUser;
    }

    public InternalUser getInternalUser()
    {
        return appUser;
    }

    public void setInternalUser(InternalUser internalUser)
    {
        this.appUser = internalUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#getPrincipal()
     */
    @Override
    public Object getPrincipal()
    {
        return springUser.getUsername();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#isAuthenticated()
     */
    @Override
    public boolean isAuthenticated()
    {
        return authenticated;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#setAuthenticated(
     * boolean)
     */
    @Override
    public void setAuthenticated(boolean authenticated)
    {
        this.authenticated = authenticated;
    }

}