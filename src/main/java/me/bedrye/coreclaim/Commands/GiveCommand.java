package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GiveCommand extends AbstractCommand{
    public GiveCommand(){
        permission = "coreclaim.admin.give";
    }
    @Override
    public void execute(CommandSender sender,String args[]) {
        if(hasPermission(sender)){
            ItemStack itemStack = new ItemStack(ConfigController.getInstance().getClaimMaterial(),1);
            ItemMeta itemmeta = itemStack.getItemMeta();
            itemmeta.setDisplayName(ConfigController.getInstance().getFromDictionary("block-name"));
            itemmeta.setCustomModelData(2);
            itemmeta.addEnchant(Enchantment.LUCK,1,true);
            itemStack.setItemMeta(itemmeta);
            ((Player) sender).getInventory().addItem(itemStack);
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("give-info"));
        }
        else {
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("not-enough-perms"));
        }
    }
}
