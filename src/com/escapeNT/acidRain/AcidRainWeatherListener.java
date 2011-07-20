
package com.escapeNT.acidRain;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

/**
 * Listener for weather change events.
 * @author escapeNT
 */
public class AcidRainWeatherListener extends WeatherListener {

    @Override
    public void onWeatherChange(WeatherChangeEvent event) {

        if(event.toWeatherState() && event.getWorld().getEnvironment() != World.Environment.SKYLANDS) {
            int r = new Random().nextInt(100);
            if(r <= Config.getAcidRainChance()) {
                Util.getWorldIsAcidRaining().put(event.getWorld(), Boolean.TRUE);

                if(Config.willBroadcastMessage()) {
                    Util.getPlugin().getServer().broadcastMessage(ChatColor.GRAY
                            + "Acid rain has begun in world " + event.getWorld().getName() + "!");
                }
                Util.log("Acid rain has begun in world " + event.getWorld().getName() + "!");
            }
            
        }
        else if(!event.toWeatherState()) {
            Util.getWorldIsAcidRaining().put(event.getWorld(), Boolean.FALSE);
        }
    }
}