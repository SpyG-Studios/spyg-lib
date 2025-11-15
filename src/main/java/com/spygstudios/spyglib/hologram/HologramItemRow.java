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
    private static final double HEIGHT_OFFSET = -0.4d;
    private ItemStack item;
    private Object textDisplay;

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
        super(hologram, location.clone().add(0, HEIGHT_OFFSET, 0));
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
        setLocation(location.clone().add(0, HEIGHT_OFFSET, 0));
        try {
            HoloUtils.setLocation(textDisplay, location, getHologram().getViewers());
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
            HoloUtils.setItem(textDisplay, item);
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
        for (Player player : getHologram().getViewers()) {
            try {
                Object packet = HoloUtils.destroyPacket(getEntity());
                HoloUtils.sendPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getHologram().removeRow(this);
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
            Method refreshMethod = textDisplay.getClass().getMethod("refreshEntityData", HoloUtils.getNMSClass("server.level.ServerPlayer"));
            refreshMethod.invoke(textDisplay, HoloUtils.getHandle(player));
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
        for (Player player : getHologram().getViewers()) {
            try {
                Method refreshMethod = textDisplay.getClass().getMethod("refreshEntityData", HoloUtils.getNMSClass("server.level.ServerPlayer"));
                refreshMethod.invoke(textDisplay, HoloUtils.getHandle(player));
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
        if (textDisplay == null) {
            try {
                // NMS World
                Object nmsWorld = HoloUtils.getNMSWorld(getLocation().getWorld());

                Class<?> itemStackClass = HoloUtils.getNMSClass("world.item.ItemStack");
                // NMS ItemEntity
                Class<?> entityArmorStandClass = HoloUtils.getNMSClass("world.entity.item.ItemEntity");
                // World (classic NMS) or Level class (Mojang mappings)
                Class<?> worldClass = HoloUtils.getWorldClass();
                Constructor<?> armorStandConstructor = entityArmorStandClass.getConstructor(worldClass, double.class, double.class, double.class, itemStackClass);
                textDisplay = armorStandConstructor.newInstance(nmsWorld, getLocation().getX(), getLocation().getY(), getLocation().getZ(), HoloUtils.getNMSItemStack(item));

                // Set properties
                HoloUtils.setItem(textDisplay, item);
                HoloUtils.setNoGravity(textDisplay, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return textDisplay;
    }

}
