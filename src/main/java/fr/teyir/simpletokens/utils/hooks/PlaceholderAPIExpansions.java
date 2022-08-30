package fr.teyir.simpletokens.utils.hooks;

import fr.teyir.simpletokens.SimpleTokens;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansions extends PlaceholderExpansion {

    private SimpleTokens plugin;

    public PlaceholderAPIExpansions(SimpleTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "simpletokens";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Teyir";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        //TODO PAPI EXPANSIONS
        return null;
    }

}