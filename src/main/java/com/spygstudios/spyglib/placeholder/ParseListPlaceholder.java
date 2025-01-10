package com.spygstudios.spyglib.placeholder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * ParseListPlaceholder class.
 * </p>
 *
 * @author Peter
 * @version $Id: $Id
 */
public class ParseListPlaceholder {

    /**
     * <p>
     * parse.
     * </p>
     *
     * @param list         a {@link java.util.List} object
     * @param placeholders a {@link java.util.Map} object
     * @return a {@link java.util.List} object
     */
    public static List<String> parse(List<String> list, Map<String, String> placeholders) {
        List<String> parsed = new ArrayList<>();
        for (String line : list) {
            for (Entry<String, String> placeholder : placeholders.entrySet()) {
                line = line.replace(placeholder.getKey(), placeholder.getValue());
            }
            parsed.add(line);
        }
        return parsed;
    }

}
