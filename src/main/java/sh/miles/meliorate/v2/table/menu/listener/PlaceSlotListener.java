package sh.miles.meliorate.v2.table.menu.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import sh.miles.megumi.chat.ChatUtil;
import sh.miles.megumi.menu.item.ButtonAction;
import sh.miles.megumi.task.TaskBuilder;
import sh.miles.megumi.task.TaskTime;
import sh.miles.megumi.task.TaskType;
import sh.miles.meliorate.ItemBuilder;
import sh.miles.meliorate.v2.Meliorate;

public class PlaceSlotListener implements ButtonAction {

    public static final Sound ITEM_LOCK_SOUND = Sound.BLOCK_BEACON_ACTIVATE;
    public static final float ITEM_LOCK_VOLUME = 0.75f;
    public static final float ITEM_LOCK_PITCH = 0.5f;

    @Getter
    private static final ItemStack toolPlaceHolder = ItemBuilder.builder()
            .material(Material.ORANGE_STAINED_GLASS_PANE)
            .name(ChatUtil.style("<word:gray>Tool Slot"))
            .lore(ChatUtil.style("<word:gray>Place your tool here"))
            .glow(true)
            .build().make();
    @Getter
    private static final ItemStack modifierPlaceHolder = ItemBuilder.builder()
            .material(Material.LIME_STAINED_GLASS_PANE)
            .name(ChatUtil.style("<word:gray>Modifier Slot"))
            .lore(ChatUtil.style("<word:gray>Place your modifier here"))
            .glow(true)
            .build().make();

    @Override
    public void execute(Player player, InventoryClickEvent e) {
        final ItemStack tool = e.getCursor();

        final ItemStack clicked = e.getCurrentItem();

        if (clicked != null && !(clicked.equals(toolPlaceHolder) || clicked.equals(modifierPlaceHolder))) {
            e.setCancelled(false);
            if (e.getClick() == ClickType.RIGHT && clicked.getAmount() > 1) {
                return;
            }
            TaskBuilder.of(TaskType.DELAY, () -> {
                if (e.getSlot() == 11) {
                    e.getInventory().setItem(11, toolPlaceHolder);
                } else {
                    e.getInventory().setItem(15, modifierPlaceHolder);
                }
            }).delay(1).timeUnit(TaskTime.TICKS).build().run(Meliorate.getInstance());
            return;
        }

        if (tool.getType() == Material.AIR) {
            return;
        }

        player.setItemOnCursor(new ItemStack(Material.AIR));
        e.getInventory().setItem(e.getSlot(), tool);
        player.playSound(player.getLocation(), ITEM_LOCK_SOUND, ITEM_LOCK_VOLUME, ITEM_LOCK_PITCH);
    }

}
