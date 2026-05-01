package com.github.Syaaddd.coralForgeGrow.setup;

import com.github.Syaaddd.coralForgeGrow.items.armor.AbyssSovereignArmor;
import com.github.Syaaddd.coralForgeGrow.items.armor.CoralForgeArmor;
import com.github.Syaaddd.coralForgeGrow.items.utilities.CoralForgeUtility;
import com.github.Syaaddd.coralForgeGrow.items.weapons.CoralForgeSword;
import com.github.Syaaddd.coralForgeGrow.items.weapons.CoralForgeTool;
import com.github.Syaaddd.coralForgeGrow.items.weapons.CoralForgeTrident;
import com.github.Syaaddd.coralForgeGrow.machines.CoralForgeInfuser;
import com.github.Syaaddd.coralForgeGrow.machines.CoralForgeWorkbench;
import com.github.Syaaddd.coralForgeGrow.machines.WorkbenchRecipe;
import com.github.Syaaddd.coralForgeGrow.utils.OceanMinerItems;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static com.github.Syaaddd.coralForgeGrow.setup.Groups.MAIN;
import static com.github.Syaaddd.coralForgeGrow.utils.OceanMinerItems.*;

public final class ItemSetup {

    private static RecipeType WORKBENCH_TYPE;
    private static RecipeType INFUSER_TYPE;

    private ItemSetup() {}

    public static void setup(SlimefunAddon addon) {
        JavaPlugin plugin = addon.getJavaPlugin();
        // ── Machines (registered first so RecipeType can reference them) ──────
        registerMachines(addon, plugin);

        // ── Intermediate materials ────────────────────────────────────────────
        registerMaterials(addon);

        // ── Armor ─────────────────────────────────────────────────────────────
        registerArmor(addon);

        // ── Weapons & Tools ───────────────────────────────────────────────────
        registerWeapons(addon);

        // ── Utilities ─────────────────────────────────────────────────────────
        registerUtilities(addon);

        // ── Workbench recipes (registered AFTER all items are registered) ─────
        registerWorkbenchRecipes();
        registerInfuserRecipes();

        // ── Research (unlock system, registered last) ─────────────────────────
        ResearchSetup.setup(addon);
    }

    // =========================================================================
    // MACHINES
    // =========================================================================

    private static void registerMachines(SlimefunAddon addon, JavaPlugin plugin) {
        SlimefunItemStack workbenchStack = new SlimefunItemStack(
            "CFG_WORKBENCH",
            Material.BLAST_FURNACE,
            ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "CoralForge Workbench",
            ChatColor.GRAY + "Craft CoralForge materials and armor",
            ChatColor.GRAY + "Requires OceanMiner materials"
        );

        CoralForgeWorkbench workbench = new CoralForgeWorkbench(
            MAIN, workbenchStack,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                new ItemStack(Material.PRISMARINE_BRICKS), new ItemStack(Material.PRISMARINE_BRICKS), new ItemStack(Material.PRISMARINE_BRICKS),
                new ItemStack(Material.PRISMARINE_BRICKS), new ItemStack(Material.FURNACE),            new ItemStack(Material.PRISMARINE_BRICKS),
                new ItemStack(Material.PRISMARINE_BRICKS), new ItemStack(Material.PRISMARINE_BRICKS), new ItemStack(Material.PRISMARINE_BRICKS)
            }
        );
        workbench.register(addon);
        WORKBENCH_TYPE = new RecipeType(
            new NamespacedKey(plugin, "coralforge_workbench"), workbenchStack);

        SlimefunItemStack infuserStack = new SlimefunItemStack(
            "CFG_INFUSER",
            Material.RESPAWN_ANCHOR,
            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "CoralForge Infuser",
            ChatColor.GRAY + "Infuse Legendary materials",
            ChatColor.DARK_PURPLE + "Required for Void Alloy"
        );

