package com.github.Syaaddd.coralForgeGrow.items.utilities;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

/**
 * Utility items (Pearl Lantern, Depth Gauge, etc.).
 * Behaviour hooks are in the specific listener or item handler.
 */
public class CoralForgeUtility extends SlimefunItem {

    public enum UtilityType {
        PEARL_LANTERN,
        DEPTH_GAUGE,
        ABYSSAL_TORCH_STACK,
        PRESSURE_COMPASS,
        TIDE_BEACON,
        VOID_LENS
    }

    private final UtilityType utilityType;

    public CoralForgeUtility(ItemGroup group, SlimefunItemStack item,
                             RecipeType recipeType, ItemStack[] recipe,
                             UtilityType utilityType) {
        super(group, item, recipeType, recipe);
        this.utilityType = utilityType;
    }

    public UtilityType getUtilityType() { return utilityType; }
}
