package com.parrotanalytics.api.apidb_model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the Account database table.
 */
@Entity
@Table(name = "account", schema = "subscription")
@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
public class Account implements Serializable {
    private static final long serialVersionUID = -577492024294467714L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAccount;

    private String accountName;

    private String apiId;

    private String division;

    private Integer homeMarket;

    private String primaryContactEmail;

    private String primaryContactName;

    private String primaryContactNumber;

    private Integer status;

    @Column(name = "access_level")
    private String accessLevel;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_level")
    private String accountLevel;

    @Column(name = "access_level_percent")
    private Integer accessLevelPercent;

    @Temporal(TemporalType.DATE)
    @Column(name = "subscription_start")
    private Date subscriptionStart;

    @Column(name = "historical_data_offset")
    private Integer historicalOffset;

    // bi-directional many-to-one association to Customer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFetch
    @JoinColumn(name = "idCustomer")
    private Customer customer;

    // bi-directional many-to-one association to User
    @OneToMany(mappedBy = "account")
    private List<InternalUser> users;

    // bi-directional many-to-one association to Subscription
    @OneToMany
    @JoinColumn(name = "idAccount", nullable = false)
    private List<Subscription> subscriptions;

    // bi-directional many-to-one association to Project
    @OneToMany(mappedBy = "account")
    private List<Project> projects;

    @Column(name = "accessibleTier")
    private String accessibleTier;

    /*
     * no args default constructor
     */
    public Account() {
    }

    public Account(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public Account(Integer idAccount, String accountName) {
        this.idAccount = idAccount;
        this.accountName = accountName;
    }

    /**
     * @return the idAccount
     */
    public Integer getIdAccount() {
        return idAccount;
    }

    /**
     * @param idAccount the idAccount to set
     */
    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    /**
     * Gets the account name.
     *
     * @return the account name
     */
    public String getAccountName() {
        return this.accountName;
    }

    /**
     * Sets the account name.
     *
     * @param accountName the new account name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel;
    }

    /**
     * Gets the api id.
     *
     * @return the api id
     */
    public String getApiId() {
        return this.apiId;
    }

    /**
     * Sets the api id.
     *
     * @param apiId the new api id
     */
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    /**
     * Gets the division.
     *
     * @return the division
     */
    public String getDivision() {
        return this.division;
    }

    /**
     * Sets the division.
     *
     * @param division the new division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the home market.
     *
     * @return the home market
     */
    public Integer getHomeMarket() {
        return this.homeMarket;
    }

    /**
     * Sets the home market.
     *
     * @param homeMarket the new home market
     */
    public void setHomeMarket(Integer homeMarket) {
        this.homeMarket = homeMarket;
    }

    /**
     * Gets the primary contact email.
     *
     * @return the primary contact email
     */
    public String getPrimaryContactEmail() {
        return this.primaryContactEmail;
    }

    /**
     * Sets the primary contact email.
     *
     * @param primaryContactEmail the new primary contact email
     */
    public void setPrimaryContactEmail(String primaryContactEmail) {
        this.primaryContactEmail = primaryContactEmail;
    }

    /**
     * Gets the primary contact name.
     *
     * @return the primary contact name
     */
    public String getPrimaryContactName() {
        return this.primaryContactName;
    }

    /**
     * Sets the primary contact name.
     *
     * @param primaryContactName the new primary contact name
     */
    public void setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
    }

    /**
     * Gets the primary contact number.
     *
     * @return the primary contact number
     */
    public String getPrimaryContactNumber() {
        return this.primaryContactNumber;
    }

    /**
     * Sets the primary contact number.
     *
     * @param primaryContactNumber the new primary contact number
     */
    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Integer getAccessLevelPercent() {
        return accessLevelPercent;
    }

    public void setAccessLevelPercent(Integer accessLevelPercent) {
        this.accessLevelPercent = accessLevelPercent;
    }

    public Integer getHistoricalOffset() {
        return historicalOffset;
    }

    public void setHistoricalOffset(Integer historicalOffset) {
        this.historicalOffset = historicalOffset;
    }

    /**
     * @return the subscriptionStart
     */
    public Date getSubscriptionStart() {
        return subscriptionStart;
    }

    /**
     * @param subscriptionStart the subscriptionStart to set
     */
    public void setSubscriptionStart(Date subscriptionStart) {
        this.subscriptionStart = subscriptionStart;
    }

    /**
     * @return the historicalWindow
     */
    public Integer getHistoricalWindow() {
        return historicalOffset;
    }

    /**
     * @param historicalWindow the historicalWindow to set
     */
    public void setHistoricalWindow(Integer historicalWindow) {
        this.historicalOffset = historicalWindow;
    }

    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Sets the customer.
     *
     * @param customer the new customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    /**
     * Gets the subscriptions.
     *
     * @return the subscriptions
     */
    public List<Subscription> getSubscriptions() {
        return this.subscriptions;
    }

    /**
     * Sets the subscriptions.
     *
     * @param subscriptions the new subscriptions
     */
    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /**
     * Adds the subscription.
     *
     * @param subscription the subscription
     * @return the subscription
     */
    public Subscription addSubscription(Subscription subscription) {
        getSubscriptions().add(subscription);
        subscription.setIdAccount(this.idAccount);
        return subscription;
    }

    /**
     * Removes the subscription.
     *
     * @param subscription the subscription
     * @return the subscription
     */
    public Subscription removeSubscription(Subscription subscription) {
        getSubscriptions().remove(subscription);
        return subscription;
    }

    /**
     * Gets the users.
     *
     * @return the users
     */
    public List<InternalUser> getUsers() {
        return this.users;
    }

    /**
     * Sets the users.
     *
     * @param users the new users
     */
    public void setUsers(List<InternalUser> users) {
        this.users = users;
    }

    /**
     * Adds the user.
     *
     * @param user the user
     * @return the user
     */
    public InternalUser addUser(InternalUser user) {
        getUsers().add(user);
        user.setAccount(this);

        return user;
    }

    /**
     * Removes the user.
     *
     * @param user the user
     * @return the user
     */
    public InternalUser removeUser(InternalUser user) {
        getUsers().remove(user);
        user.setAccount(null);

        return user;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Project addProject(Project project) {
        getProjects().add(project);
        project.setAccount(this);

        return project;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAccount == null) ? 0 : idAccount.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (idAccount == null) {
            if (other.idAccount != null)
                return false;
        } else if (!idAccount.equals(other.idAccount))
            return false;
        return true;
    }

    public String getAccessibleTier() {
        return accessibleTier;
    }

    public void setAccessibleTier(String accessibleTier) {
        this.accessibleTier = accessibleTier;
    }

}