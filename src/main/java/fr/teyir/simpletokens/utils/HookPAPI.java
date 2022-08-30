package fr.teyir.simpletokens.utils;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.utils.hooks.PlaceholderAPIExpansions;
import org.bukkit.Bukkit;

import static org.bukkit.Bukkit.getLogger;

public class HookPAPI {

    private final SimpleTokens plugin;

    public HookPAPI(SimpleTokens plugin) {
        this.plugin = plugin;

        if(isInstalled())
            register();
    }


    public boolean isInstalled()
    {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }


    private void register()
    {
        new PlaceholderAPIExpansions(plugin).register();
        getLogger().info("PlaceholderAPI hook with success");
    }
}
