package com.parrotanalytics.api.response.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.response.customer.CustomerResponse;
import com.parrotanalytics.api.response.notification.CoreNotificationPayload;
import com.parrotanalytics.apicore.model.response.APIResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class UserResponse.
 *
 * @author Jackson Lee
 */
@JsonPropertyOrder(
{
        "user_id", "user_email", "first_name", "last_name", "mobile_number", "ddi_number", "skype_id", "linkedin",
        "logged_in_before", "created_on", "status", "account_config", "modules", "user_roles", "account"
})
public class UserResponse extends APIResponse
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1551754593785717765L;

    /** The user id. */

    @JsonProperty("user_id")
    private Integer userId;

    /** The user email. */
    @JsonProperty("user_email")
    private String userEmail;

    /** The first name. */
    @JsonProperty("first_name")
    private String firstName;

    /** The last name. */
    @JsonProperty("last_name")
    private String lastName;

    /** The mobile number. */
    @JsonProperty("mobile_number")
    private String mobileNumber;

    /** The ddi number. */
    @JsonProperty("ddi_number")
    private String ddiNumber;

    /** The skype ID. */
    @JsonProperty("skype_id")
    private String skypeID;

    /** The linkedin. */
    @JsonProperty("linkedin")
    private String linkedin;

    /** The logged in before. */
    @JsonProperty("logged_in_before")
    private Integer loggedInBefore;

    /** The created on. */
    @JsonProperty("created_on")
    private String createdOn;

    /** The title. */
    @JsonProperty("title")
    private String title;

    /** The department. */
    @JsonProperty("department")
    private String department;

    /** The status. */
    @JsonProperty("status")
    private Integer status;

    /** The access from. */
    @JsonProperty("access_from")
    private String accessFrom;

    /** The modules. */
    @JsonProperty("modules")
    private Map<String, Map<String, Object>> modules;

    /** The user roles. */
    @JsonProperty("roles")
    List<String> userRoles;

    /** The account. */
    @JsonProperty("account")
    private UserAccountResponse account;

    /** The customer. */
    @JsonProperty("customer")
    private CustomerResponse customer;

    /** The email sent. */
    @JsonProperty("email_sent")
    private Integer emailSent;

    /** The last logged in date. */
    @JsonProperty("last_logged_in_date")
    private String lastLoggedInDate;

    /** The settings. */
    @JsonProperty("settings")
    private Map<String, Map<String, Object>> settings = new HashMap<String, Map<String, Object>>();

    @JsonProperty("notifications")
    private List<CoreNotificationPayload> notifications;

    /** The job title. */
    @JsonProperty("job_title")
    private String jobTitle;

    /** The location. */
    @JsonProperty("location")
    private String location;

    /** The business unit. */
    @JsonProperty("business_unit")
    private String businessUnit;

    /** The parrot touch point. */
    @JsonProperty("parrot_touch_point")
    private String parrotTouchPoint;

    /** The region. */
    @JsonProperty("region")
    private String region;

    /** The notes. */
    @JsonProperty("notes")
    private String notes;

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public Integer getUserId()
    {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the new user id
     */
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    /**
     * Gets the user email.
     *
     * @return the user email
     */
    public String getUserEmail()
    {
        return userEmail;
    }

    /**
     * Sets the user email.
     *
     * @param userEmail
     *            the new user email
     */
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the new first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the new last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets the mobile number.
     *
     * @return the mobile number
     */
    public String getMobileNumber()
    {
        return mobileNumber;
    }

    /**
     * Sets the mobile number.
     *
     * @param mobileNumber
     *            the new mobile number
     */
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Gets the ddi number.
     *
     * @return the ddi number
     */
    public String getDdiNumber()
    {
        return ddiNumber;
    }

    /**
     * Sets the ddi number.
     *
     * @param ddiNumber
     *            the new ddi number
     */
    public void setDdiNumber(String ddiNumber)
    {
        this.ddiNumber = ddiNumber;
    }

    /**
     * Gets the skype ID.
     *
     * @return the skype ID
     */
    public String getSkypeID()
    {
        return skypeID;
    }

    /**
     * Sets the skype ID.
     *
     * @param skypeID
     *            the new skype ID
     */
    public void setSkypeID(String skypeID)
    {
        this.skypeID = skypeID;
    }

    /**
     * Gets the linkedin.
     *
     * @return the linkedin
     */
    public String getLinkedin()
    {
        return linkedin;
    }

    /**
     * Sets the linkedin.
     *
     * @param linkedin
     *            the new linkedin
     */
    public void setLinkedin(String linkedin)
    {
        this.linkedin = linkedin;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Integer getStatus()
    {
        return status;
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
    public List<String> getUserRoles()
    {
        return userRoles;
    }

    /**
     * Sets the user roles.
     *
     * @param userRoles
     *            the new user roles
     */
    public void setUserRoles(List<String> userRoles)
    {
        this.userRoles = userRoles;
    }

    /**
     * Gets the access from.
     *
     * @return the accessFrom
     */
    public String getAccessFrom()
    {
        return accessFrom;
    }

    /**
     * Sets the access from.
     *
     * @param accessFrom
     *            the accessFrom to set
     */
    public void setAccessFrom(String accessFrom)
    {
        this.accessFrom = accessFrom;
    }

    /**
     * Gets the modules.
     *
     * @return the modules
     */
    public Map<String, Map<String, Object>> getModules()
    {
        return modules;
    }

    /**
     * Sets the modules.
     *
     * @param modules
     *            the modules
     */
    public void setModules(Map<String, Map<String, Object>> modules)
    {
        this.modules = modules;
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    public UserAccountResponse getAccount()
    {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param account
     *            the new account
     */
    public void setAccount(UserAccountResponse account)
    {
        this.account = account;
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
        this.loggedInBefore = loggedInBefore;
    }

    /**
     * Gets the created on.
     *
     * @return the createdOn
     */
    public String getCreatedOn()
    {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn(String createdOn)
    {
        this.createdOn = createdOn;
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
        this.title = title;
    }

    /**
     * Gets the last logged in date.
     *
     * @return the last logged in date
     */
    public String getLastLoggedInDate()
    {
        return lastLoggedInDate;
    }

    /**
     * Sets the last logged in date.
     *
     * @param lastLoggedInDate
     *            the new last logged in date
     */
    public void setLastLoggedInDate(String lastLoggedInDate)
    {
        this.lastLoggedInDate = lastLoggedInDate;
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
        this.emailSent = emailSent;
    }

    /**
     * Gets the settings.
     *
     * @return the settings
     */
    public Map<String, Map<String, Object>> getSettings()
    {
        return settings;
    }

    /**
     * Sets the settings.
     *
     * @param settings
     *            the settings to set
     */
    public void setSettings(Map<String, Map<String, Object>> settings)
    {
        this.settings = settings;
    }

    public List<CoreNotificationPayload> getNotifications()
    {
        return notifications;
    }

    public void setNotifications(List<CoreNotificationPayload> notifications)
    {
        this.notifications = notifications;
    }

    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public CustomerResponse getCustomer()
    {
        return customer;
    }

    /**
     * Sets the customer.
     *
     * @param customer
     *            the new customer
     */
    public void setCustomer(CustomerResponse customer)
    {
        this.customer = customer;
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
        this.jobTitle = jobTitle;
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
        this.location = location;
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
        this.businessUnit = businessUnit;
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
        this.parrotTouchPoint = parrotTouchPoint;
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
        this.notes = notes;
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
        this.region = region;
    }

}
