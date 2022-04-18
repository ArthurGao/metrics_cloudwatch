package com.parrotanalytics.api.request.customersuccess;

/**
 * The persistent class for the Role database table.
 * 
 */

public class AskNicelyUserRequest
{
    private String firstname;

    private String lastname;

    private String name;

    private String email;

    private String title;

    private String department;

    private String account;

    private String segment;

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getSegment()
    {
        return segment;
    }

    public void setSegment(String segment)
    {
        this.segment = segment;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}