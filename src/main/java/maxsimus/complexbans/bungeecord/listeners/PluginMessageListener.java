package maxsimus.complexbans.bungeecord.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import maxsimus.complexbans.api.ActionType;
import maxsimus.complexbans.api.Logger;
import maxsimus.complexbans.bungeecord.actions.ActionInterface;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;

public class PluginMessageListener implements Listener {
    @EventHandler
    public void onPluginMsg(PluginMessageEvent e) {
        if (!e.getTag().equalsIgnoreCase("BungeeCord")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());

        if (!in.readUTF().equals("ComplexBans")) return;

        try {
            ActionType action = ActionType.valueOf(in.readUTF());

            ArrayList<String> strings = new ArrayList<>();

            try {
                while (true) {
                    strings.add(in.readUTF());
                }
            } catch (Exception ignored) {}

            String[] args = strings.toArray(new String[0]);

            switch (action) {
                case BAN: ActionInterface.ban(args); break;
                case UNBAN: ActionInterface.unBan(args); break;
                case KICK: ActionInterface.kick(args); break;
                case IPBAN: ActionInterface.ipBan(args); break;
                case IPUNBAN: ActionInterface.ipUnban(args); break;
            }
        } catch (Exception ignored) {
            Logger.severe("Received some shit!");
        }
    }
}
