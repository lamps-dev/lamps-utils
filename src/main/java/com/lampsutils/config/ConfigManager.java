package com.lampsutils.config;

public final class ConfigManager {
    private ConfigManager() {}

    public static final com.lampsutils.config.LampsUtilsConfigWrapper CONFIG = com.lampsutils.config.LampsUtilsConfigWrapper.createAndLoad();
}
