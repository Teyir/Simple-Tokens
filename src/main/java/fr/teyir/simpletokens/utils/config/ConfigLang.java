package fr.teyir.simpletokens.utils.config;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.utils.HookManager;
import fr.teyir.simpletokens.utils.HookPAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigLang {

    final private SimpleTokens plugin;
    private File configFile;

    private String[] availablesLang = {"EN", "FR"}; //Default lang files

    public ConfigLang(SimpleTokens plugin) {
        this.plugin = plugin;

        generateConfigFiles();

        configFile = new File(plugin.getDataFolder() + File.separator + "lang" + File.separator + plugin.getConfiguration().getLang() + ".yml");

        Bukkit.getLogger().info("[Simple-Tokens] You are using the lang file: " + configFile.getName());

        load();
    }

    /***
     * Generate default languages files
     */
    private void generateConfigFiles() {

        for(String lang : availablesLang) {
            configFile = new File(plugin.getDataFolder() + File.separator + "lang" + File.separator + lang + ".yml");
            if (!configFile.exists()) {
                plugin.saveResource("lang/" + lang + ".yml", false);
                Bukkit.getLogger().info("[Simple-Tokens] Save lang file " + lang);
            }
        }

    }

    public void load() {
        final YamlConfiguration conf = YamlConfiguration.loadConfiguration(configFile);

        conf.getConfigurationSection("messages");
    }

    public String get(String message) {
        final YamlConfiguration conf = YamlConfiguration.loadConfiguration(configFile);


        if(conf.getConfigurationSection("messages").getString(message) == null){
            return "§c§lTRANSLATION NOT FOUND §o`" + message + "`";
        }

        return Objects.requireNonNull(Objects.requireNonNull(conf.getConfigurationSection("messages"))
                .getString(message))
                .replace("&", "§")
                .replace("{{prefix}}", plugin.getConfiguration().getPrefix());

        /**
         *         //If PlaceholderAPI is installed on the server we cane parse the placeholders
         *         if(new HookPAPI(plugin).isInstalled())
         *         {
         *             message = PlaceholderAPI.setPlaceholders(Player, message);
         *         }
         */
    }

    public void save() throws IOException {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(configFile);
        conf.save(configFile);
    }

}
