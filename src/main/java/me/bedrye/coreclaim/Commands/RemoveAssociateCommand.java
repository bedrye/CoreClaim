package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RemoveAssociateCommand extends AbstractCommand {
    public RemoveAssociateCommand(){
        permission = "coreclaim.player.removeassociate";
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(hasPermission(sender)){
            Player player = (Player) sender;
            Claim claim = ClaimController.getInstance().canAdministrate(player.getLocation().getBlock(),player);
            if(claim!=null && (args.length>1)) {
                    claim.RemoveAssociate(args[1]);
                player.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("remove-associate"));
            }
        }
        else {
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("not-enough-perms"));
        }
    }
}
