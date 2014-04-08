package ru.elweb.mapsapp.core.util.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Semyon Danilov on 08.04.2014.
 */
public class HttpQuery {

    private Map<String, String> queryParamsMap = null;
    private String query;

    private HttpQuery(final String query, final Map<String, String> queryParamsMap) {
        this.queryParamsMap = queryParamsMap;
        this.query = query;
    }

    public String getRequestParam(final String name) {
        return queryParamsMap.get(name);
    }

    public static HttpQuery parse(final String _query) throws MalformedQueryException {
        String query = _query;
        Map<String, String> queryParamsMap = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return null;
        }
        int index = query.indexOf("?");
        query = query.substring(index + 1);
        String[] keysWithValues = query.split("&");
        for (int i = 0; i < keysWithValues.length; i++) {
            String[] keyAndValue = keysWithValues[i].split("=");
            if (keyAndValue.length != 2) {
                throw new MalformedQueryException("Query is malformed, exception on: [" + keysWithValues[i] + "]");
            }
            queryParamsMap.put(keyAndValue[0], keyAndValue[1]);
        }
        return new HttpQuery(query, queryParamsMap);
    }

    @Override
    public String toString() {
        String out = "{";
        Set<Map.Entry<String, String>> entrySet = queryParamsMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        Map.Entry<String, String> entry = iterator.next();
        while (entry != null) {
            out += entry.getKey() + " : " + entry.getValue();
            if (iterator.hasNext()) {
                out += ", ";
            } else {
                break;
            }
            entry = iterator.next();
        }
        out += "}";
        return out;
    }
}
