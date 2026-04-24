package com.github.Syaaddd.coralForgeGrow.setup;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Groups {

    /** Satu kategori CoralForge di Slimefun Guide. */
    public static ItemGroup MAIN;

    private Groups() {}

    public static void setup(JavaPlugin plugin) {
        MAIN = new ItemGroup(
            new NamespacedKey(plugin, "coralforge"),
            new CustomItemStack(
                Material.PRISMARINE_CRYSTALS,
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "CoralForge",
                ChatColor.GRAY + "Underwater Hardcore Gear"
            )
        );
    }
}
