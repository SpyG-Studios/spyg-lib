package com.spygstudios.spyglib.datacontainer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.spygstudios.spyglib.datacontainer.types.CustomPersitentDataType;

import lombok.Getter;

@Getter
public class DataContainer {
    protected JavaPlugin plugin;
    protected PersistentDataContainer dataContainer;

    protected DataContainer(JavaPlugin plugin, PersistentDataContainer dataContainer) {
        this.plugin = plugin;
        this.dataContainer = dataContainer;
    }

    public static ItemContainer create(JavaPlugin plugin, ItemStack item) {
        if (item.getType().isAir()) {
            throw new IllegalArgumentException("ItemStack cannot be air");
        }
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        return new ItemContainer(plugin, item, meta);
    }

    public static TileContainer create(JavaPlugin plugin, TileState tile) {
        return new TileContainer(plugin, tile);
    }

    public static DataContainer create(JavaPlugin plugin, PersistentDataContainer dataContainer) {
        return new DataContainer(plugin, dataContainer);
    }

    public static DataContainer create(JavaPlugin plugin, Entity entity) {
        return new DataContainer(plugin, entity.getPersistentDataContainer());
    }

    public static DataContainer create(JavaPlugin plugin, World world) {
        return new DataContainer(plugin, world.getPersistentDataContainer());
    }

    public static ItemContainer empty(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.STONE, 1);
        return create(plugin, item);
    }

    public static ItemContainer fromBlob(JavaPlugin plugin, byte[] blob) {
        ItemStack item = ItemStack.deserializeBytes(blob);
        return create(plugin, item);
    }

    public Object get(String key) {

        if (hasString(key)) {
            return getString(key);
        } else if (hasInt(key)) {
            return getInt(key);
        } else if (hasDouble(key)) {
            return getDouble(key);
        } else if (hasBoolean(key)) {
            return getBoolean(key);
        } else if (hasLong(key)) {
            return getLong(key);
        } else if (hasItemStack(key)) {
            return getItemStack(key);
        }
        return null;

    }

    public byte[] toBlob() {
        return toBytes();
    }

    public byte[] toBytes() {

        try {

            if (this instanceof ItemContainer itemContainer) {
                return itemContainer.getItem().serializeAsBytes();
            }

            return dataContainer.serializeToBytes();

        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public NamespacedKey getNamespacedKey(String key) {
        return new NamespacedKey(plugin, key);
    }

    public boolean hasString(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.STRING);
    }

    public boolean hasInt(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.INTEGER);
    }

    public boolean hasDouble(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.DOUBLE);
    }

    public boolean hasBoolean(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.BYTE);
    }

    public boolean hasLong(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.LONG);
    }

    public boolean hasStringList(String key) {
        return dataContainer.has(getNamespacedKey(key), CustomPersitentDataType.STRING_LIST);
    }

    public boolean hasItemStack(String key) {
        return dataContainer.has(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK);
    }

    public boolean hasItemStackList(String key) {
        return dataContainer.has(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK_LIST);
    }

    public boolean hasLocation(String key) {
        return dataContainer.has(getNamespacedKey(key), CustomPersitentDataType.LOCATION);
    }

    public boolean hasByteArray(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.BYTE_ARRAY);
    }

    public boolean hasByteArrayList(String key) {
        return dataContainer.has(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.STRING, def);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.INTEGER, def);
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double def) {
        try {
            return dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.DOUBLE, def);
        } catch (IllegalArgumentException e) {
            int intValue = dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.INTEGER, (int) def);
            double doubleValue = Double.valueOf(intValue);
            set(key, doubleValue);
            return doubleValue;
        }
    }

    public boolean getBoolean(String key) {
        return dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.BYTE, (byte) 0) == 1;
    }

    public boolean getBoolean(String key, boolean def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.BYTE, (byte) (def ? 1 : 0)) == 1;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long def) {
        try {
            return dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.LONG, def);
        } catch (IllegalArgumentException e) {
            int intValue = dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.INTEGER, (int) def);
            long longValue = Long.valueOf(intValue);
            set(key, longValue);
            return longValue;
        }
    }

    public List<String> getStringList(String key) {
        return getStringList(key, new ArrayList<>());
    }

    public List<String> getStringList(String key, List<String> def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), CustomPersitentDataType.STRING_LIST, def);
    }

    public ItemStack getItemStack(String key) {
        return getItemStack(key, null);
    }

    public ItemStack getItemStack(String key, ItemStack def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK, def);
    }

    public List<ItemStack> getItemStackList(String key) {
        return getItemStackList(key, null);
    }

    public List<ItemStack> getItemStackList(String key, List<ItemStack> def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK_LIST, def);
    }

    public Location getLocation(String key) {
        return getLocation(key, null);
    }

    public Location getLocation(String key, Location def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), CustomPersitentDataType.LOCATION, def);
    }

    public byte[] getByteArray(String key) {
        return getByteArray(key, null);
    }

    public byte[] getByteArray(String key, byte[] def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), PersistentDataType.BYTE_ARRAY, def);
    }

    public List<byte[]> getByteArrayList(String key) {
        return getByteArrayList(key, null);
    }

    public List<byte[]> getByteArrayList(String key, List<byte[]> def) {
        return dataContainer.getOrDefault(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST, def);
    }

    public void setStringList(String key, List<String> value) {

    }

    public void set(String key, byte[] value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE_ARRAY, value);
        }
    }

    public void setByteArray(String key, List<byte[]> value) {
        if (this instanceof DataStore dataStore) {
            dataStore.setByteArray(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST, value);
        }
    }

    public void set(String key, String value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        }
    }

    public void set(String key, int value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), PersistentDataType.INTEGER, value);
        }
    }

    public void set(String key, double value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), PersistentDataType.DOUBLE, value);
        }
    }

    public void set(String key, boolean value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
        }
    }

    public void set(String key, long value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), PersistentDataType.LONG, value);
        }
    }

    public void set(String key, ItemStack value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK, value);
        }
    }

    public void setItemStackList(String key, List<ItemStack> value) {
        if (this instanceof DataStore dataStore) {
            dataStore.setItemStackList(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK_LIST, value);
        }
    }

    public void set(String key, Location value) {
        if (this instanceof DataStore dataStore) {
            dataStore.set(key, value);
        } else {
            dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.LOCATION, value);
        }
    }

    public void remove(String key) {
        if (this instanceof DataStore dataStore) {
            dataStore.remove(key);
        } else {
            dataContainer.remove(getNamespacedKey(key));
        }
    }

    public List<String> getKeys() {
        return dataContainer.getKeys().stream().map(k -> k.getKey()).toList();
    }
}
