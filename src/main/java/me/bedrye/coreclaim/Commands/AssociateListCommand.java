package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AssociateListCommand extends AbstractCommand {

    public AssociateListCommand(){
        permission = "coreclaim.player.list";
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(hasPermission(sender)){
            Player player = (Player) sender;
            Claim claim = ClaimController.getInstance().canAdministrate(player.getLocation().getBlock(),player);
            if(claim!=null) {
                player.sendMessage(ConfigController.getInstance().getPrefix()+claim.getAssociatesList());
            }
        }
        else {
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("not-enough-perms"));
        }
    }
}
