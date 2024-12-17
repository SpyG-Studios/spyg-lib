package com.spygstudios.spyglib.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * <p>
 * LocationUtils class.
 * </p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public class LocationUtils {

    /*
     * Splits a string into a location.
     * 
     * @param already formatted location
     */
    /**
     * <p>
     * toLocation.
     * </p>
     *
     * @param location a {@link java.lang.String} object
     * @return a {@link org.bukkit.Location} object
     */
    public static Location toLocation(String location) {
        String[] parts = location.split(" ");
        return new Location(Bukkit.getWorld(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Float.parseFloat(parts[4]),
                Float.parseFloat(parts[5]));
    }

    /*
     * Splits a string into a location.
     * 
     * @param already formatted location
     * 
     * @param world
     */
    /**
     * <p>
     * toLocation.
     * </p>
     *
     * @param location a {@link java.lang.String} object
     * @param world    a {@link org.bukkit.World} object
     * @return a {@link org.bukkit.Location} object
     */
    public static Location toLocation(String location, World world) {
        location = world.getName() + " " + location;
        return toLocation(location);
    }

    /*
     * Creates a formatted location string.
     * 
     * @param location
     * 
     * @param includeWorld
     */
    /**
     * <p>
     * fromLocation.
     * </p>
     *
     * @param location     a {@link org.bukkit.Location} object
     * @param includeWorld a boolean
     * @return a {@link java.lang.String} object
     */
    public static String fromLocation(Location location, boolean includeWorld) {
        if (includeWorld) {
            return location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch();
        }
        return fromLocation(location);
    }

    /*
     * Creates a formatted location string.
     * 
     * @param location
     */
    /**
     * <p>
     * fromLocation.
     * </p>
     *
     * @param location a {@link org.bukkit.Location} object
     * @return a {@link java.lang.String} object
     */
    public static String fromLocation(Location location) {
        return location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch();
    }

}
