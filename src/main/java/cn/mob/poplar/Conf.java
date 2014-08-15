package cn.mob.poplar;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * @version 1.0 date: 2014/8/15
 * @author: Dempe
 */
public class Conf {
    private static final Logger LOGGER = Logger.getLogger(Conf.class);
    private static PropertiesConfiguration conf;

    static {
        try {
            conf = new PropertiesConfiguration("application.properties");
        } catch (ConfigurationException e) {
            LOGGER.error("cannot found applicaton.properties in classpath", e);
        }
    }

    public static String getString(String key) {
        return conf.getString(key);
    }

    public static String getString(String key, String defaultValue) {
        return conf.getString(key, defaultValue);
    }

}
