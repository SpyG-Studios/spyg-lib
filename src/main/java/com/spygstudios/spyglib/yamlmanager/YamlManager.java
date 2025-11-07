package com.spygstudios.spyglib.yamlmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.spygstudios.spyglib.color.TranslateColor;

import net.kyori.adventure.text.Component;

/**
 * <p>
 * YamlManager class.
 * </p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public abstract class YamlManager {

    private static List<YamlManager> yamlManagers = new ArrayList<>();

    private JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private String filename;

    /**
     * <p>
     * Constructor for YamlManager.
     * </p>
     *
     * @param fileName a {@link java.lang.String} object
     * @param plugin   a {@link org.bukkit.plugin.java.JavaPlugin} object
     */
    protected YamlManager(String fileName, JavaPlugin plugin) {
        this.filename = fileName;
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.config = YamlConfiguration.loadConfiguration(configFile);

        yamlManagers.add(this);
    }


    /**
     * <p>
     * Constructor for YamlManager.
     * </p>
     *
     * @param file a {@link java.io.File} file
     * @param plugin   a {@link org.bukkit.plugin.java.JavaPlugin} object
     */
    protected YamlManager(File file, JavaPlugin plugin) {
        this.filename = file.getName();
        this.plugin = plugin;
        this.configFile = file;
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * <p>
     * reloadConfig.
     * </p>
     */
    public void reloadConfig() {
        if (isConfigFileNull()) {
            this.configFile = new File(this.plugin.getDataFolder(), filename);
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource(filename);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.config.setDefaults(defaultConfig);
        }
    }

    /**
     * Loads the default configuration from the provided InputStream.
     *
     * @param defaultStream the InputStream of the default configuration
     */
    public void loadDefaultConfig(InputStream defaultStream) {
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
        this.config.setDefaults(defaultConfig);
    }

    /**
     * Gets the configuration. If the configuration is null, it reloads
     * the configuration.
     *
     * @return the configuration
     */
    private FileConfiguration getConfig() {
        if (isFileNull()) {
            reloadConfig();
        }
        return config;
    }

    /**
     * Gets an integer from the configuration
     * 
     * @param node The node to get the value from
     */
    /**
     * <p>
     * getInt.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a int
     */
    public int getInt(String node) {
        return getConfig().getInt(node);
    }

    /**
     * Gets an integer from the configuration
     * 
     * @param node         The node to get the value from
     * 
     * @param defaultValue The default value to return if the node is not
     *                     found
     */
    /**
     * <p>
     * getInt.
     * </p>
     *
     * @param node         a {@link java.lang.String} object
     * @param defaultValue a int
     * @return a int
     */
    public int getInt(String node, int defaultValue) {
        return getConfig().getInt(node, defaultValue);
    }

    /**
     * Gets a double from the configuration
     * 
     * @param node The node to get the value from
     */
    /**
     * <p>
     * getDouble.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a double
     */
    public double getDouble(String node) {
        return getConfig().getDouble(node);
    }

    /**
     * Gets a double from the configuration
     * 
     * @param node         The node to get the value from
     * 
     * @param defaultValue The default value to return if the node is not
     *                     found
     */
    /**
     * <p>
     * getDouble.
     * </p>
     *
     * @param node         a {@link java.lang.String} object
     * @param defaultValue a double
     * @return a double
     */
    public double getDouble(String node, double defaultValue) {
        return getConfig().getDouble(node, defaultValue);
    }

    /**
     * Gets a boolean from the configuration
     * 
     * @param node The node to get the value from
     */
    /**
     * <p>
     * getBoolean.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean getBoolean(String node) {
        return getConfig().getBoolean(node);
    }

    /**
     * Gets a boolean from the configuration
     * 
     * @param node         The node to get the value from
     * 
     * @param defaultValue The default value to return if the node is not
     *                     found
     */
    /**
     * <p>
     * getBoolean.
     * </p>
     *
     * @param node         a {@link java.lang.String} object
     * @param defaultValue a boolean
     * @return a boolean
     */
    public boolean getBoolean(String node, boolean defaultValue) {
        return getConfig().getBoolean(node, defaultValue);
    }

    /**
     * Gets a string from the configuration
     * 
     * @param node The node to get the value from
     */
    /**
     * <p>
     * getString.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public String getString(String node) {
        return getConfig().getString(node);
    }

    /**
     * Gets the prefix from the configuration
     *
     * @return a {@link java.lang.String} object
     */
    public String getPrefix() {
        return getString("prefix", "");
    }

    /**
     * Gets a message from the configuration
     *
     * @param node The node to get the value from
     * @return a {@link net.kyori.adventure.text.Component} object
     */
    public Component getMessage(String node) {
        return TranslateColor.translate(getString("messages." + node).replace("%prefix%", getPrefix()));
    }

    /**
     * Gets a string from the configuration
     * 
     * @param node         The node to get the value from
     * 
     * @param defaultValue The default value to return if the node is not
     *                     found
     */
    /**
     * <p>
     * getString.
     * </p>
     *
     * @param node         a {@link java.lang.String} object
     * @param defaultValue a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public String getString(String node, String defaultValue) {
        return getConfig().getString(node, defaultValue);
    }

    /**
     * Gets a list of strings from the configuration
     * 
     * @param node The node to get the value from
     */
    /**
     * <p>
     * getStringList.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a {@link java.util.List} object
     */
    public List<String> getStringList(String node) {
        return getConfig().getStringList(node);
    }

    /**
     * Gets a list of strings from the configuration
     * 
     * @param node The node to get the value from
     */
    /**
     * <p>
     * getFloatList.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a {@link java.util.List} object
     */
    public List<Float> getFloatList(String node) {
        return getConfig().getFloatList(node);
    }

    /**
     * Sets a value in the configuration
     *
     * @param node  The node to get the value from
     * @param value The value to set
     */
    public void overwriteSet(String node, Object value) {
        getConfig().set(node, value);
    }

    /**
     * Sets a value in the configuration, if the value is not already set
     * 
     * @param node  The node to set the value in
     * 
     * @param value The value to set
     */
    /**
     * <p>
     * set.
     * </p>
     *
     * @param node  a {@link java.lang.String} object
     * @param value a {@link java.lang.Object} object
     */
    public void set(String node, Object value) {
        getConfig().set(node, config.get(node, value));
    }

    /**
     * Sets a value in the configuration
     *
     * @param node     The node to set the value in
     * @param value    The value to set
     * @param comments The comments to set
     */
    public void set(String node, Object value, List<String> comments) {
        getConfig().set(node, getConfig().get(node, value));
        getConfig().setComments(node, comments);
    }

    /**
     * Gets a configuration section from the configuration
     * 
     * @param node The node to get the configuration section from
     */
    /**
     * <p>
     * getConfigurationSection.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a {@link org.bukkit.configuration.ConfigurationSection}
     *         object
     */
    public ConfigurationSection getConfigurationSection(String node) {
        return getConfig().getConfigurationSection(node);
    }

    /**
     * Creates a section in the configuration
     * 
     * @param node The node to create the section in
     */
    /**
     * <p>
     * createSection.
     * </p>
     *
     * @param node a {@link java.lang.String} object
     * @return a {@link org.bukkit.configuration.ConfigurationSection}
     *         object
     */
    public ConfigurationSection createSection(String node) {
        return getConfig().createSection(node);
    }

    /**
     * <p>
     * saveConfig.
     * </p>
     */
    public void saveConfig() {
        if (areAnyFileNull()) {
            throw new RuntimeException("Cannot save config, file is null. Filename: " + this.filename);
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not save config to " + this.configFile, e);
        }
    }

    /**
     * <p>
     * saveDefaultConfig.
     * </p>
     */
    public void saveDefaultConfig() {
        if (isConfigFileNull())
            this.configFile = new File(this.plugin.getDataFolder(), filename);

        if (!this.configFile.exists()) {
            this.plugin.saveResource(filename, false);
        }
    }

    /**
     * <p>
     * getFileName.
     * </p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getFileName() {
        return this.filename;
    }

    /**
     * <p>
     * isFileNull.
     * </p>
     *
     * @return a boolean
     */
    public boolean isFileNull() {
        return this.config == null;
    }

    /**
     * <p>
     * isConfigFileNull.
     * </p>
     *
     * @return a boolean
     */
    public boolean isConfigFileNull() {
        return this.configFile == null;
    }

    /**
     * <p>
     * areAnyFileNull.
     * </p>
     *
     * @return a boolean
     */
    public boolean areAnyFileNull() {
        return isConfigFileNull() || isFileNull();
    }

    /**
     * <p>
     * Getter for the field <code>yamlManagers</code>.
     * </p>
     *
     * @return a {@link java.util.List} object
     */
    public static List<YamlManager> getYamlManagers() {
        return yamlManagers;
    }

    /**
     * <p>
     * getYamlManager.
     * </p>
     *
     * @param fileName a {@link java.lang.String} object
     * @return a {@link com.spygstudios.spyglib.yamlmanager.YamlManager}
     *         object
     */
    public static YamlManager getYamlManager(String fileName) {
        for (YamlManager yamlManager : yamlManagers) {
            if (yamlManager.getFileName().equals(fileName)) {
                return yamlManager;
            }
        }
        return null;
    }

}
