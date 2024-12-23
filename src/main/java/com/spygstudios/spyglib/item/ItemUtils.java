package com.spygstudios.spyglib.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.spygstudios.spyglib.color.TranslateColor;

/**
 * <p>
 * ItemUtils class.
 * </p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public class ItemUtils {

    /**
     * Creates an ItemStack with the specified material, displayname, lore, and
     * amount
     *
     * @param material    The material of the item
     * @param displayname The display name of the item
     * @param lore        The lore of the item
     * @param amount      The amount of the item
     * @return a {@link org.bukkit.inventory.ItemStack} object
     */
    public static ItemStack create(String material, String displayname, List<String> lore, int amount) {

        Material mat = Material.getMaterial(material);

        if (mat == null) {
            throw new IllegalArgumentException("Invalid material: " + material);
        }

        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();

        if (displayname != null && !displayname.equalsIgnoreCase("none")) {
            meta.displayName(TranslateColor.translate(displayname));
        }

        if (lore != null) {
            meta.lore(TranslateColor.translate(lore));
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Creates an ItemStack with the specified material, displayname, and lore
     *
     * @param material    a {@link java.lang.String} object
     * @param displayname a {@link java.lang.String} object
     * @param lore        a {@link java.util.List} object
     * @return a {@link org.bukkit.inventory.ItemStack} object
     */
    public static ItemStack create(String material, String displayname, List<String> lore) {
        return create(material, displayname, lore, 1);
    }

    /**
     * Creates an ItemStack with the specified material and displayname
     *
     * @param material    a {@link java.lang.String} object
     * @param displayname a {@link java.lang.String} object
     * @return a {@link org.bukkit.inventory.ItemStack} object
     */
    public static ItemStack create(String material, String displayname) {
        return create(material, displayname, null, 1);
    }

}
