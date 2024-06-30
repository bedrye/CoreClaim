package me.bedrye.coreclaim;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Objects;

import static org.bukkit.plugin.java.JavaPlugin.getProvidingPlugin;

public class ConfigController {
    private int size;
    private Material material;
    private final HashMap<String,String> text = new HashMap<>();
    private static ConfigController instance;
    public static ConfigController getInstance(){
        if (instance == null) {
            instance = new ConfigController();
        }
        return instance;

    }
    private ConfigController(){}
    public void updateConfig(){
        getProvidingPlugin(CoreClaim.class).saveDefaultConfig();
        getProvidingPlugin(CoreClaim.class).getConfig().options().copyDefaults(true);
        getProvidingPlugin(CoreClaim.class).reloadConfig();
        size = getProvidingPlugin(CoreClaim.class).getConfig().getInt("area-size");
        material = Material.valueOf(Objects.requireNonNull(getProvidingPlugin(CoreClaim.class).getConfig().get("block-material")).toString());
        text.clear();
        addToDictionary("block-name");
        addToDictionary("not-enough-perms");
        addToDictionary("cannot-use-claim");
        addToDictionary("new-claim");
        addToDictionary("delete-claim");
        addToDictionary("add-associate");
        addToDictionary("remove-associate");
        addToDictionary("reload-info");
        addToDictionary("give-info");
        addToDictionary("change-owner");
        addToDictionary("new-owner");
        addToDictionary("too-close");
        addToDictionary("new-associate");
        addToDictionary("owner-info");
        addToDictionary("location-info");
        addToDictionary("associates-count-info");
        addToDictionary("associates-info");
        addToDictionary("menu-info-button");
        addToDictionary("menu-list-button");
        addToDictionary("help-info");
        addToDictionary("cooldown-message");
        addToDictionary("menu-owner");
    }

    public int getClaimSize() {
        return size;
    }

    public Material getClaimMaterial() {
        return material;
    }
    private void addToDictionary(String key){
    if(getProvidingPlugin(CoreClaim.class).getConfig().contains(key))
        text.put(key,getProvidingPlugin(CoreClaim.class).getConfig().getString(key));
    else
        System.out.println("Didn't find a key named:"+key);


    }
    public String getFromDictionary(String key){
        if(text.containsKey(key))
            return text.get(key);
        System.out.println("Didn't find a key named:"+key);
        return key;

    }
    public String getPrefix(){
        return "§2[CoreClaim]§f";

    }
    public String getFromDictionaryPrefix(String key){
        return getPrefix()+getFromDictionary(key);


    }
}
