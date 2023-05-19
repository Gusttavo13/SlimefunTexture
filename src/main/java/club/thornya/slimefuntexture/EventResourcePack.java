package club.thornya.slimefuntexture;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.Objects;

public class EventResourcePack implements Listener {

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent e){
        if(e.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
            e.getPlayer().sendMessage(Objects.requireNonNull(Config.get().getString("messages.thanks")).replace("&", "§"));

        }
        if(e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){
            e.getPlayer().sendMessage("§cOPS! Parece que ocorreu um erro.");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(Config.get().contains("players."+e.getPlayer().getUniqueId())){
            if(Config.get().getBoolean("players."+e.getPlayer().getUniqueId())) {
                ServerPlayer sp = ((CraftPlayer) e.getPlayer()).getHandle();
                String url = Config.get().getString("url");
                sp.connection.send(new ClientboundResourcePackPacket(url, SlimefunTexture.checkHashURL(url), false, Component.nullToEmpty(Objects.requireNonNull(Config.get().getString("messages.message_resourcepack")).replace("&", "§"))));
            }
        }

    }

}
