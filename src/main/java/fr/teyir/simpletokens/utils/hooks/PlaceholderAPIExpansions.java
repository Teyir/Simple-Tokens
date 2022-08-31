package fr.teyir.simpletokens.utils.hooks;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.db.DBRequest;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class PlaceholderAPIExpansions extends PlaceholderExpansion {

    final private SimpleTokens plugin;

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

        if (params.equalsIgnoreCase("balance")) {
            String uuid = player.getUniqueId().toString();

            String value = "";

            try {
                value += new DBRequest(plugin).getPlayerTokens(uuid);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return value;
        } else if (params.startsWith("balance_")) {
            String pseudo = params.split("(?i)balance_")[1];
            Player p = Bukkit.getPlayer(pseudo);

            String value = "";

            if (p != null) {
                try {
                    value += new DBRequest(plugin).getPlayerTokens(p.getUniqueId().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return value;
        }

        return null;
    }

}