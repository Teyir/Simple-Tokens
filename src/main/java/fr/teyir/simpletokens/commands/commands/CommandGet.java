package fr.teyir.simpletokens.commands.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.ICommand;
import fr.teyir.simpletokens.db.DBRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandGet implements ICommand {

    private final SimpleTokens plugin;

    public CommandGet(SimpleTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getLabel() {
        return "get";
    }

    @Override
    public String getUsage() {
        return "tokens get PSEUDO";
    }

    @Override
    public String getPermission() {
        return "simpletokens.get";
    }

    @Override
    public String getDescription() {
        return plugin.getLang("commands.commandGetUsage");
    }

    @Override
    public int getMinArgs() {
        return 2;
    }

    @Override
    public int getMaxArgs() {
        return 2;
    }

    @Override
    public void perform(SimpleTokens plugin, CommandSender sender, String[] args) {

        Player target = Bukkit.getPlayer(args[1]);

        if (target != null) {
            String uuid = String.valueOf(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId());

            DBRequest dbRequest = new DBRequest(plugin);

            try {
                 int tokens = dbRequest.getPlayerTokens(uuid);

                sender.sendMessage(plugin.getLang("commands.commandGetMessage")
                        .replace("{{player}}", target.getDisplayName())
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

        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("get");
            return list;
        }

        return null;
    }
}
