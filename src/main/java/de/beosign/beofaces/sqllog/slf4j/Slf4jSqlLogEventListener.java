package de.beosign.beofaces.sqllog.slf4j;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beofaces.sqllog.SqlLogEvent;
import de.beosign.beofaces.sqllog.SqlLogEventListener;

/**
 * Uses SLF4J to log.
 * 
 * @author florian
 */
public class Slf4jSqlLogEventListener implements SqlLogEventListener {
    private static final Logger log = LoggerFactory.getLogger(Slf4jSqlLogEventListener.class);

    @Override
    public void logEvent(SqlLogEvent sqlLogEvent) {
        HttpServletRequest request = sqlLogEvent.getRequest();
        Map<String, List<String>> operationsMap = sqlLogEvent.getOperationsMap();

        operationsMap.entrySet().forEach(entry -> {
            String event = "GET";
            String source = "<null>";
            if (request.getParameter("javax.faces.partial.ajax") != null) {
                event = request.getParameter("javax.faces.partial.event");
                source = request.getParameter("javax.faces.source");
            }
            log.trace(request.getRequestURI().toString() + "(" + source + "," + event + "): " + entry.getKey() + ": " + entry.getValue().size());
        });

    }

}
