package fr.teyir.simpletokens.commands;

import fr.teyir.simpletokens.SimpleTokens;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICommand {
    String getLabel();

    String getUsage();

    String getPermission();

    String getDescription();

    int getMinArgs();

    int getMaxArgs();

    void perform(SimpleTokens plugin, CommandSender sender, String[] args);

    List<String> tabComplete(SimpleTokens plugin, CommandSender sender, String[] args);
}
