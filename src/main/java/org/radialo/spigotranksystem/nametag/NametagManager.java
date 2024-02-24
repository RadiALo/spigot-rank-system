package org.radialo.spigotranksystem.nametag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.radialo.spigotranksystem.RankSystemPlugin;
import org.radialo.spigotranksystem.rank.Rank;

import java.util.Objects;

public class NametagManager {
    private final RankSystemPlugin plugin;

    public NametagManager(RankSystemPlugin plugin) {
        this.plugin = plugin;
    }

    public void setNametags(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        for (Rank rank : Rank.values()) {
            Team team = player.getScoreboard().registerNewTeam(rank.name());
            team.setPrefix(ChatColor.BOLD + rank.getDisplay() + " " + ChatColor.RESET);
        }

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.getUniqueId().equals(player.getUniqueId())) {
                continue;
            }

            Rank rank = plugin.getRankManager().getRank(target.getUniqueId());

            player.getScoreboard().getTeam(rank.name())
                    .addEntry(target.getName());
        }
    }

    public void newTag(Player player) {
        Rank rank = plugin.getRankManager().getRank(player.getUniqueId());

        for (Player target : Bukkit.getOnlinePlayers()) {
            Objects.requireNonNull(target.getScoreboard().getTeam(rank.name()))
                    .addEntry(player.getName());
        }
    }

    public void removeTag(Player player) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            Team entryTeam = target.getScoreboard().getEntryTeam(player.getName());

            if (entryTeam != null) {
                entryTeam.removeEntry(player.getName());
            }
        }
    }
}
