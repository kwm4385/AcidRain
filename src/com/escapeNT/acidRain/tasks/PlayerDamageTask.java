
package com.escapeNT.acidRain.tasks;

import com.escapeNT.acidRain.AcidRain;
import com.escapeNT.acidRain.Config;
import com.escapeNT.acidRain.Util;
import org.bukkit.entity.Player;

/**
 *
 * @author escapeNT
 */
public class PlayerDamageTask implements Runnable {
    public void run() {
        for(Player p : Util.getAffectedPlayers()) {
            if(!p.hasPermission(AcidRain.IMMUNE_PERMISSION)) {
                p.damage(Config.getRainDamage());
                if(Util.debugOn) {
                    Util.log("Dissolving player " + p.getDisplayName());
                }
            }
        }
    }
}