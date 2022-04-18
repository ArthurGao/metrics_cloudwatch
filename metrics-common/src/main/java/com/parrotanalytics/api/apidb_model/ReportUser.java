package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;

@Entity
@Table(name = "report_user", schema = "portal")
@NamedQuery(name = "ReportUser.findAll", query = "SELECT a FROM ReportUser a")
@IdClass(value = ReportUserPK.class)
public class ReportUser extends RDSBaseEntity
{

    @Id
    @Column(name = "idReport")
    private Integer idReport;

    @Id
    @Column(name = "idUser")
    private Integer idUser;

    @Column(name = "mode")
    private Integer mode;

    public ReportUser(Integer idReport, Integer idUser, Integer mode)
    {
        super();
        this.idReport = idReport;
        this.idUser = idUser;
        this.mode = mode;
    }

    public ReportUser()
    {

    }

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

    public Integer getMode()
    {
        return mode;
    }

    public void setMode(Integer mode)
    {
        this.mode = mode;
    }
}
