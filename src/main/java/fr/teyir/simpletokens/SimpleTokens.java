package fr.teyir.simpletokens;

import fr.teyir.simpletokens.commands.CommandsHandler;
import fr.teyir.simpletokens.db.DBRequest;
import fr.teyir.simpletokens.db.DatabaseManager;
import fr.teyir.simpletokens.listeners.JoinListener;
import fr.teyir.simpletokens.utils.HookManager;
import fr.teyir.simpletokens.utils.config.Config;
import fr.teyir.simpletokens.utils.config.ConfigLang;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

public final class SimpleTokens extends JavaPlugin {

    private static Config configuration;
    private static ConfigLang configLang;
    private DatabaseManager connection;


    private void logAndDisable(Level level, String log) {
        getLogger().log(level, log);
        Bukkit.getPluginManager().disablePlugin(this);
    }

    @Override
    public void onEnable() {
        SimpleTokens plugin = this;

        /* HOOK MANAGER */
        try {
            new HookManager(this);
        } catch (Throwable throwable)
        {
            logAndDisable(Level.WARNING, "Hook Manager issue, please look your installed plugins (PlaceholderAPI)");
        }

        if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
            logAndDisable(Level.SEVERE, "Unable to create the plugin folder !");
        }

        /* Load config area */
        configuration = new Config(this);
        configLang = new ConfigLang(this);
        connection = new DatabaseManager(configuration.getDBUser(), configuration.getDBPass(), configuration.getDB());


        /* INIT DATABASE ON STARTUP */
        try {
            new DBRequest(plugin).initDB();
            getLogger().info("Database ready !");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        /* Commands register */
        CommandsHandler commandsHandler = new CommandsHandler(this);
        Objects.requireNonNull(getCommand("tokens")).setExecutor(commandsHandler);
        Objects.requireNonNull(getCommand("tokens")).setTabCompleter(commandsHandler);

        /* Register events */
        new JoinListener(this);

        getLogger().info("Plugin Simple Tokens by Teyir enabled with success !");
        getLogger().info("For a better support join our Discord -> http://discord.teyir.fr");
    }

    @Override
    public void onDisable() {

        try {
            configuration.save();
            configLang.save();
            getLogger().info("Configurations files save with success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.disconnect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        getLogger().info("Plugin Simple Tokens by Teyir disabled with success !");

    }

    public DatabaseManager getConnection() {
        return this.connection;
    }

    public Config getConfiguration() {
        return configuration;
    }
    public ConfigLang getLangConfiguration() {
        return configLang;
    }

    public String getLang(@NotNull String message){
        return configLang.get(message);
    }
}
