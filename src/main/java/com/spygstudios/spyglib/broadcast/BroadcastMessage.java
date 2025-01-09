package com.spygstudios.spyglib.broadcast;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.spygstudios.spyglib.color.TranslateColor;

/**
 * <p>BroadcastMessage class.</p>
 *
 * @author Peter
 * @version $Id: $Id
 */
public class BroadcastMessage {

    /**
     * <p>chat.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param placeholders a {@link java.util.Map} object
     * @param permission a {@link java.lang.String} object
     */
    public static void chat(String message, Map<String, String> placeholders, String permission) {
        for (String key : placeholders.keySet()) {
            message = message.replace(key, placeholders.get(key));
        }

        if (permission == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(TranslateColor.translate(message));
            }
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                player.sendMessage(TranslateColor.translate(message));
            }
        }

    }

    /**
     * <p>chat.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param placeholders a {@link java.util.Map} object
     */
    public static void chat(String message, Map<String, String> placeholders) {
        chat(message, placeholders, null);
    }

    /**
     * <p>chat.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public static void chat(String message) {
        chat(message, null, null);
    }

    /**
     * <p>chat.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param permission a {@link java.lang.String} object
     */
    public static void chat(String message, String permission) {
        chat(message, null, permission);
    }

    /**
     * <p>actionBar.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param placeholders a {@link java.util.Map} object
     * @param permission a {@link java.lang.String} object
     */
    public static void actionBar(String message, Map<String, String> placeholders, String permission) {
        for (String key : placeholders.keySet()) {
            message = message.replace(key, placeholders.get(key));
        }

        if (permission == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendActionBar(TranslateColor.translate(message));
            }
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player.hasPermission(permission)) {
                player.sendActionBar(TranslateColor.translate(message));
            }

        }

    }

    /**
     * <p>actionBar.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param placeholders a {@link java.util.Map} object
     */
    public static void actionBar(String message, Map<String, String> placeholders) {
        actionBar(message, placeholders, null);
    }

    /**
     * <p>actionBar.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public static void actionBar(String message) {
        actionBar(message, null, null);
    }

    /**
     * <p>actionBar.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param permission a {@link java.lang.String} object
     */
    public static void actionBar(String message, String permission) {
        actionBar(message, null, permission);
    }

}
