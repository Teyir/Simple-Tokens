package fr.teyir.simpletokens.utils;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.utils.hooks.Metrics;

public class HookManager {

    private final SimpleTokens plugin;

    public HookManager(SimpleTokens plugin) {
        this.plugin = plugin;

        bStats();
        registerExpansions();
    }

    private void registerExpansions()
    {
        //REGISTER PAPI
        new HookPAPI(plugin);
    }

    private void bStats() {
        int pluginId = 16321;
        Metrics metrics = new Metrics(plugin, pluginId);
    }

}
