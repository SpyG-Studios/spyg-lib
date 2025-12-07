package com.spygstudios.spyglib.datacontainer;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface DataStore {

    public void set(String key, String value);

    public void set(String key, int value);

    public void set(String key, double value);

    public void set(String key, boolean value);

    public void set(String key, long value);

    public void set(String key, ItemStack value);

    public void set(String key, Location value);

    public void set(String key, byte[] value);

    public void setByteArray(String key, List<byte[]> value);

    public void setItemStackList(String key, List<ItemStack> value);

    public void setStringList(String key, List<String> value);

    public void remove(String key);
}
