package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.List;


public class ReloadCommand extends AbstractCommand {
    public ReloadCommand(){
        permission = "coreclaim.admin.reload";
    }
    @Override
    public void execute(CommandSender sender,String[] args) {
        if(hasPermission(sender)){
            ConfigController.getInstance().updateConfig();
            ClaimController.getInstance().UpdateClaims();
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("reload-info"));
        }
        else {
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("not-enough-perms"));
        }
    }
}
