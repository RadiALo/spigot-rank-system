package org.radialo.spigotranksystem.rank;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.radialo.spigotranksystem.RankSystemPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class RankManager {
    private static final String FILE_NAME = "ranks.yaml";

    private final RankSystemPlugin plugin;

    private File file;
    private YamlConfiguration config;

    public RankManager(RankSystemPlugin plugin) {
        this.plugin = plugin;
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

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

        if (player.isOnline()) {
            plugin.getNametagManager()
                    .removeTag(Objects.requireNonNull(player.getPlayer()));
            
            plugin.getNametagManager()
                    .newTag(Objects.requireNonNull(player.getPlayer()));
        }
    }

    public Rank getRank(UUID uuid) {
        return Rank.valueOf(config.getString(uuid.toString()));
    }
}
