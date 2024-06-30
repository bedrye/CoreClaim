package me.bedrye.coreclaim;

import org.bukkit.plugin.java.JavaPlugin;



public final class CoreClaim extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginCommand("CC").setExecutor(new ClaimCommandHandler());
        getServer().getPluginManager().registerEvents(new ClaimEventHandler(),this);
        Load();
    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
        saveConfig();
    }

    public void Load(){
        ConfigController.getInstance().updateConfig();
        ClaimController.getInstance().UpdateClaims();
        TimerTaskManager.getInstance().setProvidingPlugin(this);
    }
}
