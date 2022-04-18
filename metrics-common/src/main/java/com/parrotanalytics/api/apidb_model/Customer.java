package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the Customer database table.
 * 
 */
@Entity
@Table(name = "customer", schema = "subscription")
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private Integer idCustomer;

    private String companyName;

    private String primaryContactEmail;

    private String primaryContactName;

    private String primaryContactNumber;

    private Integer status;

    // bi-directional many-to-one association to Account
    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    /**
     * Instantiates a new customer.
     */
    public Customer()
    {
    }

    /**
     * @return the idCustomer
     */
    public Integer getIdCustomer()
    {
        return idCustomer;
    }

    /**
     * @param idCustomer
     *            the idCustomer to set
     */
    public void setIdCustomer(Integer idCustomer)
    {
        this.idCustomer = idCustomer;
    }

    /**
     * Gets the company name.
     *
     * @return the company name
     */
    public String getCompanyName()
    {
        return this.companyName;
    }

    /**
     * Sets the company name.
     *
     * @param companyName
     *            the new company name
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * Gets the primary contact email.
     *
     * @return the primary contact email
     */
    public String getPrimaryContactEmail()
    {
        return this.primaryContactEmail;
    }

    /**
     * Sets the primary contact email.
     *
     * @param primaryContactEmail
     *            the new primary contact email
     */
    public void setPrimaryContactEmail(String primaryContactEmail)
    {
        this.primaryContactEmail = primaryContactEmail;
    }

    /**
     * Gets the primary contact name.
     *
     * @return the primary contact name
     */
    public String getPrimaryContactName()
    {
        return this.primaryContactName;
    }

    /**
     * Sets the primary contact name.
     *
     * @param primaryContactName
     *            the new primary contact name
     */
    public void setPrimaryContactName(String primaryContactName)
    {
        this.primaryContactName = primaryContactName;
    }

    /**
     * Gets the primary contact number.
     *
     * @return the primary contact number
     */
    public String getPrimaryContactNumber()
    {
        return this.primaryContactNumber;
    }

    /**
     * Sets the primary contact number.
     *
     * @param primaryContactNumber
     *            the new primary contact number
     */
    public void setPrimaryContactNumber(String primaryContactNumber)
    {
        this.primaryContactNumber = primaryContactNumber;
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
     * Gets the accounts.
     *
     * @return the accounts
     */
    public List<Account> getAccounts()
    {
        return this.accounts;
    }

    /**
     * Sets the accounts.
     *
     * @param accounts
     *            the new accounts
     */
    public void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }

    /**
     * Adds the account.
     *
     * @param account
     *            the account
     * @return the account
     */
    public Account addAccount(Account account)
    {
        getAccounts().add(account);
        account.setCustomer(this);

        return account;
    }

    /**
     * Removes the account.
     *
     * @param account
     *            the account
     * @return the account
     */
    public Account removeAccount(Account account)
    {
        getAccounts().remove(account);
        account.setCustomer(null);

        return account;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result + ((idCustomer == null) ? 0 : idCustomer.hashCode());
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
        Customer other = (Customer) obj;
        if (companyName == null)
        {
            if (other.companyName != null)
                return false;
        }
        else if (!companyName.equals(other.companyName))
            return false;
        if (idCustomer == null)
        {
            if (other.idCustomer != null)
                return false;
        }
        else if (!idCustomer.equals(other.idCustomer))
            return false;
        return true;
    }

}