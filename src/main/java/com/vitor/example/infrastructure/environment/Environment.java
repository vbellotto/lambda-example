package com.vitor.example.infrastructure.environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility methods to handle environment settings.
 *
 */
public final class Environment {

    private static final String REGION = "REGION";
    private static final String LOCAL_REGION = "env.region";
    private static final String TABLE = "TABLE";
    private static final String LOCAL_TABLE = "env.table";

    private static Properties localConfigurations;

    static {
        localConfigurations = new Properties();
        try {
            InputStream input = Environment.class.getClassLoader().getResourceAsStream("config.properties");
            localConfigurations.load(input);
        } catch (final IOException ioException) {
            throw new IllegalStateException("Can't load localConfigurations file.", ioException);
        }
    }

    private Environment() {
    }

    /**
     * Get the current region configuration.
     *
     * @return AWS region in String representation
     */
    public static String getRegion() {
        return getParameter(REGION, LOCAL_REGION);
    }

    public static String getTableName(){
        return getParameter(TABLE, LOCAL_TABLE);
    }

    private static String getParameter(final String envName, final String configName) {
        return System.getenv(envName) != null ? System.getenv(envName) : localConfigurations.getProperty(configName);
    }
}
