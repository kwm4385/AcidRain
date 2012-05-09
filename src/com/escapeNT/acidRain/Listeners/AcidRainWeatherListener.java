
package com.escapeNT.acidRain.Listeners;

import java.util.Random;

import com.escapeNT.acidRain.AcidRain;
import com.escapeNT.acidRain.Config;
import com.escapeNT.acidRain.Util;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Listener for weather change events.
 * @author escapeNT
 */
public class AcidRainWeatherListener implements Listener {


    private AcidRain instance;


    public AcidRainWeatherListener(AcidRain ar) {
        this.instance = ar;
    }


    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {

        if(event.toWeatherState() && instance.getConfig().getList(Config.worldsEnabled).contains(event.getWorld().getName())) {
            int r = new Random().nextInt(100);
            if(r <= instance.getConfig().getInt(Config.acidRainChance)) {
                Util.getWorldIsAcidRaining().put(event.getWorld(), Boolean.TRUE);
                Util.acidRainMessage(event.getWorld());
            }
        }
        else if(!event.toWeatherState()) {
            Util.getWorldIsAcidRaining().put(event.getWorld(), Boolean.FALSE);
        }
    }
}