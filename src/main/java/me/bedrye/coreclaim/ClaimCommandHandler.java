package me.bedrye.coreclaim;

import me.bedrye.coreclaim.Commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.plugin.java.JavaPlugin.getProvidingPlugin;

public class ClaimCommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equalsIgnoreCase("cc")
                && (commandSender instanceof Player)){
            if(ClaimController.getInstance().hasCooldown(((Player) commandSender).getPlayer())){
                commandSender.sendMessage(
                    ConfigController.getInstance().getFromDictionaryPrefix("cooldown-message")
            );
                return true;}
            ClaimController.getInstance().addCooldown((Player) commandSender);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ClaimController.getInstance().removeCooldown((Player) commandSender);
                }
            }.runTaskLaterAsynchronously( getProvidingPlugin(CoreClaim.class),20L);
            AbstractCommand command1;
            if( strings.length==0) return true;
            switch (strings[0].toLowerCase()){
                    case "give":
                        command1= new GiveCommand();
                    break;
                    case "reload":
                        command1= new ReloadCommand();
                        break;
                    case "add":
                        command1= new AddAssociateCommand();
                        break;
                    case "remove":
                        command1= new RemoveAssociateCommand();
                        break;
                    case "changeown":
                        command1= new ChangeOwnerCommand();
                        break;
                    case "info":
                        command1= new InfoCommand();
                        break;
                    case "list":
                        command1= new AssociateListCommand();
                        break;
                    default:
                        command1= new HelpCommand();
                        break;
                }
                command1.execute(commandSender,strings);

        }


        return true;
    }
}
