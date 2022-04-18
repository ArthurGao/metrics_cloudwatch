package com.parrotanalytics.api.request.markets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * REST API {@link Endpoint} create user request.
 * 
 * @author Jackson Lee
 *
 */
public class MarketsRequest extends APIRequest
{

    protected OperationType marketsAction;

    @JsonProperty("market_id")
    protected Integer marketId;

    @JsonProperty("iso")
    protected String iso;

    public OperationType getMarketsAction()
    {
        return marketsAction;
    }

    public void setMarketsAction(OperationType marketsAction)
    {
        this.marketsAction = marketsAction;
    }

    public Integer getMarketId()
    {
        return marketId;
    }

    public void setMarketId(Integer marketId)
    {
        this.marketId = marketId;
    }

    public String getIso()
    {
        return iso;
    }

    public void setIso(String iso)
    {
        this.iso = iso;
    }

}
