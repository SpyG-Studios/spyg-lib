package com.spygstudios.spyglib.hologram;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

public class HologramTextRow extends HologramRow {
    public static final double HEIGHT_OFFSET = -1.75d;
    
    private final Hologram hologram;
    private Location location;
    private Component text;
    private Object armorStand;

    public HologramTextRow(Hologram hologram, Location location, Component text) {
        this.hologram = hologram;
        this.location = location.clone().add(0, HEIGHT_OFFSET, 0);
        this.text = text;
        // call to create entity
        getEntity();
        for(Player player : hologram.getViewers()) {
            show(player);
        }
    }

    public Component getText() {
        return text;
    }

    public void teleport(Location location) {
        this.location = location.clone().add(0, HEIGHT_OFFSET, 0);
        try {
            HoloUtils.setLocation(armorStand, this.location, hologram.getViewers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

    public void setText(Component text) {
        this.text = text;
        try {
            HoloUtils.setCustomName(armorStand, text);
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
