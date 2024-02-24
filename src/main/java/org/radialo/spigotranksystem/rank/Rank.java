package org.radialo.spigotranksystem.rank;

import org.bukkit.ChatColor;

public enum Rank {
    COPPER(ChatColor.YELLOW + "Copper", new String[] {}),
    IRON(ChatColor.GRAY + "Iron", new String[] {}),
    GOLD(ChatColor.GOLD + "Gold", new String[] {}),
    DIAMOND(ChatColor.AQUA + "Diamond", new String[] {"worldedit.help"}),
    NETHERITE(ChatColor.DARK_RED + "Netherite", new String[] {"worldedit.help"  });

    private String display;
    private String[] permissions;

    Rank(String display, String[] permissions) {
        this.display = display;
        this.permissions = permissions;
    }

    public String getDisplay() {
        return display;
    }

    public String[] getPermissions() {
        return permissions;
    }
}
