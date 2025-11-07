package com.spygstudios.spyglib.hologram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>
 * HologramManager class.
 * </p>
 *
 * @author Koponya
 * @version $Id: $Id
 */
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
        Bukkit.getScheduler().runTaskTimer(plugin, this::syncHologramLocations, 20L, 20L);
    }

    JavaPlugin getPlugin() {
        return plugin;
    }

    private int getEntityTrackingRange() {
        @SuppressWarnings("removal")
        int dist = plugin.getServer().spigot().getConfig().getInt("world-settings.default.entity-tracking-range.players", 64);
        return Math.min(plugin.getServer().getViewDistance() * 16, dist);
    }

    /**
     * 
     * @param location      a {@link org.bukkit.Location} object
     * @param seeTrough     can players see the hologram through blocks
     * @param hologramRange range of the hologram, if it is less than the entity
     *                      tracking range, it will be set to the entity tracking
     *                      range
     * @param canSee        a {@link java.util.function.Predicate} object
     * @return
     */
    public Hologram createHologram(Location location, boolean seeTrough, int hologramRange, Predicate<Player> canSee) {
        Hologram hologram = new Hologram(this, location, seeTrough, Math.min(entityTrackingRange, hologramRange), canSee);
        holograms.add(hologram);
        return hologram;
    }

    /**
     * 
     * @param location      a {@link org.bukkit.Location} object
     * @param seeTrough     can players see the hologram through blocks
     * @param hologramRange range of the hologram, if it is less than the entity
     *                      tracking range, it will be set to the entity tracking
     *                      range
     * @param canSee        a {@link java.util.function.Predicate} object
     * @return
     */
    public Hologram createHologram(Location location, boolean seeTrough, Predicate<Player> canSee) {
        return createHologram(location, seeTrough, entityTrackingRange, canSee);
    }

    /**
     * 
     * @param location  a {@link org.bukkit.Location} object
     * @param seeTrough can players see the hologram through blocks
     * @return
     */
    public Hologram createHologram(Location location, boolean seeTrough) {
        return createHologram(location, seeTrough, entityTrackingRange, p -> true);
    }

    /**
     * <p>
     * removeHologram.
     * </p>
     *
     * @param hologram a {@link com.spygstudios.spyglib.hologram.Hologram}
     *                 object
     */
    public void removeHologram(Hologram hologram) {
        if (hologram == null) {
            throw new IllegalArgumentException("Hologram cannot be null");
        }
        if (holograms.contains(hologram)) {
            holograms.remove(hologram);
            hologram.remove();
        }
    }

    /**
     * <p>
     * On player move event
     * </p>
     *
     * @param event a {@link org.bukkit.event.player.PlayerMoveEvent}
     *              object
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        for (Hologram hologram : holograms) {
            if (event.getPlayer().getWorld().equals(hologram.getLocation().getWorld())) {
                hologram.updateVisibility(event.getPlayer());
            }
        }
    }

    /**
     * <p>
     * On player teleport event
     * </p>
     *
     * @param event a {@link org.bukkit.event.player.PlayerTeleportEvent}
     *              object
     */
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        for (Hologram hologram : holograms) {
            if (event.getTo().getWorld().equals(hologram.getLocation().getWorld())) {
                hologram.updateVisibility(event.getPlayer());
            }
        }
    }

    /**
     * <p>
     * On player quit event
     * </p>
     *
     * @param event a {@link org.bukkit.event.player.PlayerQuitEvent}
     *              object
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for (Hologram hologram : holograms) {
            if (event.getPlayer().getWorld().equals(hologram.getLocation().getWorld())) {
                hologram.removeViewer(event.getPlayer());
            }
        }
    }

    /**
     * <p>
     * On plugin disable event
     * </p>
     *
     * @param event a {@link org.bukkit.event.server.PluginDisableEvent}
     *              object
     */
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(plugin)) {
            return;
        }
        for (Hologram hologram : new ArrayList<>(holograms)) {
            if (hologram == null) {
                continue;
            }
            hologram.remove();
        }
        managers.remove(plugin);
    }

    /**
     * <p>
     * Get the HologramManager for a plugin
     * </p>
     *
     * @param plugin a {@link org.bukkit.plugin.java.JavaPlugin} object
     * @return a {@link com.spygstudios.spyglib.hologram.HologramManager}
     *         object
     */
    public static HologramManager getManager(JavaPlugin plugin) {
        if (managers.containsKey(plugin)) {
            return managers.get(plugin);
        }
        return new HologramManager(plugin);
    }

    private void syncHologramLocations() {
        for (Hologram hologram : new ArrayList<>(holograms)) {
            hologram.teleport(hologram.getLocation());
        }
    }

}