        CoralForgeInfuser infuser = new CoralForgeInfuser(
            MAIN, infuserStack,
            WORKBENCH_TYPE,
            new ItemStack[]{
                new ItemStack(Material.OBSIDIAN),        new ItemStack(Material.CRYING_OBSIDIAN),   new ItemStack(Material.OBSIDIAN),
                new ItemStack(Material.CRYING_OBSIDIAN), new ItemStack(Material.RESPAWN_ANCHOR),    new ItemStack(Material.CRYING_OBSIDIAN),
                new ItemStack(Material.OBSIDIAN),        new ItemStack(Material.CRYING_OBSIDIAN),   new ItemStack(Material.OBSIDIAN)
            }
        );
        infuser.register(addon);
        INFUSER_TYPE = new RecipeType(
            new NamespacedKey(plugin, "coralforge_infuser"), infuserStack);
    }

    // =========================================================================
    // INTERMEDIATE MATERIALS
    // =========================================================================

    private static void registerMaterials(SlimefunAddon addon) {
        // Abyssal Ingot: Abyssite(6) + Iron Ingot SF (2) — use vanilla iron ingot as placeholder
        SlimefunItemStack abyssalIngot = new SlimefunItemStack(
            "CFG_ABYSSAL_INGOT", Material.IRON_INGOT,
            ChatColor.DARK_BLUE + "Abyssal Ingot",
            ChatColor.GRAY + "Intermediate material for T2 items"
        );
        new SlimefunItem(MAIN, abyssalIngot, WORKBENCH_TYPE,
            recipeOf(ABYSSITE, ABYSSITE, ABYSSITE, ABYSSITE, ABYSSITE, ABYSSITE,
                     iron(), iron(), null)
        ).register(addon);

        // Tidal Alloy: Tridentite Shard(4) + Abyssal Ingot(2)
        SlimefunItemStack tidalAlloy = new SlimefunItemStack(
            "CFG_TIDAL_ALLOY", Material.GOLD_INGOT,
            ChatColor.AQUA + "Tidal Alloy",
            ChatColor.GRAY + "Intermediate material for T2 weapons"
        );
        new SlimefunItem(MAIN, tidalAlloy, WORKBENCH_TYPE,
            recipeOf(TRIDENTITE_SHARD, TRIDENTITE_SHARD, TRIDENTITE_SHARD, TRIDENTITE_SHARD,
                     "CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT", null, null, null)
        ).register(addon);

        // Pressure Plate Mat: Pressure Gem(3) + Abyssal Ingot(4)
        SlimefunItemStack pressurePlateMat = new SlimefunItemStack(
            "CFG_PRESSURE_PLATE_MAT", Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
            ChatColor.BLUE + "Pressure Plate Mat",
            ChatColor.GRAY + "Intermediate material for T3 items"
        );
        new SlimefunItem(MAIN, pressurePlateMat, WORKBENCH_TYPE,
            recipeOf(PRESSURE_GEM, PRESSURE_GEM, PRESSURE_GEM,
                     "CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT",
                     "CFG_ABYSSAL_INGOT", null, null)
        ).register(addon);

        // Tidestone Slab: Tidestone Fragment(8) + Pressure Gem(1)
        SlimefunItemStack tidestoneSlab = new SlimefunItemStack(
            "CFG_TIDESTONE_SLAB", Material.PRISMARINE_SLAB,
            ChatColor.DARK_AQUA + "Tidestone Slab",
            ChatColor.GRAY + "Intermediate material for T3 armor & weapons"
        );
        new SlimefunItem(MAIN, tidestoneSlab, WORKBENCH_TYPE,
            recipeOf(TIDESTONE_FRAGMENT, TIDESTONE_FRAGMENT, TIDESTONE_FRAGMENT,
                     TIDESTONE_FRAGMENT, TIDESTONE_FRAGMENT, TIDESTONE_FRAGMENT,
                     TIDESTONE_FRAGMENT, TIDESTONE_FRAGMENT, PRESSURE_GEM)
        ).register(addon);

        // Void Alloy: Void Crystal(2) + Pressure Plate Mat(2) + Abyssal Core(1) — Infuser only
        SlimefunItemStack voidAlloy = new SlimefunItemStack(
            "CFG_VOID_ALLOY", Material.NETHERITE_INGOT,
            ChatColor.DARK_PURPLE + "Void Alloy",
            ChatColor.GRAY + "Legendary material for T4 items",
            ChatColor.DARK_PURPLE + "Craft in CoralForge Infuser"
        );
        new SlimefunItem(MAIN, voidAlloy, INFUSER_TYPE,
            recipeOf(VOID_CRYSTAL, VOID_CRYSTAL, "CFG_PRESSURE_PLATE_MAT",
                     "CFG_PRESSURE_PLATE_MAT", ABYSSAL_CORE, null, null, null, null)
        ).register(addon);

        // Core Shard: Abyssal Core(2) + Tidestone Slab(1)
        SlimefunItemStack coreShard = new SlimefunItemStack(
            "CFG_CORE_SHARD", Material.AMETHYST_SHARD,
            ChatColor.DARK_PURPLE + "Core Shard",
            ChatColor.GRAY + "Weapon endgame material"
        );
        new SlimefunItem(MAIN, coreShard, WORKBENCH_TYPE,
            recipeOf(ABYSSAL_CORE, ABYSSAL_CORE, "CFG_TIDESTONE_SLAB",
                     null, null, null, null, null, null)
        ).register(addon);

        // ── T5 Sovereign Intermediate Materials (Infuser only) ────────────────

        // Sovereign Ingot: Void Alloy(10) + Billon Ingot(15) + Abyssal Core(10) + Reinforced Alloy(10)
        SlimefunItemStack sovereignIngot = new SlimefunItemStack(
            "CFG_SOVEREIGN_INGOT", Material.NETHERITE_INGOT,
            ChatColor.GOLD + "" + ChatColor.BOLD + "Sovereign Ingot",
            ChatColor.GRAY + "Legendary alloy for T5 Abyss Sovereign Set",
            ChatColor.GOLD + "Craft in CoralForge Infuser"
        );
        new SlimefunItem(MAIN, sovereignIngot, INFUSER_TYPE,
            recipeOf("CFG_VOID_ALLOY", "CFG_VOID_ALLOY", "BILLON_INGOT",
                     "BILLON_INGOT", ABYSSAL_CORE, ABYSSAL_CORE,
                     "REINFORCED_ALLOY_INGOT", "REINFORCED_ALLOY_INGOT", null)
        ).register(addon);

        // Abyssal Crown Gem: Void Crystal(10) + Pressure Gem(15) + Tidestone Fragment(20)
        SlimefunItemStack abyssalCrownGem = new SlimefunItemStack(
            "CFG_ABYSSAL_CROWN_GEM", Material.ECHO_SHARD,
            ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Abyssal Crown Gem",
            ChatColor.GRAY + "Focused gem for Sovereign Helmet",
            ChatColor.DARK_AQUA + "Craft in CoralForge Infuser"
        );
        new SlimefunItem(MAIN, abyssalCrownGem, INFUSER_TYPE,
            recipeOf(VOID_CRYSTAL, VOID_CRYSTAL, VOID_CRYSTAL,
                     PRESSURE_GEM, PRESSURE_GEM, TIDESTONE_FRAGMENT,
                     TIDESTONE_FRAGMENT, TIDESTONE_FRAGMENT, null)
        ).register(addon);

        // Sovereign Core: Sovereign Ingot(10) + Void Crystal(10) + Abyssal Core(15) + Damascus Steel(20)
        SlimefunItemStack sovereignCore = new SlimefunItemStack(
            "CFG_SOVEREIGN_CORE", Material.NETHER_STAR,
            ChatColor.GOLD + "" + ChatColor.BOLD + "Sovereign Core",
            ChatColor.GRAY + "Core crystal for Sovereign Chestplate & Leggings",
            ChatColor.GOLD + "Craft in CoralForge Infuser"
        );
        new SlimefunItem(MAIN, sovereignCore, INFUSER_TYPE,
            recipeOf("CFG_SOVEREIGN_INGOT", "CFG_SOVEREIGN_INGOT", VOID_CRYSTAL,
                     VOID_CRYSTAL, ABYSSAL_CORE, ABYSSAL_CORE,
                     ABYSSAL_CORE, "DAMASCUS_STEEL", "DAMASCUS_STEEL")
        ).register(addon);
    }

    // =========================================================================
    // ARMOR
    // =========================================================================

    private static void registerArmor(SlimefunAddon addon) {

        // ── T1 Coral ──────────────────────────────────────────────────────────
        new CoralForgeArmor(MAIN, item("CFG_CORAL_HELMET", Material.GOLDEN_HELMET,
            ChatColor.GREEN + "Coral Helmet",
            ChatColor.GRAY + "Night Vision saat di air"),
            WORKBENCH_TYPE, recipeOf(CORAL_DUST, CORAL_DUST, CORAL_DUST, CORAL_DUST, null, CORAL_DUST, PEARLSTONE, PEARLSTONE, PEARLSTONE),
            List.of(effect(PotionEffectType.NIGHT_VISION, 0))
        ).register(addon);

        new CoralForgeArmor(MAIN, item("CFG_CORAL_CHESTPLATE", Material.GOLDEN_CHESTPLATE,
            ChatColor.GREEN + "Coral Chestplate",
            ChatColor.GRAY + "Water Breathing saat di air"),
            WORKBENCH_TYPE, recipeOf(CORAL_DUST, null, CORAL_DUST, CORAL_DUST, CORAL_DUST, CORAL_DUST, CORAL_DUST, CORAL_DUST, CORAL_DUST),
            List.of(effect(PotionEffectType.WATER_BREATHING, 0))
        ).register(addon);

        new CoralForgeArmor(MAIN, item("CFG_CORAL_LEGGINGS", Material.GOLDEN_LEGGINGS,
            ChatColor.GREEN + "Coral Leggings",
            ChatColor.GRAY + "Speed I saat di air"),
            WORKBENCH_TYPE, recipeOf(CORAL_DUST, CORAL_DUST, CORAL_DUST, CORAL_DUST, null, CORAL_DUST, PEARLSTONE, null, PEARLSTONE),
            List.of(effect(PotionEffectType.SPEED, 0))
        ).register(addon);

        SlimefunItemStack coralBoots = item("CFG_CORAL_BOOTS", Material.GOLDEN_BOOTS,
            ChatColor.GREEN + "Coral Boots",
            ChatColor.GRAY + "Depth Strider III");
        coralBoots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        new CoralForgeArmor(MAIN, coralBoots,
            WORKBENCH_TYPE, recipeOf(null, null, null, CORAL_DUST, null, CORAL_DUST, PEARLSTONE, null, PEARLSTONE),
            List.of()
        ).register(addon);

        // ── T2 Abyssal ────────────────────────────────────────────────────────
        new CoralForgeArmor(MAIN, item("CFG_ABYSSAL_HELMET", Material.CHAINMAIL_HELMET,
            ChatColor.DARK_AQUA + "Abyssal Helmet",
            ChatColor.GRAY + "Night Vision + Conduit Power saat di air"),
            WORKBENCH_TYPE, recipeOf(ABYSSITE, ABYSSITE, ABYSSITE, ABYSSITE, null, ABYSSITE, "CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT"),
            List.of(effect(PotionEffectType.NIGHT_VISION, 0), effect(PotionEffectType.CONDUIT_POWER, 0))
        ).register(addon);

        new CoralForgeArmor(MAIN, item("CFG_ABYSSAL_CHESTPLATE", Material.CHAINMAIL_CHESTPLATE,
            ChatColor.DARK_AQUA + "Abyssal Chestplate",
            ChatColor.GRAY + "Water Breathing + Resistance I saat di air"),
            WORKBENCH_TYPE, recipeOf(ABYSSITE, null, ABYSSITE, "CFG_ABYSSAL_INGOT", ABYSSITE, "CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT", TRIDENTITE_SHARD, "CFG_ABYSSAL_INGOT"),
            List.of(effect(PotionEffectType.WATER_BREATHING, 0), effect(PotionEffectType.RESISTANCE, 0))
        ).register(addon);

        new CoralForgeArmor(MAIN, item("CFG_ABYSSAL_LEGGINGS", Material.CHAINMAIL_LEGGINGS,
            ChatColor.DARK_AQUA + "Abyssal Leggings",
            ChatColor.GRAY + "Speed II + Slow Falling saat di air"),
            WORKBENCH_TYPE, recipeOf(ABYSSITE, ABYSSITE, ABYSSITE, "CFG_ABYSSAL_INGOT", null, "CFG_ABYSSAL_INGOT", TRIDENTITE_SHARD, TRIDENTITE_SHARD, null),
            List.of(effect(PotionEffectType.SPEED, 1), effect(PotionEffectType.SLOW_FALLING, 0))
        ).register(addon);

        SlimefunItemStack abyssalBoots = item("CFG_ABYSSAL_BOOTS", Material.CHAINMAIL_BOOTS,
            ChatColor.DARK_AQUA + "Abyssal Boots",
            ChatColor.GRAY + "Depth Strider III + Dolphin's Grace");
        abyssalBoots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        new CoralForgeArmor(MAIN, abyssalBoots,
            WORKBENCH_TYPE, recipeOf(null, null, null, ABYSSITE, null, ABYSSITE, "CFG_ABYSSAL_INGOT", TRIDENTITE_SHARD, "CFG_ABYSSAL_INGOT"),
            List.of(effect(PotionEffectType.DOLPHINS_GRACE, 0))
        ).register(addon);

        // ── T3 Pressure ───────────────────────────────────────────────────────
        // T3 Helmet: always effects (Night Vision + Conduit Power lvl 3)
        new CoralForgeArmor(MAIN, item("CFG_PRESSURE_HELMET", Material.IRON_HELMET,
            ChatColor.BLUE + "Pressure Helmet",
            ChatColor.GRAY + "Full underwater vision, Conduit Power III"),
            WORKBENCH_TYPE, recipeOf(PRESSURE_GEM, PRESSURE_GEM, PRESSURE_GEM, "CFG_PRESSURE_PLATE_MAT", null, "CFG_PRESSURE_PLATE_MAT", "CFG_TIDESTONE_SLAB", ABYSSITE, ABYSSITE),
            List.of(effect(PotionEffectType.WATER_BREATHING, 0)),   // water effects
            List.of(effect(PotionEffectType.NIGHT_VISION, 0),        // always effects
                    effect(PotionEffectType.CONDUIT_POWER, 2))
        ).register(addon);

        new CoralForgeArmor(MAIN, item("CFG_PRESSURE_CHESTPLATE", Material.IRON_CHESTPLATE,
            ChatColor.BLUE + "Pressure Chestplate",
            ChatColor.GRAY + "Water Breathing + Resistance II + Speed I"),
            WORKBENCH_TYPE, recipeOf(PRESSURE_GEM, null, PRESSURE_GEM, "CFG_PRESSURE_PLATE_MAT", ABYSSITE, "CFG_PRESSURE_PLATE_MAT", "CFG_TIDESTONE_SLAB", TRIDENTITE_SHARD, "CFG_TIDESTONE_SLAB"),
            List.of(effect(PotionEffectType.WATER_BREATHING, 0), effect(PotionEffectType.RESISTANCE, 1), effect(PotionEffectType.SPEED, 0))
        ).register(addon);

        new CoralForgeArmor(MAIN, item("CFG_PRESSURE_LEGGINGS", Material.IRON_LEGGINGS,
            ChatColor.BLUE + "Pressure Leggings",
            ChatColor.GRAY + "Speed III saat di air"),
            WORKBENCH_TYPE, recipeOf(PRESSURE_GEM, PRESSURE_GEM, PRESSURE_GEM, "CFG_PRESSURE_PLATE_MAT", null, "CFG_PRESSURE_PLATE_MAT", TRIDENTITE_SHARD, "CFG_TIDAL_ALLOY", TRIDENTITE_SHARD),
            List.of(effect(PotionEffectType.SPEED, 2))
        ).register(addon);

        SlimefunItemStack pressureBoots = item("CFG_PRESSURE_BOOTS", Material.IRON_BOOTS,
            ChatColor.BLUE + "Pressure Boots",
            ChatColor.GRAY + "Depth Strider III + Feather Falling IV");
        pressureBoots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        pressureBoots.addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 4);
        new CoralForgeArmor(MAIN, pressureBoots,
            WORKBENCH_TYPE, recipeOf(null, null, null, PRESSURE_GEM, null, PRESSURE_GEM, "CFG_TIDESTONE_SLAB", ABYSSITE, "CFG_PRESSURE_PLATE_MAT"),
            List.of(effect(PotionEffectType.SLOW_FALLING, 0))
        ).register(addon);

        // ── T4 Void (Legendary) ───────────────────────────────────────────────
        new CoralForgeArmor(MAIN, item("CFG_VOID_HELMET", Material.NETHERITE_HELMET,
            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Void Helmet",
            ChatColor.GRAY + "All water effects MAX + Haste II"),
            WORKBENCH_TYPE, recipeOf(VOID_CRYSTAL, "CFG_VOID_ALLOY", VOID_CRYSTAL, ABYSSAL_CORE, "CFG_VOID_ALLOY", ABYSSAL_CORE, PRESSURE_GEM, ABYSSAL_CORE, PRESSURE_GEM),
            List.of(effect(PotionEffectType.WATER_BREATHING, 0), effect(PotionEffectType.DOLPHINS_GRACE, 0)),
            List.of(effect(PotionEffectType.NIGHT_VISION, 0), effect(PotionEffectType.CONDUIT_POWER, 2), effect(PotionEffectType.HASTE, 1))
        ).register(addon);

        new CoralForgeArmor(MAIN, item("CFG_VOID_CHESTPLATE", Material.NETHERITE_CHESTPLATE,
            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Void Chestplate",
            ChatColor.GRAY + "Resistance III + Water Breathing + Speed II"),
            WORKBENCH_TYPE, recipeOf("CFG_VOID_ALLOY", VOID_CRYSTAL, "CFG_VOID_ALLOY", ABYSSAL_CORE, "CFG_VOID_ALLOY", ABYSSAL_CORE, PRESSURE_GEM, PRESSURE_GEM, PRESSURE_GEM),
            List.of(effect(PotionEffectType.WATER_BREATHING, 0), effect(PotionEffectType.SPEED, 1)),
            List.of(effect(PotionEffectType.RESISTANCE, 2))
        ).register(addon);

        SlimefunItemStack voidLeggings = item("CFG_VOID_LEGGINGS", Material.NETHERITE_LEGGINGS,
            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Void Leggings",
            ChatColor.GRAY + "Speed IV saat di air, Swift Sneak III");
        voidLeggings.addUnsafeEnchantment(Enchantment.SWIFT_SNEAK, 3);
        new CoralForgeArmor(MAIN, voidLeggings,
            WORKBENCH_TYPE, recipeOf("CFG_VOID_ALLOY", VOID_CRYSTAL, "CFG_TIDAL_ALLOY", ABYSSAL_CORE, "CFG_VOID_ALLOY", ABYSSAL_CORE, "CFG_TIDAL_ALLOY", TRIDENTITE_SHARD, PRESSURE_GEM),
            List.of(effect(PotionEffectType.SPEED, 3))
        ).register(addon);

        SlimefunItemStack voidBoots = item("CFG_VOID_BOOTS", Material.NETHERITE_BOOTS,
            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Void Boots",
            ChatColor.GRAY + "Depth Strider III + Dolphin's Grace + Feather IV");
        voidBoots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        voidBoots.addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 4);
        new CoralForgeArmor(MAIN, voidBoots,
            WORKBENCH_TYPE, recipeOf("CFG_VOID_ALLOY", VOID_CRYSTAL, "CFG_VOID_ALLOY", ABYSSAL_CORE, null, ABYSSAL_CORE, PRESSURE_GEM, null, PRESSURE_GEM),
            List.of(effect(PotionEffectType.DOLPHINS_GRACE, 0), effect(PotionEffectType.SLOW_FALLING, 0))
        ).register(addon);

        // ── T5 Abyss Sovereign (Infuser only) ────────────────────────────────
        // Unique in-water effects handled by SovereignArmorListener.
        // Full-set bonus (Conduit Power + Resistance IV) handled by ArmorListener.

        // Helmet: always Night Vision + Conduit Power III + Haste II | water Regen II
        new AbyssSovereignArmor(MAIN,
            item("CFG_SOVEREIGN_HELMET", Material.NETHERITE_HELMET,
                ChatColor.GOLD + "" + ChatColor.BOLD + "Abyss Sovereign Helmet",
                ChatColor.GRAY + "Armor +6 | Always: Night Vision, Conduit III, Haste II",
                ChatColor.AQUA + "In Water: " + ChatColor.GREEN + "Regen II (passive)"),
            INFUSER_TYPE,
            recipeOf(VOID_CRYSTAL, "CFG_SOVEREIGN_INGOT", VOID_CRYSTAL,
                     ABYSSAL_CORE, "CFG_ABYSSAL_CROWN_GEM", ABYSSAL_CORE,
                     PRESSURE_GEM, "CFG_SOVEREIGN_INGOT", PRESSURE_GEM),
            List.of(effect(PotionEffectType.REGENERATION, 1)),         // water: Regen II
            List.of(effect(PotionEffectType.NIGHT_VISION, 0),          // always
                    effect(PotionEffectType.CONDUIT_POWER, 2),
                    effect(PotionEffectType.HASTE, 1)),
            AbyssSovereignArmor.SovereignPiece.HELMET
        ).register(addon);

        // Chestplate: always Resistance III | water Water Breathing + Speed II + Thorns 25%
        new AbyssSovereignArmor(MAIN,
            item("CFG_SOVEREIGN_CHESTPLATE", Material.NETHERITE_CHESTPLATE,
                ChatColor.GOLD + "" + ChatColor.BOLD + "Abyss Sovereign Chestplate",
                ChatColor.GRAY + "Armor +10 | Always: Resistance III",
                ChatColor.AQUA + "In Water: " + ChatColor.GREEN + "Water Breath, Speed II, Thorns 25%"),
            INFUSER_TYPE,
            recipeOf(VOID_CRYSTAL, "CFG_SOVEREIGN_CORE", VOID_CRYSTAL,
                     ABYSSAL_CORE, "CFG_SOVEREIGN_INGOT", ABYSSAL_CORE,
                     PRESSURE_GEM, "CFG_SOVEREIGN_CORE", PRESSURE_GEM),
            List.of(effect(PotionEffectType.WATER_BREATHING, 0),       // water
                    effect(PotionEffectType.SPEED, 1)),
            List.of(effect(PotionEffectType.RESISTANCE, 2)),            // always: Resistance III
            AbyssSovereignArmor.SovereignPiece.CHESTPLATE
        ).register(addon);

        // Leggings: water Speed IV | Swift Sneak III enchant | XP +50% unique
        SlimefunItemStack sovereignLeggings = item("CFG_SOVEREIGN_LEGGINGS", Material.NETHERITE_LEGGINGS,
            ChatColor.GOLD + "" + ChatColor.BOLD + "Abyss Sovereign Leggings",
            ChatColor.GRAY + "Armor +9 | Swift Sneak III",
            ChatColor.AQUA + "In Water: " + ChatColor.GREEN + "Speed IV, XP +50%");
        sovereignLeggings.addUnsafeEnchantment(Enchantment.SWIFT_SNEAK, 3);
        new AbyssSovereignArmor(MAIN, sovereignLeggings,
            INFUSER_TYPE,
            recipeOf(VOID_CRYSTAL, "CFG_SOVEREIGN_CORE", VOID_CRYSTAL,
                     ABYSSAL_CORE, "CFG_SOVEREIGN_INGOT", ABYSSAL_CORE,
                     TRIDENTITE_SHARD, "DAMASCUS_STEEL", TRIDENTITE_SHARD),
            List.of(effect(PotionEffectType.SPEED, 3)),                 // water: Speed IV
            List.of(),
            AbyssSovereignArmor.SovereignPiece.LEGGINGS
        ).register(addon);

        // Boots: water Dolphin Grace + Slow Falling | Depth Strider III + Feather IV enchant | Durability -75% unique
        SlimefunItemStack sovereignBoots = item("CFG_SOVEREIGN_BOOTS", Material.NETHERITE_BOOTS,
            ChatColor.GOLD + "" + ChatColor.BOLD + "Abyss Sovereign Boots",
            ChatColor.GRAY + "Armor +5 | Depth Strider III + Feather Falling IV",
            ChatColor.AQUA + "In Water: " + ChatColor.GREEN + "Dolphin Grace, Durability -75%");
        sovereignBoots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        sovereignBoots.addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 4);
        new AbyssSovereignArmor(MAIN, sovereignBoots,
            INFUSER_TYPE,
            recipeOf(null, VOID_CRYSTAL, null,
                     ABYSSAL_CORE, "CFG_SOVEREIGN_INGOT", ABYSSAL_CORE,
                     PRESSURE_GEM, "REINFORCED_ALLOY_INGOT", PRESSURE_GEM),
            List.of(effect(PotionEffectType.DOLPHINS_GRACE, 0),         // water
                    effect(PotionEffectType.SLOW_FALLING, 0)),
            List.of(),
            AbyssSovereignArmor.SovereignPiece.BOOTS
        ).register(addon);
    }

    // =========================================================================
    // WEAPONS & TOOLS
    // =========================================================================

    private static void registerWeapons(SlimefunAddon addon) {

        // T1
        new CoralForgeSword(MAIN, item("CFG_CORAL_DAGGER", Material.IRON_SWORD,
            ChatColor.GREEN + "Coral Dagger",
            ChatColor.GRAY + "+7 damage, no slow di dalam air"),
            WORKBENCH_TYPE, recipeOf(CORAL_DUST, null, null, CORAL_DUST, null, null, PEARLSTONE, null, null),
            7, List.of(), true
        ).register(addon);

        new CoralForgeSword(MAIN, item("CFG_KELP_BLADE", Material.IRON_SWORD,
            ChatColor.GREEN + "Kelp Blade",
            ChatColor.GRAY + "+8 damage, Life Steal kecil di air"),
            WORKBENCH_TYPE, recipeOf(CORAL_DUST, CORAL_DUST, null, CORAL_DUST, CORAL_DUST, null, PEARLSTONE, PEARLSTONE, null),
            8, List.of(effect(PotionEffectType.REGENERATION, 0)), true
        ).register(addon);

        SlimefunItemStack tidePick = item("CFG_TIDE_PICKAXE", Material.IRON_PICKAXE,
            ChatColor.GREEN + "Tide Pickaxe",
            ChatColor.GRAY + "Efficiency III + Aqua Affinity");
        tidePick.addUnsafeEnchantment(Enchantment.EFFICIENCY, 3);
        tidePick.addUnsafeEnchantment(Enchantment.AQUA_AFFINITY, 1);
        new CoralForgeTool(MAIN, tidePick, WORKBENCH_TYPE,
            recipeOf(CORAL_DUST, CORAL_DUST, CORAL_DUST, null, PEARLSTONE, null, null, PEARLSTONE, null)
        ).register(addon);

        // T2
        new CoralForgeSword(MAIN, item("CFG_ABYSSAL_BLADE", Material.DIAMOND_SWORD,
            ChatColor.DARK_AQUA + "Abyssal Blade",
            ChatColor.GRAY + "+13 damage, Smite III, +15% attack speed di air"),
            WORKBENCH_TYPE, recipeOf("CFG_TIDAL_ALLOY", null, null, "CFG_TIDAL_ALLOY", null, null, ABYSSITE, TRIDENTITE_SHARD, CORAL_DUST),
            13, List.of(effect(PotionEffectType.WEAKNESS, 0)), true
        ).register(addon);

        SlimefunItemStack tideTridentStack = item("CFG_TIDE_RIP_TRIDENT", Material.TRIDENT,
            ChatColor.DARK_AQUA + "Trident: Tide Rip",
            ChatColor.GRAY + "+11 damage, Riptide V + Loyalty III");
        tideTridentStack.addUnsafeEnchantment(Enchantment.RIPTIDE, 5);
        tideTridentStack.addUnsafeEnchantment(Enchantment.LOYALTY, 3);
        new CoralForgeTrident(MAIN, tideTridentStack,
            WORKBENCH_TYPE, recipeOf("CFG_TIDAL_ALLOY", "CFG_TIDAL_ALLOY", null, ABYSSITE, ABYSSITE, null, TRIDENTITE_SHARD, TRIDENTITE_SHARD, null),
            11, List.of()
        ).register(addon);

        SlimefunItemStack abyssalDrill = item("CFG_ABYSSAL_DRILL", Material.DIAMOND_PICKAXE,
            ChatColor.DARK_AQUA + "Abyssal Drill",
            ChatColor.GRAY + "Efficiency V + Fortune II");
        abyssalDrill.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5);
        abyssalDrill.addUnsafeEnchantment(Enchantment.FORTUNE, 2);
        new CoralForgeTool(MAIN, abyssalDrill,
            WORKBENCH_TYPE, recipeOf("CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT", "CFG_ABYSSAL_INGOT", null, ABYSSITE, null, null, TRIDENTITE_SHARD, null)
        ).register(addon);

        // T3
        SlimefunItemStack pressureSpear = item("CFG_PRESSURE_SPEAR", Material.TRIDENT,
            ChatColor.BLUE + "Pressure Spear",
            ChatColor.GRAY + "+18 damage, Riptide V + Impaling V + Channeling");
        pressureSpear.addUnsafeEnchantment(Enchantment.RIPTIDE, 5);
        pressureSpear.addUnsafeEnchantment(Enchantment.IMPALING, 5);
        pressureSpear.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
        new CoralForgeTrident(MAIN, pressureSpear,
            WORKBENCH_TYPE, recipeOf("CFG_TIDAL_ALLOY", "CFG_TIDESTONE_SLAB", "CFG_TIDAL_ALLOY", PRESSURE_GEM, TIDESTONE_FRAGMENT, TRIDENTITE_SHARD, null, null, null),
            18, List.of()
        ).register(addon);

        new CoralForgeSword(MAIN, item("CFG_DEEP_CUTTER", Material.DIAMOND_SWORD,
            ChatColor.BLUE + "Deep Cutter",
            ChatColor.GRAY + "+20 damage, Sharpness V, Life Steal di air"),
            WORKBENCH_TYPE, recipeOf("CFG_TIDAL_ALLOY", "CFG_TIDESTONE_SLAB", null, PRESSURE_GEM, ABYSSITE, null, null, TRIDENTITE_SHARD, null),
            20, List.of(effect(PotionEffectType.REGENERATION, 1)), true
        ).register(addon);

        SlimefunItemStack abyssExcavator = item("CFG_ABYSS_EXCAVATOR", Material.NETHERITE_SHOVEL,
            ChatColor.BLUE + "Abyss Excavator",
            ChatColor.GRAY + "Efficiency VII + Silk Touch + Fortune III");
        abyssExcavator.addUnsafeEnchantment(Enchantment.EFFICIENCY, 7);
        abyssExcavator.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        abyssExcavator.addUnsafeEnchantment(Enchantment.FORTUNE, 3);
        new CoralForgeTool(MAIN, abyssExcavator,
            WORKBENCH_TYPE, recipeOf("CFG_PRESSURE_PLATE_MAT", "CFG_PRESSURE_PLATE_MAT", null, PRESSURE_GEM, TIDESTONE_FRAGMENT, ABYSSITE, null, null, null)
        ).register(addon);

        // T4 Legendary
        new CoralForgeSword(MAIN, item("CFG_VOID_REAVER", Material.NETHERITE_SWORD,
            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Void Reaver",
            ChatColor.GRAY + "+28 damage, Sharpness V + Smite V + Life Steal"),
            WORKBENCH_TYPE, recipeOf("CFG_VOID_ALLOY", "CFG_CORE_SHARD", "CFG_VOID_ALLOY", VOID_CRYSTAL, "CFG_VOID_ALLOY", ABYSSAL_CORE, PRESSURE_GEM, TIDESTONE_FRAGMENT, null),
            28, List.of(effect(PotionEffectType.REGENERATION, 1)), false
        ).register(addon);

        SlimefunItemStack voidTrident = item("CFG_VOID_TRIDENT", Material.TRIDENT,
            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Void Trident",
            ChatColor.GRAY + "+25 damage, ALL trident enchants MAX");
        voidTrident.addUnsafeEnchantment(Enchantment.RIPTIDE, 5);
        voidTrident.addUnsafeEnchantment(Enchantment.LOYALTY, 3);
        voidTrident.addUnsafeEnchantment(Enchantment.IMPALING, 7);
        voidTrident.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
        new CoralForgeTrident(MAIN, voidTrident,
            WORKBENCH_TYPE, recipeOf("CFG_VOID_ALLOY", "CFG_CORE_SHARD", "CFG_VOID_ALLOY", VOID_CRYSTAL, ABYSSAL_CORE, PRESSURE_GEM, null, null, null),
            25, List.of()
        ).register(addon);
    }

    // =========================================================================
    // UTILITIES
    // =========================================================================

    private static void registerUtilities(SlimefunAddon addon) {

        new CoralForgeUtility(MAIN, item("CFG_PEARL_LANTERN", Material.SEA_LANTERN,
            ChatColor.YELLOW + "Pearl Lantern",
            ChatColor.GRAY + "Light 15, tidak padam di air"),
            WORKBENCH_TYPE, recipeOf(PEARLSTONE, PEARLSTONE, null, CORAL_DUST, new ItemStack(Material.GLASS), CORAL_DUST, PEARLSTONE, PEARLSTONE, null),
            CoralForgeUtility.UtilityType.PEARL_LANTERN
        ).register(addon);

        new CoralForgeUtility(MAIN, item("CFG_DEPTH_GAUGE", Material.CLOCK,
            ChatColor.YELLOW + "Depth Gauge",
            ChatColor.GRAY + "Tampilkan Y zone + biome saat ini"),
            WORKBENCH_TYPE, recipeOf(CORAL_DUST, PEARLSTONE, null, null, new ItemStack(Material.CLOCK), null, null, CORAL_DUST, null),
            CoralForgeUtility.UtilityType.DEPTH_GAUGE
        ).register(addon);

        new CoralForgeUtility(MAIN, item("CFG_ABYSSAL_TORCH", Material.TORCH,
            ChatColor.YELLOW + "Abyssal Torch Stack",
            ChatColor.GRAY + "Torch bisa dipasang dalam air"),
            WORKBENCH_TYPE, recipeOf(CORAL_DUST, ABYSSITE, null, CORAL_DUST, new ItemStack(Material.COAL), null, null, null, null),
            CoralForgeUtility.UtilityType.ABYSSAL_TORCH_STACK
        ).register(addon);

        new CoralForgeUtility(MAIN, item("CFG_PRESSURE_COMPASS", Material.COMPASS,
            ChatColor.AQUA + "Pressure Compass",
            ChatColor.GRAY + "Tunjukkan zona Y + nearest Ocean Monument"),
            WORKBENCH_TYPE, recipeOf(PRESSURE_GEM, TRIDENTITE_SHARD, null, null, new ItemStack(Material.COMPASS), null, null, null, null),
            CoralForgeUtility.UtilityType.PRESSURE_COMPASS
        ).register(addon);

        new CoralForgeUtility(MAIN, item("CFG_TIDE_BEACON", Material.BEACON,
            ChatColor.AQUA + "Tide Beacon",
            ChatColor.GRAY + "Beacon bawah air: range +50%, Haste/Speed"),
            WORKBENCH_TYPE, recipeOf(ABYSSITE, TIDESTONE_FRAGMENT, ABYSSITE, ABYSSITE, new ItemStack(Material.BEACON), ABYSSITE, null, null, null),
            CoralForgeUtility.UtilityType.TIDE_BEACON
        ).register(addon);

        new CoralForgeUtility(MAIN, item("CFG_VOID_LENS", Material.SPYGLASS,
            ChatColor.DARK_PURPLE + "Void Lens",
            ChatColor.GRAY + "Sonar visual radius 5 blok"),
            WORKBENCH_TYPE, recipeOf(PRESSURE_GEM, TIDESTONE_FRAGMENT, ABYSSAL_CORE, PRESSURE_GEM, new ItemStack(Material.SPYGLASS), null, null, null, null),
            CoralForgeUtility.UtilityType.VOID_LENS
        ).register(addon);
    }

    // =========================================================================
    // WORKBENCH RECIPES (shapeless quantity-based)
    // =========================================================================

    private static void registerWorkbenchRecipes() {
        // ── Intermediate materials ────────────────────────────────────────────
        addWB("CFG_ABYSSAL_INGOT", 1,
            ABYSSITE, 6,
            "IRON_INGOT", 2);          // vanilla iron ingot as fallback

        addWB("CFG_TIDAL_ALLOY", 1,
            TRIDENTITE_SHARD, 4,
            "CFG_ABYSSAL_INGOT", 2);

        addWB("CFG_PRESSURE_PLATE_MAT", 1,
            PRESSURE_GEM, 3,
            "CFG_ABYSSAL_INGOT", 4);

        addWB("CFG_TIDESTONE_SLAB", 1,
            TIDESTONE_FRAGMENT, 8,
            PRESSURE_GEM, 1);

        addWB("CFG_CORE_SHARD", 1,
            ABYSSAL_CORE, 2,
            "CFG_TIDESTONE_SLAB", 1);

        // ── T1 Coral armor ────────────────────────────────────────────────────
        addWB("CFG_CORAL_HELMET",     1, CORAL_DUST, 12, PEARLSTONE, 6);
        addWB("CFG_CORAL_CHESTPLATE", 1, CORAL_DUST, 20, PEARLSTONE, 10);
        addWB("CFG_CORAL_LEGGINGS",   1, CORAL_DUST, 18, PEARLSTONE, 8);
        addWB("CFG_CORAL_BOOTS",      1, CORAL_DUST, 10, PEARLSTONE, 4);

        // ── T1 weapons ────────────────────────────────────────────────────────
        addWB("CFG_CORAL_DAGGER",  1, CORAL_DUST, 20, PEARLSTONE, 10);
        addWB("CFG_KELP_BLADE",    1, CORAL_DUST, 30, PEARLSTONE, 15);
        addWB("CFG_TIDE_PICKAXE",  1, CORAL_DUST, 24, PEARLSTONE, 12);

        // ── T1 utilities ──────────────────────────────────────────────────────
        addWB("CFG_PEARL_LANTERN",    1, PEARLSTONE, 6, CORAL_DUST, 4);
        addWB("CFG_DEPTH_GAUGE",      1, CORAL_DUST, 8, PEARLSTONE, 4);
        addWB("CFG_ABYSSAL_TORCH",    1, CORAL_DUST, 4, ABYSSITE, 2);

        // ── T2 Abyssal armor ──────────────────────────────────────────────────
        addWB("CFG_ABYSSAL_HELMET",     1, ABYSSITE, 8,  CORAL_DUST, 12,          PEARLSTONE, 6,         "CFG_ABYSSAL_INGOT", 3);
        addWB("CFG_ABYSSAL_CHESTPLATE", 1, ABYSSITE, 12, PEARLSTONE, 8,           TRIDENTITE_SHARD, 2,   "CFG_ABYSSAL_INGOT", 5);
        addWB("CFG_ABYSSAL_LEGGINGS",   1, ABYSSITE, 10, TRIDENTITE_SHARD, 4,     CORAL_DUST, 8,         "CFG_ABYSSAL_INGOT", 4);
        addWB("CFG_ABYSSAL_BOOTS",      1, ABYSSITE, 6,  TRIDENTITE_SHARD, 2,     PEARLSTONE, 4,         "CFG_ABYSSAL_INGOT", 2);

        // ── T2 weapons ────────────────────────────────────────────────────────
        addWB("CFG_ABYSSAL_BLADE",     1, ABYSSITE, 10,  TRIDENTITE_SHARD, 6, CORAL_DUST, 10, "CFG_TIDAL_ALLOY", 3);
        addWB("CFG_TIDE_RIP_TRIDENT",  1, ABYSSITE, 8,   TRIDENTITE_SHARD, 10, "CFG_TIDAL_ALLOY", 4);
        addWB("CFG_ABYSSAL_DRILL",     1, ABYSSITE, 12,  TRIDENTITE_SHARD, 4,  CORAL_DUST, 8, "CFG_ABYSSAL_INGOT", 4);

        // ── T2 utilities ──────────────────────────────────────────────────────
        addWB("CFG_PRESSURE_COMPASS", 1, PRESSURE_GEM, 2,  TRIDENTITE_SHARD, 2);
        addWB("CFG_TIDE_BEACON",      1, ABYSSITE, 8,       TIDESTONE_FRAGMENT, 4);

        // ── T3 Pressure armor ─────────────────────────────────────────────────
        addWB("CFG_PRESSURE_HELMET",     1, PRESSURE_GEM, 5,  ABYSSITE, 10, TIDESTONE_FRAGMENT, 6,  "CFG_PRESSURE_PLATE_MAT", 3, "CFG_TIDESTONE_SLAB", 1);
        addWB("CFG_PRESSURE_CHESTPLATE", 1, PRESSURE_GEM, 8,  ABYSSITE, 12, TRIDENTITE_SHARD, 6,    "CFG_PRESSURE_PLATE_MAT", 5, "CFG_TIDESTONE_SLAB", 2);
        addWB("CFG_PRESSURE_LEGGINGS",   1, PRESSURE_GEM, 6,  TRIDENTITE_SHARD, 8, TIDESTONE_FRAGMENT, 8, "CFG_PRESSURE_PLATE_MAT", 4, "CFG_TIDAL_ALLOY", 3);
        addWB("CFG_PRESSURE_BOOTS",      1, PRESSURE_GEM, 4,  TRIDENTITE_SHARD, 4, ABYSSITE, 6,     "CFG_PRESSURE_PLATE_MAT", 2, "CFG_TIDESTONE_SLAB", 1);

        // ── T3 weapons ────────────────────────────────────────────────────────
        addWB("CFG_PRESSURE_SPEAR",  1, PRESSURE_GEM, 6, TIDESTONE_FRAGMENT, 8,  TRIDENTITE_SHARD, 8, "CFG_TIDESTONE_SLAB", 2, "CFG_TIDAL_ALLOY", 3);
        addWB("CFG_DEEP_CUTTER",     1, PRESSURE_GEM, 5, ABYSSITE, 10, TRIDENTITE_SHARD, 10, TIDESTONE_FRAGMENT, 6, "CFG_TIDAL_ALLOY", 4, "CFG_TIDESTONE_SLAB", 2);
        addWB("CFG_ABYSS_EXCAVATOR", 1, PRESSURE_GEM, 4, TIDESTONE_FRAGMENT, 10, ABYSSITE, 8, "CFG_PRESSURE_PLATE_MAT", 2);

        // ── T3 utility ────────────────────────────────────────────────────────
        addWB("CFG_VOID_LENS", 1, PRESSURE_GEM, 4, TIDESTONE_FRAGMENT, 6, ABYSSAL_CORE, 1);

        // ── T4 Void armor ─────────────────────────────────────────────────────
        addWB("CFG_VOID_HELMET",     1, VOID_CRYSTAL, 2,  ABYSSAL_CORE, 3,  PRESSURE_GEM, 8,  "CFG_VOID_ALLOY", 2);
        addWB("CFG_VOID_CHESTPLATE", 1, VOID_CRYSTAL, 3,  ABYSSAL_CORE, 4,  PRESSURE_GEM, 10, TIDESTONE_FRAGMENT, 10, "CFG_VOID_ALLOY", 3);
        addWB("CFG_VOID_LEGGINGS",   1, VOID_CRYSTAL, 2,  ABYSSAL_CORE, 3,  PRESSURE_GEM, 8,  TRIDENTITE_SHARD, 8,   "CFG_VOID_ALLOY", 2, "CFG_TIDAL_ALLOY", 3);
        addWB("CFG_VOID_BOOTS",      1, VOID_CRYSTAL, 2,  ABYSSAL_CORE, 2,  PRESSURE_GEM, 6,  "CFG_VOID_ALLOY", 2);

        // ── T4 weapons ────────────────────────────────────────────────────────
        addWB("CFG_VOID_REAVER",  1, VOID_CRYSTAL, 3, ABYSSAL_CORE, 4, PRESSURE_GEM, 10, TIDESTONE_FRAGMENT, 12, "CFG_VOID_ALLOY", 3, "CFG_CORE_SHARD", 2);
        addWB("CFG_VOID_TRIDENT", 1, VOID_CRYSTAL, 4, ABYSSAL_CORE, 3, PRESSURE_GEM, 8,  "CFG_VOID_ALLOY", 2, "CFG_CORE_SHARD", 2);
    }

    private static void registerInfuserRecipes() {
        // Void Alloy: Void Crystal(2) + Pressure Plate Mat(2) + Abyssal Core(1)
        CoralForgeInfuser.addRecipe(
            WorkbenchRecipe.builder(sfItem("CFG_VOID_ALLOY"))
                .ingredient(VOID_CRYSTAL, 2)
                .ingredient("CFG_PRESSURE_PLATE_MAT", 2)
                .ingredient(ABYSSAL_CORE, 1)
                .build()
        );

        // ── T5 Sovereign Intermediate Materials ──────────────────────────────

        addInfuser("CFG_SOVEREIGN_INGOT", 1,
            "CFG_VOID_ALLOY", 10,
            "BILLON_INGOT", 15,
            ABYSSAL_CORE, 10,
            "REINFORCED_ALLOY_INGOT", 10);

        addInfuser("CFG_ABYSSAL_CROWN_GEM", 1,
            VOID_CRYSTAL, 10,
            PRESSURE_GEM, 15,
            TIDESTONE_FRAGMENT, 20);

        addInfuser("CFG_SOVEREIGN_CORE", 1,
            "CFG_SOVEREIGN_INGOT", 10,
            VOID_CRYSTAL, 10,
            ABYSSAL_CORE, 15,
            "DAMASCUS_STEEL", 20);

        // ── T5 Abyss Sovereign Armor ─────────────────────────────────────────
        // Each ingredient requires 10-25 units to reflect the endgame grind.

        addInfuser("CFG_SOVEREIGN_HELMET", 1,
            VOID_CRYSTAL, 15,
            ABYSSAL_CORE, 15,
            PRESSURE_GEM, 20,
            TIDESTONE_FRAGMENT, 20,
            "CFG_SOVEREIGN_INGOT", 10,
            "CFG_ABYSSAL_CROWN_GEM", 5,
            "REINFORCED_ALLOY_INGOT", 15);

        addInfuser("CFG_SOVEREIGN_CHESTPLATE", 1,
            VOID_CRYSTAL, 20,
            ABYSSAL_CORE, 20,
            PRESSURE_GEM, 25,
            TRIDENTITE_SHARD, 30,
            "CFG_SOVEREIGN_CORE", 10,
            "CFG_SOVEREIGN_INGOT", 15,
            "BILLON_INGOT", 20);

        addInfuser("CFG_SOVEREIGN_LEGGINGS", 1,
            VOID_CRYSTAL, 18,
            ABYSSAL_CORE, 18,
            PRESSURE_GEM, 20,
            TRIDENTITE_SHARD, 25,
            "CFG_SOVEREIGN_CORE", 10,
            "CFG_SOVEREIGN_INGOT", 12,
            "DAMASCUS_STEEL", 20);

        addInfuser("CFG_SOVEREIGN_BOOTS", 1,
            VOID_CRYSTAL, 12,
            ABYSSAL_CORE, 12,
            PRESSURE_GEM, 15,
            TIDESTONE_FRAGMENT, 20,
            "CFG_SOVEREIGN_INGOT", 10,
            "REINFORCED_ALLOY_INGOT", 15,
            "HARDENED_METAL", 15);
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private static void addWB(String outputId, int outputAmount, Object... ingredientPairs) {
        ItemStack out = sfItem(outputId);
        out.setAmount(outputAmount);
        WorkbenchRecipe.Builder b = WorkbenchRecipe.builder(out);
        for (int i = 0; i + 1 < ingredientPairs.length; i += 2) {
            String id = ingredientPairs[i].toString();
            int amt = (int) ingredientPairs[i + 1];
            b.ingredient(id, amt);
        }
        CoralForgeWorkbench.addRecipe(b.build());
    }

    private static void addInfuser(String outputId, int outputAmount, Object... ingredientPairs) {
        ItemStack out = sfItem(outputId);
        out.setAmount(outputAmount);
        WorkbenchRecipe.Builder b = WorkbenchRecipe.builder(out);
        for (int i = 0; i + 1 < ingredientPairs.length; i += 2) {
            String id = ingredientPairs[i].toString();
            int amt = (int) ingredientPairs[i + 1];
            b.ingredient(id, amt);
        }
        CoralForgeInfuser.addRecipe(b.build());
    }

    /** Get a SlimefunItem's template ItemStack, or BARRIER if not found yet. */
    private static ItemStack sfItem(String id) {
        SlimefunItem sf = SlimefunItem.getById(id);
        return sf != null ? sf.getItem().clone() : new ItemStack(Material.BARRIER);
    }

    private static ItemStack iron() {
        return new ItemStack(Material.IRON_INGOT);
    }

    /** Build a 3x3 recipe grid from a mix of Slimefun IDs (String) or ItemStacks. */
    @SuppressWarnings("unchecked")
    private static ItemStack[] recipeOf(Object... slots) {
        ItemStack[] arr = new ItemStack[9];
        for (int i = 0; i < Math.min(9, slots.length); i++) {
            if (slots[i] == null) {
                arr[i] = null;
            } else if (slots[i] instanceof ItemStack is) {
                arr[i] = is;
            } else {
                // It's a Slimefun item ID string
                arr[i] = sfItem(slots[i].toString());
            }
        }
        return arr;
    }

    private static SlimefunItemStack item(String id, Material mat, String name, String... lore) {
        return new SlimefunItemStack(id, mat, name, lore);
    }

    private static PotionEffect effect(PotionEffectType type, int amplifier) {
        return new PotionEffect(type, Integer.MAX_VALUE, amplifier, true, false, true);
    }
}
