package com.parrotanalytics.api.commons.constants;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public enum PortfolioQueryType
{
    PORTFOLIOS("portfolios"), PORTFOLIOS_ITEMS("portfolios_items");

    private String value;

    private PortfolioQueryType(String type)
    {
        this.value = type;
    }

    /**
     * Value.
     * 
     * @return the string
     */
    public String value()
    {
        return this.value;
    }

    public static PortfolioQueryType fromValue(String value)
    {
        if (value != null)
        {
            for (PortfolioQueryType instance : PortfolioQueryType.values())
            {
                if (value.equalsIgnoreCase(instance.value()))
                {
                    return instance;
                }
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return this.value;
    }
}
