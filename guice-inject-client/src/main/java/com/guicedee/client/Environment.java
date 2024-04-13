package com.guicedee.client;

public class Environment
{
    public static String getProperty(String key, String defaultValue)
    {
        if (System.getProperty(key) == null)
        {
            if (System.getenv(key) == null)
            {
                System.setProperty(key, defaultValue);
            }else {
                System.setProperty(key, System.getenv(key));
            }
        }
        return System.getProperty(key);
    }

}
