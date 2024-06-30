package me.bedrye.coreclaim.Commands;

import me.bedrye.coreclaim.Claim;
import me.bedrye.coreclaim.ClaimController;
import me.bedrye.coreclaim.ConfigController;
import me.bedrye.coreclaim.GUI.Button;
import me.bedrye.coreclaim.GUI.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenuOpenCommand extends AbstractCommand{
    public MainMenuOpenCommand(){
        permission = "coreclaim.player.openmainmenu";
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(hasPermission(sender)){
            Player player = (Player) sender;
            Claim claim = ClaimController.getInstance().canAdministrate(player.getLocation().getBlock(),player);
            if(claim!=null) {
                Menu menu = new Menu(9,ConfigController.getInstance().getFromDictionary("menu-owner")+player.getName());
                ItemStack itemStack = new ItemStack(Material.GOLD_BLOCK);
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(ConfigController.getInstance().getFromDictionary("menu-info-button"));
                itemStack.setItemMeta(meta);
                menu.addButton(new Button(0,itemStack) {
                    @Override
                    public void onCommand(Player player) {
                        InfoCommand command = new InfoCommand();
                        command.execute(player,null);
                        player.closeInventory();
                    }
                });
                itemStack = new ItemStack(Material.EMERALD_BLOCK);
                meta = itemStack.getItemMeta();
                meta.setDisplayName(ConfigController.getInstance().getFromDictionary("menu-list-button"));
                itemStack.setItemMeta(meta);
                menu.addButton(new Button(1,itemStack) {
                    @Override
                    public void onCommand(Player player) {
                        AssociateListCommand command = new AssociateListCommand();
                        command.execute(player,null);
                        player.closeInventory();
                    }
                });

                menu.displayToPlayer(player);
                ClaimController.getInstance().addPlayerMenu(player,menu);
            }
        }
        else {
            sender.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("not-enough-perms"));
        }
    }
}
