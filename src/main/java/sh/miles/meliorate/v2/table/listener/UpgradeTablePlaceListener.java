package sh.miles.meliorate.v2.table.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import sh.miles.meliorate.v2.table.UpgradeTable;

public class UpgradeTablePlaceListener implements Listener {
    
    @EventHandler
    public void onBlockPlace(BlockBreakEvent e){
        if(UpgradeTable.isUpgradeTable(e.getBlock())){
            e.setCancelled(true);
        }
    }

}
