package club.thornya.slimefuntexture.commands.textura;

import club.thornya.slimefuntexture.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabTexture implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("textura")){
            if(args.length == 1){
                return List.of("set", "reload", "remove");
            }else if(args.length == 2){
                if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("remove")) {
                    List<String> players = new ArrayList<>();
                    Bukkit.getOnlinePlayers().forEach(p -> {
                        if (Config.get().contains("players." + p.getUniqueId())) {
                            if (Config.get().getBoolean("players." + p.getUniqueId())) {
                                players.add(p.getName());
                            }
                        }
                    });
                    return players;
                }
                if(args[0].equalsIgnoreCase("set")){
                    return List.of("<url>");
                }
            }else {
                return List.of();
            }
        }
        return null;
    }
}
