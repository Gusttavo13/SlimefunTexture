package club.thornya.slimefuntexture;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandTexture implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command instanceof Player){
            Bukkit.getConsoleSender().sendMessage("§cComando executado somente por jogadores!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("textura")){
            Player p = (Player) sender;
            if(args.length == 0){
                String url = Config.get().getString("url");
                ServerPlayer sp = ((CraftPlayer) p).getHandle();
                sp.connection.send(new ClientboundResourcePackPacket(url, SlimefunTexture.checkHashURL(url), false, Component.nullToEmpty(Objects.requireNonNull(Config.get().getString("messages.message_resourcepack")).replace("&", "§"))));
                Config.savePlayer(p.getUniqueId().toString());
            }
            if(args.length > 0){
                if(p.isOp() || p.hasPermission("slimefuntexture.admin")){
                    if(args.length == 2){
                        if(args[0].equalsIgnoreCase("set")){
                            Config.get().set("url", args[1]);
                            Config.save();
                            Config.reload();
                            p.sendMessage("§bTextura definida com sucesso - §e" + args[1]);
                        }else{
                            p.sendMessage(Objects.requireNonNull(Config.get().getString("messages.usage")).replace("&", "§"));
                        }
                    }else{
                        p.sendMessage(Objects.requireNonNull(Config.get().getString("messages.usage")).replace("&", "§"));
                    }
                }else{
                    p.sendMessage(Objects.requireNonNull(Config.get().getString("messages.player_usage")).replace("&", "§"));
                }
            }
        }
        return false;
    }
}
