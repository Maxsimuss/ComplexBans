package maxsimus.complexbans.spigot.util;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import maxsimus.complexbans.api.ActionType;
import maxsimus.complexbans.api.Logger;
import maxsimus.complexbans.bungeecord.util.db.DBInterface;
import maxsimus.complexbans.spigot.ComplexBans;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionInterface {
    @SuppressWarnings("all")
    public static void sendAction(ActionType action, String[] args) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ComplexBans");
        out.writeUTF(action.name());

        for (String arg:args) {
            out.writeUTF(arg);
        }

        sendPluginMessage(out);
    }

    private static void sendPluginMessage(ByteArrayDataOutput out) {
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player == null) {
            Logger.severe("Cannot send plugin message!");
            return;
        }

        player.sendPluginMessage(ComplexBans.getPlugin(ComplexBans.class), "BungeeCord", out.toByteArray());
    }
}
