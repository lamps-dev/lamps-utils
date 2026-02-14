package com.lampsutils.config;
import io.wispforest.owo.config.annotation.RangeConstraint;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import static com.lampsutils.LampsUtils.MOD_ID;

@Modmenu(modId = MOD_ID)
@Config(name = "lamps-utils", wrapperName = "LampsUtilsConfigWrapper")
public class LampsUtilsConfig {
    public boolean showHttpWarning = true;

    @RangeConstraint(min = 100, max = 5000)
    public int maxTextLimit = 300;
}