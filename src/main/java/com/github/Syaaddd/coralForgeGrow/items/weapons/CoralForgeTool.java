package com.github.Syaaddd.coralForgeGrow.items.weapons;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

/** Pickaxe / shovel tool — no extra logic beyond registered enchantments on the item. */
public class CoralForgeTool extends SlimefunItem {

    public CoralForgeTool(ItemGroup group, SlimefunItemStack item,
                          RecipeType recipeType, ItemStack[] recipe) {
        super(group, item, recipeType, recipe);
    }
}
