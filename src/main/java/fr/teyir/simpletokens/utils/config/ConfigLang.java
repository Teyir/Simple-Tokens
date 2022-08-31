package fr.teyir.simpletokens.utils.config;

import fr.teyir.simpletokens.SimpleTokens;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigLang {

    final private SimpleTokens plugin;
    private File configFile;

    final private String[] availableLang = {"EN", "FR"}; //Default lang files

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

        for (String lang : availableLang) {
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


        if (Objects.requireNonNull(conf.getConfigurationSection("messages")).getString(message) == null) {
            return "§c§lTRANSLATION NOT FOUND §o`" + message + "`";
        }

        return Objects.requireNonNull(Objects.requireNonNull(conf.getConfigurationSection("messages"))
                        .getString(message))
                .replace("&", "§")
                .replace("{{prefix}}", plugin.getConfiguration().getPrefix());
    }

    public void save() throws IOException {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(configFile);
        conf.save(configFile);
    }

}
