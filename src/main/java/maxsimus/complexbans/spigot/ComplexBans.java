package maxsimus.complexbans.spigot;

import maxsimus.complexbans.api.Logger;
import maxsimus.complexbans.spigot.cmd.actions.Ban;
import maxsimus.complexbans.spigot.cmd.actions.IPBan;
import maxsimus.complexbans.spigot.cmd.actions.IPUnban;
import maxsimus.complexbans.spigot.cmd.actions.Unban;
import org.bukkit.plugin.java.JavaPlugin;

public final class ComplexBans extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger.setLogger(getLogger());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().getPluginCommand("ban").setExecutor(new Ban());
        getServer().getPluginCommand("unban").setExecutor(new Unban());
        getServer().getPluginCommand("ipban").setExecutor(new IPBan());
        getServer().getPluginCommand("ipunban").setExecutor(new IPUnban());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
