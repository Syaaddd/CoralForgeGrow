package com.github.Syaaddd.coralForgeGrow.listeners;

import com.github.Syaaddd.coralForgeGrow.items.weapons.CoralForgeSword;
import com.github.Syaaddd.coralForgeGrow.items.weapons.CoralForgeTrident;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class WeaponListener implements Listener {

    public WeaponListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        ItemStack held = attacker.getInventory().getItemInMainHand();
        if (held == null) return;

        SlimefunItem sf = SlimefunItem.getByItem(held);

        if (sf instanceof CoralForgeSword sword) {
            handleSword(event, attacker, target, sword);
        } else if (sf instanceof CoralForgeTrident trident) {
            handleTrident(event, attacker, target, trident);
        }
    }

    private void handleSword(EntityDamageByEntityEvent event, Player attacker,
                              LivingEntity target, CoralForgeSword sword) {
        boolean inWater = attacker.isInWater();

        if (sword.requiresWater() && !inWater) return;

        event.setDamage(event.getDamage() + sword.getBonusDamage());

        for (PotionEffect effect : sword.getHitEffects()) {
            target.addPotionEffect(new PotionEffect(
                effect.getType(), effect.getDuration(), effect.getAmplifier()
            ));
        }
    }

    private void handleTrident(EntityDamageByEntityEvent event, Player attacker,
                                LivingEntity target, CoralForgeTrident trident) {
        event.setDamage(event.getDamage() + trident.getBonusDamage());

        for (PotionEffect effect : trident.getHitEffects()) {
            target.addPotionEffect(new PotionEffect(
                effect.getType(), effect.getDuration(), effect.getAmplifier()
            ));
        }
    }
}
