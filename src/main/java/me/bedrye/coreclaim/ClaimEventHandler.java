package me.bedrye.coreclaim;

import me.bedrye.coreclaim.Commands.AbstractCommand;
import me.bedrye.coreclaim.Commands.MainMenuOpenCommand;
import me.bedrye.coreclaim.GUI.Button;
import me.bedrye.coreclaim.GUI.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;





public class ClaimEventHandler implements Listener {


    @EventHandler
    public void GeneralPlaceBlock(BlockPlaceEvent event){
        if(event.getBlock().getType()==ConfigController.getInstance().getClaimMaterial()&&event.getItemInHand().hasItemMeta()){
            if(event.getItemInHand().getItemMeta().getCustomModelData()==2) {
                Claim bool = ClaimController.getInstance().CreateClaim(event.getPlayer(), event.getBlock().getLocation());
                if (bool==null)
                    event.getPlayer().sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("too-close"));
                else {
                    event.getPlayer().sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("new-claim"));
                    TimerTaskManager.getInstance().RunTask(bool,event.getPlayer(),15);
                }
                event.setCancelled(bool==null);
            }
        }
        else {
            if(ClaimController.getInstance().isInClaim(event.getBlock(),event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("cannot-use-claim"));
            }
        }
    }
    @EventHandler
    public void GeneralBreakBlock(BlockBreakEvent event) {
        if(event.getBlock().getType()==ConfigController.getInstance().getClaimMaterial()){
            boolean bool = ClaimController.getInstance().RemoveClaim(event.getBlock().getLocation(),event.getPlayer());
            if(bool) event.getPlayer().sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("cannot-use-claim"));
            event.setCancelled(bool);
        }
        else {
            if(ClaimController.getInstance().isInClaim(event.getBlock(),event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("cannot-use-claim"));
            }
        }
    }
    @EventHandler
    public void GeneralUseBlock(PlayerInteractEvent event) {
        if(event.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if(event.getClickedBlock()==null) return;

        if(event.getClickedBlock().getType()==ConfigController.getInstance().getClaimMaterial()){
            Claim claim = ClaimController.getInstance().canAdministrate(event.getClickedBlock(),event.getPlayer());
            if(claim!=null&&claim.isOriginBlock(event.getClickedBlock().getLocation())){
                AbstractCommand command = new MainMenuOpenCommand();
                command.execute(event.getPlayer(), null);
            }
        }
        else {
            if(ClaimController.getInstance().isInClaim(event.getClickedBlock(),event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("cannot-use-claim"));
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        Menu menu = ClaimController.getInstance().getPlayerMenu(player);
        if(menu!=null) {
            for (Button button : menu.getButtons()) {
                if (button.getSlot() == slot) {
                    button.onCommand(player);
                    event.setCancelled(true);

                }
            }
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        ClaimController.getInstance().removePlayerMenu(player);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ClaimController.getInstance().removePlayerMenu(player);
    }
}
