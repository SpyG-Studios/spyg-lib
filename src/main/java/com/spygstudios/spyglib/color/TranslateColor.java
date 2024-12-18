package com.spygstudios.spyglib.color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.spygstudios.spyglib.components.ComponentUtils;

import net.kyori.adventure.text.Component;

/**
 * <p>
 * TranslateColor class.
 * </p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public class TranslateColor {
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private static final Pattern oldPattern = Pattern.compile("&[a-fA-F0-9lkLKmMnNrRoO]");

    private static final Map<String, String> colorCodes = new HashMap<>() {
        {
            put("&0", "<black>");
            put("&1", "<dark_blue>");
            put("&2", "<dark_green>");
            put("&3", "<dark_aqua>");
            put("&4", "<dark_red>");
            put("&5", "<dark_purple>");
            put("&6", "<gold>");
            put("&7", "<gray>");
            put("&8", "<dark_gray>");
            put("&9", "<blue>");
            put("&a", "<green>");
            put("&b", "<aqua>");
            put("&c", "<red>");
            put("&d", "<light_purple>");
            put("&e", "<yellow>");
            put("&f", "<white>");
            put("&k", "<obfuscated>");
            put("&l", "<bold>");
            put("&m", "<strikethrough>");
            put("&n", "<underlined>");
            put("&o", "<italic>");
            put("&r", "<reset>");
        }
    };

    /*
     * Translates color codes in a string to components This supports HEX color
     * codes and the old Minecraft color codes
     * 
     * @param message The message to translate
     */
    /**
     * <p>
     * translate.
     * </p>
     *
     * @param message a {@link java.lang.String} object
     * @return a {@link net.kyori.adventure.text.Component} object
     */
    public static Component translate(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Argument for method color(String) cannot be null!");
        }
        Matcher match = pattern.matcher(message);
        while (match.find()) {
            int charPos = match.start() - 1;
            if (charPos >= 0 && message.charAt(charPos) == '&') {
                String color = message.substring(match.start(), match.end());
                message = message.replace("&" + color, "<" + color + ">");
                match = pattern.matcher(message);
            }
        }
        Matcher matchOld = oldPattern.matcher(message);
        while (matchOld.find()) {
            String color = message.substring(matchOld.start(), matchOld.end());
            message = message.replace(color, colorCodes.get(color));
            matchOld = oldPattern.matcher(message);
        }
        return ComponentUtils.toComponent("<!i>" + message);
    }

    /*
     * Translates color codes in a list of strings to a list of components
     * 
     * @param messages The messages to translate
     */
    /**
     * <p>
     * translate.
     * </p>
     *
     * @param messages a {@link java.util.List} object
     * @return a {@link java.util.List} object
     */
    public static List<Component> translate(List<String> messages) {
        if (messages == null) {
            throw new IllegalArgumentException("Argument for method color(List<String>) cannot be null!");
        }
        return messages.stream().map(TranslateColor::translate).toList();
    }

}
