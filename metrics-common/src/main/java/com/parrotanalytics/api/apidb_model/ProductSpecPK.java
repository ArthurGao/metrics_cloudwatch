package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;

public class ProductSpecPK implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -563449201649711356L;

    @Column(name = "idAccount")
    private Integer idAccount;

    @Column(name = "idProduct")
    private Integer idProduct;

    @Column(name = "specificationName")
    private String specificationName;

    public ProductSpecPK()
    {
    }

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
    }

    public Integer getIdProduct()
    {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct)
    {
        this.idProduct = idProduct;
    }

    public String getSpecificationName()
    {
        return specificationName;
    }

    public void setSpecificationName(String specificationName)
    {
        this.specificationName = specificationName;
    }

}
