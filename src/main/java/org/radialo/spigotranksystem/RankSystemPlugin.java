package org.radialo.spigotranksystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RankSystemPlugin extends JavaPlugin {
    private final RankManager rankManager = new RankManager(this);

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("rank"))
                .setExecutor(new RankCommand(this));

        Bukkit.getPluginManager().registerEvents(new RankListener(this), this);
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}
