package com.spygstudios.spyglib.version;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;

/**
 * <p>VersionChecker class.</p>
 *
 * @author ikoli
 * @version $Id: $Id
 */
public class VersionChecker {

    /**
     * <p>isLatestVersion.</p>
     *
     * @param apiUrl a {@link java.lang.String} object
     * @param currentVersion a {@link java.lang.String} object
     * @return a {@link java.util.Map.Entry} object
     */
    public static Entry<String, Boolean> isLatestVersion(String apiUrl, String currentVersion) {
        try {
            String latestVersion = fetchLatestVersion(apiUrl);
            int versionDifference = checkVersionDifference(latestVersion, currentVersion);
            if (versionDifference > 0) {
                return Map.entry(latestVersion, false);
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Unable to get version data: " + e.getMessage());
        }
        return Map.entry(currentVersion, true);
    }

    private static int checkVersionDifference(String latestVersion, String currentVersion) throws Exception {
        if (latestVersion == null) {
            return -1;
        }
        return calculateVersionDifference(currentVersion, latestVersion);
    }

    private static String fetchLatestVersion(String apiUrl) throws Exception {
        URL url = URI.create(apiUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "text/plain");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return reader.readLine().trim();
            }
        } else {
            String warnin = "An error happend during the version check http code: " + responseCode;
            Bukkit.getLogger().warning(warnin);
        }
        return null;
    }

    private static int calculateVersionDifference(String currentVersion, String latestVersion) {
        String[] currentParts = currentVersion.split("\\.");
        String[] latestParts = latestVersion.split("\\.");

        int maxLength = Math.max(currentParts.length, latestParts.length);
        for (int i = 0; i < maxLength; i++) {
            int currentPart = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int latestPart = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;

            if (currentPart < latestPart) {
                return 1;
            } else if (currentPart > latestPart) {
                return 0;
            }
        }
        return 0;
    }
}
