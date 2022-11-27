package sh.miles.meliorate.v2.dispatcher;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;

public class BlockBreakDispatcher implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        final ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
        if (tool == null || tool.getType().isAir()) {
            return;
        }

        if (Modifiers.LAVA_CRYSTAL.hasModifier(tool)) {
            Modifiers.LAVA_CRYSTAL.execute(e);
        }
    }

}
