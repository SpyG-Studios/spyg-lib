package com.spygstudios.spyglib.hologram;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class HologramManager implements Listener {
    private static Map<Plugin, HologramManager> managers = new HashMap<>();

    private final JavaPlugin plugin;
    private final int entityTrackingRange;
    private final List<Hologram> holograms = new ArrayList<>();


    private HologramManager(JavaPlugin plugin) {
        managers.put(plugin, this);
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        entityTrackingRange = getEntityTrackingRange();
    }

    JavaPlugin getPlugin() {
        return plugin;
    }

    private int getEntityTrackingRange() {
        int dist = 64;
        try {
            Class<?> spigotConfigClass = Class.forName("org.spigotmc.SpigotConfig");
            Field configField = spigotConfigClass.getDeclaredField("config");
            configField.setAccessible(true);
            YamlConfiguration config = (YamlConfiguration) configField.get(null);
            dist = config.getInt("world-settings.default.entity-tracking-range.players", 64);
        } catch (Exception e) {}
        return Math.min(plugin.getServer().getViewDistance() * 16, dist);
    }

    public Hologram createHologram(Location location) {
        Hologram hologram = new Hologram(this, location, entityTrackingRange);
        holograms.add(hologram);
        return hologram;
    }

    public void removeHologram(Hologram hologram) {
        if(hologram == null) {
            throw new IllegalArgumentException("Hologram cannot be null");
        }
        if(holograms.contains(hologram)) {
            holograms.remove(hologram);
            hologram.remove();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        for(Hologram hologram : holograms) {
            if(event.getPlayer().getWorld().equals(hologram.getLocation().getWorld())) {
                hologram.update(event.getPlayer());
            }
        }
    }
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        for(Hologram hologram : holograms) {
            if(event.getPlayer().getWorld().equals(hologram.getLocation().getWorld()) || event.getFrom().getWorld().equals(hologram.getLocation().getWorld())) {
                hologram.update(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for(Hologram hologram : holograms) {
            if(event.getPlayer().getWorld().equals(hologram.getLocation().getWorld())) {
                hologram.update(event.getPlayer());
            }
        }
    }




    public static HologramManager getManager(JavaPlugin plugin) {
        if(managers.containsKey(plugin)) {
            return managers.get(plugin);
        }
        return new HologramManager(plugin);
    }
}
