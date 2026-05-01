package com.github.Syaaddd.coralForgeGrow.listeners;

import com.github.Syaaddd.coralForgeGrow.items.armor.AbyssSovereignArmor;
import com.github.Syaaddd.coralForgeGrow.items.armor.AbyssSovereignArmor.SovereignPiece;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class SovereignArmorListener implements Listener {

    private final Random random = new Random();

    public SovereignArmorListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // Chestplate unique: reflect 25% of final damage back to attacker when victim is in water
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!victim.isInWater()) return;

        if (!isSovereignPiece(victim.getInventory().getChestplate(), SovereignPiece.CHESTPLATE)) return;

        double reflect = event.getFinalDamage() * 0.25;
        if (reflect > 0 && event.getDamager() instanceof LivingEntity attacker) {
            attacker.damage(reflect, victim);
        }
    }

    // Leggings unique: +50% XP from mob kills when killer is in water
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null || !killer.isInWater()) return;
        if (!isSovereignPiece(killer.getInventory().getLeggings(), SovereignPiece.LEGGINGS)) return;

        event.setDroppedExp((int) (event.getDroppedExp() * 1.5));
    }

    // Boots unique: 75% chance to cancel durability loss when player is in water
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemDamage(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();
        if (!player.isInWater()) return;
        if (!isSovereignPiece(event.getItem(), SovereignPiece.BOOTS)) return;

        if (random.nextDouble() < 0.75) {
            event.setDamage(0);
        }
    }

    private boolean isSovereignPiece(ItemStack stack, SovereignPiece type) {
        if (stack == null) return false;
        SlimefunItem sf = SlimefunItem.getByItem(stack);
        return sf instanceof AbyssSovereignArmor armor && armor.getPieceType() == type;
    }
}
