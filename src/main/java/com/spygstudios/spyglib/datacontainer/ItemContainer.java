package com.spygstudios.spyglib.datacontainer;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.spygstudios.spyglib.datacontainer.types.CustomPersitentDataType;

import lombok.Getter;

@Getter
public class ItemContainer extends DataContainer implements DataStore {

    private ItemStack item;
    private ItemMeta meta;

    public ItemContainer(JavaPlugin plugin, ItemStack item, ItemMeta meta) {
        super(plugin, meta.getPersistentDataContainer());
        this.item = item;
        this.meta = meta;
    }

    private void setItemMeta() {

        if (meta == null) {
            return;
        }

        item.setItemMeta(meta);
    }

    public void set(String key, String value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.STRING, value);
        setItemMeta();
    }

    public void set(String key, int value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.INTEGER, value);
        setItemMeta();
    }

    public void set(String key, double value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.DOUBLE, value);
        setItemMeta();
    }

    public void set(String key, boolean value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
        setItemMeta();
    }

    public void set(String key, long value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.LONG, value);
        setItemMeta();
    }

    public void set(String key, ItemStack value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK, value);
        setItemMeta();
    }

    public void set(String key, Location value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.LOCATION, value);
        setItemMeta();
    }

    public void set(String key, byte[] value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE_ARRAY, value);
        setItemMeta();
    }

    public void setByteArray(String key, List<byte[]> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST, value);
        setItemMeta();
    }

    public void setStringList(String key, List<String> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.STRING_LIST, value);
        setItemMeta();
    }

    public boolean hasByteArrayList(String key) {
        return dataContainer.has(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST);
    }

    public void remove(String key) {
        dataContainer.remove(getNamespacedKey(key));
        setItemMeta();
    }

    public void setItemStackList(String key, List<ItemStack> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK_LIST, value);
        setItemMeta();
    }
}
