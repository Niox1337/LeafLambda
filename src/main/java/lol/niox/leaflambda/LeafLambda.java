package lol.niox.leaflambda;

import org.bukkit.plugin.java.JavaPlugin;

public final class LeafLambda extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new LeavesDecay(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
