package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import javax.persistence.Id;

public class ReportUserPK
{
    @Id
    @Column(name = "idReport")
    private Integer idReport;

    @Id
    @Column(name = "idUser")
    private Integer idUser;

    public Integer getIdReport()
    {
        return idReport;
    }

    public void setIdReport(Integer idReport)
    {
        this.idReport = idReport;
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
    }
}
