package com.parrotanalytics.api.commons;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.parrotanalytics.api.apidb_model.nonmanaged.PortfolioType;

@Converter(autoApply = true)
public class PortfolioTypeConverter implements AttributeConverter<PortfolioType, String>
{

    @Override
    public String convertToDatabaseColumn(PortfolioType attribute)
    {
        return attribute.value();
    }

    @Override
    public PortfolioType convertToEntityAttribute(String dbData)
    {
        return PortfolioType.fromValue(dbData);
    }

}
