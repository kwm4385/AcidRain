

package com.escapeNT.acidRain;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author escapeNT
 */
public class AcidRainPlayerListener extends PlayerListener {

    /**
     * Tests if a player is outdoors (has all air over their head).
     * @param player THe player to test.
     * @return True if the player is outdoors.
     */
    private boolean isOutdoors(Player player) {
        boolean o = true;
        World w = player.getWorld();
        Location l = player.getLocation();

        for(int locY = player.getLocation().getBlockY() + 2; locY <= 126; locY++) {
            if(w.getBlockAt(l.getBlockX(), locY, l.getBlockZ()).getType() != Material.AIR) {
                o = false;
                break;
            }
        }
        return o;
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        World w = p.getWorld();
        Biome b = w.getBlockAt(p.getLocation()).getBiome();

        if(isOutdoors(p) && Util.getWorldIsAcidRaining().get(w) && b != Biome.TUNDRA && b != Biome.TAIGA) {
            if(!Util.getAffectedPlayers().contains(p)) {
                Util.getAffectedPlayers().add(p);
            }
        }
        else {
            Util.getAffectedPlayers().remove(p);
        }
    }

    @Override
    public void onPlayerKick(PlayerKickEvent event) {
        if(Util.getAffectedPlayers().contains(event.getPlayer())) {
            Util.getAffectedPlayers().remove(event.getPlayer());
        }
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(Util.getAffectedPlayers().contains(event.getPlayer())) {
            Util.getAffectedPlayers().remove(event.getPlayer());
        }
    }
}