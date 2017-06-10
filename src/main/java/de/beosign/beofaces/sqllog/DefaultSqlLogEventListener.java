package de.beosign.beofaces.sqllog;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSqlLogEventListener implements SqlLogEventListener {
    private static final Logger log = LoggerFactory.getLogger(DefaultSqlLogEventListener.class);

    @Override
    public void logEvent(HttpServletRequest request, Map<String, List<String>> operationsMap) {
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
