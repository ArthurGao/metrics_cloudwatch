package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.JoinFetch;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the userRoles database table.
 * 
 */
@Entity
@Table(name = "userroles", schema = "subscription")
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
public class UserRole implements Serializable
{
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UserRolePK id;

    // bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    @JoinFetch
    @JsonBackReference
    private InternalUser user;

    // bi-directional many-to-one association to Role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRole")
    @JoinFetch
    @JsonManagedReference
    private Role role;

    /**
     * Instantiates a new user role.
     */
    public UserRole()
    {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public UserRolePK getId()
    {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(UserRolePK id)
    {
        this.id = id;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public InternalUser getUser()
    {
        return this.user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            the new user
     */
    public void setUser(InternalUser user)
    {
        this.user = user;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public Role getRole()
    {
        return this.role;
    }

    /**
     * Sets the role.
     *
     * @param role
     *            the new role
     */
    public void setRole(Role role)
    {
        this.role = role;
    }

    public String getRoleName(Role role)
    {
        return this.role.getRoleName();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRole other = (UserRole) obj;
        if (role == null)
        {
            if (other.role != null)
                return false;
        }
        else if (!role.equals(other.role))
            return false;
        if (user == null)
        {
            if (other.user != null)
                return false;
        }
        else if (!user.equals(other.user))
            return false;
        return true;
    }

}