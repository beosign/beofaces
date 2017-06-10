package de.beosign.beofaces.sqllog;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface SqlLogEventListener {
    void logEvent(HttpServletRequest request, Map<String, List<String>> operationsMap);
}
