package com.github.Syaaddd.coralForgeGrow.setup;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")

/**
 * Registers Slimefun Research nodes for every CoralForge item.
 * IDs 22001-22015 are reserved for this addon.
 *
 * Tier costs (minimum 100 levels as configured):
 *   T0 Machines     : 100 lvl
 *   T1 Coral        : 100 lvl
 *   T2 Abyssal      : 150 lvl
 *   T3 Pressure     : 200 lvl
 *   T4 Void Legend  : 300 lvl
 */
public final class ResearchSetup {

    private ResearchSetup() {}

    public static void setup(SlimefunAddon addon) {
        JavaPlugin plugin = addon.getJavaPlugin();

        // ── T0: Machines ──────────────────────────────────────────────────────
        research(plugin, addon, "cfg_machines", 22001,
            "CoralForge Machines", 100,
            "CFG_WORKBENCH", "CFG_INFUSER"
        );

        // ── T1: Intermediate materials ────────────────────────────────────────
        research(plugin, addon, "cfg_t1_materials", 22002,
            "Coral Materials", 100,
            "CFG_ABYSSAL_INGOT", "CFG_TIDAL_ALLOY"
        );

        // ── T1: Coral Armor ───────────────────────────────────────────────────
        research(plugin, addon, "cfg_coral_armor", 22003,
            "Coral Armor", 100,
            "CFG_CORAL_HELMET", "CFG_CORAL_CHESTPLATE",
            "CFG_CORAL_LEGGINGS", "CFG_CORAL_BOOTS"
        );

        // ── T1: Coral Weapons & Tools ─────────────────────────────────────────
        research(plugin, addon, "cfg_coral_weapons", 22004,
            "Coral Weapons & Tools", 100,
            "CFG_CORAL_DAGGER", "CFG_KELP_BLADE", "CFG_TIDE_PICKAXE"
        );

        // ── T1: Basic Utilities ───────────────────────────────────────────────
        research(plugin, addon, "cfg_basic_utilities", 22005,
            "Coral Utilities", 100,
            "CFG_PEARL_LANTERN", "CFG_DEPTH_GAUGE", "CFG_ABYSSAL_TORCH"
        );

        // ── T2: Advanced Materials ────────────────────────────────────────────
        research(plugin, addon, "cfg_t2_materials", 22006,
            "Abyssal Materials", 150,
            "CFG_PRESSURE_PLATE_MAT", "CFG_TIDESTONE_SLAB"
        );

        // ── T2: Abyssal Armor ─────────────────────────────────────────────────
        research(plugin, addon, "cfg_abyssal_armor", 22007,
            "Abyssal Armor", 150,
            "CFG_ABYSSAL_HELMET", "CFG_ABYSSAL_CHESTPLATE",
            "CFG_ABYSSAL_LEGGINGS", "CFG_ABYSSAL_BOOTS"
        );

        // ── T2: Abyssal Weapons ───────────────────────────────────────────────
        research(plugin, addon, "cfg_abyssal_weapons", 22008,
            "Abyssal Weapons & Tools", 150,
            "CFG_ABYSSAL_BLADE", "CFG_TIDE_RIP_TRIDENT", "CFG_ABYSSAL_DRILL"
        );

        // ── T2: Advanced Utilities ────────────────────────────────────────────
        research(plugin, addon, "cfg_advanced_utilities", 22009,
            "Abyssal Utilities", 150,
            "CFG_PRESSURE_COMPASS", "CFG_TIDE_BEACON"
        );

        // ── T3: Pressure Armor ────────────────────────────────────────────────
        research(plugin, addon, "cfg_pressure_armor", 22010,
            "Pressure Armor", 200,
            "CFG_PRESSURE_HELMET", "CFG_PRESSURE_CHESTPLATE",
            "CFG_PRESSURE_LEGGINGS", "CFG_PRESSURE_BOOTS"
        );

        // ── T3: Pressure Weapons ──────────────────────────────────────────────
        research(plugin, addon, "cfg_pressure_weapons", 22011,
            "Pressure Weapons & Tools", 200,
            "CFG_PRESSURE_SPEAR", "CFG_DEEP_CUTTER", "CFG_ABYSS_EXCAVATOR"
        );

        // ── T3: Elite Utility ─────────────────────────────────────────────────
        research(plugin, addon, "cfg_elite_utilities", 22012,
            "Pressure Utilities", 200,
            "CFG_VOID_LENS"
        );

        // ── T4: Void Materials ────────────────────────────────────────────────
        research(plugin, addon, "cfg_void_materials", 22013,
            "Void Materials", 250,
            "CFG_VOID_ALLOY", "CFG_CORE_SHARD"
        );

        // ── T4: Void Armor ────────────────────────────────────────────────────
        research(plugin, addon, "cfg_void_armor", 22014,
            "Void Legendary Armor", 300,
            "CFG_VOID_HELMET", "CFG_VOID_CHESTPLATE",
            "CFG_VOID_LEGGINGS", "CFG_VOID_BOOTS"
        );

        // ── T4: Void Weapons ──────────────────────────────────────────────────
        research(plugin, addon, "cfg_void_weapons", 22015,
            "Void Legendary Weapons", 300,
            "CFG_VOID_REAVER", "CFG_VOID_TRIDENT"
        );

        // ── T5: Sovereign Materials ───────────────────────────────────────────
        research(plugin, addon, "cfg_sovereign_materials", 22016,
            "Sovereign Materials", 350,
            "CFG_SOVEREIGN_INGOT", "CFG_ABYSSAL_CROWN_GEM", "CFG_SOVEREIGN_CORE"
        );

        // ── T5: Abyss Sovereign Armor ─────────────────────────────────────────
        research(plugin, addon, "cfg_sovereign_armor", 22017,
            "Abyss Sovereign Armor", 400,
            "CFG_SOVEREIGN_HELMET", "CFG_SOVEREIGN_CHESTPLATE",
            "CFG_SOVEREIGN_LEGGINGS", "CFG_SOVEREIGN_BOOTS"
        );
    }

    private static void research(JavaPlugin plugin, SlimefunAddon addon,
                                  String key, int id, String name, int levelCost,
                                  String... itemIds) {
        Research r = new Research(new NamespacedKey(plugin, key), id, name, levelCost);
        for (String itemId : itemIds) {
            SlimefunItem item = SlimefunItem.getById(itemId);
            if (item != null) {
                r.addItems(item);
            }
        }
        r.register();
    }
}
