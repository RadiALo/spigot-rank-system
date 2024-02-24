package org.radialo.spigotranksystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RankCommand implements CommandExecutor {
    private final RankSystemPlugin plugin;

    public RankCommand(RankSystemPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.isOp()) {
                player.sendMessage(ChatColor.RED + "You must be OP to use this command!");

            } else if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "Invalid usage! Please use /rank <player> <rank>.");
            } else {
                String targetName = args[0];
                String rankName = args[1];

                OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

                if (target == null) {
                    player.sendMessage(ChatColor.RED + "User was not found!");
                }

                for (Rank rank : Rank.values()) {
                    if (rank.name().equalsIgnoreCase(rankName)) {
                        plugin.getRankManager().setRank(target.getUniqueId(), rank);

                        player.sendMessage(
                                ChatColor.GREEN + "You changed"
                                        + target.getName() + "'s rank to " + rank.getDisplay()
                                        + ChatColor.GREEN + "."
                        );

                        if (target.isOnline()) {
                            Objects.requireNonNull(target.getPlayer()).sendMessage(
                                    ChatColor.GREEN + player.getName()
                                            + " set your rank to " + rank.getDisplay()
                                            + ChatColor.GREEN + "."
                            );
                        }

                        return false;
                    }
                }

                player.sendMessage("Invalid rank was specified!");
            }
        }

        return false;
    }
}
