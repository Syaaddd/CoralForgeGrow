package com.github.Syaaddd.coralForgeGrow.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/** High-tier machine for crafting Legendary (Void Alloy) materials. */
public class CoralForgeInfuser extends SlimefunItem {

    public static final int[] INPUT_SLOTS  = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    public static final int   CRAFT_SLOT   = 13;
    public static final int   OUTPUT_SLOT  = 22;
    private static final int[] DIVIDER_SLOTS = {9, 10, 11, 12, 14, 15, 16, 17};
    private static final int[] BG_SLOTS    = {18, 19, 20, 21, 23, 24, 25, 26};

    private static final List<WorkbenchRecipe> RECIPES = new ArrayList<>();

    public CoralForgeInfuser(ItemGroup group, SlimefunItemStack item,
                              RecipeType recipeType, ItemStack[] recipe) {
        super(group, item, recipeType, recipe);
    }

    public static void addRecipe(WorkbenchRecipe recipe) {
        RECIPES.add(recipe);
    }

    @Override
    public void preRegister() {
        new BlockMenuPreset(getId(), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "CoralForge Infuser") {
            @Override
            public void init() {
                ItemStack divider = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
                ItemMeta dm = divider.getItemMeta();
                dm.setDisplayName(" ");
                divider.setItemMeta(dm);

                ItemStack bg = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta bm = bg.getItemMeta();
                bm.setDisplayName(" ");
                bg.setItemMeta(bm);

                ItemStack craftBtn = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
                ItemMeta cm = craftBtn.getItemMeta();
                cm.setDisplayName(ChatColor.LIGHT_PURPLE + "» Infuse «");
                cm.setLore(Collections.singletonList(ChatColor.GRAY + "Click to infuse legendary material"));
                craftBtn.setItemMeta(cm);

                for (int s : DIVIDER_SLOTS) addItem(s, divider.clone(), (p, slot, stack, action) -> false);
                for (int s : BG_SLOTS)      addItem(s, bg.clone(),      (p, slot, stack, action) -> false);
                addItem(CRAFT_SLOT, craftBtn, (p, slot, stack, action) -> false);
            }

            @Override
            public boolean canOpen(Block b, Player p) { return true; }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }
        };

        addItemHandler((BlockUseHandler) e -> {
            e.cancel();
            e.getClickedBlock().ifPresent(b -> {
                BlockMenu menu = BlockStorage.getInventory(b.getLocation());
                if (menu != null) {
                    menu.addMenuClickHandler(CRAFT_SLOT, (p, slot, item, action) -> {
                        tryToInfuse(menu, p);
                        return false;
                    });
                    menu.open(e.getPlayer());
                }
            });
        });
    }

    private static void tryToInfuse(BlockMenu menu, Player player) {
        ItemStack[] inputItems = new ItemStack[INPUT_SLOTS.length];
        for (int i = 0; i < INPUT_SLOTS.length; i++) {
            ItemStack s = menu.getItemInSlot(INPUT_SLOTS[i]);
            inputItems[i] = (s != null) ? s.clone() : null;
        }

        Map<String, Integer> inputMap = WorkbenchRecipe.buildInputMap(inputItems);

        for (WorkbenchRecipe recipe : RECIPES) {
            if (!recipe.matches(inputMap)) continue;

            ItemStack existing = menu.getItemInSlot(OUTPUT_SLOT);
            if (existing != null && existing.getType() != Material.AIR) {
                player.sendMessage(ChatColor.RED + "[CoralForge] Output slot is not empty!");
                return;
            }

            ItemStack[] liveInputs = new ItemStack[INPUT_SLOTS.length];
            for (int i = 0; i < INPUT_SLOTS.length; i++) {
                liveInputs[i] = menu.getItemInSlot(INPUT_SLOTS[i]);
            }

            recipe.consumeFrom(liveInputs);

            for (int i = 0; i < INPUT_SLOTS.length; i++) {
                ItemStack updated = liveInputs[i];
                menu.replaceExistingItem(INPUT_SLOTS[i],
                        (updated == null || updated.getAmount() <= 0) ? null : updated);
            }

            menu.replaceExistingItem(OUTPUT_SLOT, recipe.getOutput());
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[CoralForge] Infusion complete!");
            return;
        }

        player.sendMessage(ChatColor.RED + "[CoralForge] No matching infusion recipe found!");
    }
}
