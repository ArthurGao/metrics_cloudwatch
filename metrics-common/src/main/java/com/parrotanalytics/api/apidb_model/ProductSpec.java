package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.JoinFetch;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "productspecs", schema = "subscription")
@NamedQuery(name = "ProductSpecs.findAll", query = "SELECT p FROM ProductSpec p")
@IdClass(value = ProductSpecPK.class)
public class ProductSpec implements Serializable
{

    private static final long serialVersionUID = 8124440584549236013L;

    @Id
    @Column(name = "idAccount")
    private Integer idAccount;

    @Id
    @Column(name = "idProduct")
    private Integer idProduct;

    @Id
    @Column(name = "specificationName")
    private String specificationName;

    @Column(name = "specificationValue")
    private String specificationValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFetch
    @JoinColumn(name = "idAccount", insertable = false, updatable = false)
    @JsonBackReference
    private Account account;

    // bi-directional many-to-one association to Product
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFetch
    @JoinColumn(name = "idProduct", insertable = false, updatable = false)
    private Product product;

    public ProductSpec()
    {
    }

    public ProductSpec(Integer idAccount, Integer idProduct, String specificationName, String specificationValue)
    {
        super();
        this.idAccount = idAccount;
        this.idProduct = idProduct;
        this.specificationName = specificationName;
        this.specificationValue = specificationValue;
    }

    public Integer getIdProduct()
    {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct)
    {
        this.idProduct = idProduct;
    }

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
    }

    public String getSpecificationName()
    {
        return specificationName;
    }

    public void setSpecificationName(String specificationName)
    {
        this.specificationName = specificationName;
    }

    public String getSpecificationValue()
    {
        return specificationValue;
    }

    public void setSpecificationValue(String specificationValue)
    {
        this.specificationValue = specificationValue;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

}