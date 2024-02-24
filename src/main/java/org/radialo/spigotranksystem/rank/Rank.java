package org.radialo.spigotranksystem.rank;

import org.bukkit.ChatColor;

public enum Rank {
    COPPER(ChatColor.YELLOW + "Copper"),
    IRON(ChatColor.GRAY + "Iron"),
    GOLD(ChatColor.GOLD + "Gold"),
    DIAMOND(ChatColor.AQUA + "Diamond"),
    NETHERITE(ChatColor.DARK_RED + "Netherite");

    private String display;

    Rank(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
