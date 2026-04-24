package com.github.Syaaddd.coralForgeGrow.items.weapons;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

/** Custom sword with bonus damage and optional water-triggered effects. */
public class CoralForgeSword extends SlimefunItem {

    private final double bonusDamage;
    private final List<PotionEffect> hitEffectsOnTarget;
    private final boolean requiresWater;

    public CoralForgeSword(ItemGroup group, SlimefunItemStack item,
                           RecipeType recipeType, ItemStack[] recipe,
                           double bonusDamage,
                           List<PotionEffect> hitEffectsOnTarget,
                           boolean requiresWater) {
        super(group, item, recipeType, recipe);
        this.bonusDamage        = bonusDamage;
        this.hitEffectsOnTarget = List.copyOf(hitEffectsOnTarget);
        this.requiresWater      = requiresWater;
    }

    public double getBonusDamage()               { return bonusDamage; }
    public List<PotionEffect> getHitEffects()    { return hitEffectsOnTarget; }
    public boolean requiresWater()               { return requiresWater; }
}
