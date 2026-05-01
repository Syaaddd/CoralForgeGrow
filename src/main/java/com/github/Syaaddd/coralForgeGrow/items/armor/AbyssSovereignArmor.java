package com.github.Syaaddd.coralForgeGrow.items.armor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class AbyssSovereignArmor extends CoralForgeArmor {

    public enum SovereignPiece { HELMET, CHESTPLATE, LEGGINGS, BOOTS }

    private final SovereignPiece pieceType;

    public AbyssSovereignArmor(ItemGroup group, SlimefunItemStack item,
                               RecipeType recipeType, ItemStack[] recipe,
                               List<PotionEffect> waterEffects,
                               List<PotionEffect> alwaysEffects,
                               SovereignPiece pieceType) {
        super(group, item, recipeType, recipe, waterEffects, alwaysEffects);
        this.pieceType = pieceType;
    }

    public SovereignPiece getPieceType() { return pieceType; }
}
