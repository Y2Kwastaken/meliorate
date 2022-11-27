package sh.miles.meliorate.v2.table.menu;

import java.util.stream.IntStream;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import sh.miles.megumi.chat.ChatUtil;
import sh.miles.megumi.menu.Menu;
import sh.miles.megumi.menu.item.Button;
import sh.miles.megumi.menu.item.ButtonAction;
import sh.miles.meliorate.ItemBuilder;
import sh.miles.meliorate.v2.table.menu.listener.ExecuteListener;
import sh.miles.meliorate.v2.table.menu.listener.PlaceSlotListener;

public class UpgradeTableMenu extends Menu {

    public static final int UPGRADE_TABLE_SIZE = 27;
    public static final String UPGRADE_TABLE_TITLE = ChatUtil.style("<word:light_purple>Upgrade Table");

    public static final Sound FAIL_SOUND = Sound.BLOCK_ANVIL_BREAK;
    public static final float FAIL_VOLUME = 0.5f;
    public static final float FAIL_PITCH = 0.1f;

    public static final Sound SUCCESS_SOUND = Sound.BLOCK_SMITHING_TABLE_USE;
    public static final float SUCCESS_VOLUME = 0.2f;
    public static final float SUCCESS_PITCH = 0.01f;

    public UpgradeTableMenu() {
        super(UPGRADE_TABLE_TITLE, UPGRADE_TABLE_SIZE);
    }

    @Override
    public void init() {
        final ItemStack background0 = ItemBuilder.builder()
                .material(Material.BLACK_STAINED_GLASS_PANE)
                .name(" ")
                .build().make();

        fill(new Button(background0, ButtonAction.EMPTY), IntStream.range(0, UPGRADE_TABLE_SIZE));
        // 11(tool), 13(execute), 15(modifier), 22(output)
        final PlaceSlotListener listener = new PlaceSlotListener();
        setItem(11, new Button(PlaceSlotListener.getToolPlaceHolder(), listener));
        setItem(15, new Button(PlaceSlotListener.getModifierPlaceHolder(), listener));
        setItem(13, new Button(ExecuteListener.getExecutePlaceHolder(), new ExecuteListener()));

        setItem(22, new Button(new ItemStack(Material.AIR), ButtonAction.ALLOW_CLICK));

    }

    @Override
    public void onClose() {
        addOrDrop(getViewer(), getInventory().getItem(11));
        addOrDrop(getViewer(), getInventory().getItem(15));
        addOrDrop(getViewer(), getInventory().getItem(22));
    }

    private void addOrDrop(Player player, final ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        if (item.equals(PlaceSlotListener.getToolPlaceHolder())
                || item.equals(PlaceSlotListener.getModifierPlaceHolder())) {
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }

}
