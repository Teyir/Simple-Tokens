package fr.teyir.simpletokens.listeners;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.db.DBRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class JoinListener implements Listener {
    private final SimpleTokens plugin;

    public JoinListener(SimpleTokens plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();

        //If the player has never played we store this data on the database
        if (!player.hasPlayedBefore() || !new DBRequest(plugin).checkIfPlayerExist(String.valueOf(player.getUniqueId()))) {
            new DBRequest(plugin).initPlayer(String.valueOf(player.getUniqueId()), plugin.getConfiguration().getStartBalance());
        }

    }
}