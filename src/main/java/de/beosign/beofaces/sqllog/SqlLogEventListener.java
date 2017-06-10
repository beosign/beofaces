package de.beosign.beofaces.sqllog;

/**
 * Listens to SQL logging events.
 * 
 * @author florian
 */
public interface SqlLogEventListener {
    /**
     * Called at the end of a JSF request. Implementations can now log the executed sql statements.
     * 
     * @param sqlLogEvent event object
     */
    void logEvent(SqlLogEvent sqlLogEvent);
}
