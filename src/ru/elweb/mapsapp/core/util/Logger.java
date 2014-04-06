package ru.elweb.mapsapp.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Semyon Danilov on 06.04.2014.
 */
public class Logger {

    private String className = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm:ss");

    public Logger(final String className) {
        this.className = className;
    }

    public void log(final String message) {
        log(null, message);
    }

    public void log(final String tag, final String message) {
        Date date = new Date();
        String dateString = sdf.format(date);
        String logMsg = "[" + "]"+ dateString + (tag == null ? "" : ("[" + tag + "]")) + ": " + message;
        System.out.println(logMsg);
    }

    public void error(final String errorMessage) {
        log("ERROR", errorMessage);
    }

    public static Logger getLogger(final Class className) {
        return new Logger(className.getSimpleName());
    }

}
