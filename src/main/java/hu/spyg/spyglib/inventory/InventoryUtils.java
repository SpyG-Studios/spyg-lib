package hu.spyg.spyglib.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
     * hasFreeSlot.
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
     * hasFreeSlot.
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     * @return a boolean
     */
    public static boolean hasFreeSlot(Player player) {
        return hasFreeSlot(player.getInventory());
    }

    /*
     * Checks if the inventory has a certain amount of free slots
     * 
     * @param inventory The inventory to check
     * 
     * @param amount The amount of free slots to check for
     */
    /**
     * <p>
     * hasFreeSlots.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param amount    a int
     * @return a boolean
     */
    public static boolean hasFreeSlots(Inventory inventory, int amount) {
        return inventory.firstEmpty() >= amount - 1;
    }

    /*
     * Checks if the player has a certain amount of free slots in their inventory
     * 
     * @param player The player to check
     * 
     * @param amount The amount of free slots to check for
     */
    /**
     * <p>
     * hasFreeSlots.
     * </p>
     *
     * @param player a {@link org.bukkit.entity.Player} object
     * @param amount a int
     * @return a boolean
     */
    public static boolean hasFreeSlots(Player player, int amount) {
        return hasFreeSlots(player.getInventory(), amount);
    }

    /*
     * Checks if the inventory has a certain amount of a certain item
     * 
     * @param inventory The inventory to check
     * 
     * @param material The material to check for
     */
    /**
     * <p>
     * hasItem.
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

    /*
     * Checks if the player has a certain amount of a certain item in their
     * inventory
     * 
     * @param player The player to check
     * 
     * @param material The material to check for
     */
    /**
     * <p>
     * hasItem.
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param material a {@link org.bukkit.Material} object
     * @return a boolean
     */
    public static boolean hasItem(Player player, Material material) {
        return hasItem(player.getInventory(), material);
    }

    /*
     * Checks if the inventory has a certain amount of a certain item
     * 
     * @param inventory The inventory to check
     * 
     * @param material The material to check for
     * 
     * @param amount The amount of the item to check for
     */
    /**
     * <p>
     * hasItem.
     * </p>
     *
     * @param inventory a {@link org.bukkit.inventory.Inventory} object
     * @param material  a {@link org.bukkit.Material} object
     * @param amount    a int
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

    /*
     * Checks if the player has a certain amount of a certain item in their
     * inventory
     * 
     * @param player The player to check
     * 
     * @param material The material to check for
     * 
     * @param amount The amount of the item to check for
     */
    /**
     * <p>
     * hasItem.
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param material a {@link org.bukkit.Material} object
     * @param amount   a int
     * @return a boolean
     */
    public static boolean hasItem(Player player, Material material, int amount) {
        return hasItem(player.getInventory(), material, amount);
    }

    /*
     * Removes all of a certain item from the inventory
     * 
     * @param inventory The inventory to remove the item from
     * 
     * @param material The material to remove
     */
    /**
     * <p>
     * removeAll.
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

    /*
     * Removes all of a certain item from the player's inventory
     * 
     * @param player The player to remove the item from
     * 
     * @param material The material to remove
     */
    /**
     * <p>
     * removeAll.
     * </p>
     *
     * @param player   a {@link org.bukkit.entity.Player} object
     * @param material a {@link org.bukkit.Material} object
     */
    public static void removeAll(Player player, Material material) {
        removeAll(player.getInventory(), material);
    }
}
