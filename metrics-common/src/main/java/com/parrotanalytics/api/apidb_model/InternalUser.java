package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.PrivateOwned;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "user", schema = "subscription")
public class InternalUser implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant OPS_API_ID. */
    public static final String OPS_API_ID = "opsapiuser@parrotanalytics.com";

    /** The id user. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    /** The ddi number. */
    private String ddiNumber;

    /** The email address. */
    private String emailAddress;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The linked in. */
    private String linkedIn;

    /** The mobile number. */
    private String mobileNumber;

    /** The password. */
    private String password;

    /** The skype id. */
    private String skypeId;

    /** The created on. */
    @Temporal(TemporalType.DATE)
    private Date createdOn;

    /** The created on. */
    @Temporal(TemporalType.DATE)
    private Date lastLoggedInDate;

    /** The status. */
    private Integer status;

    /** The logged in before. */
    private Integer loggedInBefore;

    /** The notification received. */
    private Integer notificationReceived;

    /** The notification received. */
    private Integer emailSent;

    /** The title. */
    private String title;

    /** The department. */
    private String department;

    /** The job title. */
    private String jobTitle;

    /** The location. */
    private String location;

    /** The region. */
    private String region;

    /** The business unit. */
    private String businessUnit;

    /** The parrot touch point. */
    private String parrotTouchPoint;

    /** The notes. */
    private String notes;

    /** user level breakdown module access */
    private Boolean accessBreakdownModule;

    /** The account. */
    // bi-directional many-to-one association to Account
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFetch
    @JoinColumn(name = "idAccount")
    private Account account;

    /** The user roles. */
    // bi-directional many-to-one association to UserRole
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JoinFetch
    @JsonManagedReference
    @PrivateOwned
    private List<UserRole> userRoles;

    /**
     * Instantiates a new user.
     */
    public InternalUser()
    {
    }

    /**
     * Gets the id user.
     *
     * @return the id user
     */
    public Integer getIdUser()
    {
        return this.idUser;
    }

    /**
     * Sets the id user.
     *
     * @param idUser
     *            the new id user
     */
    public void setIdUser(Integer idUser)
    {
        if (null != idUser)
        {
            this.idUser = idUser;
        }
    }

    /**
     * Gets the ddi number.
     *
     * @return the ddi number
     */
    public String getDdiNumber()
    {

        return this.ddiNumber;
    }

    /**
     * Sets the ddi number.
     *
     * @param ddiNumber
     *            the new ddi number
     */
    public void setDdiNumber(String ddiNumber)
    {
        if (null != ddiNumber)
        {
            this.ddiNumber = ddiNumber;
        }
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmailAddress()
    {
        return this.emailAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress
     *            the new email address
     */
    public void setEmailAddress(String emailAddress)
    {
        if (null != emailAddress)
        {
            this.emailAddress = emailAddress;
        }
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName()
    {
        return this.firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the new first name
     */
    public void setFirstName(String firstName)
    {
        if (null != firstName)
        {
            this.firstName = firstName;
        }
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName()
    {
        return this.lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the new last name
     */
    public void setLastName(String lastName)
    {
        if (null != lastName)
        {
            this.lastName = lastName;
        }
    }

    /**
     * Gets the linked in.
     *
     * @return the linked in
     */
    public String getLinkedIn()
    {
        return this.linkedIn;
    }

    /**
     * Sets the linked in.
     *
     * @param linkedIn
     *            the new linked in
     */
    public void setLinkedIn(String linkedIn)
    {
        if (null != linkedIn)
        {
            this.linkedIn = linkedIn;
        }
    }

    /**
     * Gets the mobile number.
     *
     * @return the mobile number
     */
    public String getMobileNumber()
    {
        return this.mobileNumber;
    }

    /**
     * Sets the mobile number.
     *
     * @param mobileNumber
     *            the new mobile number
     */
    public void setMobileNumber(String mobileNumber)
    {
        if (null != mobileNumber)
        {
            this.mobileNumber = mobileNumber;
        }
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets the skype id.
     *
     * @return the skype id
     */
    public String getSkypeId()
    {
        return this.skypeId;
    }

    /**
     * Sets the skype id.
     *
     * @param skypeId
     *            the new skype id
     */
    public void setSkypeId(String skypeId)
    {
        if (null != skypeId)
        {
            this.skypeId = skypeId;
        }
    }

    /**
     * Gets the created on.
     *
     * @return the createdOn
     */
    public Date getCreatedOn()
    {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn(Date createdOn)
    {
        if (null != createdOn)
        {
            this.createdOn = createdOn;
        }
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
        if (null != status)
        {
            this.status = status;
        }
    }

    /**
     * Gets the logged in before.
     *
     * @return the logged in before
     */
    public Integer getLoggedInBefore()
    {
        return loggedInBefore;
    }

    /**
     * Sets the logged in before.
     *
     * @param loggedInBefore
     *            the new logged in before
     */
    public void setLoggedInBefore(Integer loggedInBefore)
    {
        if (null != loggedInBefore)
        {
            this.loggedInBefore = loggedInBefore;
        }
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public void setTitle(String title)
    {
        if (null != title)
        {
            this.title = title;
        }
    }

    /**
     * Gets the department.
     *
     * @return the department
     */
    public String getDepartment()
    {
        return department;
    }

    /**
     * Sets the department.
     *
     * @param department
     *            the new department
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }

    /**
     * Gets the email sent.
     *
     * @return the email sent
     */
    public Integer getEmailSent()
    {
        return emailSent;
    }

    /**
     * Sets the email sent.
     *
     * @param emailSent
     *            the new email sent
     */
    public void setEmailSent(Integer emailSent)
    {

        if (null != emailSent)
        {
            this.emailSent = emailSent;
        }
    }

    /**
     * Gets the notification received.
     *
     * @return the notification received
     */
    public Integer getNotificationReceived()
    {
        return notificationReceived;
    }

    /**
     * Sets the notification received.
     *
     * @param notificationReceived
     *            the new notification received
     */
    public void setNotificationReceived(Integer notificationReceived)
    {
        if (null != notificationReceived)
        {
            this.notificationReceived = notificationReceived;
        }
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    public Account getAccount()
    {
        return this.account;
    }

    /**
     * Sets the account.
     *
     * @param account
     *            the new account
     */
    public void setAccount(Account account)
    {
        if (null != account)
        {
            this.account = account;
        }
    }

    /**
     * Gets the job title.
     *
     * @return the job title
     */
    public String getJobTitle()
    {
        return jobTitle;
    }

    /**
     * Sets the job title.
     *
     * @param jobTitle
     *            the new job title
     */
    public void setJobTitle(String jobTitle)
    {
        if (null != jobTitle)
        {
            this.jobTitle = jobTitle;
        }
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location
     *            the new location
     */
    public void setLocation(String location)
    {
        if (null != location)
        {
            this.location = location;
        }
    }

    /**
     * Gets the business unit.
     *
     * @return the business unit
     */
    public String getBusinessUnit()
    {
        return businessUnit;
    }

    /**
     * Sets the business unit.
     *
     * @param businessUnit
     *            the new business unit
     */
    public void setBusinessUnit(String businessUnit)
    {
        if (null != businessUnit)
        {
            this.businessUnit = businessUnit;
        }
    }

    /**
     * Gets the parrot touch point.
     *
     * @return the parrot touch point
     */
    public String getParrotTouchPoint()
    {

        return parrotTouchPoint;

    }

    /**
     * Sets the parrot touch point.
     *
     * @param parrotTouchPoint
     *            the new parrot touch point
     */
    public void setParrotTouchPoint(String parrotTouchPoint)
    {
        if (null != parrotTouchPoint)
        {
            this.parrotTouchPoint = parrotTouchPoint;
        }
    }

    /**
     * Gets the region.
     *
     * @return the region
     */
    public String getRegion()
    {
        return region;
    }

    /**
     * Sets the region.
     *
     * @param region
     *            the new region
     */
    public void setRegion(String region)
    {
        if (null != region)
        {
            this.region = region;
        }
    }

    /**
     * Gets the notes.
     *
     * @return the notes
     */
    public String getNotes()
    {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes
     *            the new notes
     */
    public void setNotes(String notes)
    {
        if (null != notes)
        {
            this.notes = notes;
        }
    }

    public Boolean hasAccessBreakdownModule()
    {
        return accessBreakdownModule;
    }

    public void setAccessBreakdownModule(Boolean accessBreakdownModule)
    {
        this.accessBreakdownModule = accessBreakdownModule;
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
        if (null != userRoles)
        {
            this.userRoles = userRoles;
        }
    }

    /**
     * Gets the last logged in date.
     *
     * @return the last logged in date
     */
    public Date getLastLoggedInDate()
    {
        return lastLoggedInDate;
    }

    /**
     * Sets the last logged in date.
     *
     * @param lastLoggedInDate
     *            the new last logged in date
     */
    public void setLastLoggedInDate(Date lastLoggedInDate)
    {
        if (null != lastLoggedInDate)
        {
            this.lastLoggedInDate = lastLoggedInDate;
        }
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
        userRole.setUser(this);

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
        userRole.setUser(null);

        return userRole;
    }

    /**
     * A convenience method to conform this user into a spring-security user.
     *
     * @return the org.springframework.security.core.userdetails. user
     */
    @Transient
    public org.springframework.security.core.userdetails.User toSpringUser()
    {
        List<GrantedAuthority> roles = new ArrayList<>();
        for (UserRole ur : getUserRoles())
        {
            String roleName = ur.getRole().getRoleName();
            roles.add(new SimpleGrantedAuthority(roleName));
        }

        boolean accountEnabled = this.getAccount().getStatus() == 1;
        boolean userEnabled = this.getStatus() == 1;

        org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(
                emailAddress, password, userEnabled, true, true, accountEnabled, roles);
        return springUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InternalUser other = (InternalUser) obj;
        if (emailAddress == null)
        {
            if (other.emailAddress != null)
                return false;
        }
        else if (!emailAddress.equals(other.emailAddress))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "InternalUser [emailAddress=" + emailAddress + ", firstName=" + firstName + ", lastName=" + lastName
                + "]";
    }

}