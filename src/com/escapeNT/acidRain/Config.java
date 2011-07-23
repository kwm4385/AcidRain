

package com.escapeNT.acidRain;



import java.util.Arrays;
import java.util.List;
import org.bukkit.util.config.Configuration;

/**
 *
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
        rainMessage = c.getString("Broadcast message text", "Acid rain has begun in <world>");
        dissolveBlockChance = c.getInt("Chance to dissolve block (0-100)", 5);
        dissolveBlocks = c.getBoolean("Rain dissolves blocks", true);
        broadcastMessage = c.getBoolean("Broadcast message", false);
        damageInterval = c.getInt("Damage interval", 1);
        rainDamage = c.getInt("Acid rain damage", 1);
        acidRainChance = c.getInt("Acid rain chance (0-100)", 25);
        worldsEnabled = Arrays.asList(c.getString("Worlds enabled (separate with space)", "world").split(" "));
        c.save();
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