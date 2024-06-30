package me.bedrye.coreclaim;

import me.bedrye.coreclaim.GUI.Menu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

import static org.bukkit.plugin.java.JavaPlugin.getProvidingPlugin;


public final class ClaimController {
    private static ClaimController instance;
    private final List<Claim> claimList= new ArrayList<>();
    private final Map<String, Menu> playerMenuMap= new HashMap<>();
    private final ArrayList<Player> playerCooldowns = new ArrayList<>();

    public static ClaimController getInstance(){
        if(instance==null) instance = new ClaimController();
        return  instance;
    }
    private ClaimController(){
    }
    @Nullable
    public Claim CreateClaim(Player player,Location location){
        for (Claim claim:claimList) {
            if(claim.isClaimed(location)) return null;
        }
        Claim claim = new Claim(player,location);
        claimList.add(claim);
        return claim;
    }
    public boolean hasCooldown(Player player){
        return  playerCooldowns.contains(player);

    }
    public void addCooldown(Player player){
        playerCooldowns.add(player);

    }
    public void removeCooldown(Player player){
        playerCooldowns.remove(player);
    }
    public void addPlayerMenu(Player key,Menu value){
            playerMenuMap.put(key.getName(), value);
    }
    @Nullable
    public Menu getPlayerMenu(Player key){
        if (containsPlayerMenu(key)) {
            return playerMenuMap.get(key.getName());
        }
        return null;
    }
    public void removePlayerMenu(Player key){
        if (containsPlayerMenu(key)) {
            playerMenuMap.remove(key.getName());
        }
    }
    public boolean containsPlayerMenu(Player key){
       return playerMenuMap.containsKey(key.getName());
    }
    public boolean RemoveClaim(Location location,Player player){
       Claim clm = null;
       boolean found = false;
        for (Claim claim: claimList) {
            if (claim.isOriginBlock(location)){
                if(claim.AllowedToAdminister(player)) {
                    clm = claim;
                }
                else {found=true;}
                break;
            }
        }

        if (clm!=null){
            claimList.remove(clm);
            player.sendMessage(ConfigController.getInstance().getFromDictionaryPrefix("delete-claim"));
            clm.DeleteFile();
        }
        return found;

    }
    @Nullable
    private Claim isInClaim(Location location){
        for (Claim claim:claimList) {
            if(claim.isInClaim(location)) return claim;
        }
        return null;

    }
    public void UpdateClaims(){
        claimList.clear();
        File[] files =new File(getProvidingPlugin(CoreClaim.class).getDataFolder().getAbsolutePath() + File.separator + "userdata").listFiles();
        if (files!= null) {
            for (File file :
                    files) {
                claimList.add(new Claim(file));

            }
        }

    }

    public boolean isInClaim(Block block,Player player){
        Claim claim = isInClaim(block.getLocation());
        return (claim !=null && (!claim.AllowedToUse(Objects.requireNonNull(player))));
    }
    @Nullable
    public Claim canAdministrate(Block block,Player player){
        Claim claim = isInClaim(block.getLocation());
        if(claim!=null && (claim.AllowedToAdminister(Objects.requireNonNull(player)))) {
                return claim;

        }
        return null ;

    }
}
