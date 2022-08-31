package fr.teyir.simpletokens.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.commands.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandsHandler implements CommandExecutor, TabCompleter {

    private final SimpleTokens plugin;

    private final List<ICommand> subCommands = new ArrayList<>();

    public CommandsHandler(SimpleTokens plugin) {
        this.plugin = plugin;

        /* Add commands files */
        subCommands.add(new CommandGet(plugin));
        subCommands.add(new CommandBalance(plugin));
        subCommands.add(new CommandTake(plugin));
        subCommands.add(new CommandGive(plugin));
        subCommands.add(new CommandSet(plugin));
        subCommands.add(new CommandPay(plugin));
        subCommands.add(new CommandTop(plugin));
        subCommands.add(new CommandReload(plugin));

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 0) {
            for (ICommand subCommand : subCommands) {
                if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
                    if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission()) && !sender.isOp() && !(sender instanceof ConsoleCommandSender)) {
                        sender.sendMessage(plugin.getLang("errors.noPermission"));
                        return false;
                    }
                    if (args.length < subCommand.getMinArgs() || args.length > subCommand.getMaxArgs()) {
                        sender.sendMessage(plugin.getLang("subCommands.helpHeader"));
                        sender.sendMessage(plugin.getLang("subCommands.helpPrefix") + subCommand.getUsage());
                        sender.sendMessage(plugin.getLang("subCommands.helpFooter"));
                        return false;
                    }
                    //Header
                    if (plugin.getConfiguration().isDisplayFramedOnQuery())
                        sender.sendMessage(plugin.getLang("subCommands.helpHeader"));
                    //Command
                    subCommand.perform(plugin, sender, args);
                    //Footer
                    if (plugin.getConfiguration().isDisplayFramedOnQuery())
                        sender.sendMessage(plugin.getLang("subCommands.helpFooter"));
                    return true;
                }
            }
        }

        //Checking that the player has permission to use at least one of the commands.
        for (ICommand subCommand : subCommands) {
            if (sender.isOp() || sender instanceof ConsoleCommandSender || sender.hasPermission(subCommand.getPermission())) {
                sender.sendMessage(plugin.getLang("subCommands.helpHeader"));

                for (ICommand cmd : subCommands)
                    sender.sendMessage(plugin.getLang("subCommands.helpUsage")
                            .replace("{{usage}}", cmd.getUsage())
                            .replace("{{description}}", cmd.getDescription()));

                sender.sendMessage(plugin.getLang("subCommands.helpFooter"));

                return false;
            }
        }


        sender.sendMessage(plugin.getLang("errors.noPermission"));

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length > 0) {
            for (ICommand subCommand : subCommands) {
                if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
                    if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
                        return new ArrayList<>();
                    }
                    return subCommand.tabComplete(plugin, sender, args);
                }
            }
        }

        List<String> list = new ArrayList<>();

        for (ICommand subCommand : subCommands)
            if (subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission()))
                if (subCommand.getLabel().startsWith(args[0]))
                    list.add(subCommand.getLabel());

        return list;
    }
}
