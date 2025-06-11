package com.spygstudios.spyglib.hologram;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * <p>
 * HoloUtils class.
 * </p>
 *
 * @author Koponya
 * @version $Id: $Id
 */
public class HoloUtils {
    /**
     * <p>
     * sendPacket.
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     * @param packet a {@link java.lang.Object} object
     * @throws java.lang.Exception if any.
     */
    public static void sendPacket(Player player, Object packet) throws Exception {
        try {
            // classic NMS
            Object nmsPlayer = getHandle(player);
            Field playerConnectionField = nmsPlayer.getClass().getField("b"); // Player connection (1.18+ field name)
            Object playerConnection = playerConnectionField.get(nmsPlayer);
            Method sendPacket = playerConnection.getClass().getMethod("a", getNMSClass("network.protocol.Packet"));
            sendPacket.invoke(playerConnection, packet);
            return;
        } catch (Exception e) {
        }
        // Mojang mappings
        Object nmsPlayer = getHandle(player);
        Field playerConnectionField = nmsPlayer.getClass().getField("connection");
        Object playerConnection = playerConnectionField.get(nmsPlayer);
        Method sendPacket = playerConnection.getClass().getMethod("send", getNMSClass("network.protocol.Packet"));
        sendPacket.invoke(playerConnection, packet);
    }

    /**
     * <p>
     * getHandle.
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     * @return a {@link java.lang.Object} object
     * @throws java.lang.Exception if any.
     */
    public static Object getHandle(Player player) throws Exception {
        Method getHandle = player.getClass().getMethod("getHandle");
        return getHandle.invoke(player);
    }

    /**
     * <p>
     * getNMSWorld.
     * </p>
     *
     * @param world a {@link org.bukkit.World} object
     * @return a {@link java.lang.Object} object
     * @throws java.lang.Exception if any.
     */
    public static Object getNMSWorld(org.bukkit.World world) throws Exception {
        Method getHandle = world.getClass().getMethod("getHandle");
        return getHandle.invoke(world);
    }

