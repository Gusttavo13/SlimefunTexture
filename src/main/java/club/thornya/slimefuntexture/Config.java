package club.thornya.slimefuntexture;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private static File file;
    private static FileConfiguration configuration;

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
        get().addDefault("messages.usage", "&cUse: /textura set <url>");

        get().options().copyDefaults(true);
        save();
    }

    public static void reload(){
        configuration = YamlConfiguration.loadConfiguration(file);
    }

}
