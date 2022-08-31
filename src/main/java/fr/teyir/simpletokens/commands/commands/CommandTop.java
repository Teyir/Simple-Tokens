package fr.teyir.simpletokens.commands.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.ICommand;
import fr.teyir.simpletokens.db.DBRequest;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CommandTop implements ICommand {

    private SimpleTokens plugin;

    public CommandTop(SimpleTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getLabel() {
        return "top";
    }

    @Override
    public String getUsage() {
        return "tokens top";
    }

    @Override
    public String getPermission() {
        return "simpletokens.top";
    }

    @Override
    public String getDescription() {
        return plugin.getLang("commands.commandTopDescription");
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public int getMaxArgs() {
        return 1;
    }

    @Override
    public void perform(SimpleTokens plugin, CommandSender sender, String[] args) {

        try {
            ResultSet rs = new DBRequest(plugin).getTopTokens();

            if (rs.next()) {
                int i = 1;
                do {
                    final String uuid = rs.getString("uuid");
                    final OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                    final int amount = rs.getInt("tokens");

                    sender.sendMessage(plugin.getLang("commands.commandTopLine")
                            .replace("{{rank}}", String.valueOf(i))
                            .replace("{{pseudo}}", Objects.requireNonNull(player.getName()))
                            .replace("{{amount}}", String.valueOf(amount)));

                    i++;
                } while (rs.next());
            } else {
                sender.sendMessage(plugin.getLang("commands.commandTopFirst"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> tabComplete(SimpleTokens plugin, CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("top");
            return list;
        }
        return null;
    }
}
