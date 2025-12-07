package com.spygstudios.spyglib.datacontainer.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

class StringListType implements PersistentDataType<String, List<String>> {

    private static final String SEPERATOR = "<;>";

    @Override
    public @NotNull List<String> fromPrimitive(@NotNull String arg0, @NotNull PersistentDataAdapterContext arg1) {
        String[] parts = arg0.split(SEPERATOR);
        return new ArrayList<>(List.of(parts));
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<List<String>> getComplexType() {
        return (Class<List<String>>) (Class<?>) List.class;
    }

    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull String toPrimitive(@NotNull List<String> arg0, @NotNull PersistentDataAdapterContext arg1) {
        return String.join(SEPERATOR, arg0);
    }

}
