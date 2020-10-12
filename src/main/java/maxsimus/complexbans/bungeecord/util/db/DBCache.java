package maxsimus.complexbans.bungeecord.util.db;

import maxsimus.complexbans.api.Logger;
import maxsimus.complexbans.bungeecord.util.Punishment;

import java.util.HashMap;

public class DBCache {
    static HashMap<String, Punishment> bans = new HashMap<>();
    static HashMap<String, Punishment> ipBans = new HashMap<>();
    static HashMap<String, Punishment> mutes = new HashMap<>();

    public static void load() {
        bans = DBInterface.requestData("ban");
        ipBans = DBInterface.requestData("ipban");
        mutes = DBInterface.requestData("mute");

        Logger.info("§2Loaded punishments!");
    }

    public static void addBan(String player, Punishment ban) {
        bans.put(player, ban);
    }
    public static void addIpBan(String player, Punishment ipBan) {
        ipBans.put(player, ipBan);
    }
    public static void addMute(String player, Punishment mute) {
        mutes.put(player, mute);
    }

    public static void removeBan(String player) {
        bans.remove(player);
    }
    public static void removeIpBan(String player) {
        ipBans.remove(player);
    }
    public static void removeMute(String player) {
        mutes.remove(player);
    }

    public static Punishment getBanned(String player) {
        Punishment ban = bans.get(player);

        if(ban != null) {
            if(ban.isValid()) {
                return ban;
            } else {
                bans.remove(player);
                DBInterface.removeExpired("ban");
            }
        }

        return null;
    }

    public static Punishment getIpBanned(String ip) {
        Punishment ipban = ipBans.get(ip);

        if(ipban != null) {
            if(ipban.isValid()) {
                return ipban;
            } else {
                bans.remove(ip);
                DBInterface.removeExpired("ipban");
            }
        }

        return null;
    }

    public static Punishment getMuted(String player) {
        Punishment mute = mutes.get(player);

        if(mute != null) {
            if(mute.isValid()) {
                return mute;
            } else {
                bans.remove(player);
                DBInterface.removeExpired("mute");
            }
        }

        return null;
    }
}
