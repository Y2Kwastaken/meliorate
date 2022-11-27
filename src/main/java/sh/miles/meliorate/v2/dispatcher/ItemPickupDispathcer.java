package sh.miles.meliorate.v2.dispatcher;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import sh.miles.megumi.task.TaskBuilder;
import sh.miles.megumi.task.TaskTime;
import sh.miles.megumi.task.TaskType;
import sh.miles.meliorate.v2.Meliorate;
import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;

public class ItemPickupDispathcer implements Listener {

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {

        if (!(e.getEntity() instanceof Player player)) {
            return;
        }

        final ItemStack item = e.getItem().getItemStack();

        if(item == null || item.getType().isAir()){
            return;
        }

        TaskBuilder.of(TaskType.DELAY, () -> {

            final ItemStack newItem = player.getInventory().getItemInMainHand();
            if (!newItem.isSimilar(item)) {
                return;
            }
            
            if(Modifiers.HASTE_SHARD.hasModifier(item)){
                Modifiers.HASTE_SHARD.execute(e);
            }

        }).delay(1).timeUnit(TaskTime.TICKS).build().run(Meliorate.getInstance());
    }

}
