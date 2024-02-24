package org.radialo.spigotranksystem.donate;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.radialo.spigotranksystem.RankSystemPlugin;

public class DonateCommand implements CommandExecutor {
    public final RankSystemPlugin plugin;

    public DonateCommand(RankSystemPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Invalid usage! Use /donate <amount>");
            } else {
                try {
                    int amount = Integer.parseInt(args[0]);
                    plugin.getDonateManager().donate(player.getUniqueId(), amount);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid number!");
                }
            }
        }

        return false;
    }
}
