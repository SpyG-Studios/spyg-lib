package com.spygstudios.spyglib.persistentdata;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>
 * PersistentData class.
 * </p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public class PersistentData {

    private JavaPlugin plugin;
    private ItemStack item;
    private ItemMeta itemMeta;
    private PersistentDataContainer dataContainer;

    /**
     * <p>
     * Constructor for PersistentData.
     * </p>
     *
     * @param plugin a {@link org.bukkit.plugin.java.JavaPlugin} object
     * @param item   a {@link org.bukkit.inventory.ItemStack} object
     */
    public PersistentData(JavaPlugin plugin, ItemStack item) {
        this.plugin = plugin;
        this.item = item;
        this.itemMeta = item.getItemMeta();
        this.dataContainer = itemMeta.getPersistentDataContainer();
    }

    /**
     * <p>
     * Constructor for PersistentData.
     * </p>
     *
     * @param plugin        a {@link org.bukkit.plugin.java.JavaPlugin}
     *                      object
     * @param dataContainer a
     *                      {@link org.bukkit.persistence.PersistentDataContainer}
     *                      object
     */
    public PersistentData(JavaPlugin plugin, PersistentDataContainer dataContainer) {
        this.plugin = plugin;
        this.dataContainer = dataContainer;
    }

    /**
     * <p>
     * getNamespacedKey.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a {@link org.bukkit.NamespacedKey} object
     */
    public NamespacedKey getNamespacedKey(String key) {
        return new NamespacedKey(plugin, key);
    }

    /**
     * <p>
     * Getter for the field <code>dataContainer</code>.
     * </p>
     *
     * @return a {@link org.bukkit.persistence.PersistentDataContainer}
     *         object
     */
    public PersistentDataContainer getDataContainer() {
        return dataContainer;
    }

    /**
     * <p>
     * save.
     * </p>
     */
    public void save() {
        if (itemMeta != null) {
            item.setItemMeta(itemMeta);
        }
    }

    /**
     * <p>
     * hasString.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean hasString(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.STRING);
    }

    /**
     * <p>
     * hasInt.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean hasInt(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.INTEGER);
    }

    /**
     * <p>
     * hasDouble.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean hasDouble(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.DOUBLE);
    }

    /**
     * <p>
     * hasBoolean.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean hasBoolean(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.BYTE);
    }

    /**
     * <p>
     * hasLong.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean hasLong(String key) {
        return dataContainer.has(getNamespacedKey(key), PersistentDataType.LONG);
    }

    /**
     * <p>
     * set.
     * </p>
     *
     * @param key   a {@link java.lang.String} object
     * @param value a {@link java.lang.String} object
     */
    public void set(String key, String value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.STRING, value);
    }

    /**
     * <p>
     * set.
     * </p>
     *
     * @param key   a {@link java.lang.String} object
     * @param value a int
     */
    public void set(String key, int value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.INTEGER, value);
    }

    /**
     * <p>
     * set.
     * </p>
     *
     * @param key   a {@link java.lang.String} object
     * @param value a double
     */
    public void set(String key, double value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.DOUBLE, value);
    }

    /**
     * <p>
     * set.
     * </p>
     *
     * @param key   a {@link java.lang.String} object
     * @param value a boolean
     */
    public void set(String key, boolean value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
    }

    /**
     * <p>
     * set.
     * </p>
     *
     * @param key   a {@link java.lang.String} object
     * @param value a long
     */
    public void set(String key, long value) {
        dataContainer.set(getNamespacedKey(key), PersistentDataType.LONG, value);
    }

    /**
     * <p>
     * getString.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public String getString(String key) {
        return dataContainer.get(getNamespacedKey(key), PersistentDataType.STRING);
    }

    /**
     * <p>
     * getInt.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a int
     */
    public int getInt(String key) {
        return dataContainer.get(getNamespacedKey(key), PersistentDataType.INTEGER);
    }

    /**
     * <p>
     * getDouble.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a double
     */
    public double getDouble(String key) {
        return dataContainer.get(getNamespacedKey(key), PersistentDataType.DOUBLE);
    }

    /**
     * <p>
     * getBoolean.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean getBoolean(String key) {
        return dataContainer.get(getNamespacedKey(key), PersistentDataType.BYTE) == 1;
    }

    /**
     * <p>
     * getLong.
     * </p>
     *
     * @param key a {@link java.lang.String} object
     * @return a long
     */
    public long getLong(String key) {
        return dataContainer.get(getNamespacedKey(key), PersistentDataType.LONG);
    }
}
