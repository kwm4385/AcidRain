
package com.escapeNT.acidRain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
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
    
    public static final List<Material> AFFECTED_MATERIALS = Arrays.asList(new Material[] {
        Material.GRASS, 
        Material.LONG_GRASS,
        Material.LEAVES,
        Material.RED_ROSE,
        Material.YELLOW_FLOWER,
        Material.SOIL,
        Material.CROPS
    });

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
     * Dissolves the specified block.
     * @param b The block to dissolve.
     */
    public static void dissolveBlock(Block b) {
        switch(b.getType()) {
            case SOIL:
            case GRASS:
                b.setType(Material.DIRT);
                break;
            case CROPS:
            case LONG_GRASS:
            case RED_ROSE:
            case YELLOW_FLOWER:
                b.setType(Material.DEAD_BUSH);
                break;  
            case LEAVES:
                b.setType(Material.AIR);
                break;
        }
        if(Util.debugOn) {
            Util.log("Block dissolved.");
        }
    }
    
    public static void acidRainMessage(World w) {
        HashMap<String, ChatColor> color = new HashMap<String, ChatColor>();
        color.put("&0", ChatColor.BLACK);
        color.put("&1", ChatColor.DARK_BLUE);
        color.put("&2", ChatColor.DARK_GREEN);
        color.put("&3", ChatColor.DARK_PURPLE);
        color.put("&4", ChatColor.DARK_RED);
        color.put("&5", ChatColor.DARK_PURPLE);
        color.put("&6", ChatColor.GOLD);
        color.put("&7", ChatColor.GRAY);
        color.put("&8", ChatColor.DARK_GRAY);
        color.put("&9", ChatColor.BLUE);
        color.put("&a", ChatColor.GREEN);
        color.put("&b", ChatColor.BLUE);
        color.put("&c", ChatColor.RED);
        color.put("&d", ChatColor.LIGHT_PURPLE);
        color.put("&e", ChatColor.YELLOW);
        color.put("&f", ChatColor.WHITE);  
        
        String message = Config.getRainMessage().replace("<world>", w.getName());
        ChatColor col = ChatColor.GRAY;
        for(String s : color.keySet()) {
            if(message.contains(s)) {
                message = message.replace(s, "");
                col = color.get(s);
                break;
            }
        }
        if(Config.willBroadcastMessage()) {
            Util.getPlugin().getServer().broadcastMessage(col + message);
        }
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