package sh.miles.meliorate.v2.table;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.jeff_media.customblockdata.CustomBlockData;

import sh.miles.megumi.chat.ChatUtil;
import sh.miles.meliorate.ItemBuilder;
import sh.miles.meliorate.v2.Meliorate;

public class UpgradeTable {

    public static final Material UPGRADE_TABLE_MATERIAL = Material.ANVIL;
    private static final NamespacedKey identifierKey = new NamespacedKey(Meliorate.getInstance(), "upgrade_table");
    private static final ItemStack item;

    static {
        ItemStack temp = ItemBuilder.builder()
                .material(UPGRADE_TABLE_MATERIAL)
                .name(ChatUtil.style("<word:light_purple>Upgrade Table"))
                .lore("Sometimes, you just need a little more power.")
                .glow(true)
                .build().make();
        final ItemMeta meta = temp.getItemMeta();
        meta.getPersistentDataContainer().set(identifierKey, PersistentDataType.BYTE, (byte) 1);
        temp.setItemMeta(meta);
        item = temp.clone();
    }

    private UpgradeTable() {
    }

    public static boolean isUpgradeTable(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(identifierKey,
                PersistentDataType.BYTE);
    }

    public static boolean isUpgradeTable(Block block) {
        return block != null && block.getType() == UPGRADE_TABLE_MATERIAL
                && new CustomBlockData(block, Meliorate.getInstance()).has(identifierKey, PersistentDataType.BYTE);
    }

    public static final ItemStack getUpgradeTableItem() {
        return item.clone();
    }

    public static final void setBlockAsUpgradeTable(Block block) {
        new CustomBlockData(block, Meliorate.getInstance()).set(identifierKey, PersistentDataType.BYTE, (byte) 1);
    }

    public static final void removeBlockAsUpgradeTable(Block block) {
        new CustomBlockData(block, Meliorate.getInstance()).remove(identifierKey);
    }

    public static final void registerUpgradeTableRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Meliorate.getInstance(), "upgrade_table_recipe"),
                item);
        recipe.shape("LHL", "NAN", "IOI");
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('H', Material.NETHERITE_AXE);
        recipe.setIngredient('N', Material.NETHERITE_SCRAP);
        recipe.setIngredient('A', Material.ANVIL);
        recipe.setIngredient('L', Material.LAVA_BUCKET);
        recipe.setIngredient('O', Material.OBSIDIAN);

        Bukkit.getServer().addRecipe(recipe);
    }
}
