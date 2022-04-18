package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.JoinFetch;

@Entity
@Table(name = "product", schema = "subscription")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final int EXTERNAL_API = 6;

    @Id
    private Integer idProduct;

    private String name;

    private String description;

    private String access_type;

    private Integer status;

    @OneToMany(mappedBy = "product")
    @JoinFetch
    private List<ProductSpec> productSpecs;

    public Product()
    {
    }

    public Integer getIdProduct()
    {
        return this.idProduct;
    }

    public void setIdProduct(Integer idProduct)
    {
        this.idProduct = idProduct;
    }

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

    public String getAccess_type()
    {
        return access_type;
    }

    public void setAccess_type(String access_type)
    {
        this.access_type = access_type;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name)
    {
        this.name = name;
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

}