package maxsimus.complexbans.bungeecord;

import maxsimus.complexbans.bungeecord.config.ConfigManager;
import maxsimus.complexbans.bungeecord.listeners.PluginMessageListener;
import maxsimus.complexbans.bungeecord.listeners.PreLoginListener;
import maxsimus.complexbans.bungeecord.util.db.DBCache;
import maxsimus.complexbans.bungeecord.util.db.DBInterface;
import maxsimus.complexbans.api.Logger;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public final class ComplexBans extends Plugin implements Listener {
    @Override
    public void onEnable() {
        Logger.setLogger(getLogger());

        ConfigManager.init(this);
        DBInterface.connect();
        DBCache.load();

        getProxy().registerChannel("BungeeCord");

        getProxy().getPluginManager().registerListener(this, new PreLoginListener());
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
