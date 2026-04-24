package com.github.Syaaddd.coralForgeGrow;

import com.github.Syaaddd.coralForgeGrow.listeners.ArmorListener;
import com.github.Syaaddd.coralForgeGrow.listeners.WeaponListener;
import com.github.Syaaddd.coralForgeGrow.setup.Groups;
import com.github.Syaaddd.coralForgeGrow.setup.ItemSetup;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoralForgeGrow extends JavaPlugin implements SlimefunAddon {

    private static CoralForgeGrow instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Groups.setup(this);
        ItemSetup.setup(this);

        int interval = getConfig().getInt("coralforge.armor-effect-interval", 30);
        new ArmorListener(this, interval);
        new WeaponListener(this);

        getLogger().info("CoralForgeGrow enabled — " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        getLogger().info("CoralForgeGrow disabled.");
    }

    @Override
    public JavaPlugin getJavaPlugin() { return this; }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Syaaddd/CoralForgeGrow/issues";
    }

    public static CoralForgeGrow getInstance() { return instance; }
}
