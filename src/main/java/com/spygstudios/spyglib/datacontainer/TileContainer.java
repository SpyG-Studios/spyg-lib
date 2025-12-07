package com.spygstudios.spyglib.datacontainer;

import java.util.List;

import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.spygstudios.spyglib.datacontainer.types.CustomPersitentDataType;

import lombok.Getter;

@Getter
public class TileContainer extends DataContainer implements DataStore {

    private TileState tileStateBlock;

    public TileContainer(JavaPlugin plugin, TileState tile) {
        super(plugin, tile.getPersistentDataContainer());
        this.tileStateBlock = tile;
    }

    public void set(String key, String value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.STRING, value);
        tileStateBlock.update();
    }

    public void set(String key, int value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.INTEGER, value);
        tileStateBlock.update();
    }

    public void set(String key, double value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.DOUBLE, value);
        tileStateBlock.update();
    }

    public void set(String key, boolean value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
        tileStateBlock.update();
    }

    public void set(String key, byte[] value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE_ARRAY, value);
        tileStateBlock.update();
    }

    public void setByteArray(String key, List<byte[]> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.BYTE_ARRAY_LIST, value);
        tileStateBlock.update();
    }

    public void setStringList(String key, List<String> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.STRING_LIST, value);
        tileStateBlock.update();
    }

    public void set(String key, long value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.LONG, value);
        tileStateBlock.update();
    }

    public void set(String key, ItemStack value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK, value);
        tileStateBlock.update();
    }

    public void setItemStackList(String key, List<ItemStack> value) {
        dataContainer.set(getNamespacedKey(key), CustomPersitentDataType.ITEMSTACK_LIST, value);
        tileStateBlock.update();
    }

    public void remove(String key) {
        dataContainer.remove(getNamespacedKey(key));
        tileStateBlock.update();
    }

}
