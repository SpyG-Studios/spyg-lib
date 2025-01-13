package com.spygstudios.spyglib.hologram;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

/**
 * <p>
 * HologramTextRow class.
 * </p>
 *
 * @author Koponya
 * @version $Id: $Id
 */
public class HologramTextRow extends HologramRow {
    /** Constant <code>HEIGHT_OFFSET=-1.75d</code> */
    public static final double HEIGHT_OFFSET = -1.75d;

    private final Hologram hologram;
    private Location location;
    private Component text;
    private Object armorStand;

    /**
     * Create a new hologram text row
     *
     * @param hologram a {@link com.spygstudios.spyglib.hologram.Hologram}
     *                 object
     * @param location a {@link org.bukkit.Location} object
     * @param text     a {@link net.kyori.adventure.text.Component} object
     */
    public HologramTextRow(Hologram hologram, Location location, Component text) {
        this.hologram = hologram;
        this.location = location.clone().add(0, HEIGHT_OFFSET, 0);
        this.text = text;
        // call to create entity
        getEntity();
        for (Player player : hologram.getViewers()) {
            show(player);
        }
    }

    /**
     * Get the text component
     *
     * @return a {@link net.kyori.adventure.text.Component} object
     */
    public Component getText() {
        return text;
    }

    /**
     * <p>
     * Teleport the hologram row
     * </p>
     */
    public void teleport(Location location) {
        this.location = location.clone().add(0, HEIGHT_OFFSET, 0);
        try {
            HoloUtils.setLocation(armorStand, this.location, hologram.getViewers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

    /**
     * <p>
     * Setter for the field <code>text</code>.
     * </p>
     *
     * @param text a {@link net.kyori.adventure.text.Component} object
     */
    public void setText(Component text) {
        this.text = text;
        try {
            HoloUtils.setCustomName(armorStand, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

    /**
     * <p>
     * Remove the hologram row
     * </p>
     */
    public void remove() {
        for (Player player : hologram.getViewers()) {
            try {
                Object packet = HoloUtils.destroyPacket(getEntity());
                HoloUtils.sendPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        hologram.removeRow(this);
    }

    /**
     * <p>
     * Show the hologram row to a player
     * </p>
     * 
     * @param player a {@link org.bukkit.entity.Player} object
     */
    public void show(Player player) {
        try {
            Object packet = HoloUtils.createPacket(getEntity());
            HoloUtils.sendPacket(player, packet);
            Method refreshMethod = armorStand.getClass().getMethod("refreshEntityData", HoloUtils.getNMSClass("server.level.ServerPlayer"));
            refreshMethod.invoke(armorStand, HoloUtils.getHandle(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Hide the hologram row from a player
     * </p>
     * 
     * @param player a {@link org.bukkit.entity.Player} object
     */
    public void hide(Player player) {
        try {
            Object packet = HoloUtils.destroyPacket(getEntity());
            HoloUtils.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Update the hologram row
     * </p>
     */
    private void update() {
        for (Player player : hologram.getViewers()) {
            try {
                Method refreshMethod = armorStand.getClass().getMethod("refreshEntityData", HoloUtils.getNMSClass("server.level.ServerPlayer"));
                refreshMethod.invoke(armorStand, HoloUtils.getHandle(player));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * Get the row's entity
     * </p>
     * 
     * @return
     */
    private Object getEntity() {
        if (armorStand == null) {
            try {
                // NMS World
                Object nmsWorld = HoloUtils.getNMSWorld(location.getWorld());

                // NMS EntityArmorStand
                Class<?> entityArmorStandClass = HoloUtils.getNMSClass("world.entity.decoration.EntityArmorStand");
                // World (classic NMS) or Level class (Mojang mappings)
                Class<?> worldClass = HoloUtils.getWorldClass();
                Constructor<?> armorStandConstructor = entityArmorStandClass.getConstructor(worldClass, double.class, double.class, double.class);
                armorStand = armorStandConstructor.newInstance(nmsWorld, location.getX(), location.getY(), location.getZ());

                // Set properties
                HoloUtils.setInvisible(armorStand, true);
                HoloUtils.setCustomName(armorStand, text);
                HoloUtils.setInvulnerable(armorStand, true);
                HoloUtils.setNoGravity(armorStand, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return armorStand;
    }

}
