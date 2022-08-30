package fr.teyir.simpletokens.commands.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.ICommand;
import fr.teyir.simpletokens.db.DBRequest;
import fr.teyir.simpletokens.utils.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandTake implements ICommand {

    private final SimpleTokens plugin;

    public CommandTake(SimpleTokens plugin) {
        this.plugin = plugin;
    }
    @Override
    public String getLabel() {
        return "take";
    }

    @Override
    public String getUsage() {
        return "tokens take PLAYER AMOUNT";
    }

    @Override
    public String getPermission() {
        return "simpletokens.take";
    }

    @Override
    public String getDescription() {
        return plugin.getLang("commands.commandTakeDescription");
    }

    @Override
    public int getMinArgs() {
        return 3;
    }

    @Override
    public int getMaxArgs() {
        return 3;
    }

    @Override
    public void perform(SimpleTokens plugin, CommandSender sender, String[] args) {


        DBRequest dbRequest = new DBRequest(plugin);


        int amount = Integer.parseInt(args[2]);

        if (args[1] != null) {

            /* Query all players (*) */
            if(args[1].equalsIgnoreCase("*")){
                try {
                    dbRequest.removeAllPlayersTokens(amount);
                    sender.sendMessage(plugin.getLang("commands.commandTakeMessageMultiplesAll")
                            .replace("{{amount}}", args[2]));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                /* Query online players (%) */
            } else if (args[1].equalsIgnoreCase("%")) {
                Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);

                for(Player p : onlinePlayers) {
                    new UserUtils(plugin).removeMoney(String.valueOf(p.getUniqueId()), amount);
                    sender.sendMessage(plugin.getLang("commands.commandTakeMessageMultiplesOnline")
                            .replace("{{amount}}", args[2]));
                }

                /* Query the specific player */
            } else {
                Player target = Bukkit.getPlayer(args[1]);

                if(target != null) {
                    String targetUUID = String.valueOf(target.getUniqueId());

                    new UserUtils(plugin).removeMoney(targetUUID, amount);

                    sender.sendMessage(plugin.getLang("commands.commandTakeMessageSingle")
                            .replace("{{amount}}", args[2])
                            .replace("{{player}}", target.getDisplayName())
                    );
                }

            }








        }

    }

    @Override
    public List<String> tabComplete(SimpleTokens plugin, CommandSender sender, String[] args) {

        if (!sender.hasPermission(getPermission())) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("take");
            return list;
        }

        if (args.length == 2) {
            List<String> list = new ArrayList<>();
            list.add("*");
            list.add("%");

            Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);

            for(Player p : onlinePlayers) {
                list.add(p.getName());
            }

            return list;
        }

        if (args.length == 3) {
            List<String> list = new ArrayList<>();
            list.add("1");
            return list;
        }

        return null;
    }
}
