package com.guicedee.client;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Environment {
    public static String getProperty(String key, String defaultValue) {
        if (System.getProperty(key) == null) {
            if (System.getenv(key) == null) {
                System.setProperty(key, defaultValue);
            } else {
                System.setProperty(key, System.getenv(key));
            }
        }
        return System.getProperty(key);
    }

    /**
     * Returns an environment or system defined property with a default value
     *
     * System Defined Properties (-Dxxx=xxx) override environment variables
     *
     * @param name         The name of the variable
     * @param defaultValue The default value to always return
     * @return The required value from the environment
     */
    public static String getSystemPropertyOrEnvironment(String name, String defaultValue) {
        if (System.getProperty(name) != null) {
            return System.getProperty(name);
        }
        if (System.getenv(name) != null) {
            try {
                System.setProperty(name, System.getenv(name));
                return System.getProperty(name);
            } catch (Exception T) {
                log.debug("⚠️ Couldn't set system property value from environment - Name: '{}', Default: '{}'", 
                        name, defaultValue, T);
                return System.getenv(name);
            }
        } else {
            if (defaultValue == null) {
                return "";
            }
            log.debug("📋 Using default value for property - Name: '{}', Value: '{}'", name, defaultValue);
            try {
                System.setProperty(name, defaultValue);
                return System.getProperty(name);
            } catch (Exception T) {
                log.debug("⚠️ Couldn't set system property to default value - Name: '{}', Value: '{}'", 
                        name, defaultValue, T);
                return defaultValue;
            }
        }
    }

}
