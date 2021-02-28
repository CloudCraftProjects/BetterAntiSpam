package tk.booky.betterantispam;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class BetterAntiSpamMain extends JavaPlugin implements Listener {

    private static final HashMap<UUID, Long> lastMessageTime = new HashMap<>();
    private static final HashMap<UUID, String> lastMessageText = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || event.getPlayer().hasPermission("bas.bypass")) return;

        long lastMessageTime = BetterAntiSpamMain.lastMessageTime.getOrDefault(event.getPlayer().getUniqueId(), 0L);
        String lastMessageText = BetterAntiSpamMain.lastMessageText.getOrDefault(event.getPlayer().getUniqueId(), "");

        if (System.currentTimeMillis() - lastMessageTime < 1000 || lastMessageText.equalsIgnoreCase(event.getMessage())) {
            event.getPlayer().sendMessage("§7[§bAntiSpam§7]§c Bitte spamme nicht!");
            event.setCancelled(true);
        } else {
            BetterAntiSpamMain.lastMessageTime.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
            BetterAntiSpamMain.lastMessageText.put(event.getPlayer().getUniqueId(), event.getMessage());
        }
    }
}
