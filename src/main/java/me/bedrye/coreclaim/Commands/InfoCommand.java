package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import me.bedrye.coreclaim.TimerTaskManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class InfoCommand extends AbstractCommand {

    public InfoCommand(){
        permission = "coreclaim.player.info";
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(hasPermission(sender)){
            Player player = (Player) sender;
            Claim claim = ClaimController.getInstance().canAdministrate(player.getLocation().getBlock(),player);
            if(claim!=null) {
                player.sendMessage(ConfigController.getInstance().getPrefix()+claim.getInfo());
                TimerTaskManager.getInstance().RunTask(claim,player,15);
            }
        }
        else {
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("not-enough-perms"));
        }
    }
}
