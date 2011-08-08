
package com.escapeNT.acidRain;


import com.escapeNT.acidRain.PailCompat.SettingsInterface;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import com.escapeNT.acidRain.tasks.BlockDissolveTask;
import com.escapeNT.acidRain.tasks.PlayerDamageTask;
import me.escapeNT.pail.Pail;

/**
 * AcidRain plugin class.
 * @author escapeNT
 */
public class AcidRain extends JavaPlugin {

    public static final String PLUGIN_NAME = "AcidRain";
    public static final String PLUGIN_VERSION = "1.2.7";

    public static final String IMMUNE_PERMISSION = "acidrain.immune";
    
    public static final int CHUNK_DISSOLVE_RATE = 8;

    @Override
    public void onEnable() {
        Util.setPlugin(this);
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

        pm.addPermission(new Permission(IMMUNE_PERMISSION, PermissionDefault.FALSE));

        // Start player damager
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerDamageTask(), 
                (long)(Config.getDamageInterval() * 20), (long)(Config.getDamageInterval() * 20));
        
        // Start block dissolver
        if(Config.willDissolveBlocks()) {
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BlockDissolveTask(), 600L, 1200L);
        }

        // Load GUI
        if(pm.getPlugin("Pail") != null) {
            ((Pail)pm.getPlugin("Pail")).loadInterfaceComponent("Acid Rain", new SettingsInterface());
        }

        // Finish
        Util.log("version " + PLUGIN_VERSION + " enabled.");
    }

    @Override
    public void onDisable() {
        Util.log("version " + PLUGIN_VERSION + " disabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	if(cmd.getName().equalsIgnoreCase("Acidrain")) {
            World w = null;
            try {
                if(!((Player)sender).isOp()) {
                    sender.sendMessage(ChatColor.RED + "You must be OP to do that!");
                    return false;
                }
                switch(args.length) {
                    case 0:
                        if(!(sender instanceof Player)) {
                            sender.sendMessage(ChatColor.RED + "You must specify a world if you aren't logged in!");
                            return false;
                        }
                        w = ((Player)sender).getWorld();
                        if(!Config.getWorldsEnabled().contains(w.getName())) {
                            ((Player)sender).sendMessage(ChatColor.RED + "Acid rain is not enabled for this world");
                            break;
                        } 
                        w.setStorm(true);
                        w.setWeatherDuration(12000);
                        Util.getWorldIsAcidRaining().put(w, Boolean.TRUE);
                        Util.acidRainMessage(w);
                        break;
                    case 1:
                        try {
                            w = getServer().getWorld(args[0]);
                        } catch(Exception ex) {
                            sender.sendMessage(ChatColor.RED + "World not found!");
                            return false;
                        }
                        if(!Config.getWorldsEnabled().contains(w.getName())) {
                            ((Player)sender).sendMessage(ChatColor.RED + "Acid rain is not enabled for this world");
                            break;
                        }
                        w.setStorm(true);
                        w.setWeatherDuration(12000);
                        Util.getWorldIsAcidRaining().put(w, Boolean.TRUE);
                        Util.acidRainMessage(w);
                        break;
                    case 2:
                        try {
                            w = getServer().getWorld(args[0]);
                            if(!Config.getWorldsEnabled().contains(w.getName())) {
                                ((Player)sender).sendMessage(ChatColor.RED + "Acid rain is not enabled for this world");
                                break;
                            }
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
                        Util.acidRainMessage(w);
                        break;
                }
            } catch(Exception ex) {
                if(Util.debugOn) {
                    ex.printStackTrace();
                }
                return false;
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