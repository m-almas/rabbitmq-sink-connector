package com.buhta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class VersionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionUtil.class);
    private static String VERSION = "unknown";

    static {
        try {
            Properties props = new Properties();
            props.load(VersionUtil.class.getResourceAsStream("/rabbitmq-sink-connector-version.properties"));
            VERSION = props.getProperty("version", VERSION).trim();
        } catch (Exception e) {
            LOGGER.warn("error while loading version:", e);
        }
    }

    public static String getVersion() {
        return VERSION;
    }

}
