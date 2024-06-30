package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Objects;

public class AddAssociateCommand extends AbstractCommand {
    public AddAssociateCommand(){
        permission = "coreclaim.player.addassociate";
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(hasPermission(sender)){
            Player player = (Player) sender;
            Claim claim = ClaimController.getInstance().canAdministrate(player.getLocation().getBlock(),player);
            if(claim!=null && (args.length>1)) {
                Player player1 = Bukkit.getPlayer(args[1]);
                if(player1!=null) {
                    claim.AddAssociate(Objects.requireNonNull(args[1]));
                    player.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("add-associate"));
                    player1.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("new-associate"));
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
