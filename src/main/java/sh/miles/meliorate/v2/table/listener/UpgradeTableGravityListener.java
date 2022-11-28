package sh.miles.meliorate.v2.table.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import sh.miles.meliorate.v2.table.UpgradeTable;

public class UpgradeTableGravityListener implements Listener {

    @EventHandler
    public void onGravity(EntityChangeBlockEvent e) {
        if (e.getEntity().getType() == EntityType.FALLING_BLOCK && e.getBlock().getType() == Material.ANVIL
                && e.getTo() == Material.AIR && UpgradeTable.isUpgradeTable(e.getBlock())) {
            e.setCancelled(true);
        }
    }

}