    /**
     * <p>
     * getNMSClass.
     * </p>
     *
     * @param name a {@link java.lang.String} object
     * @return a {@link java.lang.Class} object
     * @throws java.lang.ClassNotFoundException if any.
     */
    public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        String[] server = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        if (server.length > 3) {
            String version = server[3];
            return Class.forName("net.minecraft.server." + version + "." + name);
        }
        return Class.forName("net.minecraft." + name);
    }

    /**
     * <p>
     * getWorldClass.
     * </p>
     *
     * @return a {@link java.lang.Class} object
     * @throws java.lang.ClassNotFoundException if any.
     */
    public static Class<?> getWorldClass() throws ClassNotFoundException {
        String[] server = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        if (server.length > 3) {
            String version = server[3];
            return Class.forName("net.minecraft.server." + version + ".world.level.World");
        }
        return Class.forName("net.minecraft.world.level.Level");
    }

    /**
     * <p>
     * setInvisible.
     * </p>
     *
     * @param entity    a {@link java.lang.Object} object
     * @param invisible a boolean
     * @throws java.lang.Exception if any.
     */
    public static void setInvisible(Object entity, boolean invisible) throws Exception {
        Method setInvisible = entity.getClass().getMethod("setInvisible", boolean.class);
        setInvisible.invoke(entity, invisible);
    }

    /**
     * <p>
     * setCustomName.
     * </p>
     *
     * @param entity a {@link java.lang.Object} object
     * @param name   a {@link java.lang.String} object
     * @throws java.lang.Exception if any.
     */
    public static void setCustomName(Object entity, String name) throws Exception {
        setCustomName(entity, Component.text(name));
    }

    /**
     * <p>
     * setCustomName.
     * </p>
     *
     * @param entity    a {@link java.lang.Object} object
     * @param component a {@link net.kyori.adventure.text.Component}
     *                  object
     * @throws java.lang.Exception if any.
     */
    public static void setCustomName(Object entity, Component component) throws Exception {
        try {
            String json = GsonComponentSerializer.gson().serialize(component);
            Method setCustomName = entity.getClass().getMethod("setCustomName", getNMSClass("network.chat.Component"));
            Method setCustomNameVisible = entity.getClass().getMethod("setCustomNameVisible", boolean.class);
            Class<?> providerClass = getNMSClass("core.HolderLookup$Provider");
            Method createMethod = providerClass.getMethod("create", Stream.class);
            Class<?> registryLookupClass = getNMSClass("core.HolderLookup$RegistryLookup");
            Object provider = createMethod.invoke(null, createArrayList(registryLookupClass).stream());
            Method fromJsonMethod = getNMSClass("network.chat.Component$Serializer").getMethod("fromJson", String.class, providerClass);
            Object chatComponentText = fromJsonMethod.invoke(null, json, provider);

            setCustomName.invoke(entity, chatComponentText);
            setCustomNameVisible.invoke(entity, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * createArrayList.
     * </p>
     *
     * @param clazz a {@link java.lang.Class} object
     * @return a {@link java.util.ArrayList} object
     * @throws java.lang.Exception if any.
     */
    public static ArrayList<?> createArrayList(Class<?> clazz) throws Exception {
        return new ArrayList<>();
    }

    /**
     * <p>
     * createSet.
     * </p>
     *
     * @param clazz a {@link java.lang.Class} object
     * @return a {@link java.util.Set} object
     * @throws java.lang.Exception if any.
     */
    public static Set<?> createSet(Class<?> clazz) throws Exception {
        return Set.of();
    }

    /**
     * <p>
     * setInvulnerable.
     * </p>
     *
     * @param entity       a {@link java.lang.Object} object
     * @param invulnerable a boolean
     * @throws java.lang.Exception if any.
     */
    public static void setInvulnerable(Object entity, boolean invulnerable) throws Exception {
        Method setInvulnerable = entity.getClass().getMethod("setInvulnerable", boolean.class);
        setInvulnerable.invoke(entity, invulnerable);
    }

    /**
     * <p>
     * setNoGravity.
     * </p>
     *
     * @param entity    a {@link java.lang.Object} object
     * @param noGravity a boolean
     * @throws java.lang.Exception if any.
     */
    public static void setNoGravity(Object entity, boolean noGravity) throws Exception {
        Method setNoGravity = entity.getClass().getMethod("setNoGravity", boolean.class);
        setNoGravity.invoke(entity, noGravity);
    }

    /**
     * Set the item of a hologram row
     *
     * @param entity a {@link java.lang.Object} object
     * @param item   a {@link org.bukkit.inventory.ItemStack} object
     * @throws java.lang.Exception
     */
    public static void setItem(Object entity, org.bukkit.inventory.ItemStack item) throws Exception {
        try {
            Method setItem = entity.getClass().getMethod("setItem", getNMSClass("world.item.ItemStack"));
            setItem.invoke(entity, getNMSItemStack(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the NMS ItemStack from a Bukkit ItemStack
     *
     * @param itemStack a {@link org.bukkit.inventory.ItemStack} object
     * @throws java.lang.Exception
     * @return a {@link java.lang.Object} object
     */
    public static Object getNMSItemStack(ItemStack itemStack) throws Exception {
        try {
            Class<?> craftItemStack = Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack");
            Method asNMSCopy = craftItemStack.getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class);
            return asNMSCopy.invoke(null, itemStack);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Set the location of an entity and update the viewers
     *
     * @param entity   a {@link java.lang.Object} object
     * @param location a {@link org.bukkit.Location} object
     * @param viewers  a {@link java.util.List} object
     * @throws java.lang.Exception
     */
    public static void setLocation(Object entity, Location location, List<Player> viewers) throws Exception {
        Class<?> vec3Class = getNMSClass("world.phys.Vec3");
        Object pos = vec3Class.getConstructor(double.class, double.class, double.class).newInstance(location.getX(), location.getY(), location.getZ());
        Method setVelocity = entity.getClass().getMethod("setDeltaMovement", vec3Class);
        setVelocity.invoke(entity, vec3Class.getField("ZERO").get(null));
        Method setPosition;
        try {
            setPosition = entity.getClass().getMethod("moveTo", vec3Class);
        } catch (NoSuchMethodException e) {
            setPosition = entity.getClass().getMethod("snapTo", vec3Class);
        }
        setPosition.invoke(entity, pos);
        for (Player viewer : viewers) {
            createTeleportPacket(entity, viewer);
        }
    }

    private static void createTeleportPacket(Object entity, Player viewer) throws Exception {
        Class<?> packetClass = getNMSClass("network.protocol.game.ClientboundTeleportEntityPacket");
        Object packet;
        try {
            Constructor<?> packetConstructor = packetClass.getDeclaredConstructor(getNMSClass("world.entity.Entity"));
            packet = packetConstructor.newInstance(entity);
        } catch (Exception e) {
            try {
                // packet = int id, PositionMoveRotation change, Set<Relative>
                // relatives, boolean onGround
                Class<?> positionMoveRotationClass = getNMSClass("world.entity.PositionMoveRotation");
                Class<?> entityClass = getNMSClass("world.entity.Entity");
                Constructor<?> packetConstructor = packetClass.getDeclaredConstructor(int.class, positionMoveRotationClass, Set.class, boolean.class);
                Method ofMethod = positionMoveRotationClass.getMethod("of", entityClass);
                packet = packetConstructor.newInstance(entity.getClass().getMethod("getId").invoke(entity), ofMethod.invoke(null, entity), createSet(packetClass), false);
            } catch (Exception e2) {
                throw new Exception("Failed to create teleport packet, contact the developers!", e);
            }
        }

        sendPacket(viewer, packet);
    }

    /**
     * Create a packet to spawn an entity
     *
     * @param entity a {@link java.lang.Object} object
     * @throws java.lang.Exception
     * @return a {@link java.lang.Object} object
     */
    public static Object createPacket(Object entity) throws Exception {
        try {
            // classic NMS
            Class<?> packetPlayOutSpawnEntityLiving = getNMSClass("network.protocol.game.PacketPlayOutSpawnEntityLiving");
            Constructor<?> packetConstructor = packetPlayOutSpawnEntityLiving.getConstructor(getNMSClass("world.entity.EntityLiving"));
            return packetConstructor.newInstance(entity);
        } catch (Exception e) {
        }
        // Mojang mappings
        int entityId = (int) entity.getClass().getMethod("getId").invoke(entity);
        UUID uuid = (UUID) entity.getClass().getMethod("getUUID").invoke(entity);
        Object pos = entity.getClass().getMethod("position").invoke(entity);
        double x = (double) pos.getClass().getField("x").get(pos);
        double y = (double) pos.getClass().getField("y").get(pos);
        double z = (double) pos.getClass().getField("z").get(pos);
        Class<?> entityTypeClass = getNMSClass("world.entity.EntityType");
        Object entityType = entity.getClass().getMethod("getType").invoke(entity);
        Class<?> vec3Class = getNMSClass("world.phys.Vec3");
        Class<?> packetClass = getNMSClass("network.protocol.game.ClientboundAddEntityPacket");
        Constructor<?> packetConstructor = packetClass.getConstructor(int.class, UUID.class, double.class, double.class, double.class, float.class, float.class, entityTypeClass, int.class, vec3Class,
                double.class);
        Object vec = vec3Class.getConstructor(double.class, double.class, double.class).newInstance(0.0, 0.0, 0.0);

        return packetConstructor.newInstance(entityId, uuid, x, y, z, 0f, 0f, entityType, 0, vec, 0.0);
    }

    /**
     * Create a packet to destroy an entity
     *
     * @param entity a {@link java.lang.Object} object
     * @throws java.lang.Exception
     * @return a {@link java.lang.Object} object
     */
    public static Object destroyPacket(Object entity) throws Exception {
        try {
            // classic NMS
            Class<?> packetPlayOutEntityDestroy = getNMSClass("network.protocol.game.PacketPlayOutEntityDestroy");
            Constructor<?> packetConstructor = packetPlayOutEntityDestroy.getConstructor(int[].class);
            return packetConstructor.newInstance(new int[] { (int) entity.getClass().getMethod("getId").invoke(entity) });
        } catch (Exception e) {
        }
        // Mojang mappings
        int entityId = (int) entity.getClass().getMethod("getId").invoke(entity);
        Class<?> packetClass = getNMSClass("network.protocol.game.ClientboundRemoveEntitiesPacket");
        Constructor<?> packetConstructor = packetClass.getDeclaredConstructor(int[].class);
        return packetConstructor.newInstance(new int[] { entityId });
    }

}
