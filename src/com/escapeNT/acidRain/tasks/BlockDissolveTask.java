
package com.escapeNT.acidRain.tasks;

import com.escapeNT.acidRain.AcidRain;
import com.escapeNT.acidRain.Config;
import com.escapeNT.acidRain.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 * @author escapeNT
 */
public class BlockDissolveTask implements Runnable {
    
    public void run() {
        Random r = new Random();
        for(World w : Util.getPlugin().getServer().getWorlds()) {
            if(Util.getWorldIsAcidRaining().get(w) != null && Util.getWorldIsAcidRaining().get(w)) {    
                List<Chunk> chunksAffected = new ArrayList<Chunk>();
                for(Chunk c : w.getLoadedChunks()) {
                    int randInt = r.nextInt(100);
                    if(randInt <= AcidRain.CHUNK_DISSOLVE_RATE) {
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
}