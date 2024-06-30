package me.bedrye.coreclaim;

import me.bedrye.coreclaim.InterFaces.IDelayed;
import me.bedrye.coreclaim.InterFaces.IYMLSavable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.plugin.java.JavaPlugin.getProvidingPlugin;

public class Claim implements IYMLSavable, IDelayed {

    private String owner;

    private final List<String> associates;
    private final Location location;

    public Claim(Player owner,Location location){
        this.owner = owner.getName();
        this.location =location;
        associates = new ArrayList<>();
        SaveToYml();
    }
    public Claim(File file){
        FileConfiguration userConfig = YamlConfiguration.loadConfiguration(file);
        owner = userConfig.getString("saved.owner") ;
        location = new Location(
                Bukkit.getWorld(
                        userConfig.getString("saved.world")),
                userConfig.getInt("saved.x"),
                userConfig.getInt("saved.y"),
                userConfig.getInt("saved.z")
        );
        associates =userConfig.getStringList("saved.associates");

    }
    public void ChangeOwner(Player player){
        if(!player.getName().equals(owner)) {
            owner = player.getName();
            SaveToYml();
        }
    }
    public void AddAssociate(String player){
        if(!player.equals(owner)&&!associates.contains(player)) {
            associates.add(player);
            SaveToYml();
        }

    }
    public void RemoveAssociate(String player){
        associates.remove(player);
        SaveToYml();
    }
    public boolean AllowedToAdminister(Player player){
    return player.getName().equals(owner)||player.isOp();
    }
    public boolean AllowedToUse(Player player){
        return AllowedToAdminister(player)||associates.contains(player.getName());
    }
    public boolean isOriginBlock(Location location){
        return this.location.equals(location);


    }
    public boolean isInClaim(Location location){
        int area =ConfigController.getInstance().getClaimSize();
        Location loc1 = this.location.clone().add(area,area,area);
        Location loc2 = this.location.clone().subtract(area,area,area);
        return (((location.getBlockX()< loc1.getBlockX())&&(location.getBlockX()>loc2.getBlockX()))&&
        ((location.getBlockY()< loc1.getBlockY())&&(location.getBlockY()>loc2.getBlockY()))&&
                ((location.getBlockZ()< loc1.getBlockZ())&&(location.getBlockZ()>loc2.getBlockZ())));

    }
    public boolean isClaimed(Location location){
        int area =ConfigController.getInstance().getClaimSize();
        int x = Math.abs(this.location.getBlockX() - location.getBlockX());
        int y = Math.abs(this.location.getBlockY() - location.getBlockY());
        int z = Math.abs(this.location.getBlockZ() - location.getBlockZ());
        return (x<area*2&&y<area*2&&z<area*2);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Owner:" + owner + " Location:" + location + "Associates:");
        for (String assoc:associates) {
            str.append(assoc);
        }
        return str.toString();
    }
    public String getAssociatesList() {
        StringBuilder str = new StringBuilder(ConfigController.getInstance().getFromDictionary("associates-info"));
        for (String assoc:associates) {
            str.append(" - ");
            str.append(assoc);
            str.append('\n');
        }
        return str.toString();
    }
    public String getInfo(){
        return ConfigController.getInstance().getFromDictionary("owner-info") + owner + '\n' +
                ConfigController.getInstance().getFromDictionary("location-info") +
                location.getBlockX() + " X, " +
                location.getBlockY() + " Y, " +
                location.getBlockZ() + " Z;" +
                '\n' +
                ConfigController.getInstance().getFromDictionary("associates-count-info") +
                associates.size();


    }

    @Override
    public void SaveToYml() {
        File userfile = new File(getProvidingPlugin(CoreClaim.class).getDataFolder()
                +File.separator+"userdata"+File.separator+IDGen()+".yml");
        FileConfiguration userConfig = YamlConfiguration.loadConfiguration(userfile);
        userConfig.set("saved.owner",owner);
        userConfig.set("saved.x",location.getBlockX());
        userConfig.set("saved.y",location.getBlockY());
        userConfig.set("saved.z",location.getBlockZ());
        userConfig.set("saved.world",location.getWorld().getName());
        userConfig.set("saved.associates",associates);
        try {
            userConfig.save(userfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void DeleteFile(){
        File userfile = new File(getProvidingPlugin(CoreClaim.class).getDataFolder()
                +File.separator+"userdata"+File.separator+IDGen()+".yml");
        userfile.delete();
    }
    private String IDGen(){
        return location.getBlockX()+"X"+location.getBlockY()+"Y"+location.getBlockX()+"Z"+location.getWorld().getName()+"W";

    }

    private Location add(int x,int y, int z){
        return new Location(location.getWorld(),location.getBlockX()+x,location.getBlockY()+y,location.getBlockZ()+z);
    }
    public void showBorder(Player player){
        int area =ConfigController.getInstance().getClaimSize();
        player.spawnParticle(Particle.VILLAGER_HAPPY, add(0,area,area),area   * 8, area, 0, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY, add(0,-area,area),area   * 8, area, 0, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY, add(0,area,-area),area   * 8, area, 0, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(0,-area,-area),area   * 8, area, 0, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(area,0,area),area   * 8, 0, area, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(-area,0,area),area   * 8, 0, area, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(area,0,-area),area   * 8, 0, area, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(-area,0,-area),area   * 8, 0, area, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(area,area,0),area   * 8, 0, 0, area);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(-area,area,0),area   * 8, 0, 0, area);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(-area,-area,0),area   * 8, 0, 0, area);
        player.spawnParticle(Particle.VILLAGER_HAPPY,add(area,-area,0),area   * 8, 0, 0, area);
    }

    @Override
    public void execute(Player player) {
        showBorder(player);
    }

}
