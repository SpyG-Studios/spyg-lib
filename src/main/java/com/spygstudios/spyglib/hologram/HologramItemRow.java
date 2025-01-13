package com.spygstudios.spyglib.hologram;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * <p>
 * HologramItemRow class.
 * </p>
 *
 * @author Koponya
 * @version $Id: $Id
 */
public class HologramItemRow extends HologramRow {
    private final Hologram hologram;
    private Location location;
    private ItemStack item;
    private Object armorStand;

    /**
     * <p>
     * Constructor for HologramItemRow.
     * </p>
     *
     * @param hologram a {@link com.spygstudios.spyglib.hologram.Hologram}
     *                 object
     * @param location a {@link org.bukkit.Location} object
     * @param item     a {@link org.bukkit.inventory.ItemStack} object
     */
    public HologramItemRow(Hologram hologram, Location location, ItemStack item) {
        this.hologram = hologram;
        this.location = location;
        this.item = item;
        // call to create entity
        getEntity();
        for (Player player : hologram.getViewers()) {
            show(player);
        }
    }

    /**
     * <p>
     * Getter for the field <code>item</code>.
     * </p>
     *
     * @return a {@link org.bukkit.inventory.ItemStack} object
     */
    public ItemStack getItem() {
        return item;
    }

    /** {@inheritDoc} */
    public void teleport(Location location) {
        this.location = location;
        try {
            HoloUtils.setLocation(armorStand, location, hologram.getViewers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

    /**
     * <p>
     * Setter for the field <code>item</code>.
     * </p>
     *
     * @param item a {@link org.bukkit.inventory.ItemStack} object
     */
    public void setItem(ItemStack item) {
        this.item = item;
        try {
            HoloUtils.setItem(armorStand, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

    /**
     * <p>
     * Remove the hologram row.
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
     * Update the hologram row.
     * </p>
     * 
     * @param player a {@link org.bukkit.entity.Player} object
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
     * 
     * @return
     */
    private Object getEntity() {
        if (armorStand == null) {
            try {
                // NMS World
                Object nmsWorld = HoloUtils.getNMSWorld(location.getWorld());

                Class<?> itemStackClass = HoloUtils.getNMSClass("world.item.ItemStack");
                // NMS ItemEntity
                Class<?> entityArmorStandClass = HoloUtils.getNMSClass("world.entity.item.ItemEntity");
                // World (classic NMS) or Level class (Mojang mappings)
                Class<?> worldClass = HoloUtils.getWorldClass();
                Constructor<?> armorStandConstructor = entityArmorStandClass.getConstructor(worldClass, double.class, double.class, double.class, itemStackClass);
                armorStand = armorStandConstructor.newInstance(nmsWorld, location.getX(), location.getY(), location.getZ(), HoloUtils.getNMSItemStack(item));

                // Set properties
                HoloUtils.setItem(armorStand, item);
                HoloUtils.setNoGravity(armorStand, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return armorStand;
    }
}
