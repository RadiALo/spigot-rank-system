package org.radialo.spigotranksystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.radialo.spigotranksystem.donate.DonateCommand;
import org.radialo.spigotranksystem.donate.DonateManager;
import org.radialo.spigotranksystem.nametag.NametagManager;
import org.radialo.spigotranksystem.rank.RankCommand;
import org.radialo.spigotranksystem.rank.RankListener;
import org.radialo.spigotranksystem.rank.RankManager;

import java.util.Objects;

public final class RankSystemPlugin extends JavaPlugin {
    private final RankManager rankManager = new RankManager(this);
    private final NametagManager nametagManager = new NametagManager(this);
    private final DonateManager donateManager = new DonateManager(this);

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("rank"))
                .setExecutor(new RankCommand(this));
        Objects.requireNonNull(getCommand("donate"))
                .setExecutor(new DonateCommand(this));

        Bukkit.getPluginManager().registerEvents(new RankListener(this), this);
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public NametagManager getNametagManager() {
        return nametagManager;
    }

    public DonateManager getDonateManager() {
        return donateManager;
    }
}
