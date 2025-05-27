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

    private static final Map<String, String> colorCodes = new HashMap<>();
    static {
        colorCodes.put("&0", "<black>");
        colorCodes.put("&1", "<dark_blue>");
        colorCodes.put("&2", "<dark_green>");
        colorCodes.put("&3", "<dark_aqua>");
        colorCodes.put("&4", "<dark_red>");
        colorCodes.put("&5", "<dark_purple>");
        colorCodes.put("&6", "<gold>");
        colorCodes.put("&7", "<gray>");
        colorCodes.put("&8", "<dark_gray>");
        colorCodes.put("&9", "<blue>");
        colorCodes.put("&a", "<green>");
        colorCodes.put("&b", "<aqua>");
        colorCodes.put("&c", "<red>");
        colorCodes.put("&d", "<light_purple>");
        colorCodes.put("&e", "<yellow>");
        colorCodes.put("&f", "<white>");
        colorCodes.put("&k", "<obfuscated>");
        colorCodes.put("&l", "<bold>");
        colorCodes.put("&m", "<strikethrough>");
        colorCodes.put("&n", "<underlined>");
        colorCodes.put("&o", "<italic>");
        colorCodes.put("&r", "<reset><!i>");
    }

    /*
     * Translates color codes in a string to components This supports HEX
     * color codes and the old Minecraft color codes
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

        StringBuilder result = new StringBuilder();
        Matcher match = pattern.matcher(message);
        int lastAppendPosition = 0;
        boolean hasBold = false;

        while (match.find()) {
            int charPos = match.start() - 1;
            if (charPos >= 0 && message.charAt(charPos) == '&') {
                String color = match.group();
                String replacement = "<" + color + ">";
                if (!hasBold && color.equals("bold")) {
                    hasBold = true;
                } else if (hasBold) {
                    replacement = "<!b>" + replacement;
                }

                result.append(message, lastAppendPosition, charPos);
                result.append(replacement);
                lastAppendPosition = match.end();
            }
        }
        result.append(message.substring(lastAppendPosition));
        message = result.toString();

        StringBuilder finalResult = new StringBuilder();
        Matcher matchOld = oldPattern.matcher(message);
        lastAppendPosition = 0;

        while (matchOld.find()) {
            String color = matchOld.group();
            String replacement = colorCodes.get(color);
            if (!hasBold && color.equals("&l")) {
                hasBold = true;
            } else if (hasBold) {
                replacement = "<!b>" + replacement;
            }

            finalResult.append(message, lastAppendPosition, matchOld.start());
            finalResult.append(replacement);
            lastAppendPosition = matchOld.end();
        }
        finalResult.append(message.substring(lastAppendPosition));

        return ComponentUtils.toComponent("<!i>" + finalResult.toString());
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
