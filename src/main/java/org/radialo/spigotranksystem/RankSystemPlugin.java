package org.radialo.spigotranksystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.radialo.spigotranksystem.nametag.NametagManager;
import org.radialo.spigotranksystem.rank.RankCommand;
import org.radialo.spigotranksystem.rank.RankListener;
import org.radialo.spigotranksystem.rank.RankManager;

import java.util.Objects;

public final class RankSystemPlugin extends JavaPlugin {
    private final RankManager rankManager = new RankManager(this);
    private final NametagManager nametagManager = new NametagManager(this);

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("rank"))
                .setExecutor(new RankCommand(this));

        Bukkit.getPluginManager().registerEvents(new RankListener(this), this);
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public NametagManager getNametagManager() {
        return nametagManager;
    }
}
