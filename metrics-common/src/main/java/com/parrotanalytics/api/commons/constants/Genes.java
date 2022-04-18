package com.parrotanalytics.api.commons.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author sanjeev
 *
 */
public enum Genes
{
    GENRE("genre"),

    SUBGENRE("subgenre"),

    NETWORK("network"),

    COUNTRY("country");

    private String value;

    private Genes(String type)
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

    public static Genes fromValue(String value)
    {
        if (value != null)
        {
            for (Genes instance : Genes.values())
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

    public static List<String> supportedGenes()
    {
        List<Genes> supportedGenes = Arrays.asList(Genes.values());

        return supportedGenes.stream().map(gene -> gene.value).collect(Collectors.toList());
    }
}
