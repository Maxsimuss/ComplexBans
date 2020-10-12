package maxsimus.complexbans.bungeecord.actions;

import maxsimus.complexbans.bungeecord.util.Punishment;
import maxsimus.complexbans.bungeecord.util.db.DBCache;
import maxsimus.complexbans.bungeecord.util.db.DBInterface;
import maxsimus.complexbans.bungeecord.util.Format;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ActionInterface {
    public static void ban(String[] args) {
        if(args.length != 5) return;

        String by = args[0];
        String player = args[1];
        String duration = args[2];
        String reason = args[3];
        String displayReason = args[4];

        long time = 1000;

        switch (duration.substring(duration.length() - 1)) {
            case "m": time *= 60; break;
            case "h": time *= 3600; break;
            case "d": time *= 86400; break;
        }

        long dur = Long.parseLong(duration.substring(0, duration.length() - 1)) * time;

        Punishment ban = DBInterface.addBan(player, displayReason, reason, by, dur);
        DBInterface.addLog(by, "ban player", player, "reason: " + new TextComponent(reason).toPlainText() + " display reason: " + new TextComponent(displayReason).toPlainText());

        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(player);
        if(p != null) {
            p.disconnect(Format.ban(ban.getReason(), ban.getExpiration()));
        }

        ProxiedPlayer sender = ProxyServer.getInstance().getPlayer(by);
        sender.sendMessage("Player " + player + " was banned!");

        announce(player + " was banned by " + by + " for " + displayReason);
    }

    public static void unBan(String[] args) {
        if(args.length != 2) return;

        String by = args[0];
        String player = args[1];

        DBInterface.removeBan(player);
        DBInterface.addLog(by, "unban player", player, "");

        ProxiedPlayer sender = ProxyServer.getInstance().getPlayer(by);
        sender.sendMessage("Player " + player + " was unbanned");
    }

    public static void kick(String[] args) {
        if(args.length != 4) return;

        String by = args[0];
        String player = args[1];
        String reason = args[2];
        String displayReason = args[3];

        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(player);
        ProxiedPlayer sender = ProxyServer.getInstance().getPlayer(by);

        if(p != null) {
            p.disconnect(Format.kick(displayReason));
            announce(player + " was kicked by " + by + " for " + displayReason);
            DBInterface.addKick(player, displayReason, reason, by);
            DBInterface.addLog(by, "kick player", player, "reason: " + new TextComponent(reason).toPlainText() + " display reason: " + new TextComponent(displayReason).toPlainText());

            sender.sendMessage("Player " + player + " was kicked");
        } else {
            DBInterface.addLog(by, "tried kicking player", player, "reason: " + new TextComponent(reason).toPlainText() + " display reason: " + new TextComponent(displayReason).toPlainText());

            sender.sendMessage("Player " + player + " not found!");
        }
    }

    public static void ipBan(String[] args) {
        if(args.length != 5) return;

        String by = args[0];
        String player = args[1];
        String duration = args[2];
        String reason = args[3];
        String displayReason = args[4];

        long time = 1000;

        switch (duration.substring(duration.length() - 1)) {
            case "m": time *= 60; break;
            case "h": time *= 3600; break;
            case "d": time *= 86400; break;
        }

        long dur = Long.parseLong(duration.substring(0, duration.length() - 1)) * time;

        ArrayList<ProxiedPlayer> players = new ArrayList<>();
        ProxiedPlayer sender = ProxyServer.getInstance().getPlayer(by);
        String ip = "";

        if(!player.contains(".")) {
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(player);
            if(p != null) {
                ip = p.getAddress().getHostName();

                for (ProxiedPlayer pl:ProxyServer.getInstance().getPlayers()) {
                    if(pl.getAddress().getHostName().equals(ip)) {
                        players.add(pl);
                    }
                }
            } else {
                sender.sendMessage("Player " + player + " not found!");
                return;
            }
        }

        Punishment ban = DBInterface.addIpBan(player, displayReason, reason, by, dur);
        DBInterface.addLog(by, "ban ip", ip, "reason: " + new TextComponent(reason).toPlainText() + " display reason: " + new TextComponent(displayReason).toPlainText());

        for (ProxiedPlayer p:players) {
            p.disconnect(Format.ipBan(ban.getReason(), ban.getExpiration()));
        }
    }

    public static void ipUnban(String[] args) {
        if(args.length != 2) return;

        String by = args[0];
        String target = args[1];

        DBInterface.removeIpBan(target);
        DBInterface.addLog(by, "unban ip", target, "");

        ProxiedPlayer sender = ProxyServer.getInstance().getPlayer(by);
        sender.sendMessage("Ip " + target + " was unbanned");
    }

    private static void announce(String message) {
        TextComponent msg = new TextComponent(message);
        for (ProxiedPlayer p:ProxyServer.getInstance().getPlayers()) {
            p.sendMessage(msg);
        }
    }
}
