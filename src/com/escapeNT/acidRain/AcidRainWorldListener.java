

package com.escapeNT.acidRain;


import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldListener;

/**
 * Listener for chunk load events.
 * @author escapeNT
 */
public class AcidRainWorldListener extends WorldListener {

    @Override
    public void onChunkLoad(ChunkLoadEvent event) {
        if(Util.debugOn) {
            Util.log("Chunk load");
        }
        if(Config.willDissolveBlocks() && Util.getWorldIsAcidRaining().get(event.getWorld())) {
            Chunk c = event.getChunk();  
            for(Block b : Util.getBlocks(c)) {
                if(b.getType() == Material.GRASS) {
                    b.setType(Material.DIRT);
                    if(Util.debugOn) {
                        Util.log("Block dissolved");
                    }
                }
            }
        }
    }
}