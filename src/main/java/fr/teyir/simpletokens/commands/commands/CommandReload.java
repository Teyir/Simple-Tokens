package fr.teyir.simpletokens.commands.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.ICommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandReload implements ICommand {

    final private SimpleTokens plugin;

    public CommandReload(SimpleTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getLabel() {
        return "reload";
    }

    @Override
    public String getUsage() {
        return "tokens reload";
    }

    @Override
    public String getPermission() {
        return "simpletokens.reload";
    }

    @Override
    public String getDescription() {
        return plugin.getLang("commands.commandReloadDescription");
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
            plugin.getConfiguration().load();
            plugin.getLangConfiguration().load();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        sender.sendMessage(plugin.getLang("commands.commandReloadMessage"));
    }

    @Override
    public List<String> tabComplete(SimpleTokens plugin, CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("reload");
            return list;
        }
        return null;
    }
}
