package org.radialo.spigotranksystem.donate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.radialo.spigotranksystem.RankSystemPlugin;
import org.radialo.spigotranksystem.rank.Rank;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class DonateManager {
    private final Map<Rank, Integer> donateRanks = Map.of(
            Rank.COPPER, 0,
            Rank.IRON, 100,
            Rank.GOLD, 500,
            Rank.DIAMOND, 1000,
            Rank.NETHERITE, 5000
    );

    private static final String FILE_NAME = "donate.yaml";
    private final RankSystemPlugin plugin;
    private final File file;
    private final YamlConfiguration config;
    public DonateManager(RankSystemPlugin plugin) {
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

    public int donate(UUID uuid, int amount) {
        int value = 0;

        if (config.contains(uuid.toString())) {
            value = config.getInt(uuid.toString());
        }

        value += amount;
        config.set(uuid.toString(), value);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        checkRankUp(uuid, value);

        return value;
    }

    public void checkRankUp(UUID uuid, int amount) {
        Rank rank = plugin.getRankManager().getRank(uuid);
        int rankMin = donateRanks.get(rank);
        Rank newRank = rank;
        int newMin = 0;

        for (Map.Entry<Rank, Integer> entry : donateRanks.entrySet()) {
            int value = entry.getValue();

            if (value < amount && value > newMin) {
                newMin = value;
                newRank = entry.getKey();
            }
        }

        if (!rank.equals(newRank)) {
            plugin.getRankManager().setRank(uuid, newRank);

            Bukkit.getPlayer(uuid).sendMessage(
                    ChatColor.GREEN + "Congratulations! You have succesfully gained "
                            + newRank.getDisplay() + ChatColor.RESET
                            + ChatColor.GREEN + " rank!"
            );
        }
    }
}
