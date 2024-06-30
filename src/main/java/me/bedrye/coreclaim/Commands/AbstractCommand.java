package me.bedrye.coreclaim.Commands;

import org.bukkit.command.CommandSender;




public abstract class AbstractCommand {
    protected String permission;
    public abstract void execute(CommandSender sender, String[] args);
    protected final boolean hasPermission(CommandSender sender){
            return sender.hasPermission(permission);
    }
}
