package com.spygstudios.spyglib.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * <p>
 * Abstract HologramRow class.
 * </p>
 *
 * @author Koponya
 * @version $Id: $Id
 */
public abstract class HologramRow {
    /**
     * <p>
     * Show the hologram row to a player
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     */
    public abstract void show(Player player);

    /**
     * <p>
     * Hide the hologram row from a player
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     */
    public abstract void hide(Player player);

    /**
     * <p>
     * Teleport the hologram row
     * </p>
     *
     * @param location a {@link org.bukkit.Location} object
     */
    public abstract void teleport(Location location);

    /**
     * <p>
     * Remove the hologram row.
     * </p>
     */
    public abstract void remove();
}
