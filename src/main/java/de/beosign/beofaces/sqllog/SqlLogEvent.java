package de.beosign.beofaces.sqllog;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Event object for an SQL logging event.
 * 
 * @author florian
 */
public class SqlLogEvent {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, List<String>> operationsMap;

    /**
     * Creates an event object that can be used to call a {@link SqlLogEventListener}.
     * 
     * @param request http request
     * @param response http response
     * @param operationsMap a map from an operation to the list of associated statements with that operation.
     *            <code>operation</code> is one of: <code>select, insert, update, delete</code>
     */
    public SqlLogEvent(HttpServletRequest request, HttpServletResponse response, Map<String, List<String>> operationsMap) {
        this.request = request;
        this.response = response;
        this.operationsMap = operationsMap;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Map<String, List<String>> getOperationsMap() {
        return operationsMap;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}
