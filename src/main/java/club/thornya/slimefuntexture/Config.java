package club.thornya.slimefuntexture;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Config {

    private static File file;
    private static FileConfiguration configuration;

    @SuppressWarnings("all")
    public static void setup(String path, SlimefunTexture main){
        file = new File(main.getDataFolder(), path);
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getConsoleSender().sendMessage("§c§lConfig do SlimefunTexture não criada.");
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return configuration;
    }

    public static void save() {
        try {
            configuration.save(file);
        }catch (IOException e){
            Bukkit.getConsoleSender().sendMessage("§c§lArquivo não salvo, ERRO");
        }
    }
    public static void savePlayer(String uuid){
        get().set("players."+uuid, true);
        save();
        reload();
    }

    public static void defaultConfigs(){
        get().addDefault("url", "https://www.dropbox.com/s/vno021xgttughiz/thornyapack.zip?dl=1");
        get().addDefault("messages.thanks", "&aObrigado por usar nossa textura.");
        get().addDefault("messages.message_resourcepack", "&aEquipe Source");
        get().addDefault("messages.player_usage", "&cUse: /textura");
        get().addDefault("messages.usage", List.of("&c&lLista de Comandos", "", "&c/textura set <url>", "&c/textura reload <player>", "&c/textura remove <player>"));

        get().options().copyDefaults(true);
        save();
    }

    public static void sendStringList(String path, Player p){
        Config.get().getStringList(path).forEach(s -> p.sendMessage(s.replace("&", "§")));
    }
    public static void sendString(String path, Player p){
            p.sendMessage(Objects.requireNonNull(Config.get().getString(path)).replace("&", "§"));
    }

    public static void reload(){
        configuration = YamlConfiguration.loadConfiguration(file);
    }

}
