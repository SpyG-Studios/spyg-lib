package com.spygstudios.spyglib.inventory;

import java.util.function.Predicate;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * <p>
 * InventoryUtils class.
 * </p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public class InventoryUtils {

    /*
     * Checks if the inventory has a free slot
     * 
     * @param inventory The inventory to check
     */
    /**
     * <p>
     * Checks if the given inventory has a free slot.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @return a boolean
     */
    public static boolean hasFreeSlot(Inventory inventory) {
        return inventory.firstEmpty() != -1;
    }

    /*
     * Checks if the player has a free slot in their inventory
     * 
     * @param player The player to check
     */
    /**
     * <p>
     * Checks if the given player has a free slot in their inventory.
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     * @return a boolean
     */
    public static boolean hasFreeSlot(Player player) {
        return hasFreeSlot(player.getInventory());
    }

    /**
     * <p>
     * Checks if the inventory has a certain amount of free slots.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param amount    an int (the number of free slots to check for)
     * @return a boolean
     */
    public static boolean hasFreeSlots(Inventory inventory, int amount) {
        return inventory.firstEmpty() >= amount - 1;
    }

    /**
     * <p>
     * Checks if the player has a certain amount of free slots in their inventory.
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     * @param amount an int
     * @return a boolean
     */
    public static boolean hasFreeSlots(Player player, int amount) {
        return hasFreeSlots(player.getInventory(), amount);
    }

    /**
     * <p>
     * Checks if the inventory has a certain item by material.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param material  a {@link org.bukkit.Material} object
     * @return a boolean
     */
    public static boolean hasItem(Inventory inventory, Material material) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>
     * Checks if the player has a certain item by material in their inventory.
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param material a {@link org.bukkit.Material} object
     * @return a boolean
     */
    public static boolean hasItem(Player player, Material material) {
        return hasItem(player.getInventory(), material);
    }

    /**
     * <p>
     * Checks if the inventory has a certain amount of a certain item by material.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param material  a {@link org.bukkit.Material} object
     * @param amount    an int
     * @return a boolean
     */
    public static boolean hasItem(Inventory inventory, Material material, int amount) {
        int count = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                count += item.getAmount();
            }
        }

        return count >= amount;
    }

    /**
     * <p>
     * Checks if the player has a certain amount of a certain item by material in
     * their inventory.
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param material a {@link org.bukkit.Material} object
     * @param amount   an int
     * @return a boolean
     */
    public static boolean hasItem(Player player, Material material, int amount) {
        return hasItem(player.getInventory(), material, amount);
    }

    /**
     * <p>
     * Remove all of a certain item from the inventory.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param material  a {@link org.bukkit.Material} object
     */
    public static void removeAll(Inventory inventory, Material material) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                inventory.removeItem(item);
            }
        }
    }

    /**
     * <p>
     * Removes all of a certain item from the player's inventory
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param material a {@link org.bukkit.Material} object
     */
    public static void removeAll(Player player, Material material) {
        removeAll(player.getInventory(), material);
    }

    /**
     * <p>
     * Counts the amount of items in the inventory that match a given predicate.
     * </p>
     *
     * @param inventory     a {@link org.bukkit.inventory.Inventory} object
     * @param itemPredicate a {@link java.util.function.Predicate} object
     * @return an int
     */
    public static int countItems(Inventory inventory, Predicate<ItemStack> itemPredicate) {
        int count = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && itemPredicate.test(item)) {
                count += item.getAmount();
            }
        }

        return count;
    }

    /**
     * <p>
     * Counts the amount of items in the player's inventory that match a given
     * Material.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param material  a {@link org.bukkit.Material} object
     * @return an int
     */
    public static int countItems(Inventory inventory, Material material) {
        int count = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                count += item.getAmount();
            }
        }

        return count;
    }

    /**
     * <p>
     * Counts the amount of items in the inventory that match a given
     * Material.
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param material a {@link org.bukkit.Material} object
     * @return an int
     */
    public static int countItems(Player player, Material material) {
        return countItems(player.getInventory(), material);
    }

    /**
     * <p>
     * Counts the amount of items in the inventory that match a given
     * ItemMeta.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param itemMeta  a {@link org.bukkit.inventory.meta.ItemMeta} object
     * @return an int
     */
    public static int countItems(Inventory inventory, ItemMeta itemMeta) {
        int count = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getItemMeta().equals(itemMeta)) {
                count += item.getAmount();
            }
        }

        return count;
    }

    /**
     * <p>
     * Counts the amount of items in the Player's inventory that match a given
     * ItemMeta.
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param itemMeta a {@link org.bukkit.inventory.meta.ItemMeta} object
     * @return an int
     */
    public static int countItems(Player player, ItemMeta itemMeta) {
        return countItems(player.getInventory(), itemMeta);
    }
}
