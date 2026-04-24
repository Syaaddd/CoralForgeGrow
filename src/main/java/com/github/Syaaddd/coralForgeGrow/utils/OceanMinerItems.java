package com.github.Syaaddd.coralForgeGrow.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * ID Slimefun yang dipakai OceanMinerGrow — sesuai OceanMinerItems.java di repo tersebut.
 */
public final class OceanMinerItems {

    public static final String CORAL_DUST         = "OCEAN_MINER_CORAL_DUST";
    public static final String PEARLSTONE         = "OCEAN_MINER_PEARLSTONE";
    public static final String ABYSSITE           = "OCEAN_MINER_ABYSSITE";
    public static final String TRIDENTITE_SHARD   = "OCEAN_MINER_TRIDENTITE_SHARD";
    public static final String PRESSURE_GEM       = "OCEAN_MINER_PRESSURE_GEM";
    public static final String TIDESTONE_FRAGMENT = "OCEAN_MINER_TIDESTONE_FRAG";
    public static final String ABYSSAL_CORE       = "OCEAN_MINER_ABYSSAL_CORE";
    public static final String VOID_CRYSTAL       = "OCEAN_MINER_VOID_CRYSTAL";

    private OceanMinerItems() {}

    public static ItemStack get(String id) {
        SlimefunItem sf = SlimefunItem.getById(id);
        if (sf != null) return sf.getItem().clone();
        return new ItemStack(Material.BARRIER);
    }

    public static ItemStack getCoralDust()        { return get(CORAL_DUST); }
    public static ItemStack getPearlstone()        { return get(PEARLSTONE); }
    public static ItemStack getAbyssite()          { return get(ABYSSITE); }
    public static ItemStack getTridentiteShard()   { return get(TRIDENTITE_SHARD); }
    public static ItemStack getPressureGem()       { return get(PRESSURE_GEM); }
    public static ItemStack getTidestoneFragment() { return get(TIDESTONE_FRAGMENT); }
    public static ItemStack getAbyssalCore()       { return get(ABYSSAL_CORE); }
    public static ItemStack getVoidCrystal()       { return get(VOID_CRYSTAL); }
}
