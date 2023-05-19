package club.thornya.slimefuntexture;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Objects;

public final class SlimefunTexture extends JavaPlugin {

    public static SlimefunTexture ST = null;
    public static Config c = null;

    @Override
    public void onEnable() {
        ST = this;
        Config.setup("config.yml", this);
        saveDefaultConfig();
        saveConfig();
        Config.defaultConfigs();
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(String.format("§bSlimefun§aTexture §biniciado - §e%s", Config.get().getString("url")));
        Bukkit.getConsoleSender().sendMessage(" ");

        Objects.requireNonNull(Bukkit.getPluginCommand("textura")).setExecutor(new CommandTexture());
        Bukkit.getPluginManager().registerEvents(new EventResourcePack(), this);

    }

    public static String checkHashURL(String input) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            InputStream is = new URL(input).openStream();

            try {
                is = new DigestInputStream(is, sha1);

                int b;

                byte[] ignoredBuffer = new byte[8 * 1024]; // Up to 8K per read
                while (is.read(ignoredBuffer) > 0) {}
            } finally {
                is.close();
            }
            byte[] digest = sha1.digest();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                sb.append(
                        Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(
                                1));
            }
            return sb.toString();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
