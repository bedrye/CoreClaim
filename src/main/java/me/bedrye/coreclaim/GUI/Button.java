package me.bedrye.coreclaim.GUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class Button {

    private int slot;
    private ItemStack itemStack;
    public Button(int slot,ItemStack itemStack){
        this.slot =slot;
        this.itemStack =itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public abstract void onCommand(Player player);

}
