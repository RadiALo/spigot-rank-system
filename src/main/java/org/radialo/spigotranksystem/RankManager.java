package org.radialo.spigotranksystem;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    public void setRank(UUID uuid, Rank rank) {
        config.set(uuid.toString(), rank.name());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Rank getRank(UUID uuid) {
        return Rank.valueOf(config.getString(uuid.toString()));
    }
}
