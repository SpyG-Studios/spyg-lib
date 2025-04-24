package com.spygstudios.spyglib.item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

// import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * <p>
 * PlayerHeads class.
 * </p>
 *
 * @author ikoli
 * @version $Id: $Id
 */
public class PlayerHeads {

    /**
     * <p>
     * getOfflinePlayerHead.
     * </p>
     *
     * @param uuid a {@link java.util.UUID} object
     * @return a {@link org.bukkit.inventory.ItemStack} object
     */
    @SuppressWarnings("deprecation")
    public static ItemStack getOfflinePlayerHead(UUID uuid) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwnerProfile(getGameProfile(uuid));
        skull.setItemMeta(skullMeta);
        return skull;
    }

    // public static ItemStack getOfflinePlayerHeadPAPER(UUID uuid) {
    // ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

    // SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
    // com.destroystokyo.paper.profile.PlayerProfile profile =
    // Bukkit.createProfile(uuid);

    // profile.setProperty(new ProfileProperty("textures",
    // getSkin(uuid)));

    // skullMeta.setPlayerProfile(profile);
    // skull.setItemMeta(skullMeta);
    // return skull;
    // }

    /**
     * <p>
     * getSkin.
     * </p>
     *
     * @param uuid a {@link java.util.UUID} object
     * @return a {@link java.lang.String} object
     */
    public static String getSkin(UUID uuid) {
        String url = getSkinUrl(uuid.toString());
        if (url == null) {
            return null;
        }

        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        return new String(encodedData);
    }

    /**
     * 
     * @param uuid a {@link java.util.UUID} object
     * @return
     */
    private static String getSkinUrl(String uuid) {
        String json = getContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            JsonObject o = JsonParser.parseString(json).getAsJsonObject();
            String jsonBase64 = o.get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
            JsonObject decoded = JsonParser.parseString(new String(Base64.getDecoder().decode(jsonBase64))).getAsJsonObject();
            return decoded.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param link {@link java.lang.String} object
     * @return
     */
    private static String getContent(String link) {
        try {
            URL url = URI.create(link).toURL();
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    content.append(inputLine);
                }
                return content.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * getOnlinePlayerHead.
     * </p>
     *
     * @param uuid a {@link java.util.UUID} object
     * @return a {@link org.bukkit.inventory.ItemStack} object
     */
    public static ItemStack getOnlinePlayerHead(UUID uuid) {
        ItemStack playerHead = new ItemStack(org.bukkit.Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        if (skullMeta != null) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
            playerHead.setItemMeta(skullMeta);
        }

        return playerHead;
    }

    /**
     * 
     * @param uuid a {@link java.util.UUID} object
     * @return
     */
    @SuppressWarnings("deprecation")
    private static PlayerProfile getGameProfile(UUID uuid) {
        PlayerProfile profile = Bukkit.createProfile(uuid);
        try {
            URL url = URI.create(PlayerHeads.getSkinUrl(uuid.toString())).toURL();
            profile.getTextures().setSkin(url);
        } catch (MalformedURLException | NullPointerException e) {
            return null;
        }

        return profile;
    }
}
