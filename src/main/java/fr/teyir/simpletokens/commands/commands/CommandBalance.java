package fr.teyir.simpletokens.commands.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.ICommand;
import fr.teyir.simpletokens.db.DBRequest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandBalance implements ICommand {

    private final SimpleTokens plugin;

    public CommandBalance(SimpleTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getLabel() {
        return "balance";
    }

    @Override
    public String getUsage() {
        return "tokens balance";
    }

    @Override
    public String getPermission() {
        return "simpletokens.balance";
    }

    @Override
    public String getDescription() {
        return plugin.getLang("commands.commandBalanceDescription");
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

        if (sender instanceof Player target) {

            String uuid = String.valueOf(target.getUniqueId());

            DBRequest dbRequest = new DBRequest(plugin);

            try {
                int tokens = dbRequest.getPlayerTokens(uuid);

                sender.sendMessage(plugin.getLang("commands.commandBalanceMessage")
                        .replace("{{tokens}}", String.valueOf(tokens)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            sender.sendMessage(plugin.getLang("errors.invalidPlayer"));
        }

    }

    @Override
    public List<String> tabComplete(SimpleTokens plugin, CommandSender sender, String[] args) {

        if (!sender.hasPermission(getPermission())) {
            return new ArrayList<>();
        }

        return null;
    }
}
