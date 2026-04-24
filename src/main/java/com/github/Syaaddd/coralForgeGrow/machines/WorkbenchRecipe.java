package com.github.Syaaddd.coralForgeGrow.machines;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Shapeless recipe for CoralForge machines.
 * Matching is done by Slimefun ID and required quantity.
 */
public class WorkbenchRecipe {

    private final Map<String, Integer> ingredients;
    private final ItemStack output;

    private WorkbenchRecipe(Map<String, Integer> ingredients, ItemStack output) {
        this.ingredients = Collections.unmodifiableMap(new LinkedHashMap<>(ingredients));
        this.output = output.clone();
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public ItemStack getOutput() {
        return output.clone();
    }

    /**
     * Check whether the given input map (sfId -> total count) satisfies this recipe.
     */
    public boolean matches(Map<String, Integer> inputMap) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            if (inputMap.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Consume ingredients from the given slot array of a BlockMenu.
     * Returns a mutable copy of remaining-needed counts (all zero if successful).
     */
    public boolean consumeFrom(ItemStack[] inputStacks) {
        Map<String, Integer> needed = new HashMap<>(ingredients);

        for (ItemStack stack : inputStacks) {
            if (stack == null) continue;
            SlimefunItem sf = SlimefunItem.getByItem(stack);
            String id = sf != null ? sf.getId() : stack.getType().name();
            if (!needed.containsKey(id)) continue;

            int take = Math.min(stack.getAmount(), needed.get(id));
            needed.put(id, needed.get(id) - take);
            stack.setAmount(stack.getAmount() - take);
        }
        return needed.values().stream().allMatch(v -> v == 0);
    }

    // ---- Builder ----

    public static Builder builder(ItemStack output) {
        return new Builder(output);
    }

    public static class Builder {
        private final Map<String, Integer> ingredients = new LinkedHashMap<>();
        private final ItemStack output;

        Builder(ItemStack output) {
            this.output = output;
        }

        public Builder ingredient(String sfId, int amount) {
            ingredients.merge(sfId, amount, Integer::sum);
            return this;
        }

        public WorkbenchRecipe build() {
            return new WorkbenchRecipe(ingredients, output);
        }
    }

    // ---- Static helpers for building input maps from slot arrays ----

    public static Map<String, Integer> buildInputMap(ItemStack[] slots) {
        Map<String, Integer> map = new HashMap<>();
        for (ItemStack stack : slots) {
            if (stack == null) continue;
            SlimefunItem sf = SlimefunItem.getByItem(stack);
            String id = sf != null ? sf.getId() : stack.getType().name();
            map.merge(id, stack.getAmount(), Integer::sum);
        }
        return map;
    }
}
