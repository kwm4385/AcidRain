

package com.escapeNT.acidRain;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AcidRain plugin class.
 * @author escapeNT
 */
public class AcidRain extends JavaPlugin {

    public static final String PLUGIN_NAME = "AcidRain";
    public static final double PLUGIN_VERSION = 1.0;

    @Override
    public void onEnable() {
        Util.setPlugin(this);

        if(!Config.settingsFile.exists()) {
            Config.create();
        }
        Config.load();
        
        for(World w : this.getServer().getWorlds()) {
            Util.getWorldIsAcidRaining().put(w, Boolean.FALSE);
        }

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Type.WEATHER_CHANGE, new AcidRainWeatherListener(), Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_MOVE, new AcidRainPlayerListener(), Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_KICK, new AcidRainPlayerListener(), Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_QUIT, new AcidRainPlayerListener(), Priority.Monitor, this);
        //pm.registerEvent(Type.CHUNK_LOAD, new AcidRainWorldListener(), Priority.Monitor, this);

        // Start player damager
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for(Player p : Util.getAffectedPlayers()) {
                    p.damage(Config.getRainDamage());
                    if(Util.debugOn) {
                        Util.log("Dissolving player " + p.getDisplayName());
                    }
                }
            }
        }, (long)(Config.getDamageInterval() * 20), (long)(Config.getDamageInterval() * 20));

        Util.log("version " + PLUGIN_VERSION + " enabled.");
    }

    @Override
    public void onDisable() {
        Util.log("disabled.");
    }

    // API ----------------------------------------------------------

    /**
     * Gets whether the given world currently has acid rain.
     * @param world The world to check.
     * @return True if the world currently has acid rain.
     */
    public boolean isAcidRaining(World world) {
        if(!Util.getWorldIsAcidRaining().containsKey(world)) {
            return false;
        }
        else {
            return Util.getWorldIsAcidRaining().get(world);
        }
    }
}