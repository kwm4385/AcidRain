
package com.escapeNT.acidRain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
    public static final String PLUGIN_VERSION = "1.2.4";
    
    private static final int CHUNK_DISSOLVE_RATE = 8;

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
        
        // Start block dissolver
        if(Config.willDissolveBlocks()) {
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    Random r = new Random();
                    for(World w : getServer().getWorlds()) {
                        if(Util.getWorldIsAcidRaining().get(w) != null && Util.getWorldIsAcidRaining().get(w)) {    
                            List<Chunk> chunksAffected = new ArrayList<Chunk>();
                            for(Chunk c : w.getLoadedChunks()) {
                                int randInt = r.nextInt(100);
                                if(randInt <= CHUNK_DISSOLVE_RATE) {
                                    chunksAffected.add(c);
                                }
                            }
                            for(Chunk c : chunksAffected) {
                                if(Util.debugOn) {
                                    Util.log("Dissolve sweep for chunk" + c);
                                }
                                for(int x = 0; x <= 15; x++) {
                                    for(int z = 0; z <= 15; z++) {
                                        for(int y = 126; y > 0; y--) {
                                            Block b = c.getBlock(x, y, z);
                                            if(Util.AFFECTED_MATERIALS.contains(b.getType())) {
                                                int randInt = r.nextInt(100);
                                                if(randInt <= Config.getDissolveBlockChance()) {
                                                    Util.dissolveBlock(b); 
                                                }
                                                break;
                                            }
                                            else if(b.getType() != Material.AIR) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            } 
                        }
                    }
                }
            }, 600L, 1200L);
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