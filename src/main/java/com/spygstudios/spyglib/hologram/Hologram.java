package com.spygstudios.spyglib.hologram;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joml.Vector3f;

import lombok.Setter;
import net.kyori.adventure.text.Component;

/**
 * <p>
 * Hologram class.
 * </p>
 *
 * @author Koponya
 * @version $Id: $Id
 */
public class Hologram {
    /** Constant <code>LINE_DISTANCE=0.25d</code> */
    public static final double LINE_DISTANCE = 0.3d;

    private final HologramManager manager;
    private Location location;
    private final List<HologramRow> rows = new ArrayList<>();
    private final List<Player> viewers = new ArrayList<>();
    @Setter
    private int viewDistance;

    /**
     * <p>
     * Constructor for Hologram.
     * </p>
     *
     * @param manager      a
     *                     {@link com.spygstudios.spyglib.hologram.HologramManager}
     *                     object
     * @param location     a {@link org.bukkit.Location} object
     * @param viewDistance a int
     */
    public Hologram(HologramManager manager, Location location, int viewDistance) {
        this.manager = manager;
        this.location = location;
        this.viewDistance = viewDistance;
        for (Player player : location.getWorld().getPlayers()) {
            update(player);
        }
    }

    /**
     * <p>
     * Getter for the field <code>location</code>.
     * </p>
     *
     * @return a {@link org.bukkit.Location} object
     */
    public Location getLocation() {
        return location.clone();
    }

    /**
     * <p>
     * Teleport the hologram to a new location.
     * </p>
     *
     * @param location a {@link org.bukkit.Location} object
     */
    public void teleport(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (!location.getWorld().equals(this.location.getWorld())) {
            throw new IllegalArgumentException("Cannot teleport hologram to a different world");
        }
        this.location = location;
        update();
    }

    public void setTransformation(Vector3f transformation, int delay, int duration) {
        for (HologramRow hologramRow : rows) {
            hologramRow.setTransformation(transformation, delay, duration);
        }
    }

    /**
     * <p>
     * Add a new row {@link java.lang.String} to the hologram.
     * </p>
     *
     * @param text a {@link java.lang.String} object
     * @return a {@link com.spygstudios.spyglib.hologram.HologramRow}
     *         object
     */
    public HologramRow addRow(String text) {
        return addRow(Component.text(text));
    }

    /**
     * <p>
     * Add a new {@link net.kyori.adventure.text.Component} row to the
     * hologram.
     * </p>
     *
     * @param text a {@link net.kyori.adventure.text.Component} object
     * @return a {@link com.spygstudios.spyglib.hologram.HologramRow}
     *         object
     */
    public HologramRow addRow(Component text) {
        HologramRow row = new HologramTextRow(this, location.clone().add(0, -rows.size() * LINE_DISTANCE, 0), text);
        rows.add(row);
        update();
        return row;
    }

    /**
     * <p>
     * Add a new item row to the hologram.
     * </p>
     *
     * @param item a {@link org.bukkit.inventory.ItemStack} object
     * @return a {@link com.spygstudios.spyglib.hologram.HologramRow}
     *         object
     */
    public HologramRow addRow(ItemStack item) {
        HologramRow row = new HologramItemRow(this, location.clone().add(0, -rows.size() * LINE_DISTANCE, 0), item);
        rows.add(row);
        update();
        return row;
    }

    /**
     * <p>
     * Remove a row from the hologram.
     * </p>
     *
     * @param index a int
     */
    public void removeRow(int index) {
        if (index < 0 || index >= rows.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        HologramRow row = rows.get(index);
        rows.remove(index);
        row.remove();
        update();
    }

    /**
     * <p>
     * Remove a row from the hologram.
     * </p>
     *
     * @param row a {@link com.spygstudios.spyglib.hologram.HologramRow}
     *            object
     */
    public void removeRow(HologramRow row) {
        if (row == null) {
            throw new IllegalArgumentException("Row cannot be null");
        }
        if (rows.contains(row)) {
            rows.remove(row);
            update();
        }
    }

    /**
     * <p>
     * Getter for the field <code>rows</code>.
     * </p>
     *
     * @return a {@link java.util.List} object
     */
    public List<HologramRow> getRows() {
        return List.copyOf(rows);
    }

    /**
     * <p>
     * Getter for the field <code>viewers</code>.
     * </p>
     *
     * @return a {@link java.util.List} object
     */
    public List<Player> getViewers() {
        return List.copyOf(viewers);
    }

    /**
     * <p>
     * Update the hologram for a player.
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     */
    public void update(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        if (!player.getWorld().equals(location.getWorld()) || !player.isOnline()) {
            removeViewer(player);
            return;
        }

        double distSqrt = player.getLocation().distanceSquared(location);
        if (distSqrt <= Math.pow(viewDistance, 2)) {
            if (!viewers.contains(player)) {
                viewers.add(player);
                for (HologramRow row : rows) {
                    row.show(player);
                }
            }
        } else {
            removeViewer(player);
        }
    }

    public void removeViewer(Player player) {
        if (viewers.contains(player)) {
            viewers.remove(player);
            for (HologramRow row : rows) {
                row.hide(player);
            }
        }
    }

    /**
     * <p>
     * Update the hologram.
     * </p>
     * 
     */
    private void update() {
        for (int i = 0; i < rows.size(); i++) {
            HologramRow r = rows.get(i);
            if (r == null) {
                continue;
            }
            if (!location.getWorld().isChunkLoaded(location.getBlockX() >> 4,
                    location.getBlockZ() >> 4)) {
                continue;
            }

            r.teleport(location.clone().add(0, (rows.size() - i) * LINE_DISTANCE, 0));
        }
    }

    /**
     * <p>
     * Remove the hologram.
     * </p>
     */
    public void remove() {
        for (HologramRow row : new ArrayList<>(rows)) {
            row.remove();
        }
        manager.removeHologram(this);
    }

}
