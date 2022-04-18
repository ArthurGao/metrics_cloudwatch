package com.parrotanalytics.api.commons.model;

public class AbstractDataPoint implements IDataPoint
{
    private static final long serialVersionUID = -7005958459363795231L;

    protected String market;

    protected Double value;

    protected String at;

    /**
     * @return the market
     */
    public String getMarket()
    {
        return market;
    }

    /**
     * @param market
     *            the market to set
     */
    public void setMarket(String market)
    {
        this.market = market;
    }

    /**
     * @return the value
     */
    public Double getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Double value)
    {
        this.value = value;
    }

    /**
     * @return the at
     */
    public String getAt()
    {
        return at;
    }

    /**
     * @param at
     *            the at to set
     */
    public void setAt(String at)
    {
        this.at = at;
    }

}
