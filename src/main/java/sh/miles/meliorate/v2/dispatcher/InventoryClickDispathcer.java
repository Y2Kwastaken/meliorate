package sh.miles.meliorate.v2.dispatcher;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import sh.miles.megumi.task.TaskBuilder;
import sh.miles.megumi.task.TaskTime;
import sh.miles.megumi.task.TaskType;
import sh.miles.meliorate.v2.Meliorate;
import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;

public class InventoryClickDispathcer implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE){
            return;
        }

        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.getSlot() != e.getWhoClicked().getInventory().getHeldItemSlot()) {
            return;
        }

        TaskBuilder.of(TaskType.DELAY, () -> {
            Modifiers.HASTE_SHARD.execute(e);
        }).delay(1).timeUnit(TaskTime.TICKS).build().run(Meliorate.getInstance());

    }

}
