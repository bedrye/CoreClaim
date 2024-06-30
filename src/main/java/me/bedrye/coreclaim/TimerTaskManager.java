package me.bedrye.coreclaim;

import me.bedrye.coreclaim.InterFaces.IDelayed;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class TimerTaskManager {
    private  static TimerTaskManager instance;
    private CoreClaim providingPlugin;

    private TimerTaskManager(){

    }
    public static TimerTaskManager getInstance(){
        if(instance==null){
            instance = new TimerTaskManager();
        }
        return instance;
    }
    public void setProvidingPlugin(CoreClaim claim){

        providingPlugin = claim;
    }
    public void RunTask(IDelayed obj, Player player,int delay){
        new BukkitRunnable(){
            int i=0;
            @Override
            public void run() {
                i++;
                obj.execute(player);
                if(i==delay){cancel();}
            }
        }.runTaskTimerAsynchronously(
        providingPlugin, 1L,30L);

    }
}
