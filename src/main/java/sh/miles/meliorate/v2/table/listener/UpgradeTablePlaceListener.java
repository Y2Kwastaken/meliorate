package sh.miles.meliorate.v2.table.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import sh.miles.meliorate.v2.table.UpgradeTable;

public class UpgradeTablePlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        final ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (item != null) {
            if (UpgradeTable.isUpgradeTable(item)) {
                UpgradeTable.setBlockAsUpgradeTable(e.getBlock());
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockBreakEvent e) {
        if (UpgradeTable.isUpgradeTable(e.getBlock())) {
            UpgradeTable.removeBlockAsUpgradeTable(e.getBlock());
        }
    }

}
