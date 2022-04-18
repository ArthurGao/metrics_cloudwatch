package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * The primary key class for the userRoles database table.
 * 
 */
@Embeddable
public class UserRolePK implements Serializable
{
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iduserRoles;

    @Column(insertable = false, updatable = false)
    private int idUser;

    @Column(insertable = false, updatable = false)
    private int idRole;

    /**
     * Instantiates a new user role pk.
     */
    public UserRolePK()
    {
    }

    /**
     * Gets the iduser roles.
     *
     * @return the iduser roles
     */
    public int getIduserRoles()
    {
        return this.iduserRoles;
    }

    /**
     * Sets the iduser roles.
     *
     * @param iduserRoles
     *            the new iduser roles
     */
    public void setIduserRoles(int iduserRoles)
    {
        this.iduserRoles = iduserRoles;
    }

    /**
     * Gets the id user.
     *
     * @return the id user
     */
    public int getIdUser()
    {
        return this.idUser;
    }

    /**
     * Sets the id user.
     *
     * @param idUser
     *            the new id user
     */
    public void setIdUser(int idUser)
    {
        this.idUser = idUser;
    }

    /**
     * Gets the id role.
     *
     * @return the id role
     */
    public int getIdRole()
    {
        return this.idRole;
    }

    /**
     * Sets the id role.
     *
     * @param idRole
     *            the new id role
     */
    public void setIdRole(int idRole)
    {
        this.idRole = idRole;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (!(other instanceof UserRolePK))
        {
            return false;
        }
        UserRolePK castOther = (UserRolePK) other;
        return (this.iduserRoles == castOther.iduserRoles) && (this.idUser == castOther.idUser)
                && (this.idRole == castOther.idRole);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.iduserRoles;
        hash = hash * prime + this.idUser;
        hash = hash * prime + this.idRole;

        return hash;
    }
}