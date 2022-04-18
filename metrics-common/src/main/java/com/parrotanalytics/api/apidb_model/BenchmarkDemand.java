package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;

@Entity
@SqlResultSetMapping(name = "BenchmarkDemand", classes =
{
        @ConstructorResult(targetClass = BenchmarkDemand.class, columns =
        {
                @ColumnResult(name = "date", type = Date.class),

                @ColumnResult(name = "benchmark_de", type = Double.class),

                @ColumnResult(name = "market", type = String.class)
        })
})
@Table(name = "benchmark_demand", schema = "metrics")
public class BenchmarkDemand extends RDSBaseEntity
{
    private static final long serialVersionUID = 8180481539050907025L;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "benchmark_de")
    private double benchmark_de;

    @Column(name = "market")
    private String market;

    public BenchmarkDemand()
    {

    }

    public BenchmarkDemand(Date date, Double benchmark_de, String market)
    {
        this.date = date;
        this.benchmark_de = benchmark_de;
        this.market = market;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public double getBenchmark_de()
    {
        return benchmark_de;
    }

    public void setBenchmark_de(double benchmark_de)
    {
        this.benchmark_de = benchmark_de;
    }

    public String getMarket()
    {
        return market;
    }

    public void setMarket(String market)
    {
        this.market = market;
    }

}
