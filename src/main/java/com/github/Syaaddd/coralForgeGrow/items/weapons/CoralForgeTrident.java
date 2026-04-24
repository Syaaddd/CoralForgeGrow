package com.github.Syaaddd.coralForgeGrow.items.weapons;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

/** Custom trident with bonus damage and hit effects. */
public class CoralForgeTrident extends SlimefunItem {

    private final double bonusDamage;
    private final List<PotionEffect> hitEffectsOnTarget;

    public CoralForgeTrident(ItemGroup group, SlimefunItemStack item,
                             RecipeType recipeType, ItemStack[] recipe,
                             double bonusDamage,
                             List<PotionEffect> hitEffectsOnTarget) {
        super(group, item, recipeType, recipe);
        this.bonusDamage        = bonusDamage;
        this.hitEffectsOnTarget = List.copyOf(hitEffectsOnTarget);
    }

    public double getBonusDamage()            { return bonusDamage; }
    public List<PotionEffect> getHitEffects() { return hitEffectsOnTarget; }
}
