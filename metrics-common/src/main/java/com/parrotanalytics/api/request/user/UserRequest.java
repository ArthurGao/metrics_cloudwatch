package com.parrotanalytics.api.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.api.commons.constants.UserInfoType;
import com.parrotanalytics.apicore.model.request.APIRequest;

import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 * REST API {@link Endpoint} create user request.
 *
 * @author Jackson Lee
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest extends APIRequest
{

    /**“
     *
     */
    private static final long serialVersionUID = -3132837871811465207L;

    /**
     * The operation type.
     */
    protected OperationType operationType;

    /**
     * The password request key.
     */
    protected String passwordRequestKey;

    /**
     * The new requested password.
     */
    protected String newRequestedPassword;

    /**
     * The base URL.
     */
    protected String baseURL;

    /**
     * The reset password.
     */
    protected boolean resetPassword;

    /**
     * The welcome email.
     */
    protected boolean welcomeEmail;

    private boolean tv360;

    /**
     * The id user.
     */
    @JsonProperty("user_id")
    protected Integer requestedUserID;

    /**
     * The first name.
     */
    @JsonProperty("first_name")
    protected String firstName;

    /**
     * The last name.
     */
    @JsonProperty("last_name")
    protected String lastName;

    /**
     * The email address.
     */
    @JsonProperty("user_email")
    protected String emailAddress;

    /**
     * The current password.
     */
    @JsonProperty("current_password")
    protected String currentPassword;

    /**
     * The new password.
     */
    @JsonProperty("new_password")
    protected String newPassword;

    /**
     * The mobile number.
     */
    @JsonProperty("mobile_number")
    protected String mobileNumber;

    /**
     * The ddi number.
     */
    @JsonProperty("ddi_number")
    protected String ddiNumber;

    /**
     * The skype ID.
     */
    @JsonProperty("skype_id")
    protected String skypeID;

    /**
     * The linkedin.
     */
    @JsonProperty("linkedin")
    protected String linkedin;

    /**
     * The status.
     */
    @JsonProperty("status")
    protected Integer status;

    /**
     * The logged in before.
     */
    @JsonProperty("logged_in_before")
    protected Integer loggedInBefore;

    /**
     * The account.
     */
    @JsonProperty("account")
    protected AccountRequest account;

    /**
     * The settings.
     */
    @JsonProperty("settings")
    protected Map<String, Map<String, String>> settings;

    /**
     * The user roles.
     */
    @JsonProperty("roles")
    protected List<String> userRoles;

    /**
     * The created on.
     */
    @JsonProperty("created_on")
    protected String createdOn;

    /**
     * The title.
     */
    @JsonProperty("title")
    protected String title;

    /**
     * The department.
     */
    @JsonProperty("department")
    protected String department;

    /**
     * The account config.
     */
    @JsonProperty("account_config")
    protected Object accountConfig;

    /**
     * The modules.
     */
    @JsonProperty("modules")
    protected Object modules;

    /**
     * The access from.
     */
    @JsonProperty("access_from")
    protected String accessFrom;

    /**
     * The customer.
     */
    @JsonProperty("customer")
    protected Object customer;

    /**
     * The last logged in date.
     */
    @JsonProperty("last_logged_in_date")
    protected String lastLoggedInDate;

    /**
     * The job title.
     */
    @JsonProperty("job_title")
    private String jobTitle;

    /**
     * The location.
     */
    @JsonProperty("location")
    private String location;

    /**
     * The business unit.
     */
    @JsonProperty("business_unit")
    private String businessUnit;

    /**
     * The parrot touch point.
     */
    @JsonProperty("parrot_touch_point")
    private String parrotTouchPoint;

    /**
     * The region.
     */
    @JsonProperty("region")
    private String region;

    /**
     * The notes.
     */
    @JsonProperty("notes")
    private String notes;

    /**
     * The notes.
     */
    @JsonProperty("access_breakdown_module")
    private Boolean accessBreakdownModule;

    private UserInfoType infoType;

    /**
     * Gets the password request key.
     *
     * @return the password request key
     */
    public String getPasswordRequestKey()
    {
        return passwordRequestKey;
    }

    /**
     * Sets the password request key.
     *
     * @param passwordRequestKey the new password request key
     */
    public void setPasswordRequestKey(String passwordRequestKey)
    {
        this.passwordRequestKey = passwordRequestKey;
    }

    /**
     * Gets the settings.
     *
     * @return the settings
     */
    public Map<String, Map<String, String>> getSettings()
    {
        return settings;
    }

    /**
     * Sets the settings.
     *
     * @param settings the settings
     */
    public void setSettings(Map<String, Map<String, String>> settings)
    {
        this.settings = settings;
    }

    /**
     * Gets the operation type.
     *
     * @return the operation type
     */
    public OperationType getOperationType()
    {
        return operationType;
    }

    /**
     * Sets the operation type.
     *
     * @param operationType the new operation type
     */
    public void setOperationType(OperationType operationType)
    {
        this.operationType = operationType;
    }

    /**
     * Gets the id user.
     *
     * @return the id user
     */
    public Integer getRequestedUserID()
    {
        return requestedUserID;
    }

    /**
     * Sets the id user.
     *
     */
    public void setRequestedUserID(Integer requestedUserID)
    {
        this.requestedUserID = requestedUserID;
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
     * @param firstName the new first name
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
     * @param lastName the new last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmailAddress()
    {
        return emailAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress the new email address
     */
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the current password.
     *
     * @return the current password
     */
    public String getCurrentPassword()
    {
        return currentPassword;
    }

    /**
     * Sets the current password.
     *
     * @param currentPassword the new current password
     */
    public void setCurrentPassword(String currentPassword)
    {
        this.currentPassword = currentPassword;
    }

    /**
     * Gets the new password.
     *
     * @return the new password
     */
    public String getNewPassword()
    {
        return newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPassword the new new password
     */
    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
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
     * @param mobileNumber the new mobile number
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
     * @param ddiNumber the new ddi number
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
     * @param skypeID the new skype ID
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
     * @param linkedin the new linkedin
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
     * @param status the new status
     */
    public void setStatus(Integer status)
    {
        this.status = status;
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
     * @param loggedInBefore the new logged in before
     */
    public void setLoggedInBefore(Integer loggedInBefore)
    {
        this.loggedInBefore = loggedInBefore;
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    public AccountRequest getAccount()
    {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param account the new account
     */
    public void setAccount(AccountRequest account)
    {
        this.account = account;
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
     * @param userRoles the new user roles
     */
    public void setUserRoles(List<String> userRoles)
    {
        this.userRoles = userRoles;
    }

    /**
     * Gets the created on.
     *
     * @return the created on
     */
    public String getCreatedOn()
    {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn the new created on
     */
    public void setCreatedOn(String createdOn)
    {
        this.createdOn = createdOn;
    }

    /**
     * Gets the account config.
     *
     * @return the account config
     */
    public Object getAccountConfig()
    {
        return accountConfig;
    }

    /**
     * Sets the account config.
     *
     * @param accountConfig the new account config
     */
    public void setAccountConfig(Object accountConfig)
    {
        this.accountConfig = accountConfig;
    }

    /**
     * Gets the modules.
     *
     * @return the modules
     */
    public Object getModules()
    {
        return modules;
    }

    /**
     * Sets the modules.
     *
     * @param modules the new modules
     */
    public void setModules(Object modules)
    {
        this.modules = modules;
    }

    /**
     * Gets the access from.
     *
     * @return the access from
     */
    public String getAccessFrom()
    {
        return accessFrom;
    }

    /**
     * Sets the access from.
     *
     * @param accessFrom the new access from
     */
    public void setAccessFrom(String accessFrom)
    {
        this.accessFrom = accessFrom;
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
     * @param title the new title
     */
    public void setTitle(String title)
    {
        this.title = title;
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
     * @param department the new department
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }

    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public Object getCustomer()
    {
        return customer;
    }

    /**
     * Sets the customer.
     *
     * @param customer the new customer
     */
    public void setCustomer(Object customer)
    {
        this.customer = customer;
    }

    /**
     * Checks if is reset password.
     *
     * @return true, if is reset password
     */
    public boolean isResetPassword()
    {
        return resetPassword;
    }

    /**
     * Sets the reset password.
     *
     * @param resetPassword the new reset password
     */
    public void setResetPassword(boolean resetPassword)
    {
        this.resetPassword = resetPassword;
    }

    /**
     * Gets the new requested password.
     *
     * @return the new requested password
     */
    public String getNewRequestedPassword()
    {
        return newRequestedPassword;
    }

    /**
     * Sets the new requested password.
     *
     * @param newRequestedPassword the new new requested password
     */
    public void setNewRequestedPassword(String newRequestedPassword)
    {
        this.newRequestedPassword = newRequestedPassword;
    }

    /**
     * Gets the base URL.
     *
     * @return the base URL
     */
    public String getBaseURL()
    {
        return baseURL;
    }

    /**
     * Sets the base URL.
     *
     * @param baseURL the new base URL
     */
    public void setBaseURL(String baseURL)
    {
        this.baseURL = baseURL;
    }

    /**
     * Checks if is welcome email.
     *
     * @return true, if is welcome email
     */
    public boolean isWelcomeEmail()
    {
        return welcomeEmail;
    }

    /**
     * Sets the welcome email.
     *
     * @param welcomeEmail the new welcome email
     */
    public void setWelcomeEmail(boolean welcomeEmail)
    {
        this.welcomeEmail = welcomeEmail;
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
     * @param lastLoggedInDate the new last logged in date
     */
    public void setLastLoggedInDate(String lastLoggedInDate)
    {
        this.lastLoggedInDate = lastLoggedInDate;
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
     * @param jobTitle the new job title
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
     * @param location the new location
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
     * @param businessUnit the new business unit
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
     * @param parrotTouchPoint the new parrot touch point
     */
    public void setParrotTouchPoint(String parrotTouchPoint)
    {
        this.parrotTouchPoint = parrotTouchPoint;
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
     * @param region the new region
     */
    public void setRegion(String region)
    {
        this.region = region;
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
     * @param notes the new notes
     */
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public Boolean getAccessBreakdownModule()
    {
        return accessBreakdownModule;
    }

    public void setAccessBreakdownModule(Boolean accessBreakdownModule)
    {
        this.accessBreakdownModule = accessBreakdownModule;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "UserRequest [idUser=" + requestedUserID + ", firstName=" + firstName + ", lastName=" + lastName
                + ", emailAddress=" + emailAddress + ", password=" + currentPassword + ", mobileNumber=" + mobileNumber
                + ", ddiNumber=" + ddiNumber + ", skypeID=" + skypeID + ", linkedin=" + linkedin + ", status=" + status
                + ", loggedinBefore=" + loggedInBefore + ", account=" + account + ", userRoles=" + userRoles + "]";
    }

    public UserInfoType getInfoType()
    {
        return infoType;
    }

    public void setInfoType(UserInfoType infoType)
    {
        this.infoType = infoType;
    }

    public boolean isTv360()
    {
        return tv360;
    }

    public void setTv360(boolean tv360)
    {
        this.tv360 = tv360;
    }
}
