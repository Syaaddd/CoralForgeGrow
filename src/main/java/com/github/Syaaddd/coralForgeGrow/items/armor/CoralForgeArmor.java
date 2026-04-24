package com.github.Syaaddd.coralForgeGrow.items.armor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Base class for all CoralForge armor pieces.
 * Actual effects are applied by ArmorListener using the stored effect list.
 */
public class CoralForgeArmor extends SlimefunItem {

    private final List<PotionEffect> waterEffects;
    private final List<PotionEffect> alwaysEffects;
    private final boolean requiresWater;

    /**
     * @param waterEffects  applied only when the player is in water
     * @param alwaysEffects applied regardless of water state
     */
    public CoralForgeArmor(ItemGroup group, SlimefunItemStack item,
                           RecipeType recipeType, ItemStack[] recipe,
                           List<PotionEffect> waterEffects,
                           List<PotionEffect> alwaysEffects) {
        super(group, item, recipeType, recipe);
        this.waterEffects   = List.copyOf(waterEffects);
        this.alwaysEffects  = List.copyOf(alwaysEffects);
        this.requiresWater  = !waterEffects.isEmpty();
    }

    /** Convenience constructor when all effects are water-only. */
    public CoralForgeArmor(ItemGroup group, SlimefunItemStack item,
                           RecipeType recipeType, ItemStack[] recipe,
                           List<PotionEffect> waterEffects) {
        this(group, item, recipeType, recipe, waterEffects, List.of());
    }

    public List<PotionEffect> getWaterEffects()  { return waterEffects; }
    public List<PotionEffect> getAlwaysEffects() { return alwaysEffects; }
    public boolean requiresWater()               { return requiresWater; }
}
