package ru.elweb.mapsapp.core.util;

/**
 * Created by Semyon Danilov on 08.04.2014.
 */
public final class Util {

    public static final String extractQuery(final String header) {
        String uri = extract(header, "GET ", " ");
        return uri;
    }

    private static final String extract(final String str, final String start, final String end) {
        String _str = str;
        int s = _str.indexOf("\n\n", 0), e;
        if (s < 0) {
            s = _str.indexOf("\r\n\r\n", 0);
        }
        if (s > 0) {
            _str = str.substring(0, s);
        }
        s = _str.indexOf(start, 0) + start.length();
        if (s < start.length()) {
            return null;
        }
        e = _str.indexOf(end, s);
        if (e < 0) {
            e = _str.length();
        }
        return (_str.substring(s, e)).trim();
    }

}
