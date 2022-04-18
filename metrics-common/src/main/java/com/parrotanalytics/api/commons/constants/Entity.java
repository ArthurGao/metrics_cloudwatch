package com.parrotanalytics.api.commons.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Rating type for parrotratings API endpoint
 *
 * @author Raja
 * @author minhvu
 *
 */
public enum Entity
{
	// @formatter:off

    TV_SERIES("tvseries"),

    PORTFOLIO("portfolio"),

	  SHOW("show"),

    TALENT("talent"),

    MOVIE("movie");

    // @formatter:on
	private String value;

	private Entity(String type)
	{
		this.value = type;
	}

	@JsonValue
	public String value()
	{
		return this.value;
	}

	@JsonCreator
	public static Entity fromValue(String value)
	{
		if (value != null)
		{
			for (Entity instance : Entity.values())
			{
				if (value.equalsIgnoreCase(instance.value()))
				{
					return instance;
				}
			}
		}
		//default to TVSeries by default
		return TV_SERIES;
	}

	@Override
	public String toString()
	{
		return this.value;
	}

}
