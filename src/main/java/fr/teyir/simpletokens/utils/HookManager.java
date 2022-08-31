package fr.teyir.simpletokens.utils;

import fr.teyir.simpletokens.SimpleTokens;

public class HookManager {

    private final SimpleTokens plugin;

    public HookManager(SimpleTokens plugin) {
        this.plugin = plugin;

        registerExpansions();
    }

    private void registerExpansions()
    {
        //REGISTER PAPI
        new HookPAPI(plugin);
    }

}
