package maxsimus.complexbans.bungeecord.listeners;

import maxsimus.complexbans.bungeecord.util.Format;
import maxsimus.complexbans.bungeecord.util.db.DBCache;
import maxsimus.complexbans.bungeecord.util.Punishment;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent e) {
        Punishment ban = DBCache.getBanned(e.getConnection().getName());
        if(ban != null) {
            e.setCancelled(true);
            e.setCancelReason(Format.ban(ban.getReason(), ban.getExpiration()));
        }

        ban = DBCache.getIpBanned(e.getConnection().getAddress().getHostName());
        if(ban != null) {
            e.setCancelled(true);
            e.setCancelReason(Format.ipBan(ban.getReason(), ban.getExpiration()));
        }
    }
}
