package com.spygstudios.spyglib.placeholder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseListPlaceholder {

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
