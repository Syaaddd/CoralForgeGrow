package com.github.Syaaddd.coralForgeGrow.listeners;

import com.github.Syaaddd.coralForgeGrow.items.armor.AbyssSovereignArmor;
import com.github.Syaaddd.coralForgeGrow.items.armor.CoralForgeArmor;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ArmorListener {

    private final int EFFECT_DURATION = 60; // ticks — longer than tick interval to avoid flicker

    public ArmorListener(JavaPlugin plugin, int intervalTicks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    tickArmorEffects(player);
                }
            }
        }.runTaskTimer(plugin, 20L, intervalTicks);
    }

    private void tickArmorEffects(Player player) {
        boolean inWater = player.isInWater();

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        // armorContents: [0]=boots, [1]=leggings, [2]=chestplate, [3]=helmet

        for (ItemStack armorPiece : armorContents) {
            if (armorPiece == null || armorPiece.getType() == Material.AIR) continue;

            SlimefunItem sf = SlimefunItem.getByItem(armorPiece);
            if (!(sf instanceof CoralForgeArmor armor)) continue;

            // Always effects
            for (PotionEffect effect : armor.getAlwaysEffects()) {
                player.addPotionEffect(
                    new PotionEffect(effect.getType(), EFFECT_DURATION,
                                     effect.getAmplifier(), true, false, true)
                );
            }

            // Water-only effects
            if (inWater) {
                for (PotionEffect effect : armor.getWaterEffects()) {
                    player.addPotionEffect(
                        new PotionEffect(effect.getType(), EFFECT_DURATION,
                                         effect.getAmplifier(), true, false, true)
                    );
                }
            }
        }

        checkSovereignFullSet(player, armorContents);
    }

    // Full set bonus when all 4 Sovereign pieces are worn:
    // Conduit Power (always) + Resistance IV (overrides Chestplate's Resistance III)
    private void checkSovereignFullSet(Player player, ItemStack[] armorContents) {
        for (ItemStack piece : armorContents) {
            if (!isSovereignPiece(piece)) return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, EFFECT_DURATION, 0, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, EFFECT_DURATION, 3, true, false, true));
    }

    private boolean isSovereignPiece(ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR) return false;
        return SlimefunItem.getByItem(stack) instanceof AbyssSovereignArmor;
    }
}
