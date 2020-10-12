package maxsimus.complexbans.bungeecord.util.db;

import maxsimus.complexbans.api.Logger;
import maxsimus.complexbans.bungeecord.util.Punishment;

import java.sql.*;
import java.util.HashMap;

import static maxsimus.complexbans.bungeecord.config.ConfigManager.getMysql;

public class DBInterface {
    static Connection connection;
    static Statement statement;

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + getMysql().getString("address") + "/" + getMysql().getString("database")
                    + "?user=" + getMysql().getString("username")
                    + "&password=" + getMysql().getString("password")
                    + "&autoReconnect=true&ssl=false");
            statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS `complexbans_bans` (\n" +
                    "\t`player` VARCHAR(64) NOT NULL,\n" +
                    "\t`display_reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`by` VARCHAR(64) NOT NULL,\n" +
                    "\t`on` BIGINT NOT NULL,\n" +
                    "\t`until` BIGINT NOT NULL,\n" +
                    "\t`length` BIGINT NOT NULL\n" +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS `complexbans_ipbans` (\n" +
                    "\t`ip` VARCHAR(64) NOT NULL,\n" +
                    "\t`display_reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`by` VARCHAR(64) NOT NULL,\n" +
                    "\t`on` BIGINT NOT NULL,\n" +
                    "\t`until` BIGINT NOT NULL,\n" +
                    "\t`length` BIGINT NOT NULL\n" +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS `complexbans_kicks` (\n" +
                    "\t`player` VARCHAR(64) NOT NULL,\n" +
                    "\t`display_reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`by` VARCHAR(64) NOT NULL,\n" +
                    "\t`on` BIGINT NOT NULL\n" +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS `complexbans_warns` (\n" +
                    "\t`player` VARCHAR(64) NOT NULL,\n" +
                    "\t`display_reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`by` VARCHAR(64) NOT NULL,\n" +
                    "\t`on` BIGINT NOT NULL\n" +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS `complexbans_mutes` (\n" +
                    "\t`player` VARCHAR(64) NOT NULL,\n" +
                    "\t`display_reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`reason` VARCHAR(64) NOT NULL,\n" +
                    "\t`by` VARCHAR(64) NOT NULL,\n" +
                    "\t`on` BIGINT NOT NULL,\n" +
                    "\t`until` BIGINT NOT NULL,\n" +
                    "\t`length` BIGINT NOT NULL\n" +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS `complexbans_logs` (\n" +
                    "\t`user` VARCHAR(64) NOT NULL,\n" +
                    "\t`action` VARCHAR(64) NOT NULL,\n" +
                    "\t`target` VARCHAR(64) NOT NULL,\n" +
                    "\t`info` VARCHAR(64) NOT NULL,\n" +
                    "\t`on` BIGINT NOT NULL\n" +
                    ");");

            Logger.info("§2Successfully connected to the database!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Punishment addBan(String player, String displayReason, String reason, String by, long length) {
        try {
            long on = System.currentTimeMillis();
            long until = length + on;

            if(DBCache.getBanned(player) != null) {
                statement.execute("UPDATE `complexbans_bans` SET until = " + until + " WHERE player = '" + player + "'");
            } else {
                statement.execute("INSERT INTO `complexbans_bans` VALUES ('" + player + "', '" + displayReason + "', '" + reason + "', '" + by + "', " + on + ", " + until + ", " + length + ")");
            }

            Punishment punishment = new Punishment(displayReason, until);
            DBCache.addBan(player, new Punishment(displayReason, until));

            return punishment;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Punishment addIpBan(String ip, String displayReason, String reason, String by, long length) {
        try {
            long on = System.currentTimeMillis();
            long until = length + on;

            if(DBCache.getIpBanned(ip) != null) {
                statement.execute("UPDATE `complexbans_ipbans` SET until = " + until + " WHERE ip = '" + ip + "'");
            } else {
                statement.execute("INSERT INTO `complexbans_ipbans` VALUES ('" + ip + "', '" + displayReason + "', '" + reason + "', '" + by + "', " + on + ", " + until + ", " + length + ")");
            }

            Punishment punishment = new Punishment(displayReason, until);
            DBCache.addIpBan(ip, punishment);

            return punishment;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void addMute(String player, String displayReason, String reason, String by, long length) {
        try {
            long on = System.currentTimeMillis();
            long until = length + on;

            if(DBCache.getMuted(player) != null) {
                statement.execute("UPDATE `complexbans_mutes` SET until = " + until + " WHERE player = '" + player + "'");
            } else {
                statement.execute("INSERT INTO `complexbans_mutes` VALUES ('" + player + "', '" + displayReason + "', '" + reason + "', '" + by + "', " + on + ", " + until + ", " + length + ")");
            }

            DBCache.addMute(player, new Punishment(displayReason, until));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addKick(String player, String displayReason, String reason, String by) {
        try {
            long on = System.currentTimeMillis();
            statement.execute("INSERT INTO `complexbans_kicks` VALUES ('" + player + "', '" + displayReason + "', '" + reason + "', '" + by + "', " + on + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addWarn(String player, String displayReason, String reason, String by) {
        try {
            long on = System.currentTimeMillis();
            statement.execute("INSERT INTO `complexbans_warns` VALUES ('" + player + "', '" + displayReason + "', '" + reason + "', '" + by + "', " + on + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLog(String user, String action, String target, String info) {
        try {
            long on = System.currentTimeMillis();
            statement.execute("INSERT INTO `complexbans_logs` VALUES ('" + user + "', '" + action + "', '" + target + "', '" + info + "', " + on + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeBan(String target) {
        try {
            statement.execute("DELETE FROM `complexbans_bans` WHERE player = '" + target + "'");
            DBCache.removeBan(target);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeIpBan(String target) {
        try {
            statement.execute("DELETE FROM `complexbans_ipbans` WHERE ip = '" + target + "'");
            DBCache.removeIpBan(target);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeMute(String target) {
        try {
            statement.execute("DELETE FROM `complexbans_mutes` WHERE player = '" + target + "'");
            DBCache.removeMute(target);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeExpired(String type) {
        try {
            statement.execute("DELETE FROM `complexbans_" + type + "s` WHERE until < " + System.currentTimeMillis());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Punishment> requestData(String type) {
        try {
            removeExpired(type);

            String name = type.equals("ipban") ? "ip" : "player";

            ResultSet rs = statement.executeQuery("SELECT `" + name + "`, `display_reason`, `until` FROM `complexbans_" + type + "s` ORDER BY `until` ASC");
            HashMap<String, Punishment> result = new HashMap<>();

            while (rs.next()) {
                result.put(rs.getString(name), new Punishment(rs.getString("display_reason"), rs.getLong("until")));
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
