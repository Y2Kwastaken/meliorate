package sh.miles.meliorate.v2.table.menu.listener;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import sh.miles.megumi.chat.ChatUtil;
import sh.miles.megumi.menu.item.ButtonAction;
import sh.miles.meliorate.ItemBuilder;
import sh.miles.meliorate.v2.modifier.MeliorateModifier;
import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;
import sh.miles.meliorate.v2.table.menu.UpgradeTableMenu;

public class ExecuteListener implements ButtonAction {

        @Getter
        private static final ItemStack executePlaceHolder = ItemBuilder.builder()
                        .material(Material.NETHERITE_AXE)
                        .name(ChatUtil.style("<word:light_purple>Smithers Hammer"))
                        .lore(ChatUtil.style("<word:gray>Click to smith"))
                        .build().make();

        @Override
        public void execute(Player player, InventoryClickEvent e) {

                final ItemStack tool = e.getInventory().getItem(11);
                final ItemStack modifier = e.getInventory().getItem(15);

                if (e.getInventory().getItem(22) != null) {
                        if (!e.getInventory().getItem(22).getType().isAir()) {
                                player.playSound(player.getLocation(), UpgradeTableMenu.FAIL_SOUND,
                                                UpgradeTableMenu.FAIL_VOLUME,
                                                UpgradeTableMenu.FAIL_PITCH);
                                return;
                        }
                }

                if (tool.equals(PlaceSlotListener.getToolPlaceHolder())
                                || modifier.equals(PlaceSlotListener.getModifierPlaceHolder())) {
                        player.playSound(player.getLocation(), UpgradeTableMenu.FAIL_SOUND,
                                        UpgradeTableMenu.FAIL_VOLUME,
                                        UpgradeTableMenu.FAIL_PITCH);
                        return;
                }

                Optional<MeliorateModifier> possibleModifier = Modifiers.getPossibleModifier(modifier);
                if (possibleModifier.isEmpty()) {
                        player.playSound(player.getLocation(), UpgradeTableMenu.FAIL_SOUND,
                                        UpgradeTableMenu.FAIL_VOLUME,
                                        UpgradeTableMenu.FAIL_PITCH);
                        return;
                }

                MeliorateModifier meliorateModifier = possibleModifier.get();
                if (!meliorateModifier.apply(tool)) {
                        player.playSound(player.getLocation(), UpgradeTableMenu.FAIL_SOUND,
                                        UpgradeTableMenu.FAIL_VOLUME,
                                        UpgradeTableMenu.FAIL_PITCH);
                        return;
                }

                if (modifier.getAmount() > 1) {
                        modifier.setAmount(modifier.getAmount() - 1);
                } else {
                        e.getInventory().setItem(15, PlaceSlotListener.getModifierPlaceHolder());
                }

                e.getInventory().setItem(11, PlaceSlotListener.getToolPlaceHolder());
                e.getInventory().setItem(22, tool);

                player.playSound(player.getLocation(), UpgradeTableMenu.SUCCESS_SOUND, UpgradeTableMenu.SUCCESS_VOLUME,
                                UpgradeTableMenu.SUCCESS_PITCH);
        }

}
