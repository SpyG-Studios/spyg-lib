package com.spygstudios.spyglib.datacontainer;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.spygstudios.spyglib.datacontainer.types.CustomPersitentDataType;

import lombok.Getter;

@Getter
public class EntityContainer extends DataContainer implements DataStore {

    private final Entity entity;

    public EntityContainer(JavaPlugin plugin, Entity entity) {
        super(plugin, entity.getPersistentDataContainer());
        this.entity = entity;
    }

    public void set(String key, String value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.STRING, value);
    }

    public void set(String key, int value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.INTEGER, value);
    }

    public void set(String key, double value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.DOUBLE, value);
    }

    public void set(String key, boolean value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
    }

    public void set(String key, long value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.LONG, value);
    }

    public void set(String key, ItemStack value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK, value);
    }

    public void set(String key, Location value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.LOCATION, value);
    }

    public void set(String key, byte[] value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE_ARRAY, value);
    }

    public void setByteArray(String key, List<byte[]> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST, value);
    }

    public void setStringList(String key, List<String> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.STRING_LIST, value);
    }

    public boolean hasByteArrayList(String key) {
        return dataContainer.has(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST);
    }

    public void remove(String key) {
        dataContainer.remove(getNamespacedKey(key));
    }

    public void setItemStackList(String key, List<ItemStack> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK_LIST, value);
    }

}
