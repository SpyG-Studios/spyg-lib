package com.spygstudios.spyglib.datacontainer.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

class ItemStackListType implements PersistentDataType<String, List<ItemStack>> {

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<List<ItemStack>> getComplexType() {
        return (Class<List<ItemStack>>) (Class<?>) List.class;
    }

    @Override
    public String toPrimitive(List<ItemStack> complex, PersistentDataAdapterContext context) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("items", complex);
        return config.saveToString();
    }

    @Override
    public List<ItemStack> fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(primitive);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ItemStack list", e);
        }
        List<?> rawList = config.getList("items", new ArrayList<>());
        List<ItemStack> items = new ArrayList<>();
        if (rawList != null) {
            for (Object obj : rawList) {
                if (obj instanceof ItemStack) {
                    items.add((ItemStack) obj);
                }
            }
        }
        return items;
    }
}
