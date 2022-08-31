package fr.teyir.simpletokens.utils.config;

import fr.teyir.simpletokens.SimpleTokens;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    final private SimpleTokens plugin;
    final private File configFile;

    /* DATABASE */
    private String dbUser;
    private String dbPass;
    private String dbHost;
    private int dbPort;
    private String dbName;

    /* GENERAL */
    private String lang;
    private int startBalance;
    private String tokenName;
    private String prefix;
    private int topMaxDisplay;
    private boolean displayFramedOnQuery;

    private boolean istransactionLogs;

    public Config(SimpleTokens plugin) {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");

        if (!configFile.exists()) plugin.saveResource("config.yml", false);

        load();
    }

    public void load() {
        final YamlConfiguration conf = YamlConfiguration.loadConfiguration(configFile);
        this.initDatabaseConfig(conf);
        this.initGeneralConfig(conf);
    }

    public void save() throws IOException {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(configFile);
        conf.save(configFile);
    }

    private void initGeneralConfig(YamlConfiguration conf) {
        lang = conf.getString("general.language", "EN");
        startBalance = conf.getInt("general.startBalance", 0);
        tokenName = conf.getString("general.tokenName", "Tokens");
        prefix = conf.getString("general.prefix", "Tokens > ");
        topMaxDisplay = conf.getInt("general.topMaxDisplay", 10);
        displayFramedOnQuery = conf.getBoolean("general.displayFramedOnCommandQuery", false);
        istransactionLogs = conf.getBoolean("general.transactionLogs", true);
    }

    private void initDatabaseConfig(YamlConfiguration conf) {
        dbUser = conf.getString("database.MySQL.user");
        dbPass = conf.getString("database.MySQL.pass");
        dbHost = conf.getString("database.MySQL.host", "127.0.0.1");
        dbPort = conf.getInt("database.MySQL.port", 3306);
        dbName = conf.getString("database.MySQL.db");
    }

    public String getDB() {
        return "jdbc:mysql://" +
                dbHost + ':' +
                dbPort + '/' +
                dbName;
    }

    public String getDBUser() {
        return dbUser;
    }

    public String getDBPass() {
        return dbPass;
    }

    public int getDbPort() {
        return dbPort;
    }

    public int getStartBalance() {
        return startBalance;
    }

    public String getLang() {
        return lang;
    }

    public String getTokenName() {
        return tokenName;
    }

    public String getPrefix() {
        return prefix.replace("&", "§");
    }

    public int getTopMaxDisplay() {
        return topMaxDisplay;
    }

    public boolean isDisplayFramedOnQuery() {
        return displayFramedOnQuery;
    }

    public boolean isTransactionLogs() {
        return istransactionLogs;
    }
}
