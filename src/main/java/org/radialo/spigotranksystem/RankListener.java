package org.radialo.spigotranksystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
    }
}
