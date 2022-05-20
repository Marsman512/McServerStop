package me.marsman512.mc_server_stop;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ModConfig {
    public final int stopDelaySeconds;

    public ModConfig() {
        stopDelaySeconds = 60;
    }

    private static final Path configPath = Path.of("config/mc-server-stop/config.json");

    /**
     * Loads the mod config file. If it doesn't exist, it
     * will create the default config, save it to disk,
     * and return that.
     */
    public static ModConfig loadModConfig() {
        ModConfig toReturn;

        if(Files.isReadable(configPath)) {
            toReturn = loadFromDisk();
        } else {
            toReturn = new ModConfig();

            if(Files.notExists(configPath)) {
                try {
                    saveToDisk(toReturn);
                } catch (IOException e) {
                    McServerStop.LOG.warn("Unable to save default config!");
                }
            } else {
                McServerStop.LOG.warn("The config file might exist but it isn't readable. Human action required.");
            }
        }

        return toReturn;
    }

    private static ModConfig loadFromDisk() {
        Gson gson = new Gson();

        try {
            return gson.fromJson(Files.newBufferedReader(configPath), ModConfig.class);
        } catch (IOException e) {
            McServerStop.LOG.error("Failed to read readable file {}.", configPath);
            throw new RuntimeException(e);
        }
    }

    private static void saveToDisk(ModConfig modConfig) throws IOException {
        Files.createDirectories(configPath.getParent());
        Files.createFile(configPath);

        Gson gson = new Gson();
        Files.writeString(configPath, gson.toJson(modConfig));
    }
}
