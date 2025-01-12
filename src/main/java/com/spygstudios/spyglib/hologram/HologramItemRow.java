package com.spygstudios.spyglib.hologram;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HologramItemRow extends HologramRow {
    private final Hologram hologram;
    private Location location;
    private ItemStack item;
    private Object armorStand;

    public HologramItemRow(Hologram hologram, Location location, ItemStack item) {
        this.hologram = hologram;
        this.location = location;
        this.item = item;
        // call to create entity
        getEntity();
        for(Player player : hologram.getViewers()) {
            show(player);
        }
    }

    public ItemStack getItem() {
        return item;
    }

    public void teleport(Location location) {
        this.location = location;
        try {
            HoloUtils.setLocation(armorStand, location, hologram.getViewers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

    public void setItem(ItemStack item) {
        this.item = item;
        try {
            HoloUtils.setItem(armorStand, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

    public void remove() {
        for(Player player : hologram.getViewers()) {
            try {
                Object packet = HoloUtils.destroyPacket(getEntity());
                HoloUtils.sendPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        hologram.removeRow(this);
    }

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

    public void hide(Player player) {
        try {
            Object packet = HoloUtils.destroyPacket(getEntity());
            HoloUtils.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update() {
        for(Player player : hologram.getViewers()) {
            try {
                Method refreshMethod = armorStand.getClass().getMethod("refreshEntityData", HoloUtils.getNMSClass("server.level.ServerPlayer"));
                refreshMethod.invoke(armorStand, HoloUtils.getHandle(player));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Object getEntity() {
        if(armorStand == null) {
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
