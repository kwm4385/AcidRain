

package com.escapeNT.acidRain;

import java.io.File;

import org.bukkit.util.config.Configuration;

/**
 *
 * @author escapeNT
 */
public class Config {

    private static final String mainDirectory = "plugins/AcidRain";
    public static final File settingsFile = new File(mainDirectory + File.separator + "settings.yml");
    private static Configuration c = new Configuration(settingsFile);

    private static int damageInterval;
    private static int rainDamage;
    private static int acidRainChance;
    private static int dissolveBlockChance;
    private static boolean broadcastMessage;
    private static boolean dissolveBlocks;

    public static void create() {
        new File(mainDirectory).mkdir();
        //c.setProperty("Chance to dissove block (0-100)", 5);
        //c.setProperty("Rain dissolves blocks", true);
        c.setProperty("Broadcast message", false);
        c.setProperty("Damage interval", 1);
        c.setProperty("Acid rain damage", 1);
        c.setProperty("Acid rain chance (0-100)", 25);
        c.save();
    }

    public static void load() {
        c.load();
        //dissolveBlockChance = ((Integer)c.getProperty("Chance to dissove block (0-100)")).intValue();
        //dissolveBlocks = ((Boolean)c.getProperty("Rain dissolves blocks")).booleanValue();
        broadcastMessage = ((Boolean)c.getProperty("Broadcast message")).booleanValue();
        damageInterval = ((Integer)c.getProperty("Damage interval")).intValue();
        rainDamage = ((Integer)c.getProperty("Acid rain damage")).intValue();
        acidRainChance = ((Integer)c.getProperty("Acid rain chance (0-100)")).intValue(); 
    }

    /**
     * @return the damageInterval
     */
    public static int getDamageInterval() {
        return damageInterval;
    }

    /**
     * @return the rainDamage
     */
    public static int getRainDamage() {
        return rainDamage;
    }

    /**
     * @return the acidRainChance
     */
    public static int getAcidRainChance() {
        return acidRainChance;
    }

    /**
     * @return the willBroadcastMessage
     */
    public static boolean willBroadcastMessage() {
        return broadcastMessage;
    }

    /**
     * @return the dissolveBlocks
     */
    public static boolean willDissolveBlocks() {
        return dissolveBlocks;
    }

    /**
     * @return the dissolveBlockChance
     */
    public static int getDissolveBlockChance() {
        return dissolveBlockChance;
    }
}