package sh.miles.meliorate.v2.table.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import sh.miles.megumi.menu.MenuSession;
import sh.miles.meliorate.v2.table.UpgradeTable;
import sh.miles.meliorate.v2.table.menu.UpgradeTableMenu;

public class UpgradeTableInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getPlayer().isSneaking()) {
            return;
        }

        if (UpgradeTable.isUpgradeTable(e.getClickedBlock())) {
            e.setCancelled(true);
            MenuSession.start(new UpgradeTableMenu(), e.getPlayer());
        }

    }
}
