package club.thornya.slimefuntexture;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class EventResourcePack implements Listener {

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent e){
        if(e.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
            Config.sendString("messages.thanks", e.getPlayer());
        }
        if(e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){
            e.getPlayer().sendMessage("§cOPS! Parece que ocorreu um erro.");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(Config.get().contains("players."+e.getPlayer().getUniqueId())){
            if(Config.get().getBoolean("players."+e.getPlayer().getUniqueId())) {
                SlimefunTexture.applyTexture(e.getPlayer());
            }
        }

    }

}
