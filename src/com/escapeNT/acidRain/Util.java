
package com.escapeNT.acidRain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author escapeNT
 */
public class Util {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static AcidRain plugin;
    private static HashMap<World, Boolean> worldIsAcidRaining = new HashMap<World, Boolean>();
    private static List<Player> affectedPlayers = new ArrayList<Player>();

    // Debug on/off
    public static final boolean debugOn = false;

    /**
     * Logs an info message from the plugin to the console.
     * @param message The message to send.
     */
    public static void log(String message) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        s.append(AcidRain.PLUGIN_NAME);
        s.append("] ");
        s.append(message);
        log.log(Level.INFO, s.toString());
    }

    /**
     * Logs a message from the plugin to the console with the specified level..
     * @param level The log level.
     * @param message The message to send.
     */
    public static void log(Level level, String message) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        s.append(AcidRain.PLUGIN_NAME);
        s.append("] ");
        s.append(message);
        log.log(level, s.toString());
    }

    /**
     * Gets the non-air blocks from a given chunk.
     * @param c The chunk to get blocks from.
     * @return The list non-air of blocks in the chunk.
     */
    public static List<Block> getBlocks(Chunk c) {
        List<Block> l = new ArrayList<Block>();
        for(int y = 0; y <= 127; y++) {
            for(int x = 0; x <= 15; x++) {
                for(int z = 0; z <= 15; z++) {
                    Block b = c.getBlock(x, y, z);
                    if(b.getType() != Material.AIR) {
                        l.add(b);
                    }
                }
            }
        }
        return l;
    }
    
    public static void acidRainMessage(World w) {
        if(Config.willBroadcastMessage()) {
            Util.getPlugin().getServer().broadcastMessage(ChatColor.GRAY
                    + "Acid rain has begun in world " + w.getName() + "!");
        }
        Util.log("Acid rain has begun in world " + w.getName() + "!");
    }

    /**
     * Gets the current plugin instance.
     * @return the plugin
     */
    public static AcidRain getPlugin() {
        return plugin;
    }

    /**
     * Sets the current plugin instance.
     * @param aPlugin The plugin instance.
     */
    public static void setPlugin(AcidRain aPlugin) {
        plugin = aPlugin;
    }

    /**
     * @return the weatherState
     */
    public static HashMap<World, Boolean> getWorldIsAcidRaining() {
        return worldIsAcidRaining;
    }

    /**
     * @return the affectedPlayers
     */
    public static List<Player> getAffectedPlayers() {
        return affectedPlayers;
    }
}
