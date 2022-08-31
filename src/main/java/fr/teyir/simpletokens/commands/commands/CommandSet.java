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

public class CommandSet implements ICommand {

    private SimpleTokens plugin;

    public CommandSet(SimpleTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getLabel() {
        return "set";
    }

    @Override
    public String getUsage() {
        return "tokens set PSEUDO AMOUNT";
    }

    @Override
    public String getPermission() {
        return "simpletokens.set";
    }

    @Override
    public String getDescription() {
        return plugin.getLang("commands.commandSetDescription");
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
                    dbRequest.setAllPlayersTokens(amount);
                    sender.sendMessage(plugin.getLang("commands.commandSetMessageMultiplesAll")
                            .replace("{{amount}}", args[2]));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                /* Query online players (%) */
            } else if (args[1].equalsIgnoreCase("%")) {
                Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);

                if(onlinePlayers.length == 0){
                    sender.sendMessage(plugin.getLang("errors.noOnlinePlayers"));
                    return;
                }

                for(Player p : onlinePlayers) {
                    new UserUtils(plugin).setMoney(String.valueOf(p.getUniqueId()), amount);
                    sender.sendMessage(plugin.getLang("commands.commandSetMessageMultiplesOnline")
                            .replace("{{amount}}", args[2]));
                }

                /* Query the specific player */
            } else {
                Player target = Bukkit.getPlayer(args[1]);

                if(target != null) {
                    String targetUUID = String.valueOf(target.getUniqueId());

                    new UserUtils(plugin).setMoney(targetUUID, amount);

                    sender.sendMessage(plugin.getLang("commands.commandSetMessageSingle")
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
            list.add("set");
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
