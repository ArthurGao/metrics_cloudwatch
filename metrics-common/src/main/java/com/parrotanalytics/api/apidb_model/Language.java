package com.parrotanalytics.api.apidb_model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the Languages database table.
 */
@Entity
@Table(name = "languages", schema = "subscription")
public class Language implements Cloneable, Serializable
{

    @Id
    @Column(name = "idLanguages")
    private Integer idLanguages;

    @Column(name = "language_name")
    private String language_name;

    @Column(name = "language_code")
    private String language_code;

    public Language()
    {

    }

    public Integer getIdLanguages()
    {
        return idLanguages;
    }

    public void setIdLanguages(Integer idLanguages)
    {
        this.idLanguages = idLanguages;
    }

    public String getLanguage_name()
    {
        return language_name;
    }

    public void setLanguage_name(String language_name)
    {
        this.language_name = language_name;
    }

    public String getLanguage_code()
    {
        return language_code;
    }

    public void setLanguage_code(String language_code)
    {
        this.language_code = language_code;
    }
}
