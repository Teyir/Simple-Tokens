package fr.teyir.simpletokens.commands.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.ICommand;
import fr.teyir.simpletokens.db.DBRequest;
import fr.teyir.simpletokens.utils.LogsManager;
import fr.teyir.simpletokens.utils.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandPay implements ICommand {

    private SimpleTokens plugin;

    public CommandPay(SimpleTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getLabel() {
        return "pay";
    }

    @Override
    public String getUsage() {
        return "tokens pay PSEUDO AMOUNT";
    }

    @Override
    public String getPermission() {
        return "simpletokens.pay";
    }

    @Override
    public String getDescription() {
        return plugin.getLang("commands.commandPayDescription");
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
        if (sender instanceof Player senderPlayer) {

            DBRequest dbRequest = new DBRequest(plugin);
            Player target = Bukkit.getPlayer(args[1]);

            try {
                int amount = Integer.parseInt(args[2]);

                if (amount <= 0) {
                    sender.sendMessage(plugin.getLang("errors.invalidNumber"));
                    return;
                }

                if (target != null) {
                    String targetUUID = String.valueOf(target.getUniqueId());
                    String senderUUID = String.valueOf(senderPlayer.getUniqueId());
                    try {
                        int senderTokens = dbRequest.getPlayerTokens(String.valueOf(senderPlayer.getUniqueId()));

                        if (senderTokens >= amount) {
                            new UserUtils(plugin).sendMoney(senderUUID, targetUUID, amount);

                            sender.sendMessage(plugin.getLang("commands.commandPayMessage")
                                    .replace("{{amount}}", args[2])
                                    .replace("{{player}}", target.getDisplayName())
                            );
                            LogsManager logs = new LogsManager(plugin);
                            String content = sender.getName() + " has pay " + args[2] + " " + plugin.getConfiguration().getTokenName() + " to " + target.getDisplayName();
                            logs.sendLog(content);
                        } else {
                            sender.sendMessage(plugin.getLang("errors.insufficientBalance"));
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    sender.sendMessage(plugin.getLang("errors.invalidPlayer"));
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.getLang("errors.invalidNumber"));
            }


        } else {
            sender.sendMessage(plugin.getLang("errors.onlyPlayers"));
        }

    }

    @Override
    public List<String> tabComplete(SimpleTokens plugin, CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("pay");
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
