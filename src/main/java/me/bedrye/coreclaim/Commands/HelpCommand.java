package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.ConfigController;
import org.bukkit.command.CommandSender;

public class HelpCommand extends AbstractCommand{


    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ConfigController.getInstance().getFromDictionary("help-info"));
    }
}
