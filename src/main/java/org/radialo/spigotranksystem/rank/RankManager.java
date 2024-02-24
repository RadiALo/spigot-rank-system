package org.radialo.spigotranksystem.rank;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.radialo.spigotranksystem.RankSystemPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class RankManager {
    private static final String FILE_NAME = "ranks.yaml";

    private final Map<UUID, PermissionAttachment> permissions = new HashMap<>();
    private final RankSystemPlugin plugin;
    private final File file;
    private final YamlConfiguration config;

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
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
            Player player = Bukkit.getPlayer(uuid);

            PermissionAttachment attachment;

            if (permissions.containsKey(uuid)) {
                attachment = permissions.get(uuid);
            } else {
                attachment = player.addAttachment(plugin);
                permissions.put(uuid, attachment);
            }

            // Remove old rank permissions
            if (player.hasPlayedBefore()) {
                for (String permission : getRank(uuid).getPermissions()) {
                    if (player.hasPermission(permission)) {
                        attachment.unsetPermission(permission);
                    }
                }
            }

            // Add new rank permissions
            for (String permission : rank.getPermissions()) {
                attachment.setPermission(permission, true);
            }
        }

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
