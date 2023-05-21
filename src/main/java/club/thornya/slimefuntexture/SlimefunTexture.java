package club.thornya.slimefuntexture;

import club.thornya.slimefuntexture.commands.textura.CommandTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Objects;
public final class SlimefunTexture extends JavaPlugin {
    public static SlimefunTexture ST = null;
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

    public static void applyTexture(Player player){
        ServerPlayer sp = ((CraftPlayer) player).getHandle();
        String url = Config.get().getString("url");
        sp.connection.send(new ClientboundResourcePackPacket(url, checkHashURL(url), false, Component.nullToEmpty(Objects.requireNonNull(Config.get().getString("messages.message_resourcepack")).replace("&", "§"))));
    }
    @SuppressWarnings("all")
    public static String checkHashURL(String input) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            InputStream is = new URL(input).openStream();

            try {
                is = new DigestInputStream(is, sha1);

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
