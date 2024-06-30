package me.bedrye.coreclaim.GUI;

import me.bedrye.coreclaim.CoreClaim;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private int size;
    private List<Button> buttons;
    private String title;
    public Menu(int size,String title){
        this.size = size;
        this.title = title;
        buttons = new ArrayList<>();
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void displayToPlayer(Player player){
        final Inventory inventory = Bukkit.createInventory(player, size,title);
        for (Button button:buttons) {
            inventory.setItem(button.getSlot(),button.getItemStack());
        }
        player.openInventory(inventory);

    }

}
