package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class ChangeOwnerCommand extends AbstractCommand {

    public ChangeOwnerCommand(){
        permission = "coreclaim.player.changeowner";
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(hasPermission(sender)){
            Player player = (Player) sender;
            Claim claim = ClaimController.getInstance().canAdministrate(player.getLocation().getBlock(),player);
            if(claim!=null && (args.length>1)) {
                Player player1 = Bukkit.getPlayer(args[1]);
                if(player1!=null) {
                    claim.ChangeOwner(player1);
                    player.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("change-owner"));
                    player1.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("new-owner"));
                }
                else
                    player.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("invalid-player"));

            }
        }
        else {
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("not-enough-perms"));
        }
    }
}
