package com.spygstudios.spyglib.placeholder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>ParseListPlaceholder class.</p>
 *
 * @author Peter
 * @version $Id: $Id
 */
public class ParseListPlaceholder {

    /**
     * <p>parse.</p>
     *
     * @param list a {@link java.util.List} object
     * @param placeholders a {@link java.util.Map} object
     * @return a {@link java.util.List} object
     */
    public static List<String> parse(List<String> list, Map<String, String> placeholders) {
        List<String> parsed = new ArrayList<>();
        for (String line : list) {
            for (String key : placeholders.keySet()) {
                line = line.replace(key, placeholders.get(key));
            }
            parsed.add(line);
        }
        return parsed;
    }

}
