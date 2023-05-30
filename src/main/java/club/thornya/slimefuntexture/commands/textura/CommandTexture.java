package club.thornya.slimefuntexture.commands.textura;

import club.thornya.slimefuntexture.Config;
import club.thornya.slimefuntexture.SlimefunTexture;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTexture implements CommandExecutor {
    @Override

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (command instanceof Player){
            Bukkit.getConsoleSender().sendMessage("§cComando executado somente por jogadores!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("textura")){
            Player p = (Player) sender;
            if(args.length == 0){
                SlimefunTexture.applyTexture(p);
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
                        }else if(args[0].equalsIgnoreCase("reload")){
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if(target != null){
                                if(target.isOnline()){
                                    if(Config.get().contains("players."+target.getUniqueId())) {
                                        if (Config.get().getBoolean("players." + target.getUniqueId())) {
                                            SlimefunTexture.applyTexture(target);
                                            p.sendMessage("§aVocê recarregou a textura de §c" + target.getName());
                                        }else {
                                            p.sendMessage("§cEsse jogador não aceitou a textura.");
                                        }
                                    }else {
                                        p.sendMessage("§cEsse jogador não aceitou a textura.");
                                    }
                                }else{
                                    p.sendMessage("§cEsse jogador está offline.");
                                }
                            }else{
                                p.sendMessage("§cEsse jogador está offline.");
                            }
                        }else if(args[0].equalsIgnoreCase("remove")){
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if(target != null){
                                if(target.isOnline()){
                                    if(Config.get().contains("players."+target.getUniqueId())) {
                                        if (Config.get().getBoolean("players." + target.getUniqueId())) {
                                            Config.get().set("players." + target.getUniqueId(), false);
                                            Config.save();
                                            Config.reload();
                                            p.sendMessage("§aVocê removeu a textura de §c" + target.getName());
                                        }else {
                                            p.sendMessage("§cEsse jogador já está com a textura desativada.");
                                        }
                                    }else {
                                        p.sendMessage("§cEsse jogador não aceitou a textura.");
                                    }
                                }else{
                                    p.sendMessage("§cEsse jogador está offline.");
                                }
                            }else{
                                p.sendMessage("§cEsse jogador está offline.");
                            }
                        }else{
                            Config.sendStringList("messages.usage", p);
                        }
                    }else{
                        Config.sendStringList("messages.usage", p);
                    }
                }else{
                    Config.sendString("messages.player_usage", p);
                }
            }
        }
        return false;
    }
}
