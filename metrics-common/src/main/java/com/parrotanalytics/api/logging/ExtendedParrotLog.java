package com.parrotanalytics.api.logging;

import javax.validation.constraints.NotNull;

import com.parrotanalytics.logging.helpers.PL;

/**
 * The Class ExtendedParrotLog adds the message, level and class fields to the
 * regular ParrotLog.
 * 
 * @author chris
 * @author Jackson Lee
 */
public class ExtendedParrotLog extends PL
{
    private String message;

    private ExtendedParrotAuditLog auditLog;

    @NotNull
    private String level;

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public String getLevel()
    {
        return level;
    }

    /**
     * Sets the message.
     *
     * @param message
     *            the new message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Sets the level.
     *
     * @param level
     *            the new level
     */
    public void setLevel(String level)
    {
        this.level = level;
    }

    public ExtendedParrotAuditLog getAuditLog()
    {
        return auditLog;
    }

    public void setAuditLog(ExtendedParrotAuditLog auditLog)
    {
        this.auditLog = auditLog;
    }

}
