package maxsimus.complexbans.bungeecord.config;

import maxsimus.complexbans.bungeecord.ComplexBans;

import net.md_5.bungee.config.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    static ConfigurationProvider provider = YamlConfiguration.getProvider(YamlConfiguration.class);

    // Files & File Configs Here
    public static Configuration mysqlCfg;
    public static File mysqlFile;
    public static Configuration messagesCfg;
    public static File messagesFile;
    public static Configuration rulesCfg;
    public static File rulesFile;
    // --------------------------

    public static void init(ComplexBans plug) {
        if (!plug.getDataFolder().exists()) {
            plug.getDataFolder().mkdir();
        }

        mysqlFile = new File(plug.getDataFolder(), "mysql.yml");
        messagesFile = new File(plug.getDataFolder(), "messages.yml");
        rulesFile = new File(plug.getDataFolder(), "rules.yml");

        if (!mysqlFile.exists()) saveResource(plug, "mysql.yml");
        if (!messagesFile.exists()) saveResource(plug, "messages.yml");
        if (!rulesFile.exists()) saveResource(plug, "rules.yml");

        try {
            mysqlCfg = provider.load(mysqlFile);
            messagesCfg = provider.load(messagesFile);
            rulesCfg = provider.load(rulesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void saveResource(ComplexBans plug, String res) {
        File file = new File(plug.getDataFolder(), res);

        try (InputStream in = plug.getResourceAsStream(res)) {
            Files.copy(in, file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getMysql() {
        return mysqlCfg;
    }

    public static Configuration getMessages() {
        return messagesCfg;
    }

    public static Configuration getRules() {
        return rulesCfg;
    }

    public static void saveMysql() {
        try {
            provider.save(mysqlCfg, mysqlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMessages() {
        try {
            provider.save(messagesCfg, messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRules() {
        try {
            provider.save(rulesCfg, rulesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadAll() {
        try {
            mysqlCfg = provider.load(mysqlFile);
            messagesCfg = provider.load(messagesFile);
            rulesCfg = provider.load(rulesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
