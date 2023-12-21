package com.qa.utility;

import org.aeonbits.owner.ConfigCache;

public class ConfigFactory {
    public static ConfigReader configReader;
    static {
         configReader = ConfigCache.getOrCreate(ConfigReader.class);
    }
}
