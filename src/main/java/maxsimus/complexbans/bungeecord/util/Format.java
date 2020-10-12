package maxsimus.complexbans.bungeecord.util;

import net.md_5.bungee.api.ChatColor;

import static maxsimus.complexbans.bungeecord.config.ConfigManager.getMessages;

public class Format {
    public static String ban(String reason, String expiration) {
        StringBuilder str = new StringBuilder();

        for (String line:getMessages().getStringList("ban-template")) {
            str.append(line.replace("%reason%", reason).replace("%expiration%", expiration)).append("\n");
        }
        return ChatColor.translateAlternateColorCodes('&', str.toString());
    }

    public static String ipBan(String reason, String expiration) {
        StringBuilder str = new StringBuilder();

        for (String line:getMessages().getStringList("ipban-template")) {
            str.append(line.replace("%reason%", reason).replace("%expiration%", expiration)).append("\n");
        }
        return ChatColor.translateAlternateColorCodes('&', str.toString());
    }

    public static String kick(String reason) {
        StringBuilder str = new StringBuilder();

        for (String line:getMessages().getStringList("kick-template")) {
            str.append(line.replace("%reason%", reason)).append("\n");
        }
        return ChatColor.translateAlternateColorCodes('&', str.toString());
    }
}
