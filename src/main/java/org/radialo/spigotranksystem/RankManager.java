package org.radialo.spigotranksystem;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RankManager {
    private static final String FILE_NAME = "ranks.yaml";

    private File file;
    private YamlConfiguration config;

    public RankManager(RankSystemPlugin plugin) {
        File folder = plugin.getDataFolder();

        if (!folder.exists()) {
            folder.mkdir();
        }

        file = new File(folder, FILE_NAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }
}
