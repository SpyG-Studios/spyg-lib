package com.spygstudios.spyglib.datacontainer.types;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

class LocationType implements PersistentDataType<String, Location> {

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<Location> getComplexType() {
        return Location.class;
    }

    @Override
    public String toPrimitive(Location complex, PersistentDataAdapterContext context) {
        if (complex == null) {
            return "";
        }
        return complex.getWorld().getName() + "," + complex.getX() + "," + complex.getY() + "," + complex.getZ() + "," + complex.getYaw() + "," + complex.getPitch();
    }

    @Override
    public Location fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        if (primitive == null || primitive.isEmpty()) {
            return null;
        }
        String[] parts = primitive.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid location string: " + primitive);
        }
        String worldName = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = parts.length > 4 ? Float.parseFloat(parts[4]) : 0f;
        float pitch = parts.length > 5 ? Float.parseFloat(parts[5]) : 0f;

        Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        if (location.getWorld() == null) {
            throw new IllegalArgumentException("World not found: " + worldName);
        }
        return location;
    }
}