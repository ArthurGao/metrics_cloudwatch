package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the Role database table.
 * 
 */
@Entity
@Table(name = "role", schema = "subscription")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final Integer HELPDESK_ADMIN_ROLE_ID = 4;

    public static final Integer HELPDESK_VIEWER_ROLE_ID = 3;

    public static final Integer SUPPORT_ROLE_ID = 5;

    public static final Integer PARROT_ADMIN_ROLE_ID = 6;

    public static final Integer TALENT_LITE_VIEWER = 14;

    public static final Integer TALENT_ENTERPRISE_MODULE = 15;

    public static final Integer MOVIE_LITE_VIEWER = 16;

    public static final Integer MOVIE_ENTERPRISE_MODULE = 17;

    @Id
    private Integer idRole;

    private String description;

    private String roleName;

    private Integer status;

    // bi-directional many-to-one association to UserRole
    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private List<UserRole> userRoles;

    public static final String MONITOR_VIEWER = "Monitor Viewer";

    public static final String ENTERPRISE_ADMIN = "Enterprise Admin";

    public static final String ENTERPRISE_VIEWER = "Enterprise Viewer";

    /**
     * Instantiates a new role.
     */
    public Role()
    {
    }

    public Role(String roleName)
    {
        super();
        this.roleName = roleName;
    }

    /**
     * Gets the id role.
     *
     * @return the id role
     */
    public Integer getIdRole()
    {
        return this.idRole;
    }

    /**
     * Sets the id role.
     *
     * @param idRole
     *            the new id role
     */
    public void setIdRole(Integer idRole)
    {
        this.idRole = idRole;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets the role name.
     *
     * @return the role name
     */
    public String getRoleName()
    {
        return this.roleName;
    }

    /**
     * Sets the role name.
     *
     * @param roleName
     *            the new role name
     */
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Integer getStatus()
    {
        return this.status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    /**
     * Gets the user roles.
     *
     * @return the user roles
     */
    public List<UserRole> getUserRoles()
    {
        return this.userRoles;
    }

    /**
     * Sets the user roles.
     *
     * @param userRoles
     *            the new user roles
     */
    public void setUserRoles(List<UserRole> userRoles)
    {
        this.userRoles = userRoles;
    }

    /**
     * Adds the user role.
     *
     * @param userRole
     *            the user role
     * @return the user role
     */
    public UserRole addUserRole(UserRole userRole)
    {
        getUserRoles().add(userRole);
        userRole.setRole(this);

        return userRole;
    }

    /**
     * Removes the user role.
     *
     * @param userRole
     *            the user role
     * @return the user role
     */
    public UserRole removeUserRole(UserRole userRole)
    {
        getUserRoles().remove(userRole);
        userRole.setRole(null);

        return userRole;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
        Role other = (Role) obj;
        if (roleName == null)
        {
            if (other.roleName != null)
                return false;
        }
        else if (!roleName.equals(other.roleName))
            return false;
        return true;
    }

}