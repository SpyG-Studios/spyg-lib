package com.spygstudios.spyglib.broadcast;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.spygstudios.spyglib.color.TranslateColor;

public class BroadcastMessage {

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

    public static void chat(String message, Map<String, String> placeholders) {
        chat(message, placeholders, null);
    }

    public static void chat(String message) {
        chat(message, null, null);
    }

    public static void chat(String message, String permission) {
        chat(message, null, permission);
    }

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

    public static void actionBar(String message, Map<String, String> placeholders) {
        actionBar(message, placeholders, null);
    }

    public static void actionBar(String message) {
        actionBar(message, null, null);
    }

    public static void actionBar(String message, String permission) {
        actionBar(message, null, permission);
    }

}
