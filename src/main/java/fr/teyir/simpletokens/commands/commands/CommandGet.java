package fr.teyir.simpletokens.commands.commands;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.commands.ICommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

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

    }

    @Override
    public List<String> tabComplete(SimpleTokens plugin, CommandSender sender, String[] args) {

        if (!sender.hasPermission(getPermission())) {
            return new ArrayList<>();
        }

        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("get");
            return list;
        }

        return null;
    }
}
