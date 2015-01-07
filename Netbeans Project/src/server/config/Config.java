/*
 * Copyright © 2014 Chenghsi Inc. All rights reserved
 */
package server.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Loli, Last modification time : AM 11:57 2014/11/10
 */
public final class Config {

    public static String HTTP_SERVER_IP;
    public static final int HTTP_SERVER_PORT = 8080;
    public static final String SERVER_PATH = System.getProperty("user.dir");
    public static final String DB_DRIVER = "org.postgresql.Driver";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/ireckidb";
    public static final String DB_USER_NAME = "postgres";
    public static final String DB_PASSWORD = "a";
    public static boolean SERVER_IS_RUN = false;

    public Config() {
        HTTP_SERVER_IP = getIp();
    }

    String getIp() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
                String ip = br.readLine();
                return ip;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                    }
                }
            }
        } catch (Exception e) {
            return "Unknown";
        }
    }

}
