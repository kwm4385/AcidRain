
package com.escapeNT.acidRain;

import java.util.Arrays;
import java.util.List;

/**
 * Acid rain configuration.
 * @author escapeNT
 */
public class Config {


    private AcidRain instance;


    public Config(AcidRain ar) {
        this.instance = ar;
    }

    public static final String damageInterval = "Acid Rain Configuration.Damage Interval";
    public static final String rainDamage = "Acid Rain Configuration.Acid rain damage";
    public static final String acidRainChance = "Acid Rain Configuration.Acid rain chance (0-100)";
    public static final String dissolveBlockChance = "Acid Rain Configuration.Chance to dissolve block (0-100)";
    public static final String broadcastMessage = "Acid Rain Configuration.Broadcast message";
    public static final String dissolveBlocks = "Acid Rain Configuration.Rain dissolves blocks";
    public static final String rainMessage = "Acid Rain Configuration.Broadcast message text";
    public static final String worldsEnabled = "Acid Rain Configuration.Enabled Worlds";

    public void load() {
        instance.getConfig().options().copyDefaults(true);
        instance.saveConfig();
    }

}