package org.radialo.spigotranksystem.rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.radialo.spigotranksystem.RankSystemPlugin;

public class RankListener implements Listener {
    private RankSystemPlugin plugin;

    public RankListener(RankSystemPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            plugin.getRankManager().setRank(player.getUniqueId(), Rank.COPPER);
        }

        plugin.getNametagManager().setNametags(player);
        plugin.getNametagManager().newTag(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getNametagManager().removeTag(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();

        Bukkit.broadcastMessage(
                plugin.getRankManager().getRank(player.getUniqueId()).getDisplay()
                + " " + player.getName() + ": " + ChatColor.GRAY + event.getMessage()
        );
    }
}
