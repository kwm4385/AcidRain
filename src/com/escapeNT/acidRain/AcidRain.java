

package com.escapeNT.acidRain;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

        // Config
        if(!Config.settingsFile.exists()) {
            Config.create();
        }
        Config.load();
        
        for(World w : this.getServer().getWorlds()) {
            Util.getWorldIsAcidRaining().put(w, Boolean.FALSE);
        }

        // Register events
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
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	if(cmd.getName().equalsIgnoreCase("Acidrain")) {
            World w;
            switch(args.length) {
                case 0:
                    if(!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "You must specify a world if you aren't logged in!");
                        return false;
                    }
                    w = ((Player)sender).getWorld();
                    w.setStorm(true);
                    w.setWeatherDuration(12000);
                    Util.getWorldIsAcidRaining().put(w, Boolean.TRUE);
                    break;
                case 1:
                    try {
                        w = getServer().getWorld(args[0]);
                    } catch(Exception ex) {
                        sender.sendMessage(ChatColor.RED + "World not found!");
                        return false;
                    }
                    w.setStorm(true);
                    w.setWeatherDuration(12000);
                    Util.getWorldIsAcidRaining().put(w, Boolean.TRUE);
                    break;
                case 2:
                    try {
                        w = getServer().getWorld(args[0]);
                        w.setStorm(true);
                        w.setWeatherDuration(Integer.parseInt(args[1]));
                    } catch(NumberFormatException ex) {
                        sender.sendMessage(ChatColor.RED + "Duration is not a number!");
                        return false;
                    } catch(Exception ex) {
                        sender.sendMessage(ChatColor.RED + "World not found!");
                        return false;
                    }
                    Util.getWorldIsAcidRaining().put(w, Boolean.TRUE);
            }
            return true;
	}
	return false; 
    }

    // API ----------------------------------------------------------

    /**
     * Gets whether the given world currently has acid rain.
     * @param world The world to check.
     * @return True if the world currently has acid rain.
     */
    public boolean isAcidRaining(World world) {
        if(Util.getWorldIsAcidRaining().containsKey(world)) {
            return Util.getWorldIsAcidRaining().get(world);
        }
        else {
            return false;
        }
    }
}