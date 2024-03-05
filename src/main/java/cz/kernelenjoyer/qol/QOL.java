package cz.kernelenjoyer.qol;

import cz.kernelenjoyer.qol.Features.AutoPickupFromBreak;
import org.bukkit.plugin.java.JavaPlugin;

public final class QOL extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new AutoPickupFromBreak() , this);
    }

    public void registerCommands() {

    }
}