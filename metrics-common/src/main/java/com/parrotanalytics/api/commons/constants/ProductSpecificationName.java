package com.parrotanalytics.api.commons.constants;

/**
 * Rating type for parrotratings API endpoint
 * 
 * @author Sanjeev Sharma
 *
 */
public enum ProductSpecificationName
{

    TITLE("titles"), MARKET("markets"), USER("users"), PROJECTHOURS("projecthours"), SUBSCRIPTIONLENGTH(
            "subscriptionlength"), CONSULTANTTYPE("consultanttype"), CARRYOVER(
                    "carryover"), NONCARRYOVER("noncarryover"), MONTHLY("monthly"), YEARLY("yearly"), MODULE("modules");

    private String value;

    private ProductSpecificationName(String type)
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

    public static ProductSpecificationName fromValue(String value)
    {
        if (value != null)
        {
            for (ProductSpecificationName instance : ProductSpecificationName.values())
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
