package com.spygstudios.spyglib.hologram;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
    public static final double HEIGHT_OFFSET = 0.5d;

    private final Hologram hologram;
    private Location location;
    private Component text;
    private Object entity;

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
            HoloUtils.setLocation(entity, this.location, hologram.getViewers());
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
        for (Player player : hologram.getViewers()) {
            try {
                Object packet = HoloUtils.destroyPacket(getEntity());
                HoloUtils.sendPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        entity = null;
        getEntity();
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
            Method refreshMethod = entity.getClass().getMethod("refreshEntityData", HoloUtils.getNMSClass("server.level.ServerPlayer"));
            refreshMethod.invoke(entity, HoloUtils.getHandle(player));
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
                Method refreshMethod = entity.getClass().getMethod("refreshEntityData", HoloUtils.getNMSClass("server.level.ServerPlayer"));
                refreshMethod.invoke(entity, HoloUtils.getHandle(player));
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
        if (entity == null) {

            try {
                // NMS World
                Object nmsWorld = HoloUtils.getNMSWorld(location.getWorld());

                // NMS EntityArmorStand
                Class<?> entityTextDisplayClass = HoloUtils.getNMSClass("world.entity.Display$TextDisplay");
                // World (classic NMS) or Level class (Mojang mappings)
                Class<?> entityTypeClass = HoloUtils.getNMSClass("world.entity.EntityType");
                Field textDisplayField = entityTypeClass.getDeclaredField("TEXT_DISPLAY");
                Class<?> worldClass = HoloUtils.getWorldClass();
                Constructor<?> textDisplayConstructor = entityTextDisplayClass.getConstructor(entityTypeClass, worldClass);
                entity = textDisplayConstructor.newInstance(textDisplayField.get(null), nmsWorld);
                entity.getClass().getMethod("setPos", double.class, double.class, double.class)
                        .invoke(entity, location.getX(), location.getY(), location.getZ());
                // entity.getClass().getMethod("setTextOpacity", byte.class).invoke(entity,
                // (byte) 99);
                entity.getClass().getMethod("setWidth", float.class).invoke(entity, (float) 99);
                HoloUtils.setCustomName(entity, text);
                Class<?> enumElementClass = HoloUtils.getNMSClass("world.entity.Display$BillboardConstraints");
                Object enumElement = HoloUtils.getEnumElement(enumElementClass, "VERTICAL");
                entity.getClass().getMethod("setBillboardConstraints", enumElementClass).invoke(entity, enumElement);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

}
