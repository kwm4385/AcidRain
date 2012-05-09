

package com.escapeNT.acidRain.Listeners;

import com.escapeNT.acidRain.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author escapeNT
 */
public class AcidRainPlayerListener implements Listener {

    /**
     * Tests if a player is outdoors (has all air over their head).
     * @param player THe player to test.
     * @return True if the player is outdoors.
     */
    private boolean isOutdoors(Player player) {
        boolean o = true;
        World w = player.getWorld();
        Location l = player.getLocation();

        //The world height is higher than this, but I'm thinking stopping here would make it seem more
        //realistic.  This way the player is unaffected above clouds, and down below they won't have protection
        //if the blocks are way too high in the air.  Accounts for realistic wind.
        for(int locY = player.getLocation().getBlockY() + 2; locY <= 126; locY++) {
            if(w.getBlockAt(l.getBlockX(), locY, l.getBlockZ()).getType() != Material.AIR) {
                o = false;
                break;
            }
        }
        return o;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        World w = p.getWorld();
        Biome b = w.getBlockAt(p.getLocation()).getBiome();

        if(isOutdoors(p) && Util.getWorldIsAcidRaining().get(w)
                && b != Biome.TUNDRA && b != Biome.TAIGA && b != Biome.DESERT) {
            if(!Util.getAffectedPlayers().contains(p)) {
                Util.getAffectedPlayers().add(p);
            }
        }
        else {
            Util.getAffectedPlayers().remove(p);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if(Util.getAffectedPlayers().contains(event.getPlayer())) {
            Util.getAffectedPlayers().remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(Util.getAffectedPlayers().contains(event.getPlayer())) {
            Util.getAffectedPlayers().remove(event.getPlayer());
        }
    }
}