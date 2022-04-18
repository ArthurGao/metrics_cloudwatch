package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parrotanalytics.datasourceint.dsdb.model.base.GroupedDataEntity;
import com.rits.cloning.Cloner;

@Entity
@SqlResultSetMapping(name = "CustomParrotRating", classes =
{
        @ConstructorResult(targetClass = CustomDemandExpressions.class, columns =
        {
                @ColumnResult(name = "date", type = Date.class), @ColumnResult(name = "cid", type = Long.class),
                @ColumnResult(name = "PDR", type = Double.class),
                @ColumnResult(name = "lastDayPDR", type = Double.class),
                @ColumnResult(name = "PDE", type = Double.class),
                @ColumnResult(name = "lastDayPDE", type = Double.class)
        })
})
public class CustomDemandExpressions extends GroupedDataEntity
{
    @Column
    private long shortId;

    @Temporal(TemporalType.DATE)
    @Column
    private Date date;

    @Column
    private double PDE;

    @Column
    private double lastDayPDE;

    public CustomDemandExpressions()
    {
        super();
    }

    public CustomDemandExpressions(Date date, long shortId, double pDE, double lastDayPDE)
    {
        this.date = date;
        this.shortId = shortId;
        this.PDE = pDE;
        this.lastDayPDE = lastDayPDE;
    }

    /**
     * gets the short id
     * 
     * @return
     */
    public long getShortId()
    {
        return shortId;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * @return the pDE
     */
    public double getPDE()
    {
        return PDE;
    }

    /**
     * @param pDE
     *            the pDE to set
     */
    public void setPDE(double pDE)
    {
        PDE = pDE;
    }

    /**
     * @return the lastDayPDE
     */
    public double getLastDayPDE()
    {
        return lastDayPDE;
    }

    /**
     * @param lastDayPDE
     *            the lastDayPDE to set
     */
    public void setLastDayPDE(double lastDayPDE)
    {
        this.lastDayPDE = lastDayPDE;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(PDE);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        temp = Double.doubleToLongBits(lastDayPDE);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = (int) (prime * result + shortId);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomDemandExpressions other = (CustomDemandExpressions) obj;
        if (Double.doubleToLongBits(PDE) != Double.doubleToLongBits(other.PDE))
            return false;
        if (date == null)
        {
            if (other.date != null)
                return false;
        }
        else if (!date.equals(other.date))
            return false;
        if (Double.doubleToLongBits(lastDayPDE) != Double.doubleToLongBits(other.lastDayPDE))
            return false;
        if (shortId != other.shortId)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public CustomDemandExpressions clone()
    {
        return new Cloner().deepClone(this);
    }
}
