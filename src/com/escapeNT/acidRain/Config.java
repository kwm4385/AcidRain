
package com.escapeNT.acidRain;

import java.util.Arrays;
import java.util.List;

import org.bukkit.util.config.Configuration;

/**
 * Acid rain configuration.
 * @author escapeNT
 */
public class Config {

    private static Configuration c = Util.getPlugin().getConfiguration();

    private static int damageInterval;
    private static int rainDamage;
    private static int acidRainChance;
    private static int dissolveBlockChance;
    private static boolean broadcastMessage;
    private static boolean dissolveBlocks;
    private static String rainMessage;
    private static List<String> worldsEnabled;

    public static void load() {
        setRainMessage(c.getString("Broadcast message text", "Acid rain has begun in <world>"));
        setDissolveBlockChance(c.getInt("Chance to dissolve block (0-100)", 5));
        setDissolveBlocks(c.getBoolean("Rain dissolves blocks", true));
        setBroadcastMessage(c.getBoolean("Broadcast message", false));
        setDamageInterval(c.getInt("Damage interval", 1));
        setRainDamage(c.getInt("Acid rain damage", 1));
        setAcidRainChance(c.getInt("Acid rain chance (0-100)", 25));
        setWorldsEnabled(Arrays.asList(c.getString("Worlds enabled (separate with comma)", "world").split(",")));
        c.save();
    }

    public static void save() {
        c.setProperty("Broadcast message text", rainMessage);
        c.setProperty("Chance to dissolve block (0-100)", dissolveBlockChance);
        c.setProperty("Rain dissolves blocks", dissolveBlocks);
        c.setProperty("Broadcast message", broadcastMessage);
        c.setProperty("Damage interval", damageInterval);
        c.setProperty("Acid rain damage", rainDamage);
        c.setProperty("Acid rain chance (0-100)", acidRainChance);

        StringBuilder sb = new StringBuilder();
        for(String s : worldsEnabled) {
            sb.append(s);
            sb.append(",");
        }
        c.setProperty("Worlds enabled (separate with comma)", sb.toString());

        c.save();
    }

    /**
     * @param aDamageInterval the damageInterval to set
     */
    public static void setDamageInterval(int aDamageInterval) {
        damageInterval = aDamageInterval;
    }

    /**
     * @param aRainDamage the rainDamage to set
     */
    public static void setRainDamage(int aRainDamage) {
        rainDamage = aRainDamage;
    }

    /**
     * @param aAcidRainChance the acidRainChance to set
     */
    public static void setAcidRainChance(int aAcidRainChance) {
        acidRainChance = aAcidRainChance;
    }

    /**
     * @param aDissolveBlockChance the dissolveBlockChance to set
     */
    public static void setDissolveBlockChance(int aDissolveBlockChance) {
        dissolveBlockChance = aDissolveBlockChance;
    }

    /**
     * @param aBroadcastMessage the broadcastMessage to set
     */
    public static void setBroadcastMessage(boolean aBroadcastMessage) {
        broadcastMessage = aBroadcastMessage;
    }

    /**
     * @param aDissolveBlocks the dissolveBlocks to set
     */
    public static void setDissolveBlocks(boolean aDissolveBlocks) {
        dissolveBlocks = aDissolveBlocks;
    }

    /**
     * @param aRainMessage the rainMessage to set
     */
    public static void setRainMessage(String aRainMessage) {
        rainMessage = aRainMessage;
    }

    /**
     * @param aWorldsEnabled the worldsEnabled to set
     */
    public static void setWorldsEnabled(List<String> aWorldsEnabled) {
        worldsEnabled = aWorldsEnabled;
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

    /**
     * @return the rainMessage
     */
    public static String getRainMessage() {
        return rainMessage;
    }

    /**
     * @return the worldsEnabled
     */
    public static List<String> getWorldsEnabled() {
        return worldsEnabled;
    }
}