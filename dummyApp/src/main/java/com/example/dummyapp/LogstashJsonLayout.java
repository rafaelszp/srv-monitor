/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dummyapp;

import com.google.gson.Gson;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author rafael
 */
public class LogstashJsonLayout extends Layout {

    private final Gson gson = new Gson();// new GsonBuilder().create();
    private final String hostname = getHostname().toLowerCase();
    private final String username = System.getProperty("user.name").toLowerCase();

    @Override
    public String format(LoggingEvent le) {
        Map<String, Object> message = new LinkedHashMap<>();

        //message.put("@timestamp", le.timeStamp);
        message.put("hostname", hostname);
        message.put("username", username);
        message.put("level", le.getLevel().toString());
        message.put("thread", le.getThreadName());

        if (le.getMessage() instanceof Map) {
            message.putAll((Map<? extends String, ? extends Object>) le.getMessage());

        } else {
            message.put("message", null != le.getMessage() ? le.getMessage().toString() : null);
        }

        if (le.getThrowableStrRep() != null) {
            StringBuilder sbTrace = new StringBuilder();
            for (String trc : le.getThrowableStrRep()) {
                sbTrace.append(trc);
            }

            message.put("stacktrace", sbTrace.toString().replaceAll("\\t", "\n"));
        } else {
            try{
                Throwable ex = (Throwable) le.getMessage();
                message.put("stacktrace", errorString(ex));
            }catch(Throwable exc){
                message.put("stacktrace", "NAO FOI POSSIVEL EXTRAIR O MOTIVO DO ERRO POIS O PARAMETRO INFORMADO NA ROTINA DE LOG NAO PARECE SER IMPLENTACAO DE THROWABLE");
            }             
        }

        return gson.toJson(message) + "\n";
    }

    private static String getHostname() {
        String hostname;
        try {
            hostname = java.net.InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            hostname = "Unknown, " + e.getMessage();
        }
        return hostname;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {
    }

    public static String errorString(final Throwable e) {
        try {
            StackTraceElement[] stack = e.getStackTrace();
            final StringBuilder sbStack = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                sbStack.append(stack[i]+"\n");
            }
            stack = null;
            return sbStack.toString();
        } catch (final Exception er) {
        }
        return e.getMessage();
    }
}
